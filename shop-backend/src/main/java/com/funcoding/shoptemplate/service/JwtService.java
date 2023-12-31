package com.funcoding.shoptemplate.service;

import com.funcoding.shoptemplate.dao.UserDao;
import com.funcoding.shoptemplate.entity.JwtRequest;
import com.funcoding.shoptemplate.entity.JwtResponse;
import com.funcoding.shoptemplate.entity.User;
import com.funcoding.shoptemplate.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class JwtService implements UserDetailsService {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDao userDao;

    @Autowired
    private AuthenticationManager authenticationManager;

    public JwtResponse createJwtToken(JwtRequest jwtRequest) throws Exception {

        String userName = jwtRequest.getUserName();
        String userPassword = jwtRequest.getUserPassword();

        authenticate(userName, userPassword);

        UserDetails userDetails = loadUserByUsername(userName);

        String newGeneratedToken = jwtUtil.generateToken(userDetails);

        User user = userDao.findById(userName).get();
        return new JwtResponse(user, newGeneratedToken);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findById(username).get();

        if (user != null) {

            Set<SimpleGrantedAuthority> authorities = getAuthority(user);

            UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                    user.getUserName(),
                    user.getUserPassword(),
                    authorities
            );

            return userDetails;
        } else {

            throw new UsernameNotFoundException("User not found with username: " + username);
        }


    }

    private Set getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();

        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName()));
        });
        return authorities;
    }

    private void authenticate(String userName, String userPassword) throws Exception {
        try {

            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userName, userPassword);

            authenticationManager.authenticate(token);

        } catch (DisabledException e) {

            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {

            throw new Exception("INVALID_CREDENTIALS", e);
        } catch (Exception e) {

            throw new Exception("Exception", e);
        }
    }
}
