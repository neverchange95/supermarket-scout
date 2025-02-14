package de.thi.inf.cnd.supermarket_scout.userprofile_auth_app.adapter.rest.dto;

import de.thi.inf.cnd.supermarket_scout.userprofile_auth_app.domain.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponseDTO {
    private String token;
    private String errorMessage;
    private UserDTO user;
}
