package cz.idomatojde;

import cz.idomatojde.entity.Category;
import cz.idomatojde.entity.Offer;
import cz.idomatojde.entity.User;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TestObjects {
    public static User getUser(String username, String password) {

        Argon2PasswordEncoder encoder = new Argon2PasswordEncoder();

        User user = new User();
        user.setUsername(username);
        user.setPassword(encoder.encode(password));
        user.setName("Name");
        user.setSurname("Surname");
        user.setPhoneNumber("+420123456789");
        user.setEmail("pepega@mail.com");
        user.setCredits(123);
        user.setWantsAdvertisement(false);
        user.setAdmin(false);

        return user;
    }

    public static User getUser(String username) {
        return getUser(username, "password");
    }



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
