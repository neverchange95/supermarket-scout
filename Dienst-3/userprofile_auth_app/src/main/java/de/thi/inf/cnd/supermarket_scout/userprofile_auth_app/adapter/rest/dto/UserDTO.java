package de.thi.inf.cnd.supermarket_scout.userprofile_auth_app.adapter.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class UserDTO {
    private String username;
    private String email;
    private String location;
    private String preferredCategory;
}
