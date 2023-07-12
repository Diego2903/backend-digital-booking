package com.digital.api.digital_booking.services;

import com.digital.api.digital_booking.dto.PageResponseDTO;
import com.digital.api.digital_booking.dto.SignUpRequest;
import com.digital.api.digital_booking.dto.UserDTO;
import com.digital.api.digital_booking.models.PageResponse;
import com.digital.api.digital_booking.models.Users;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserDetails createUser(SignUpRequest signUpRequest);

    PageResponse<UserDTO> getUsers(int pageNumber, int pageSize);

    Users getUser(String jwt);

    Users updateUser(String id, Users user);

    Users updateUserState(String email) throws Exception;




}
