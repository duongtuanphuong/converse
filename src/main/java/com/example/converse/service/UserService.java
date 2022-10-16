package com.example.converse.service;

import java.util.Optional;

import com.example.converse.entity.User;
import com.example.converse.payload.request.ChangePasswordReq;
import com.example.converse.payload.request.CreateUserReq;
import com.example.converse.payload.request.UpdateProfileReq;

public interface UserService {
    
    User register(CreateUserReq req);

    User changePassword(User user,ChangePasswordReq req);

    User updateProfile(User user,UpdateProfileReq req);

    User getUser(String username);
}
