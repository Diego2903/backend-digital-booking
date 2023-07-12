package com.digital.api.digital_booking.controllers;


import com.digital.api.digital_booking.models.Booking;
import com.digital.api.digital_booking.services.BookingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/booking", produces = "application/json")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping("/create")
    public Booking createBooking(@RequestBody Booking booking) {
        System.out.println("booking: " + booking);
        return bookingService.createBooking(booking);
    }

    @GetMapping(path = "/getAll", produces = "application/json" )
    @ResponseBody
    public Iterable<Booking> getAllBooking() {
        return bookingService.getAll();
    }

    @GetMapping("/{id}")
    public Booking getBookingById(@PathVariable("id") Long id) {
        return bookingService.getBookingById(id);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteBooking(@PathVariable("id") Long id) {
        System.out.println("id: " + id);
        bookingService.deleteBooking(id);
        return "Booking with id " + id + " was deleted";
    }

}
