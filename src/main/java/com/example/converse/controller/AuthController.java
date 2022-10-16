package com.example.converse.controller;

import java.net.http.HttpHeaders;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.converse.entity.ShoppingCart;
import com.example.converse.entity.User;
import com.example.converse.payload.request.CreateUserReq;
import com.example.converse.payload.request.LoginReq;
import com.example.converse.payload.response.MessageResponse;
import com.example.converse.repository.UserRepository;
import com.example.converse.sercurity.CustomUserDetails;
import com.example.converse.sercurity.JwtTokenUtil;
import com.example.converse.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    public static final int MAX_AGE_COOKIE = 7 * 24 * 60 * 60;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@Valid @RequestBody CreateUserReq req) {

        if (userRepository.existsByUsername(req.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }
        if (userRepository.existsByEmail(req.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }
        User user = userService.register(req);
        return ResponseEntity.ok(new MessageResponse("User register successfully!"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginReq req, HttpServletResponse response,HttpSession session) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(req.getUsername(),
                        req.getPassword()));

        String token = jwtTokenUtil.generateToken((CustomUserDetails) authentication.getPrincipal());

        Cookie cookie = new Cookie("JWT_TOKEN", token);
        cookie.setMaxAge(MAX_AGE_COOKIE);
        cookie.setPath("/");
        response.addCookie(cookie);
        
        session.setAttribute("authentication", authentication);
        session.setMaxInactiveInterval(MAX_AGE_COOKIE);

        return ResponseEntity.ok(authentication);
    }

}
