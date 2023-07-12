package com.digital.api.digital_booking.controllers;

import com.digital.api.digital_booking.services.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/location", produces = "application/json")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @GetMapping(path = "/getAll", produces = "application/json" )
    @ResponseBody
    public Iterable<com.digital.api.digital_booking.models.Location> getAllLocations() {
        return locationService.getAll();
    }

    @PostMapping("/create")
    public com.digital.api.digital_booking.models.Location createLocation(@RequestBody com.digital.api.digital_booking.models.Location location) {
        return locationService.createLocation(location);
    }

    @PutMapping("/update/{id}")
    public com.digital.api.digital_booking.models.Location updateLocation(@PathVariable("id") Long id, @RequestBody com.digital.api.digital_booking.models.Location location) {
        return locationService.updateLocationById(id, location);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteLocation(@PathVariable("id") Long id) {
        System.out.println("id: " + id);
        locationService.deleteLocation(id);
        return "Location with id " + id + " deleted";
    }

    @GetMapping("/{id}")
    public com.digital.api.digital_booking.models.Location getLocationById(@PathVariable("id") Long id) {
        return locationService.getLocationById(id);
    }




}
