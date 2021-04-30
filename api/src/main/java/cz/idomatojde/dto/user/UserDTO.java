package cz.idomatojde.dto.user;

import cz.idomatojde.dto.offer.OfferDTO;

import java.util.List;
import java.util.Objects;

/**
 * DTO Holding the whole information about user
 *
 * @author Jiri Vrbka
 */
public class UserDTO {

    private Long id;

    private String username;

    private String password;

    private String email;

    private String name;

    private String surname;

    private String phoneNumber;

    private Integer credits;

    private Integer bonusCredits;

    private List<OfferDTO> offers;

    private boolean wantsAdvertisement;

    private boolean isAdmin;

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String passHash) {
        this.password = passHash;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer getCredits() {
        return credits;
    }

    public void setCredits(Integer credits) {
        this.credits = credits;
    }

    public Integer getBonusCredits() {
        return bonusCredits;
    }

    public void setBonusCredits(Integer bonusCredits) {
        this.bonusCredits = bonusCredits;
    }

    public List<OfferDTO> getOffers() {
        return offers;
    }

    public void setOffers(List<OfferDTO> offers) {
        this.offers = offers;
    }

    public boolean wantsAdvertisement() {
        return wantsAdvertisement;
    }

    public void setWantsAdvertisement(boolean wantsAdvertisement) {
        this.wantsAdvertisement = wantsAdvertisement;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;

        if (!(o instanceof UserDTO)) return false;

        UserDTO user = (UserDTO) o;
        return wantsAdvertisement() == user.wantsAdvertisement()
                && isAdmin() == user.isAdmin()
                && Objects.equals(getUsername(), user.getUsername())
                && Objects.equals(getPassword(), user.getPassword())
                && Objects.equals(getEmail(), user.getEmail())
                && Objects.equals(getName(), user.getName())
                && Objects.equals(getSurname(), user.getSurname())
                && Objects.equals(getPhoneNumber(), user.getPhoneNumber())
                && Objects.equals(getCredits(), user.getCredits())
                && Objects.equals(getBonusCredits(), user.getBonusCredits())
                && Objects.equals(getOffers(), user.getOffers()
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                getUsername(),
                getPassword(),
                getEmail(),
                getName(),
                getSurname(),
                getPhoneNumber(),
                getCredits(),
                getBonusCredits(),
                getOffers(),
                wantsAdvertisement(),
                isAdmin()
        );
    }
}
