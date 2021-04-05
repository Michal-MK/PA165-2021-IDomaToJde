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

}
