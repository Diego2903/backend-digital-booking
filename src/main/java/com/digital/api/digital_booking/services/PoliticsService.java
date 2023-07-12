package com.digital.api.digital_booking.services;

import com.digital.api.digital_booking.models.Politics;
import com.digital.api.digital_booking.repositories.IPoliticsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PoliticsService {

    private final IPoliticsRepository politicsService;

    public Politics createPolitics(Politics politics){
        return politicsService.save(politics);
    }
    public Iterable<Politics> getAll(){
        return politicsService.findAll();
    }

}
