package com.digital.api.digital_booking.services;


import com.digital.api.digital_booking.models.Booking;
import com.digital.api.digital_booking.models.Product;
import com.digital.api.digital_booking.models.Users;
import com.digital.api.digital_booking.repositories.BookingRepository;
import com.digital.api.digital_booking.repositories.IUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private IUserRepository userRepository;
    public Booking createBooking(Booking booking){

        Product existProduct = productService.getProductById(booking.getProduct().getIdProduct());
        Users existUser = userRepository.findByEmail(booking.getUser().getEmail());

        if(existProduct == null){
            throw new RuntimeException("Product not found");
        }
        if(existUser == null){
            throw new RuntimeException("User not found");
        }
        booking.setProduct(existProduct);
        booking.setUser(existUser);

        return bookingRepository.save(booking);
    }

    public Booking getBookingById(Long idBooking) {
        return bookingRepository.findByIdBooking(idBooking);
    }

    public void deleteBooking(Long idBooking) {
        bookingRepository.deleteById(idBooking);
    }

    public Iterable<Booking> getAll() {
        return bookingRepository.findAll();
    }



}
