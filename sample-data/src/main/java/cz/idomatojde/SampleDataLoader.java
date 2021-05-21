package cz.idomatojde;

import com.github.javafaker.Faker;
import cz.idomatojde.entity.Category;
import cz.idomatojde.entity.Offer;
import cz.idomatojde.entity.Timetable;
import cz.idomatojde.entity.TimetableChatMessage;
import cz.idomatojde.entity.TimetableEntry;
import cz.idomatojde.entity.User;
import cz.idomatojde.services.CategoryService;
import cz.idomatojde.services.OfferService;
import cz.idomatojde.services.TimetableChatMessageService;
import cz.idomatojde.services.TimetableService;
import cz.idomatojde.services.UserService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.temporal.ChronoField;
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
    private final CategoryService categories;

    private final List<User> usersList = new ArrayList<>();
    private final List<Category> categoriesList = new ArrayList<>();
    private final List<Offer> offerList = new ArrayList<>();
    private final List<Timetable> timetableList = new ArrayList<>();
    private final List<TimetableEntry> entryList = new ArrayList<>();
    private final List<TimetableChatMessage> chatMessageList = new ArrayList<>();


    @Inject
    public SampleDataLoader(UserService users, OfferService offers,
                            TimetableService timetables, TimetableChatMessageService chatMessages,
                            CategoryService categories) {
        this.users = users;
        this.offers = offers;
        this.timetables = timetables;
        this.chatMessages = chatMessages;
        this.categories = categories;
    }

    public void loadData() {

        createCategory("IT");
        createCategory("Sport");
        createCategory("Education");
        createCategory("Leisure");
        createCategory("Just Chatting");

        User root = TestObjects.getUser("root", "12345", "John",
                "Doe", F.phoneNumber().cellPhone(), 9999,
                false, true);

        users.create(root);
        usersList.add(root);

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
            LocalDate startLd = LocalDate.of(LocalDate.now().getYear(), Month.JANUARY, randInt(1, 28));
            Date start = F.date().between(toDate(startLd), toDate(startLd.plusDays(randInt(0, 120))));
            Date end = F.date().between(toDate(fromDate(start).plusDays(randInt(0, 50))),
                    toDate(fromDate(start).plusDays(randInt(50, 120))));

            Offer o = TestObjects.getOffer(usersList.get(RND.nextInt(usersList.size())), F.book().title(),
                    F.lorem().characters(50, 250), categoriesList.get(RND.nextInt(categoriesList.size())),
                    RND.nextInt(40), RND.nextInt(40),
                    BigDecimal.valueOf(F.number().randomNumber(2, true)),
                    fromDate(start), fromDate(end));
            offers.create(o);
            offerList.add(o);
        }

        for (User u : usersList) {
            for (int j = 0; j < 3; j++) {
                Timetable t = new Timetable();
                t.setUser(u);
                t.setYear(LocalDate.now().getYear());
                t.setWeek(LocalDate.now().get(ChronoField.ALIGNED_WEEK_OF_YEAR) - (1 - j));
                timetables.create(t);
                timetableList.add(t);
            }
        }

        for (Offer o : offerList) {
            TimetableEntry e = new TimetableEntry();
            e.setOffer(o);
            e.setTimetable(timetableList.get(RND.nextInt(timetableList.size())));
            e.setDescription(F.harryPotter().book());
            e.setEntryStart(LocalTime.of(F.number().numberBetween(8, 20), F.number().numberBetween(0, 59), 0));
            e.setLength(Duration.ofMinutes(F.number().numberBetween(20, 60 * 4)));
            e.setDay(F.number().numberBetween(0, 7));
            timetables.createEntry(e);
            entryList.add(e);
        }

        for (TimetableEntry e : entryList) {
            TimetableChatMessage cm = new TimetableChatMessage();
            cm.setSender(usersList.get(RND.nextInt(usersList.size())));
            cm.setText(F.lorem().characters(10,200));
            cm.setTimetableEntry(e);
            chatMessages.create(cm);
            chatMessageList.add(cm);
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

    private void createCategory(String name) {
        Category c = new Category();
        c.setName(name);
        categories.create(c);
        categoriesList.add(c);
    }
}
