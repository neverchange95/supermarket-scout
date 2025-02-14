package de.thi.inf.cnd.supermarket_scout.userprofile_auth_app.adapter.rest;

import de.thi.inf.cnd.supermarket_scout.userprofile_auth_app.adapter.configuration.JwtTokenProvider;
import de.thi.inf.cnd.supermarket_scout.userprofile_auth_app.adapter.mapper.UserMapper;
import de.thi.inf.cnd.supermarket_scout.userprofile_auth_app.adapter.rest.dto.UserRegistrationRequestDTO;
import de.thi.inf.cnd.supermarket_scout.userprofile_auth_app.adapter.rest.dto.UserResponseDTO;
import de.thi.inf.cnd.supermarket_scout.userprofile_auth_app.domain.models.User;
import de.thi.inf.cnd.supermarket_scout.userprofile_auth_app.domain.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user-service")
public class UserprofileAuthController {
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserMapper userMapper;

    @Autowired
    public UserprofileAuthController(UserService userService, JwtTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userMapper = new UserMapper();
    }


    @CrossOrigin
    @PostMapping("/register")
    public UserResponseDTO register(@RequestBody UserRegistrationRequestDTO userRegistrationRequestDTO) {
        if( userRegistrationRequestDTO.getUsername().isEmpty()
            || userRegistrationRequestDTO.getPassword().isEmpty()
            || userRegistrationRequestDTO.getEmail().isEmpty()
            || userRegistrationRequestDTO.getLocation().isEmpty()
            || userRegistrationRequestDTO.getPreferredCategory().isEmpty()
        ) {
            return new UserResponseDTO(
                    "",
                    "All parameters are required!",
                    null
            );
        }

        User registeredUser = userService.registerUser(
                userRegistrationRequestDTO.getUsername(),
                userRegistrationRequestDTO.getPassword(),
                userRegistrationRequestDTO.getEmail(),
                userRegistrationRequestDTO.getLocation(),
                userRegistrationRequestDTO.getPreferredCategory()
        );

        if(registeredUser == null) {
            return new UserResponseDTO(
                    "",
                    "User already exists",
                    null
            );
        }

        // generate jwt
        String token = jwtTokenProvider.generateToken(registeredUser.getUsername());

        // return registered user with authentication token
        return new UserResponseDTO(
                token,
                "",
                userMapper.toDTO(registeredUser)
        );
    }

    @CrossOrigin
    @GetMapping("/login")
    public UserResponseDTO login(@RequestParam String username, @RequestParam String password) {
        if(username.isEmpty() || password.isEmpty()) {
            return new UserResponseDTO(
                    "",
                    "Username and Password is required!",
                    null
            );
        }

        User loggedinUser = userService.loginUser(username, password);
        if(loggedinUser == null) {
            return new UserResponseDTO(
                    "",
                    "username or password is false",
                    null
            );
        } else {
            String token = jwtTokenProvider.generateToken(loggedinUser.getUsername());

            return new UserResponseDTO(
                    token,
                    "",
                    userMapper.toDTO(loggedinUser)
            );
        }
    }
}
