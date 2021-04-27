package cz.idomatojde;

import cz.idomatojde.entity.Category;
import cz.idomatojde.entity.Offer;
import cz.idomatojde.entity.User;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TestObjects {
    public static User getUser(String username) {
        User user = new User();
        user.setUsername(username);
        user.setPassHash("UGFzc3dvcmQ=");
        user.setPassSalt("U2FsdA==");
        user.setName("Name");
        user.setSurname("Surname");
        user.setPhoneNumber("+420123456789");
        user.setEmail(username + "@mail.com");
        user.setCredits(123);
        user.setWantsAdvertisement(false);
        user.setAdmin(false);

        return user;
    }

    public static Offer getOffer(String title) {
        Offer offer = new Offer();
        offer.setTitle(title);
        offer.setOwner(getUser(title));
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
