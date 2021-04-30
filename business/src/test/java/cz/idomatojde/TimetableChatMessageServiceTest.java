package cz.idomatojde;

import cz.idomatojde.dao.TimetableChatMessageDAO;
import cz.idomatojde.entity.TimetableChatMessage;
import cz.idomatojde.entity.TimetableEntry;
import cz.idomatojde.entity.User;
import cz.idomatojde.services.TimetableChatMessageService;
import cz.idomatojde.services.TimetableChatMessageServiceImpl;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static cz.idomatojde.TestObjects.getTimetable;
import static cz.idomatojde.TestObjects.getTimetableChatMessage;
import static cz.idomatojde.TestObjects.getTimetableEntry;
import static cz.idomatojde.TestObjects.getUser;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;


public class TimetableChatMessageServiceTest {

    private TimetableChatMessageDAO mockMessages;

    private TimetableChatMessageService service;

    private User user;
    private TimetableEntry timetableEntry;

    @BeforeMethod
    void setup() {
        //This is used as @Inject and @InjectMock are not compatible with constructor injection...
        mockMessages = mock(TimetableChatMessageDAO.class);
        service = new TimetableChatMessageServiceImpl(mockMessages);
        user = getUser("test");
        timetableEntry = getTimetableEntry(getTimetable(user, 2000, 1), 1, LocalTime.MIDNIGHT, Duration.ofHours(2));
    }

    @Test
    public void addMessage() {
        verifyNoInteractions(mockMessages);

        service.addMessage(user, timetableEntry, "text");

        verify(mockMessages, times(1)).addMessage(user, timetableEntry, "text");
        verifyNoMoreInteractions(mockMessages);
    }

    @Test
    public void getAllMessagesForEntry() {
        // Setup
        var expectedMessages = getMessages(timetableEntry, user);
        when(mockMessages.getAllMessagesForEntry(timetableEntry)).thenReturn(expectedMessages);

        // Act
        var actual = service.getAllMessagesForEntry(timetableEntry);

        // Validate
        assertThat(actual).isEqualTo(expectedMessages);
    }

    @Test
    public void getAllMessagesForEntryWithoutMessages() {
        // Setup
        getMessages(timetableEntry, user);
        when(mockMessages.getAllMessagesForEntry(timetableEntry)).thenReturn(new ArrayList<>());

        // Act
        var actual = service.getAllMessagesForEntry(timetableEntry);

        // Validate
        assertThat(actual).isEmpty();
    }

    @Test
    public void getAllMessagesForUser() {
        // Setup
        var expectedMessages = getMessages(timetableEntry, user);
        when(mockMessages.getAllMessagesOfUser(user)).thenReturn(expectedMessages);

        // Act
        var actual = service.getAllMessagesForUser(user);

        // Validate
        assertThat(actual).isEqualTo(expectedMessages);
    }

    @Test
    public void getAllMessagesForUserWithoutMessages() {
        // Setup
        var otherUser = getUser("otherUser");
        getMessages(timetableEntry, otherUser);
        when(mockMessages.getAllMessagesOfUser(user)).thenReturn(new ArrayList<>());

        // Act
        var actual = service.getAllMessagesForUser(user);

        // Validate
        assertThat(actual).isEmpty();
    }

    @Test
    public void deleteAllMessagesOfUser() {
        // Setup
        var expectedMessages = getMessages(timetableEntry, user);
        when(mockMessages.getAllMessagesOfUser(user)).thenReturn(expectedMessages);

        // Act
        service.deleteAllMessagesOfUser(user);

        // Validate
        verify(mockMessages, times(1)).getAllMessagesOfUser(user);
        for (var msg : expectedMessages) {
            verify(mockMessages, times(1)).delete(msg);
        }
        verifyNoMoreInteractions(mockMessages);
    }

    @Test
    public void deleteAllMessagesOfUserWithoutMessage() {
        // Setup
        var otherUser = getUser("otherUser");
        getMessages(timetableEntry, otherUser);
        when(mockMessages.getAllMessagesOfUser(user)).thenReturn(new ArrayList<>());

        // Act
        service.deleteAllMessagesOfUser(user);

        // Validate
        verify(mockMessages, times(1)).getAllMessagesOfUser(user);
        verifyNoMoreInteractions(mockMessages);
    }

    private List<TimetableChatMessage> getMessages(TimetableEntry entry, User sender) {

        TimetableChatMessage m0 = getTimetableChatMessage(entry, sender, "hi");
        TimetableChatMessage m1 = getTimetableChatMessage(entry, sender, "there");
        TimetableChatMessage m2 = getTimetableChatMessage(entry, sender, "mr");
        TimetableChatMessage m3 = getTimetableChatMessage(entry, sender, "kenobi");

        return List.of(m0, m1, m2, m3);
    }
}
