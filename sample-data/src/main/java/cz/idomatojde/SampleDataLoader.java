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
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
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

    private final Map<User, Offer> ownership = new HashMap<>();
    private final Map<Offer, List<User>> subscribersOf = new HashMap<>();


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

        Category it = createCategory("IT");
        createCategory("Sport");
        createCategory("Education");
        createCategory("Leisure");
        createCategory("Just Chatting");


        User root = createUser("root", "12345", "John", "Doe", phone(), 9999, false, true);
        User user = createUser("john", "doe", "John", "Doe The Not-Admin", phone(), 0, true, false);

        Offer epic = createOffer(user, it, "Epic Offer!");

        epic.getSubscribers().add(root);

        for (int i = 0; i < 2; i++) {
            User u = createUser("admin" + i, "admin" + i, "Admin " + i, "The Admin", phone(), randInt(0, 100), RND.nextDouble() > 0.8, true);
            epic.getSubscribers().add(u);
        }

        for (int i = 0; i < 30; i++) {
            createUser("user" + i, phone(), randInt(0, 100), RND.nextDouble() > 0.8, false);
        }


        for (int i = 0; i < 1000; i++) {
            Category cat = categoriesList.get(randInt(0, categoriesList.size()));
            User owner = usersList.get(randInt(0, usersList.size()));
            createOffer(owner, cat, "My offer in " + cat.getName() + " (" + i + ")");
        }

        for (Offer offer : offerList) {
            for (User u : usersList) {
                if (RND.nextDouble() > 0.98) {
                    offer.getSubscribers().add(u);
                    u.getSubscribedOffers().add(offer);
                    subscribersOf.get(offer).add(u);
                }
            }
        }

        for (User u : usersList) {
            for (int j = 0; j < 3; j++) {
                createTimetable(u, j);
            }
        }

        for (Timetable t : timetableList) {
            var subbed = t.getUser().getSubscribedOffers();
            if (!subbed.isEmpty()) {
                createTimetableEntry(subbed.get(RND.nextInt(subbed.size())), t);
            }
        }

        for (TimetableEntry e : entryList) {
            var subs = subscribersOf.get(e.getOffer());
            if (!subs.isEmpty()) {
                createTimetableChatMessage(subs.get(randInt(0, subs.size())), e);
            }
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

    private String phone() {
        return F.phoneNumber().cellPhone();
    }

    private Category createCategory(String name) {
        Category c = new Category();
        c.setName(name);
        categories.create(c);
        categoriesList.add(c);

        return c;
    }

    private User createUser(String userName, String pass, String name, String surname,
                            String phoneNum, int creds, boolean ads, boolean admin) {
        User user = TestObjects.getUser(userName, pass, name, surname, phoneNum, creds, ads, admin);
        users.create(user);
        usersList.add(user);

        return user;
    }


    private User createUser(String pass, String phoneNum, int creds, boolean ads, boolean admin) {
        String firstName = F.name().firstName();
        String lastName = F.name().lastName();
        String userName = firstName.toLowerCase(Locale.ROOT) + "." + lastName.toLowerCase(Locale.ROOT);

        return createUser(userName, pass, firstName, lastName, phoneNum, creds, ads, admin);
    }

    private Offer createOffer(User owner, Category cat, String title) {
        LocalDate startLocalDate = LocalDate.of(LocalDate.now().getYear(), Month.JANUARY, randInt(1, 28));
        Date start = F.date().between(toDate(startLocalDate), toDate(startLocalDate.plusDays(randInt(0, 120))));
        Date end = F.date().between(toDate(fromDate(start).plusDays(randInt(0, 50))),
                toDate(fromDate(start).plusDays(randInt(50, 120))));

        int capacity = F.number().numberBetween(2, 60);
        String lorem = F.lorem().paragraph(F.number().numberBetween(2, 4));

        Offer o = TestObjects.getOffer(owner, title,
                lorem.substring(0, Math.min(255, lorem.length())), cat,
                capacity, F.number().numberBetween(0, capacity),
                BigDecimal.valueOf(F.number().randomNumber(2, true)),
                fromDate(start), fromDate(end));
        offers.create(o);
        offerList.add(o);

        ownership.put(owner, o);
        subscribersOf.put(o, new ArrayList<>());

        return o;
    }

    private Timetable createTimetable(User u, int j) {
        Timetable t = new Timetable();
        t.setUser(u);
        t.setYear(LocalDate.now().getYear());
        t.setWeek(LocalDate.now().get(ChronoField.ALIGNED_WEEK_OF_YEAR) - (1 - j));

        timetables.create(t);
        timetableList.add(t);

        return t;
    }

    private TimetableEntry createTimetableEntry(Offer o, Timetable timetable) {
        TimetableEntry e = new TimetableEntry();
        String lorem = F.lorem().paragraph(F.number().numberBetween(2, 4));

        e.setOffer(o);
        e.setTimetable(timetable);
        e.setDescription(lorem.substring(0, Math.min(255, lorem.length())));
        e.setEntryStart(LocalTime.of(F.number().numberBetween(8, 20), F.number().numberBetween(0, 59), 0));
        e.setLength(Duration.ofMinutes(F.number().numberBetween(20, 60 * 4)));
        e.setDay(F.number().numberBetween(0, 7));

        timetables.createEntry(e);
        entryList.add(e);

        return e;
    }

    private TimetableChatMessage createTimetableChatMessage(User sender, TimetableEntry e) {
        TimetableChatMessage cm = new TimetableChatMessage();
        String lorem = F.lorem().paragraph(F.number().numberBetween(2, 4));
        cm.setSender(sender);
        cm.setText(lorem.substring(0, Math.min(255, lorem.length())));
        cm.setTimetableEntry(e);

        chatMessages.create(cm);
        chatMessageList.add(cm);

        return cm;
    }
}
