package org.service.core;

import org.service.entity.BookingEntity;
import org.service.entity.BookingParamsEntity;
import org.service.entity.PageEntity;
import org.service.exception.ProblemDetailsException;
import org.service.input_port.BookingTransportationServiceInputPort;
import org.service.output_port.aggregate.TransportationServiceOutputPortAggregate;
import org.service.output_port.create.CreateBookingTransportationServiceOutputPort;
import org.service.output_port.find.FindByPhoneTransportationServiceOutputPort;
import org.service.output_port.revoke.RevokeBookingTransportationServiceOutputPort;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.regex.Pattern;


public class BookingTransportationServiceCore extends TransportationServiceCore implements BookingTransportationServiceInputPort {
    private static final String PHONE_PATTERN = "^(?:\\+?7|8)[\\s-]?\\(?\\d{3}\\)?[\\s-]?\\d{3}[\\s-]?\\d{2}[\\s-]?\\d{2}$";

    public BookingTransportationServiceCore(TransportationServiceOutputPortAggregate aggregate, ExecutorService executorService) {
        super(aggregate, executorService);
    }

    @Override
    public void createBooking(BookingParamsEntity bookingParams) {
        try {
            if (isPhone(bookingParams.numberPhone())) {

                aggregate.getOutputPort(CreateBookingTransportationServiceOutputPort.class).create(generateByPattern(bookingParams));
            } else {
                throw new IsNotPhoneException();
            }
        } catch (IsNotPhoneException e) {
            throw new IsNotPhoneException();
        } catch (ProblemDetailsException e) {
            throw new ProblemDetailsException(e);
        }
    }

    @Override
    public void revokeBooking(String id) {
        aggregate.getOutputPort(RevokeBookingTransportationServiceOutputPort.class).revoke(id);
    }

    @Override
    public CompletableFuture<List<BookingEntity>> findByPhone(final String phone, PageEntity pageEntity) {

        return CompletableFuture.supplyAsync(() -> {
            if (isPhone(phone)) {
                String finalPhone = phone.replaceAll(" ", "").replaceAll("\\+", "");
                return aggregate.getOutputPort(FindByPhoneTransportationServiceOutputPort.class).findBy(finalPhone, pageEntity);
            } else {
                throw new IsNotPhoneException();
            }
        }, executorService);

    }

    private BookingParamsEntity generateByPattern(BookingParamsEntity entity) {
        return new BookingParamsEntity(entity.numberPhone().replaceAll(" ", "").replaceAll("\\+", ""),
                entity.routeId());
    }

    private boolean isPhone(String phone) {

        return Pattern.compile(PHONE_PATTERN).matcher(phone).find();
    }
}
