package ethiqque.registration.controller;

import ethiqque.registration.entity.dto.RegistrationRequest;
import ethiqque.registration.entity.dto.UserDto;
import ethiqque.registration.service.confirmation.token.ConfirmationTokenService;
import ethiqque.registration.service.registration.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/registration")
@AllArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;
    private final ConfirmationTokenService confirmationTokenService;

    @PostMapping
    public UserDto register(@RequestBody RegistrationRequest request) {
        return registrationService.register(request);
    }

    @GetMapping(path = "/confirm")
    public String confirm(@RequestParam("token") String token) {
        return confirmationTokenService.confirmToken(token);
    }

}