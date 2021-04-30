package cz.idomatojde;

import cz.idomatojde.dao.OfferDao;
import cz.idomatojde.entity.Offer;
import cz.idomatojde.entity.User;
import cz.idomatojde.services.OfferService;
import cz.idomatojde.services.OfferServiceImpl;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static cz.idomatojde.TestObjects.getUser;
import static cz.idomatojde.TestObjects.getOffer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Jiri Vrbka
 */
public class OfferServiceTest {

    private OfferDao mockOffers;

    private OfferService service;

    private User user;

    @BeforeMethod
    void setup() {
        //This is used as @Inject and @InjectMock are not compatible with constructor injection...
        mockOffers = mock(OfferDao.class);
        service = new OfferServiceImpl(mockOffers);
        user = getUser("test");

        when(mockOffers.getActiveOffers()).thenReturn(getOffers(user));
    }

    @Test
    public void findByUser() {
        // Setup
        var expectedOffers = getOffers(user);
        when(mockOffers.findByUser(user)).thenReturn(expectedOffers);

        // Act
        var userOffers = service.findByUser(user);

        // Validate
        assertThat(userOffers).isEqualTo(expectedOffers);
    }

    @Test
    public void findByUserWithoutOffers() {
        // Setup
        getOffers(user);
        when(mockOffers.findByUser(user)).thenReturn(new ArrayList<>());

        // Act
        var userOffers = service.findByUser(user);

        // Validate
        assertThat(userOffers).isEmpty();
    }

    @Test
    public void getActiveOffers() {
        // Setup
        var expectedOffers = getOffers(user);
        when(mockOffers.getActiveOffers()).thenReturn(expectedOffers);

        // Act
        var activeOffers = service.getActiveOffers();

        // Validate
        assertThat(activeOffers).isEqualTo(expectedOffers);
    }

    @Test
    public void getActiveOffersNoOffers() {
        // Setup
        getOffers(user);
        when(mockOffers.getActiveOffers()).thenReturn(new ArrayList<>());

        // Act
        var activeOffers = service.getActiveOffers();

        // Validate
        assertThat(activeOffers).isEmpty();
    }

    private List<Offer> getOffers(User defOwner) {

        Offer o0 = getOffer(defOwner, "C#");
        Offer o1 = getOffer(defOwner, "Java");
        Offer o2 = getOffer(defOwner, "Python");
        Offer o3 = getOffer(defOwner, "Haskell");

        return List.of(o0, o1, o2, o3);
    }
}
