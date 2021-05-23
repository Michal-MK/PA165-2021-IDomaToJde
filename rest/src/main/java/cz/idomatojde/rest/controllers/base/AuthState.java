package cz.idomatojde.rest.controllers.base;

import cz.idomatojde.dto.user.UserDTO;

public class AuthState {
    private UserDTO user;

    public AuthState(UserDTO user) {
        this.user = user;
    }

    public boolean authenticated() {
        return user != null;
    }

    public UserDTO principal() {
        return user;
    }

    public long principalId() {
        return user.getId();
    }

    public boolean admin() {
        return user.isAdmin();
    }
}
