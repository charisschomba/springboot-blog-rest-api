package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Role;
import com.springboot.blog.entity.User;
import com.springboot.blog.exception.BlogApiException;
import com.springboot.blog.payload.LoginDto;
import com.springboot.blog.payload.RegisterDto;
import com.springboot.blog.repository.RoleRepository;
import com.springboot.blog.repository.UserRepository;
import com.springboot.blog.security.JwtTokenProvider;
import com.springboot.blog.service.AuthService;
import com.springboot.blog.utils.PasswordGeneratorEncoder;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImp implements AuthService {

    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private JwtTokenProvider jwtTokenProvider;
    public AuthServiceImp(
            AuthenticationManager authenticationManager,
            UserRepository userRepository,
            RoleRepository roleRepository,
            JwtTokenProvider jwtTokenProvider
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    ModelMapper mapper = new ModelMapper();
    @Override
    public String Login(LoginDto loginDto) {
       Authentication authentication = authenticationManager
               .authenticate(new UsernamePasswordAuthenticationToken(
                       loginDto.getUsernameOrEmail(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.generateToken(authentication);
       return token;
    }

    @Override
    public String SignUp(RegisterDto userDto) {
        if(userRepository.existsByUsername(userDto.getUsername())) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Username is already taken");
        }
        if(userRepository.existsByEmail(userDto.getEmail())) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Email is already taken");
        }
        User user = mapper.map(userDto, User.class);
        String hashPassword = PasswordGeneratorEncoder.encodePassword(user.getPassword());
        user.setPassword(hashPassword);
        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName("ROLE_USER").get();
        roles.add(userRole);
        user.setRoles(roles);
        userRepository.save(user);
        return "User created successfully";
    }
}
