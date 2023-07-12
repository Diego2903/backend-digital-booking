package com.digital.api.digital_booking.dto;

import java.util.List;

public class UserPageDTO extends PageResponseDTO<UserDTO> {
    public UserPageDTO(List<UserDTO> content) {
        super(content);
    }
}
