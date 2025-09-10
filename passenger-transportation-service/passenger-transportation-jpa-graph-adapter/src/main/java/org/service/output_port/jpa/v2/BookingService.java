package org.service.output_port.jpa.v2;

import lombok.AllArgsConstructor;
import org.service.output_port.repository.BookingRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;
}
