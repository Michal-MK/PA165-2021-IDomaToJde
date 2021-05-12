package cz.idomatojde;

import cz.idomatojde.entity.Category;
import cz.idomatojde.entity.Offer;
import cz.idomatojde.entity.Timetable;
import cz.idomatojde.entity.TimetableChatMessage;
import cz.idomatojde.entity.TimetableEntry;
import cz.idomatojde.entity.User;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Class providing predefined Entity objects
 *
 * @author Michal Hazdra
 */
public final class TestObjects {

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
        User user = new User();
        Argon2PasswordEncoder encoder = new Argon2PasswordEncoder();

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

    /** Creates a new {@link Timetable} entity
     * <p>Owner = user</p>
     * <p>Year = year</p>
     * <p>Week = week</p>
     * <p>Entries = Empty ArrayList&lt;&gt;</p>

     * @param user the {@link User} object this {@link Timetable} belongs to
     * @param year the year this {@link Timetable} is for
     * @param week the week this {@link Timetable} is for
     * @return new {@link Timetable} object
     */
    public static Timetable getTimetable(User user, int year, int week) {
        Timetable timetable = new Timetable();

        timetable.setUser(user);
        timetable.setYear(year);
        timetable.setWeek(week);

        return timetable;
    }

    /** Creates a new {@link TimetableEntry} entity
     * <p>Timetable = timetable</p>
     * <p>Day = day</p>
     * <p>EntryStart = start</p>
     * <p>Length = length</p>
     * <p>Description = "description"</p>
     * <p>Messages = Empty ArrayList&lt;&gt;</p>

     * @param timetable the {@link Timetable} object this {@link TimetableEntry} belongs to
     * @param day the day this {@link TimetableEntry} is for
     * @param start the hour and minutes when this {@link TimetableEntry} begins
     * @param length the duration of this {@link TimetableEntry}
     * @return new {@link TimetableEntry} object
     */
    public static TimetableEntry getTimetableEntry(Timetable timetable, int day, LocalTime start, Duration length) {
        TimetableEntry timetableEntry = new TimetableEntry();

        timetableEntry.setTimetable(timetable);
        timetableEntry.setDay(day);
        timetableEntry.setEntryStart(start);
        timetableEntry.setLength(length);
        timetableEntry.setDescription("description");

        return timetableEntry;
    }

    /** Creates a new {@link TimetableChatMessage} entity
     * <p>Sender = sender</p>
     * <p>TimetableEntry = entry</p>
     * <p>Text = text</p>

     * @param entry the {@link TimetableEntry} object this {@link TimetableChatMessage} belongs to
     * @param sender the {@link User} this {@link TimetableChatMessage} is was sent by
     * @param text the contents of this {@link TimetableChatMessage}
     * @return new {@link TimetableChatMessage} object
     */
    public static TimetableChatMessage getTimetableChatMessage(TimetableEntry entry, User sender, String text) {
        var message = new TimetableChatMessage();

        message.setTimetableEntry(entry);
        message.setSender(sender);
        message.setText(text);

        return message;
    }

    /** Creates a new {@link Offer} entity
     * <p>Title = title</p>
     * <p>Owner = user</p>
     * <p>Description = "description"</p>
     * <p>Capacity = 10</p>
     * <p>Registered = 5</p>
     * <p>Price = 1</p>
     * <p>Created At = 2021.04.1</p>
     * <p>Expires At = $tomorrow</p>

     * @param user the {@link User} object this {@link Offer} was created by
     * @param title the title of this {@link Offer}
     * @return new {@link Offer} object
     */
    public static Offer getOffer(User user, Category cat, String title) {
        Offer offer = new Offer();

        offer.setTitle(title);
        offer.setOwner(user);
        offer.setDescription("description");
        offer.setCategory(cat);
        offer.setCapacity(10);
        offer.setRegistered(5);
        offer.setPrice(BigDecimal.ONE);
        offer.setCreatedDate(LocalDate.of(2021, 4, 1));
        offer.setExpirationDate(LocalDate.now().plusDays(1));

        return offer;
    }
}
