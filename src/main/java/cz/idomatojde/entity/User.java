package cz.idomatojde.entity;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashMap;


/*
Created by Ondrej Urbanovsky
 */
@Entity
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long userid;

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
    //???
    @OneToMany
    private HashMap<Long,Offer> offerHashMap;

    @NotNull
    private boolean wantsAdvertisment;
/*
    //we have to discuss this
    @OneToMany
    private Rating rating;
*/
    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
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

    public HashMap<Long, Offer> getOfferHashMap() {
        return offerHashMap;
    }

    public void setOfferHashMap(HashMap<Long, Offer> offerHashMap) {
        this.offerHashMap = offerHashMap;
    }

    public boolean isWantsAdvertisment() {
        return wantsAdvertisment;
    }

    public void setWantsAdvertisment(boolean wantsAdvertisment) {
        this.wantsAdvertisment = wantsAdvertisment;
    }
}
