package com.example.converse.payload.response;

import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoResponse {
    
    private long id;

    private String username;

    private String password;

    private List<String> roles;
    

    public UserInfoResponse(Long id, String username,List<String> roles) {
		this.id = id;
		this.username = username;
		this.roles = roles;
	}
}