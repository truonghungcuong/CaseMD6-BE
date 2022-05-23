package com.codegym.controller.auth;

import com.codegym.model.entity.auth.JwtResponse;
import com.codegym.model.entity.user.Role;
import com.codegym.model.entity.auth.ErrorMessage;
import com.codegym.model.entity.auth.UserRegisterForm;
import com.codegym.model.entity.user.User;
import com.codegym.service.auth.JwtService;
import com.codegym.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class AuthController {
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    IUserService userService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtService jwtService;

    @PostMapping ("/register")
    public ResponseEntity<?> register (@RequestBody UserRegisterForm userRegisterForm){
        if (!userRegisterForm.confirmPasswordMatch()) {
            ErrorMessage errorMessage = new ErrorMessage("Mật khẩu nhập lại không khớp!");
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }
        Optional<User> findUser = userService.findByUsername(userRegisterForm.getUsername());
        if (findUser.isPresent()) {
            ErrorMessage errorMessage = new ErrorMessage("Tên đăng nhập đã tồn tại!");
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }
        Optional<User> findUserByEmail = userService.findByEmail(userRegisterForm.getEmail());
        if (findUserByEmail.isPresent()) {
            ErrorMessage errorMessage = new ErrorMessage("Địa chỉ email đã tồn tại!");
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }
        User user = new User();
        user.setEmail(userRegisterForm.getEmail());
        user.setUsername(userRegisterForm.getUsername());
        user.setFullName(userRegisterForm.getFullName());
        user.setAddress(userRegisterForm.getAddress());
        String encodedPassword = passwordEncoder.encode(userRegisterForm.getPassword());
        user.setPassword(encodedPassword);
        Role role = new Role(3L, Role.ROLE_CUSTOMER);
        user.setImage("user-default.jpg");
        user.setRole(role);

        return new ResponseEntity<>(userService.save(user), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user){
//        String inputUsername = user.getUsername();
        String inputEmail = user.getEmail();
        String inputPassword = user.getPassword();
//        Optional<User> findUser = userService.findByUsername(inputUsername);
        Optional<User> findUser = userService.findByEmail(inputEmail);

        if (!findUser.isPresent()) {
            ErrorMessage errorMessage = new ErrorMessage("Tài khoản không tồn tại");
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }

        boolean matchPassword = passwordEncoder.matches(inputPassword, findUser.get().getPassword());
        if (!matchPassword) {
            ErrorMessage errorMessage = new ErrorMessage("Mật khẩu không đúng");
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(findUser.get().getUsername(), inputPassword)); // tạo đối tượng Authentication
        SecurityContextHolder.getContext().setAuthentication(authentication);  // lưu đối tượng Authentication vào ContextHolder
        String jwt = jwtService.generateTokenLogin(authentication);  // tạo token từ đối tượng Authentication
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User currentUser = findUser.get();

        JwtResponse jwtResponse = new JwtResponse();
        jwtResponse.setId(currentUser.getId());
        jwtResponse.setToken(jwt);
        jwtResponse.setUsername(userDetails.getUsername());
        jwtResponse.setRoles(userDetails.getAuthorities());
        jwtResponse.setEmail(inputEmail);
        return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
    }
}
