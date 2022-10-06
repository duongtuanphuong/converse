

      $('.register-button').click(function(){
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

      $('.login-button').click(function (){
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
        $('#product-detail .product-image img').remove();
        $('.input-number').val(1)
        let id = $(btn).data('id');
        let listUrl = [];
        let html = ``;
        $('.product-image[data-id = '+id+'] div img').each(function(){
          let url = $(this).attr('src');
          html += `<img src="${url}" alt="product img">`
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
            $('#product-detail .product-image').append(`${html}`)
          }
        })
        $('#product-detail').modal('show');
      }
      
      $('.input-number-product .minus-button').click(function(){
        let current = parseInt($('.input-number').val());
        current --;
        if(current <1){
          current =1
        }
        $('.input-number').val(current);
      })
      $('.input-number-product .plus-button').click(function(){
        let current = parseInt($('.input-number').val());
        current ++;
        $('.input-number').val(current);
      })