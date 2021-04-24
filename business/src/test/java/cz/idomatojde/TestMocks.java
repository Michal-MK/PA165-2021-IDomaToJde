package cz.idomatojde;

import cz.idomatojde.entity.Category;
import cz.idomatojde.entity.Offer;
import cz.idomatojde.entity.Timetable;
import cz.idomatojde.entity.TimetableEntry;
import cz.idomatojde.entity.User;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public final class TestMocks {

    public static User mockUser(String username) {
        User user = mock(User.class);

        when(user.getUsername()).thenReturn(username);
        when(user.getPassHash()).thenReturn("UGFzc3dvcmQ=");
        when(user.getPassSalt()).thenReturn("U2FsdA==");
        when(user.getName()).thenReturn("Name");
        when(user.getSurname()).thenReturn("Surname");
        when(user.getPhoneNumber()).thenReturn("+420123456789");
        when(user.getEmail()).thenReturn("pepega@mail.com");
        when(user.getCredits()).thenReturn(123);
        when(user.wantsAdvertisement()).thenReturn(false);
        when(user.isAdmin()).thenReturn(false);

        when(user.toString()).thenReturn("User: " + username);

        return user;
    }

    public static Timetable mockTimetable(User user, int year, int week) {
        Timetable timetable = mock(Timetable.class);

        when(timetable.getUser()).thenReturn(user);
        when(timetable.getYear()).thenReturn(year);
        when(timetable.getWeek()).thenReturn(week);

        when(timetable.toString()).thenReturn("Timetable: YEAR: " + year + ", WEEK: " + week);

        return timetable;
    }

    public static TimetableEntry mockTimetableEntry(Timetable timetable, int day, LocalTime start, Duration length) {
        TimetableEntry timetableEntry = mock(TimetableEntry.class);

        when(timetableEntry.getTimetable()).thenReturn(timetable);
        when(timetableEntry.getDay()).thenReturn(day);
        when(timetableEntry.getEntryStart()).thenReturn(start);
        when(timetableEntry.getLength()).thenReturn(length);

        when(timetableEntry.toString()).thenReturn("TimetableEntry: DAY: " + day + ", START: " + start);

        return timetableEntry;
    }


    public static Offer mockOffer(User user, String title) {
        Offer offer = mock(Offer.class);

        when(offer.getTitle()).thenReturn(title);
        when(offer.getOwner()).thenReturn(user);
        when(offer.getDescription()).thenReturn("description");
        when(offer.getCategory()).thenReturn(Category.EDUCATION);
        when(offer.getCapacity()).thenReturn(10);
        when(offer.getRegistered()).thenReturn(5);
        when(offer.getPrice()).thenReturn(BigDecimal.ONE);
        when(offer.getCreatedDate()).thenReturn(LocalDate.of(2021, 4, 1));
        when(offer.getExpirationDate()).thenReturn(LocalDate.of(2021, 4, 20));

        when(offer.toString()).thenReturn("Offer: " + title);

        return offer;
    }
}
