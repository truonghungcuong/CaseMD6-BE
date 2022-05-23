package com.codegym.service.user;

import com.codegym.model.entity.Order;
import com.codegym.model.entity.user.User;
import com.codegym.service.IGeneralService;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;


public interface IUserService extends IGeneralService<User>, UserDetailsService {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);

}