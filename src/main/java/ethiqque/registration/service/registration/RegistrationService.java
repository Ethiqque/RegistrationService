package ethiqque.registration.service.registration;

import ethiqque.registration.entity.dto.RegistrationRequest;
import ethiqque.registration.entity.dto.UserDto;

public interface RegistrationService {
    UserDto register(RegistrationRequest request);
}
