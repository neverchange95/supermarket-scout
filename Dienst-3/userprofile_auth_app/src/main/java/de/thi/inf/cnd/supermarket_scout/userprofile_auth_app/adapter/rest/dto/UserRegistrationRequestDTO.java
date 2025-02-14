package de.thi.inf.cnd.supermarket_scout.userprofile_auth_app.adapter.rest.dto;

import lombok.Data;

@Data
public class UserRegistrationRequestDTO {
    private String username;
    private String password;
    private String location;
    private String email;
    private String preferredCategory;
}
