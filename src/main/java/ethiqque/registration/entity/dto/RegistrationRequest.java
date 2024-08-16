package ethiqque.registration.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequest {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}