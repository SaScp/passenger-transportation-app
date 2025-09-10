package org.service.output_port.jpa.api.v2;

import lombok.AllArgsConstructor;
import org.service.output_port.TransportationServiceOutputPort;
import org.service.output_port.jpa.v2.UserService;
import org.service.output_port.v2.UserServiceOutputPort;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DefaultUserServiceAdapter implements UserServiceOutputPort {

    private final UserService userService;

    @Override
    public Class<? extends TransportationServiceOutputPort> getOutputPortType() {
        return this.getClass();
    }
}
