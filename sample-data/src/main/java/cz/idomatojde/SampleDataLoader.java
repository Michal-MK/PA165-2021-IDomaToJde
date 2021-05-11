package cz.idomatojde;

import com.github.javafaker.Faker;
import cz.idomatojde.entity.Category;
import cz.idomatojde.entity.Offer;
import cz.idomatojde.entity.User;
import cz.idomatojde.services.OfferService;
import cz.idomatojde.services.TimetableChatMessageService;
import cz.idomatojde.services.TimetableService;
import cz.idomatojde.services.UserService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

/**
 * Component class for pre-loading the database with sample data.
 *
 * @author Michal Hazdra
 */
@Component
@Transactional
public class SampleDataLoader {

    private static final Random RND = new Random();
    private static final Faker F = new Faker();

    private final UserService users;
    private final OfferService offers;
    private final TimetableService timetables;
    private final TimetableChatMessageService chatMessages;

    private final List<User> usersList = new ArrayList<>();

    @Inject
    public SampleDataLoader(UserService users, OfferService offers,
                            TimetableService timetables, TimetableChatMessageService chatMessages) {
        this.users = users;
        this.offers = offers;
        this.timetables = timetables;
        this.chatMessages = chatMessages;
    }

    public void loadData() {
        for (int i = 0; i < 2; i++) {
            String firstN = F.name().firstName();
            String lastN = F.name().lastName();
            String username = firstN.toLowerCase(Locale.ROOT) + "." + lastN.toLowerCase(Locale.ROOT);

            User u = TestObjects.getUser(username, "admin" + i, firstN,
                    lastN, F.phoneNumber().cellPhone(), F.number().numberBetween(0, 100),
                    RND.nextDouble() > 0.8, true);
            users.create(u);
            usersList.add(u);
        }

        for (int i = 0; i < 15; i++) {
            String firstN = F.name().firstName();
            String lastN = F.name().lastName();
            String username = firstN.toLowerCase(Locale.ROOT) + "." + lastN.toLowerCase(Locale.ROOT);

            User u = TestObjects.getUser(username, "user" + i, firstN,
                    lastN, F.phoneNumber().cellPhone(), F.number().numberBetween(0, 100),
                    RND.nextDouble() > 0.8, false);
            users.create(u);
            usersList.add(u);
        }


        for (int i = 0; i < 30; i++) {
            LocalDate startLd = LocalDate.of(2020, Month.JANUARY, randInt(1, 28));
            Date start = F.date().between(toDate(startLd), toDate(startLd.plusDays(randInt(0, 120))));
            Date end = F.date().between(toDate(fromDate(start).plusDays(randInt(0, 50))),
                    toDate(fromDate(start).plusDays(randInt(50, 120))));

            Offer o = TestObjects.getOffer(usersList.get(RND.nextInt(usersList.size())), F.book().title(),
                    F.lorem().characters(50, 250), Category.values()[RND.nextInt(Category.values().length)],
                    RND.nextInt(40), RND.nextInt(40),
                    BigDecimal.valueOf(F.number().randomNumber(2, true)),
                    fromDate(start), fromDate(end));
            offers.create(o);
        }
    }

    private LocalDate fromDate(Date d) {
        return d.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    private Date toDate(LocalDate d) {
        return Date.from(d.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    private int randInt(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}
