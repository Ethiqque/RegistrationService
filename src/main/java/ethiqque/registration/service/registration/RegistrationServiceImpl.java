package ethiqque.registration.service.registration;

import com.example.demo.registration.token.ConfirmationToken;
import com.example.demo.registration.token.ConfirmationTokenService;
import ethiqque.registration.entity.dto.UserDto;
import ethiqque.registration.entity.dto.RegistrationRequest;
import ethiqque.registration.entity.model.ConfirmationToken;
import ethiqque.registration.entity.model.User;
import ethiqque.registration.exception.NotFoundException;
import ethiqque.registration.mapper.UserMapper;
import ethiqque.registration.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {

    private final static String USER_NOT_FOUND_MSG =
            "user with email %s not found";

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserMapper userMapper;
    private final ConfirmationTokenService confirmationTokenService;

    @Override
    public UserDto loadUserByUsername(String email)
            throws NotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                String.format(USER_NOT_FOUND_MSG, email)));
    }

    public String signUpUser(RegistrationRequest request) {


        boolean userExists = userRepository
                .findByEmail(request.getEmail())
                .isPresent();

        if (userExists) {
            // TODO check of attributes are the same and
            // TODO if email not confirmed send confirmation email.

            throw new IllegalStateException("email already taken");
        }

        User user = userMapper.toEntity(request);

        String encodedPassword = bCryptPasswordEncoder
                .encode(request.getPassword());

        user.setPassword(encodedPassword);

        userRepository.save(user);

        String token = UUID.randomUUID().toString();

        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                user
        );

        confirmationTokenService.saveConfirmationToken(
                confirmationToken);

//        TODO: SEND EMAIL

        return token;
    }

    public int enableAppUser(String email) {
        return userRepository.enableAppUser(email);
    }

    package com.example.demo.registration;

import com.example.demo.appuser.AppUser;
import com.example.demo.appuser.AppUserRole;
import com.example.demo.appuser.AppUserService;
import com.example.demo.email.EmailSender;
import com.example.demo.registration.token.ConfirmationToken;
import com.example.demo.registration.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

    @Service
    @AllArgsConstructor
    public class RegistrationService {

        private final AppUserService appUserService;
        private final EmailValidator emailValidator;
        private final ConfirmationTokenService confirmationTokenService;
        private final EmailSender emailSender;

        public String register(RegistrationRequest request) {
            boolean isValidEmail = emailValidator.
                    test(request.getEmail());

            if (!isValidEmail) {
                throw new IllegalStateException("email not valid");
            }

            String token = appUserService.signUpUser(
                    new AppUser(
                            request.getFirstName(),
                            request.getLastName(),
                            request.getEmail(),
                            request.getPassword(),
                            AppUserRole.USER

                    )
            );

            String link = "http://localhost:8080/api/v1/registration/confirm?token=" + token;
            emailSender.send(
                    request.getEmail(),
                    buildEmail(request.getFirstName(), link));

            return token;
        }
}