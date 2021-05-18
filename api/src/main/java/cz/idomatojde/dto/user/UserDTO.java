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

    private UserContactInfoDTO contactInfo;

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

    public UserContactInfoDTO getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(UserContactInfoDTO contactInfoDTO) {
        this.contactInfo = contactInfoDTO;
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
                && Objects.equals(getContactInfo(), user.getContactInfo())
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
                getContactInfo(),
                getCredits(),
                getBonusCredits(),
                getOffers(),
                wantsAdvertisement(),
                isAdmin()
        );
    }
}
