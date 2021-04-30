package cz.idomatojde;

import cz.idomatojde.dao.TimetableDAO;
import cz.idomatojde.entity.Offer;
import cz.idomatojde.entity.Timetable;
import cz.idomatojde.entity.TimetableEntry;
import cz.idomatojde.entity.User;
import cz.idomatojde.services.TimetableService;
import cz.idomatojde.services.TimetableServiceImpl;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoField;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;


import static cz.idomatojde.TestObjects.getOffer;
import static cz.idomatojde.TestObjects.getUser;
import static cz.idomatojde.TestObjects.getTimetable;
import static cz.idomatojde.TestObjects.getTimetableEntry;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author Michal Hazdra
 */
@ContextConfiguration("classpath:applicationConfig.xml")
@TestExecutionListeners(TransactionalTestExecutionListener.class)
public class TimetableServiceTest extends AbstractTestNGSpringContextTests {

    private TimetableDAO mockDao;

    private TimetableService service;

    private final LocalTime TIME_4PM = LocalTime.of(16, 0);
    private final Duration DUR_2H = Duration.ofHours(2);

    private User DEF_USER;
    private Offer DEF_OFFER;
    private Timetable DEF_TIMETABLE;
    private TimetableEntry DEF_ENTRY;

    @BeforeMethod
    void setup() {

        //This is used as @Inject and @InjectMock are not compatible with constructor injection...
        mockDao = mock(TimetableDAO.class);
        service = new TimetableServiceImpl(mockDao);

        DEF_USER = getUser("username");
        DEF_OFFER = getOffer(DEF_USER, "Yoga");
        DEF_TIMETABLE = getTimetable(DEF_USER, 2020, 1);
        DEF_ENTRY = getTimetableEntry(DEF_TIMETABLE, 20, TIME_4PM, DUR_2H);

        DEF_TIMETABLE.setId(1L);
        DEF_ENTRY.setId(1L);

        AtomicReference<Long> timetableId = new AtomicReference<>(1L);

        List<Timetable> list = List.of(
                getTimetable(DEF_USER, 2019, 1),
                getTimetable(DEF_USER, 2020, 1),
                getTimetable(getUser("username1"), 2020, 1),
                getTimetable(getUser("username2"), 2021, 1));

        when(mockDao.findAll()).thenReturn(list);

        when(mockDao.getById(anyLong())).thenAnswer(a -> {
            DEF_TIMETABLE.setId(a.getArgument(0));
            return DEF_TIMETABLE;
        });

        when(mockDao.create(any(Timetable.class))).thenAnswer((params) ->{
            params.getArgument(0, Timetable.class).setId(timetableId.get());
            timetableId.getAndSet(timetableId.get() + 1);
            return timetableId.get();
        });

        when(mockDao.findEntry(anyLong())).thenAnswer((param) -> {
            DEF_ENTRY.setId(param.getArgument(0));
            DEF_ENTRY.setTimetable(DEF_TIMETABLE);

            return DEF_ENTRY;
        });

        when(mockDao.createTimetable(any(User.class), anyInt(), anyInt())).thenAnswer((params) -> {
            DEF_TIMETABLE.setId(1L);
            DEF_TIMETABLE.setUser(params.getArgument(0));
            DEF_TIMETABLE.setYear(params.getArgument(1));
            DEF_TIMETABLE.setWeek(params.getArgument(2));

            return DEF_TIMETABLE;
        });

        when(mockDao.getTimetable(any(User.class), anyInt(), anyInt())).thenAnswer((params) -> {
            DEF_TIMETABLE.setUser(params.getArgument(0));
            DEF_TIMETABLE.setYear(params.getArgument(1));
            DEF_TIMETABLE.setWeek(params.getArgument(2));

            return DEF_TIMETABLE;
        });

        when(mockDao.createEntry(any(Timetable.class), any(Offer.class), any(LocalTime.class), any(Duration.class)))
                .thenAnswer((params) -> {
                    TimetableEntry entry = getTimetableEntry(params.getArgument(0), 20,
                            params.getArgument(2),
                            params.getArgument(3));

                    entry.setOffer(params.getArgument(1));
                    return entry;
                });

        when(mockDao.getAllTimetableEntries(anyLong())).thenAnswer((params) -> {
            TimetableEntry t1 = getTimetableEntry(DEF_TIMETABLE, 19, TIME_4PM, DUR_2H);
            TimetableEntry t2 = DEF_ENTRY;
            TimetableEntry t3 = getTimetableEntry(DEF_TIMETABLE, 21, TIME_4PM, Duration.ofHours(4L));

            DEF_TIMETABLE.setId(params.getArgument(0));

            t1.setId(2L);
            t2.setId(3L);
            t3.setId(4L);
            t1.setTimetable(DEF_TIMETABLE);
            t2.setTimetable(DEF_TIMETABLE);
            t3.setTimetable(DEF_TIMETABLE);

            return List.of(t1, t2, t3);
        });

        when(mockDao.getTimetableForCurrentWeek(any(User.class))).thenAnswer((params) -> {
            DEF_TIMETABLE.setUser(params.getArgument(0));
            DEF_TIMETABLE.setYear(LocalDate.now().getYear());
            DEF_TIMETABLE.setWeek(LocalDate.now().get(ChronoField.ALIGNED_WEEK_OF_YEAR));

            return DEF_TIMETABLE;
        });

        when(mockDao.getByIdWithUser(1)).thenAnswer((params) -> {
            DEF_TIMETABLE.setUser(DEF_USER);

            return DEF_TIMETABLE;
        });
    }

