

      $('.register-button').on("click",function(){
        let name = $('#register-name').val();
        let email = $('#register-email').val();
        let password = $('#register-password').val();

        req = {
          username: name,
          email : email,
          password : password
        }
        var myJSON = JSON.stringify(req);
        $('#login_modal').modal('hide');
        $.ajax({
          url: '/api/auth/register',
          type: 'POST',
          data: myJSON,
          contentType: "application/json; charset=utf-8",
          success: function(data){
            $('.modal').modal('hide');
            toastr.success('Đăng ký thành công');
          }
        })
          
      })

      $('.login-button').on("click",function (){
        let name = $('#login-name').val();
        let password = $('#login-password').val();
        req = {
          username : name,
          password : password,
        }
        var myJSON = JSON.stringify(req);
        $.ajax({
          url: '/api/auth/login',
          type: 'POST',
          data: myJSON,
          contentType: "application/json; charset=utf-8",
          success: function(data){
            $('.modal').modal('hide');
            toastr.success('Đăng nhập thành công');
            signedValidate(true,name);
          }
        })
      })

      $('.add-to-cart button').on("click",function(){
        let id = $('#product-detail .product-id').text();
        let quantity = $('#product-detail .input-number').val();
        req = {
          productId : id,
          quantity : quantity
        }
        var myJSON = JSON.stringify(req);
        $.ajax({  
          url: '/client/api/add-to-cart',
          type: 'POST',
          data: myJSON,
          contentType: "application/json; charset=utf-8",
          success: function(data){
            toastr.success("Thêm giỏ hàng thành công");
            $('#product-detail').modal('hide');
          }
        })
      })


      function productDetail(btn){
        $('#product-detail .product-gallery .sub-image').children().remove();
        $('.input-number').val(1)
        let id = $(btn).data('id');
        let html = ``;
        $('.product-image[data-id = '+id+'] div img').each(function(){
          let url = $(this).attr('src');
          html += `<div onclick="getImage(this)"><img src="${url}" alt="product img"></div>`
        })
        $.ajax({
          url: '/api/product/' + id,
          type: 'GET',
          contentType: "application/json; charset = utf-8",
          success: function(data){
            $('#product-detail .product-id').text(data.id);
            $('#product-detail .product-name').text(data.name);
            $('#product-detail .product-price').text(data.price + '₫');
            $('#product-detail .product-category a').text(data.category.name).attr('href',"/client/category/" + data.category.id);
            $('#product-detail .product-gallery .sub-image').append(`${html}`)
            $('.sub-image div:first').addClass("choose");
            let u = $('.choose img').attr("src");
            $('.product-gallery .main-image img').attr("src",u);
          }
        })

        
        $('#product-detail').modal('show');
      }
      
      $('.input-number-product .minus-button').on("click",function(){
        let current = parseInt($('.input-number').val());
        current --;
        if(current <1){
          current =1
        }
        $('.input-number').val(current);
      })
      $('.input-number-product .plus-button').on("click",function(){
        let current = parseInt($('.input-number').val());
        current ++;
        $('.input-number').val(current);
      })


      function getImage(btn){
        $('.sub-image .choose').toggleClass("choose");
        $(btn).toggleClass("choose");
        let url = $('.choose img').attr("src");
        $('.main-image img').attr("src",url);
    }

    function signedValidate(status = false,name =''){
      if(status == true){
        isLogined = true;
        let signedLink = `<a id="account-setting" class="nav-link account-setting" href="/tai-khoan">Xin chào ${name}</a>`;
        $('.account-setting').replaceWith(signedLink);
      }else{
        isLogined = false;
        let notSignedLink = `<a class="nav-link account-setting" href="" data-toggle="modal" data-target="#signInSignUp">Tài khoản</a>`;
        $('.account-setting').replaceWith(notSignedLink);
      }

    }

    