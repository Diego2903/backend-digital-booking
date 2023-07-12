package com.digital.api.digital_booking.controllers;

import com.digital.api.digital_booking.models.City;
import com.digital.api.digital_booking.services.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/city", produces = "application/json")

public class CityController {
    @Autowired
    private CityService cityService;

    @GetMapping(path = "/getAll", produces = "application/json" )
    @ResponseBody
    public Iterable<City> getAllCities() {
        return cityService.getAll();
    }

    @PostMapping("/create")
    public City createCity(@RequestBody City city) {
        return cityService.createCity(city);
    }

    @PutMapping("/update/{id}")
    public City updateCity(@PathVariable("id") Long id, @RequestBody City city) {
        return cityService.updateCityById(id, city);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCity(@PathVariable("id") Long id) {
        String msg= cityService.deleteCity(id);
        if (msg.equals("City deleted")){
            return ResponseEntity.ok().body(msg);
        }else{
            return ResponseEntity.badRequest().body(msg);
        }

    }

    @GetMapping("/{id}")
    public City getCityById(@PathVariable("id") Long id) {
        return cityService.getCityById(id);
    }






}
