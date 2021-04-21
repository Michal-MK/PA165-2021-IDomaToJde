package cz.idomatojde.dao.utils;

import cz.idomatojde.entity.User;

/** POJO for User's contact details
 * @author Michal Hazdra
 */
public final class UserContactInfo {
    private final String name;
    private final String surname;
    private final String email;
    private final String phone;

    public UserContactInfo(String name, String surname, String email, String phone) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
    }

    public UserContactInfo(User user) {
        this(user.getName(), user.getSurname(), user.getEmail(), user.getPhoneNumber());
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

    public String getPhone() {
        return phone;
    }
}
