package cz.idomatojde.entity;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;


/*
Created by Ondrej Urbanovsky
Updated by Jiri Vrbka
 */
@Entity
@SuppressWarnings("JpaDataSourceORMInspection")
@Table(name = "\"User\"")
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String username;

    @NotNull
    private String passHash;

    @NotNull
    private String passSalt;

    @NotNull
    private String email;

    @NotNull
    private String name;

    @NotNull
    private String surname;

    @NotNull
    private String phoneNumber;

    @NotNull
    private Integer credits;

    private Integer bonusCredits;

    @OneToMany
    private List<Offer> offers;

    @NotNull
    private boolean wantsAdvertisement;

    @NotNull
    private boolean isAdmin;

    public Long getId() {
        return id;
    }

    public void setId(Long userid) {
        this.id = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassHash() {
        return passHash;
    }

    public void setPassHash(String passHash) {
        this.passHash = passHash;
    }

    public String getPassSalt() {
        return passSalt;
    }

    public void setPassSalt(String passSalt) {
        this.passSalt = passSalt;
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

    public List<Offer> getOffers() {
        return offers;
    }

    public void setOffers(List<Offer> offers) {
        this.offers = offers;
    }

    public boolean isWantsAdvertisement() {
        return wantsAdvertisement;
    }

    public void setWantsAdvertisement(boolean wantsAdvertisment) {
        this.wantsAdvertisement = wantsAdvertisment;
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

        if (!(o instanceof User)) return false;

        User user = (User) o;
        return isWantsAdvertisement() == user.isWantsAdvertisement()
                && isAdmin() == user.isAdmin()
                && Objects.equals(getUsername(), user.getUsername())
                && Objects.equals(getPassHash(), user.getPassHash())
                && Objects.equals(getPassSalt(), user.getPassSalt())
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
                getPassHash(),
                getPassSalt(),
                getEmail(),
                getName(),
                getSurname(),
                getPhoneNumber(),
                getCredits(),
                getBonusCredits(),
                getOffers(),
                isWantsAdvertisement(),
                isAdmin()
        );
    }
}
