package com.coursework.clickboardbackend.Services;

import com.coursework.clickboardbackend.Models.DTO.User.ApiResponse;
import com.coursework.clickboardbackend.Models.DTO.User.SignUpDTO;
import com.coursework.clickboardbackend.Models.DTO.User.UpdateSettingsDTO;
import com.coursework.clickboardbackend.Models.DTO.User.UserUpdateDTO;
import com.coursework.clickboardbackend.Models.Database.User.User;
import com.coursework.clickboardbackend.Repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AdService adService;
    @Lazy
    @Autowired
    private PasswordEncoder passwordEncoder;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public void registerUser(SignUpDTO signUpDTO) {
        User newUser = new User();
        if (userRepository.findByUsername(signUpDTO.getUsername()).isPresent()) {
            throw new BadCredentialsException("Пользователь с таким логином уже существует");
        }
        else if (userRepository.findByEmail(signUpDTO.getEmail()) != null){
            throw new BadCredentialsException("Пользователь с такой почтой уже существует");
        }

        newUser.setEmail(signUpDTO.getEmail());
        newUser.setFirstName(signUpDTO.getFirstName());
        newUser.setLastName(signUpDTO.getLastName());
        newUser.setPatronymic(signUpDTO.getPatronymic());
        newUser.setUsername(signUpDTO.getUsername());
        newUser.setPassword(new BCryptPasswordEncoder().encode(signUpDTO.getPassword()));
        userRepository.save(newUser);
    }

    public void update(User user) throws Exception {
        try{
            Optional<User> optUser = userRepository.findByUsername(user.getUsername());
            User findUser = optUser.orElseThrow(() -> new UsernameNotFoundException("Не удалось обновить данные"));
            findUser.update(user);
            userRepository.save(findUser);
        }
        catch (UsernameNotFoundException e){
            throw e;
        }
        catch (Exception e){
            throw new Exception("Не удалось обновить данные");
        }
    }

    public User getByUsername(String username) throws Exception {
        try{
            Optional<User> optUser = userRepository.findByUsername(username);
            return optUser.orElseThrow();
        }
        catch (Exception e){
            throw new Exception("Произошла ошибка. Попробуйте позже.");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), new ArrayList<>());
    }

    public ApiResponse changeUserData(UserUpdateDTO userUpdateDTO) throws Exception {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(() -> new UsernameNotFoundException("Не удалось обновить данные"));
        if (passwordEncoder.matches(userUpdateDTO.getPassword(), user.getPassword())) {
            user.setFirstName(userUpdateDTO.getFirstname());
            user.setLastName(userUpdateDTO.getLastname());
            user.setPatronymic(userUpdateDTO.getPatronymic());
            update(user);
            return new ApiResponse(true, "Данные успешно изменены"){};
        }
        throw new BadCredentialsException("Неверный пароль");

    }

    public User getByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }
}
