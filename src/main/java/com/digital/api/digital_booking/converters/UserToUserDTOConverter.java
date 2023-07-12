package com.digital.api.digital_booking.converters;


import com.digital.api.digital_booking.dto.UserDTO;
import com.digital.api.digital_booking.models.Users;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserToUserDTOConverter implements Converter<Users, UserDTO> {

    @Override
        public UserDTO convert(Users source) {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(source.getEmail());
        userDTO.setRole(source.getRole().name());

        return userDTO;
    }

}