    @Test
    public void create() {
        verifyNoInteractions(mockDao);
        service.create(DEF_TIMETABLE);

        verify(mockDao, times(1)).create(DEF_TIMETABLE);
        verifyNoMoreInteractions(mockDao);
    }

    @Test
    public void findAllEmpty() {
        verifyNoInteractions(mockDao);
        when(mockDao.findAll()).thenReturn(List.of());

        verifyNoInteractions(mockDao);
        List<Timetable> results = service.findAll();

        verify(mockDao, times(1)).findAll();
        verifyNoMoreInteractions(mockDao);

        assertThat(results).isNotNull();
        assertThat(results.size()).isEqualTo(0);
    }

    @Test
    public void findAllNonEmpty() {
        verifyNoInteractions(mockDao);
        verifyNoInteractions(mockDao);
        List<Timetable> results = service.findAll();

        verify(mockDao, times(1)).findAll();
        verifyNoMoreInteractions(mockDao);

        assertThat(results).isNotNull();
        assertThat(results.size()).isEqualTo(4);

        assertThat(results.get(0).getYear()).isEqualTo(2019);
        assertThat(results.get(0).getWeek()).isEqualTo(1);

        assertThat(results.get(1).getYear()).isEqualTo(2020);
        assertThat(results.get(1).getWeek()).isEqualTo(1);

        assertThat(results.get(2).getYear()).isEqualTo(2020);
        assertThat(results.get(2).getWeek()).isEqualTo(1);

        assertThat(results.get(3).getYear()).isEqualTo(2021);
        assertThat(results.get(3).getWeek()).isEqualTo(1);
    }

