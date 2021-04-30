package cz.idomatojde.dto.user;

/**
 * DTO holding the necessary user contact details
 *
 * @author Michal Hazdra
 */
public final class UserContactInfoDTO {

    private final String name;
    private final String surname;
    private final String email;
    private final String phoneNumber;

    public UserContactInfoDTO(String name, String surname, String email, String phoneNumber) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
