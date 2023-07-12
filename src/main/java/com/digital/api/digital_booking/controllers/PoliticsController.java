package com.digital.api.digital_booking.controllers;

import com.digital.api.digital_booking.models.Politics;
import com.digital.api.digital_booking.services.PoliticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/politics", produces = "application/json")
public class PoliticsController {

    @Autowired
    private PoliticsService politicsService;

    @GetMapping(path = "/getAll", produces = "application/json" )
    public Iterable<Politics> getAllPolitics() {
        return politicsService.getAll();
    }

    @PostMapping("/create")
    public Politics createPolitics(@RequestBody Politics politics) {
        System.out.println(politics);
        return politicsService.createPolitics(politics);
    }



}
