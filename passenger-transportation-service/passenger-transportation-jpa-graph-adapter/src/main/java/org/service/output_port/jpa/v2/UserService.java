package org.service.output_port.jpa.v2;

import lombok.AllArgsConstructor;
import org.service.output_port.repository.UserCredentialRepository;
import org.service.output_port.repository.UserDataRepository;
import org.service.output_port.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserDataRepository userDataRepository;
    private final UserCredentialRepository userCredentialRepository;


}
