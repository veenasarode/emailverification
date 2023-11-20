package com.springemailverification.registration;

import com.springemailverification.event.RegistrationCompleteEvent;
import com.springemailverification.registration.token.VerificationToken;
import com.springemailverification.registration.token.VerificationTokenRepository;
import com.springemailverification.user.User;
import com.springemailverification.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/register")
public class RegistrationController {

    private final UserService userService;
    private final ApplicationEventPublisher publisher;
    private final VerificationTokenRepository tokenRepository;
    @PostMapping
    public String registerUser(@RequestBody RegistrationRequest registrationRequest , final HttpServletRequest httpServletRequest){

        User user = userService.registerUser(registrationRequest);

        //publish registration event
        publisher.publishEvent(new RegistrationCompleteEvent(user , applicationUrl(httpServletRequest)));

        return "Success!! please check your email for to complete your registration";
    }

    @GetMapping("/verifyEmail")
    public String verifyEmail(@RequestParam("token") String token){
        VerificationToken theToken = tokenRepository.findByToken(token);

        if (theToken.getUser().isEnabled())
        {
            return "This account has already been verified, please login.";
        }
        String verificationResult = userService.validateToken(token);

        if(verificationResult.equalsIgnoreCase("valid")){
            return "Email verified Successfully. Now you can login to your account";
        }

        return "Invalid verification token";
    }



    public String applicationUrl(HttpServletRequest httpServletRequest) {

        return "http://"+httpServletRequest.getServerName()+":"+httpServletRequest.getServerPort()+httpServletRequest.getContextPath();
    }
}
