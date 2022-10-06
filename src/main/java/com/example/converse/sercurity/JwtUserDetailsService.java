package com.example.converse.sercurity;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.converse.entity.User;
import com.example.converse.repository.UserRepository;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       User user = userRepository.findByUsername(username); 
       if(user != null){
           return new CustomUserDetails(user);
       }
       else{
           throw new UsernameNotFoundException("Username " + username + " không tồn tại ");
       }
    }
    
}
