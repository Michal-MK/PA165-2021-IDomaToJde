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
     * <p>Username = username</p>
     * <p>Password = Hashed $password</p>
     * <p>Name = name</p>
     * <p>Surname = surname</p>
     * <p>Phone = phoneNum</p>
     * <p>Email = "$Username@mail.com"</p>
     * <p>Credits = credits</p>
     * <p>Wants Ads = wantsAds</p>
     * <p>Is Admin = isAdmin</p>
     *
     * @param username the username of the {@link User} used as an email as well
     * @param password the raw password string
     * @return new {@link User} object
     */
    public static User getUser(String username, String password, String name, String surname,
                               String phoneNum, int credits, boolean wantsAds, boolean isAdmin) {
        User user = new User();

        user.setUsername(username);
        user.setPassword(password);
        user.setName(name);
        user.setSurname(surname);
        user.setPhoneNumber(phoneNum);
        user.setEmail(username + "@mail.com");
        user.setCredits(credits);
        user.setWantsAdvertisement(wantsAds);
        user.setAdmin(isAdmin);

        return user;
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
     * <p>Description = description</p>
     * <p>Capacity = capacity</p>
     * <p>Registered = registered</p>
     * <p>Price = price</p>
     * <p>Created At = created</p>
     * <p>Expires At = expiration</p>

     * @param user the {@link User} object this {@link Offer} was created by
     * @param title the title of this {@link Offer}
     * @return new {@link Offer} object
     */
    public static Offer getOffer(User user, String title, String description, Category c, int capacity, int registered, BigDecimal price, LocalDate created, LocalDate expiration) {
        Offer offer = new Offer();

        offer.setTitle(title);
        offer.setOwner(user);
        offer.setDescription(description);
        offer.setCategory(c);
        offer.setCapacity(capacity);
        offer.setRegistered(registered);
        offer.setPrice(price);
        offer.setCreatedDate(created);
        offer.setExpirationDate(expiration);

        return offer;
    }
}