    @Test
    public void getById() {
        verifyNoInteractions(mockDao);
        verifyNoInteractions(mockDao);
        Timetable result = service.getById(1L);

        verify(mockDao, times(1)).getById(1L);
        verifyNoMoreInteractions(mockDao);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getWeek()).isEqualTo(1);
        assertThat(result.getYear()).isEqualTo(2020);
    }

    @Test
    public void getByIdWithUser() {
        verifyNoInteractions(mockDao);

        Timetable result = service.getByIdWithUser(1L);

        verify(mockDao, times(1)).getByIdWithUser(1L);
        verifyNoMoreInteractions(mockDao);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getUser().getUsername()).isEqualTo("username");
        assertThat(result.getWeek()).isEqualTo(1);
        assertThat(result.getYear()).isEqualTo(2020);
    }

    @Test
    public void delete() {
        verifyNoInteractions(mockDao);
        service.delete(DEF_TIMETABLE);

        verify(mockDao, times(1)).delete(DEF_TIMETABLE);
        verifyNoMoreInteractions(mockDao);
    }


    @Test
    void createNewTimetable() {
        verifyNoInteractions(mockDao);
        service.createTimetable(DEF_USER, 2020, 40);

        verify(mockDao, times(1)).createTimetable(DEF_USER, 2020, 40);
        verifyNoMoreInteractions(mockDao);
    }

    @Test
    public void createEntry() {
        verifyNoInteractions(mockDao);
        service.createEntry(DEF_TIMETABLE, DEF_OFFER, LocalTime.of(16, 0), Duration.ofHours(2L));

        verify(mockDao, times(1))
                .createEntry(DEF_TIMETABLE, DEF_OFFER, LocalTime.of(16, 0), Duration.ofHours(2L));
        verifyNoMoreInteractions(mockDao);
    }

    @Test
    public void moveEntryThreeParams() {
        verifyNoInteractions(mockDao);
        service.moveEntry(DEF_ENTRY, LocalTime.of(18, 0), Duration.ofHours(1L));

        verify(mockDao, times(1))
                .moveEntry(DEF_ENTRY, LocalTime.of(18, 0), Duration.ofHours(1L));
        verifyNoMoreInteractions(mockDao);
    }

    @Test
    public void moveEntryTwoParams() {
        verifyNoInteractions(mockDao);
        service.moveEntry(DEF_ENTRY, LocalTime.of(8, 0));

        verify(mockDao, times(1))
                .moveEntry(DEF_ENTRY, LocalTime.of(8, 0));
        verifyNoMoreInteractions(mockDao);
    }

    @Test
    public void removeEntry() {
        verifyNoInteractions(mockDao);
        service.removeEntry(DEF_ENTRY);

        verify(mockDao, times(1))
                .removeEntry(DEF_ENTRY);
        verifyNoMoreInteractions(mockDao);
    }

    @Test
    public void updateEntry() {
        verifyNoInteractions(mockDao);
        service.updateEntry(DEF_ENTRY);

        verify(mockDao, times(1))
                .updateEntry(DEF_ENTRY);
        verifyNoMoreInteractions(mockDao);
    }

    @Test
    public void getSimpleTimetable() {
        verifyNoInteractions(mockDao);
        Timetable t = service.getTimetable(DEF_USER, 2020, 1);

        assertThat(t.getId()).isEqualTo(1L);
        assertThat(t.getUser()).isEqualTo(DEF_USER);
        assertThat(t.getYear()).isEqualTo(2020);
        assertThat(t.getWeek()).isEqualTo(1);
    }

    @Test
    public void findEntry() {
        verifyNoInteractions(mockDao);
        TimetableEntry entry = service.findEntry(5L);

        verify(mockDao, times(1))
                .findEntry(5L);
        verifyNoMoreInteractions(mockDao);

        assertThat(entry.getId()).isEqualTo(5L);
    }

    @Test
    public void getTimetableForCurrentWeek() {
        verifyNoInteractions(mockDao);
        Timetable timetable = service.getTimetableForCurrentWeek(DEF_USER);

        verify(mockDao, times(1))
                .getTimetableForCurrentWeek(DEF_USER);
        verifyNoMoreInteractions(mockDao);

        assertThat(timetable.getUser()).isEqualTo(DEF_USER);
        assertThat(timetable.getYear()).isEqualTo(LocalDate.now().getYear());
        assertThat(timetable.getWeek()).isEqualTo(LocalDate.now().get(ChronoField.ALIGNED_WEEK_OF_YEAR));
    }

    @Test
    public void getAllTimetableEntries() {
        verifyNoInteractions(mockDao);
        Timetable t = DEF_TIMETABLE;
        long id = t.getId();

        List<TimetableEntry> entries = service.getAllTimetableEntries(id);

        assertThat(entries.size()).isEqualTo(3);
        assertThat(entries.get(0).getTimetable()).isEqualTo(t);
        assertThat(entries.get(1).getTimetable()).isEqualTo(t);
        assertThat(entries.get(2).getTimetable()).isEqualTo(t);

        assertThat(entries.get(0).getId()).isEqualTo(2L);
        assertThat(entries.get(1).getId()).isEqualTo(3L);
        assertThat(entries.get(2).getId()).isEqualTo(4L);
    }
}
