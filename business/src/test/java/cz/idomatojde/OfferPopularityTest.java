package cz.idomatojde;

import cz.idomatojde.dao.OfferDao;
import cz.idomatojde.dao.UserDao;
import cz.idomatojde.entity.Category;
import cz.idomatojde.entity.Offer;
import cz.idomatojde.entity.User;
import cz.idomatojde.services.OfferPopularityService;
import cz.idomatojde.services.OfferPopularityServiceImpl;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static cz.idomatojde.TestObjects.getOffer;
import static cz.idomatojde.TestObjects.getUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Michal Hazdra
 */
public class OfferPopularityTest {

    private OfferDao mockOffers;

    private OfferPopularityService service;

    private List<Offer> allActiveOffers;
    private User user;
    private List<Offer> userPreferred;

    @BeforeMethod
    void setup() {
        //This is used as @Inject and @InjectMock are not compatible with constructor injection...
        UserDao mockUsers = mock(UserDao.class);
        mockOffers = mock(OfferDao.class);
        service = new OfferPopularityServiceImpl(mockUsers, mockOffers);

        user = getUser("test");
        allActiveOffers = getActiveOffers(user);
        userPreferred = getUserPreferredOffers(user);

        user.setSubscribedOffers(userPreferred);

        when(mockOffers.getActiveOffers()).thenReturn(allActiveOffers);
    }

    @Test
    void activeByITCategory() {
        //Act
        List<Offer> filtered = service.getPopularOffersByPreferredCategory(Category.IT);

        //Validate
        assertThat(filtered).isEmpty();
    }

    @Test
    void activeByEducationCategory() {
        //Setup
        //- Haskell is full so it is not returned!
        var manual = allActiveOffers.stream().filter(f -> f.getCategory() == Category.EDUCATION)
                .filter(f -> !f.getTitle().equals("Haskell")).toArray(Offer[]::new);

        //Act
        List<Offer> filtered = service.getPopularOffersByPreferredCategory(Category.EDUCATION);

        //Validate
        assertThat(filtered).containsOnly(manual);
    }

    @Test
    void mostPopularCategoryByUser() {
        //Setup
        var arr = new ArrayList<Offer>();
        arr.addAll(allActiveOffers);
        arr.addAll(userPreferred);
        when(mockOffers.getActiveOffers()).thenReturn(arr);
        when(mockOffers.getSubscribedOffers(user)).thenReturn(userPreferred);

        //- Haskell is full so it is not returned!
        var manual = allActiveOffers.stream().filter(f -> f.getCategory() == Category.EDUCATION)
                .filter(f -> !f.getTitle().equals("Haskell")).toArray(Offer[]::new);

        //Act
        List<Offer> filtered = service.getPopularOffersByPreferredCategory(user);

        //Validate
        assertThat(filtered).containsOnly(manual);
    }

    private List<Offer> getActiveOffers(User defOwner) {

        Offer o0 = getOffer(defOwner, "C#");
        Offer o1 = getOffer(defOwner, "Java");
        Offer o2 = getOffer(defOwner, "Python");
        Offer o3 = getOffer(defOwner, "Haskell");
        o3.setRegistered(10);

        Offer o4 = getOffer(defOwner, "Yoga");
        o4.setCategory(Category.SPORT);
        o4.setCapacity(5);

        Offer o5 = getOffer(defOwner, "Chess");
        o5.setCategory(Category.SPORT);
        o5.setCapacity(2);
        o5.setRegistered(1);

        Offer o6 = getOffer(defOwner, "Tic-Tac-Toe");
        o6.setCategory(Category.SPORT);

        Offer o7 = getOffer(defOwner, "DnD");
        o7.setCategory(Category.SPORT);


        Offer o8 = getOffer(defOwner, "Cooking");
        o8.setCategory(Category.LEISURE);

        Offer o9 = getOffer(defOwner, "Painting");
        o9.setCategory(Category.LEISURE);


        return List.of(o0, o1, o2, o3, o4, o5, o6, o7, o8, o9);
    }

    private List<Offer> getUserPreferredOffers(User defOwner) {

        Offer o0 = getOffer(defOwner, "C#");
        Offer o1 = getOffer(defOwner, "Java");
        Offer o2 = getOffer(defOwner, "Python");

        Offer o3 = getOffer(defOwner, "C++");
        o3.setCategory(Category.IT);

        Offer o4 = getOffer(defOwner, "Chatting");
        o4.setCategory(Category.LEISURE);

        return List.of(o0, o1, o2, o3, o4);
    }
}
