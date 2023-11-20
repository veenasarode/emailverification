package com.springemailverification.user;

import com.springemailverification.registration.RegistrationRequest;
import com.springemailverification.registration.token.VerificationToken;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    List<User> getUsers();
    User registerUser(RegistrationRequest request);
    Optional<User> findByEmail(String email);


    void saveUserVerificationToken(User theUser, String verificationToken);

    String validateToken(String theToken);
}
