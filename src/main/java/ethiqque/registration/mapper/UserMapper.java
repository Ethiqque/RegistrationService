package ethiqque.registration.mapper;

import ethiqque.registration.entity.dto.RegistrationRequest;
import ethiqque.registration.entity.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    User toEntity(RegistrationRequest request);
}