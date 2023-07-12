package com.digital.api.digital_booking.services;

import com.digital.api.digital_booking.models.Location;
import com.digital.api.digital_booking.repositories.ILocationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LocationService {

    private final ILocationRepository locationRepository;

    public Location createLocation(Location location) {
        return locationRepository.save(location);
    }

    public Location updateLocationById(Long idLocation, Location location) {
        Location locationToUpdate = locationRepository.findByIdLocation(idLocation);

        if (locationToUpdate != null) {
            if (location.getLatitude() != null) {
                locationToUpdate.setLatitude(location.getLatitude());
            }
            if (location.getLongitude() != null) {
                locationToUpdate.setLongitude(location.getLongitude());
            }
            if (location.getUrlLocation() != null) {
                locationToUpdate.setUrlLocation(location.getUrlLocation());
            }
            return locationRepository.save(locationToUpdate);
        } else {
            return null;
        }
    }

    public Location getLocationById(Long idLocation) {
        return locationRepository.findByIdLocation(idLocation);
    }

    public void deleteLocation(Long idLocation) {
        locationRepository.deleteById(idLocation);
    }

    public Iterable<Location> getAll() {
        return locationRepository.findAll();
    }


}
