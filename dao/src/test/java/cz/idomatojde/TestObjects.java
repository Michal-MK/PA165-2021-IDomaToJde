package cz.idomatojde;

import cz.idomatojde.entity.Category;
import cz.idomatojde.entity.Offer;
import cz.idomatojde.entity.User;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TestObjects {

    /**
     * Creates a new {@link User} entity
     * <p>Name = "Name"</p>
     * <p>Surname = "Surname"</p>
     * <p>Phone = "+420123456789"</p>
     * <p>Email = "$Name@mail.com"</p>
     * <p>Credits = 123</p>
     * <p>Wants Ads = false</p>
     * <p>Is Admin = false</p>
     *
     * @param username the username of the {@link User} used as an email as well
     * @param password the raw password string
     * @return new {@link User} object
     */
    public static User getUser(String username, String password) {

        Argon2PasswordEncoder encoder = new Argon2PasswordEncoder();

        User user = new User();
        user.setUsername(username);
        user.setPassword(encoder.encode(password));
        user.setName("Name");
        user.setSurname("Surname");
        user.setPhoneNumber("+420123456789");
        user.setEmail(username + "@mail.com");
        user.setCredits(123);
        user.setWantsAdvertisement(false);
        user.setAdmin(false);

        return user;
    }

    /**
     * Creates a new {@link User} with predefined password "password" using the default function:
     * <p>{@link #getUser(String, String)}</p>
     *
     * @param username the username of the {@link User} used as an email as well
     * @return new {@link User} object
     */
    public static User getUser(String username) {
        return getUser(username, "password");
    }

    /** Creates a new {@link Offer} entity
     * <p>Title = title</p>
     * <p>Owner = {@link #getUser(String,String)} with parameters: "Mr. $title", "12345"</p>
     * <p>Description = "description"</p>
     * <p>Capacity = 10</p>
     * <p>Registered = 5</p>
     * <p>Price = 1</p>
     * <p>Created At = 2021.04.1</p>
     * <p>Expires At = $tomorrow</p>

     * @param title the title of this {@link Offer}
     * @return new {@link Offer} object
     */
    public static Offer getOffer(String title) {
        Offer offer = new Offer();
        offer.setTitle(title);
        offer.setOwner(getUser("Mr. " + title, "12345"));
        offer.setDescription("description");
        offer.setCategory(Category.EDUCATION);
        offer.setCapacity(10);
        offer.setRegistered(5);
        offer.setPrice(BigDecimal.ONE);
        offer.setCreatedDate(LocalDate.of(2021, 4, 1));
        offer.setExpirationDate(LocalDate.now().plusDays(1));

        return offer;
    }
}
