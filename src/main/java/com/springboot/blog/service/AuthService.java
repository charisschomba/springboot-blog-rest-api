package com.springboot.blog.service;

import com.springboot.blog.payload.LoginDto;
import com.springboot.blog.payload.RegisterDto;

public interface AuthService {
    String Login(LoginDto loginDto);
    String SignUp(RegisterDto userDto);
}
