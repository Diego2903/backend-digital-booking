package com.digital.api.digital_booking.controllers;


import com.digital.api.digital_booking.dto.PageResponseDTO;
import com.digital.api.digital_booking.dto.UserDTO;
import com.digital.api.digital_booking.dto.UserPageDTO;
import com.digital.api.digital_booking.models.PageResponse;
import com.digital.api.digital_booking.models.Users;
import com.digital.api.digital_booking.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@Tag(name = "UserController (Este es solo para mostrar ejemplo de DTO y paginado)")
public class UserController {

    private final UserService userService;

    @Operation(security = { @SecurityRequirement(name = "bearer-key") },summary = "Get page of users",
            parameters = { @Parameter(in = ParameterIn.QUERY, name = "page", description = "Page"),
                    @Parameter(in = ParameterIn.QUERY, name = "size", description = "Size") },
            responses = {
                    @ApiResponse(responseCode = "200",description = "Successful Operation",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UserPageDTO.class)))})
    @GetMapping ("/api/users")
    @ResponseBody
    public PageResponse<UserDTO> getUsers(@RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "10") int size) {
        return userService.getUsers(page,size);
    }

    @GetMapping ("/api/users/{jwt}")
    public Users getUser(@PathVariable String jwt) {
        System.out.println("jwt: " + jwt);
        return userService.getUser(jwt);

    }

    @PutMapping("/api/users/{email}")//body con usuario a modificar
    public Users updateUser(@PathVariable String email,@RequestBody Users user) {

        return userService.updateUser(email,user);
    }

    @PutMapping("/api/userState/{email}")//body con usuario a modificar
    public Users updateUserState(@PathVariable String email) throws Exception {
        System.out.println("email: " + email);

        return userService.updateUserState(email);
    }




}
