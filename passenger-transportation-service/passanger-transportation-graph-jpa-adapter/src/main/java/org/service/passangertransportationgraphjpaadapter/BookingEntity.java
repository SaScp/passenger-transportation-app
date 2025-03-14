package org.service.passangertransportationgraphjpaadapter;

public record BookingEntity(
        String id,
        String bookingTime,
        String userPhone,
        String status,
        String route
) {

    public static BookingEntity fromBooking(Booking booking) {
        return new BookingEntity(booking.getId(), booking.getBookingTime().toString(), booking.getUserPhone().getNumberPhone(), booking.getStatus().getStatus(), booking.getRoute());
    }
}
