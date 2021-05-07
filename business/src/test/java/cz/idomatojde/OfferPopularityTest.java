package cz.idomatojde;

import cz.idomatojde.dao.CategoryDao;
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
import java.util.stream.Collectors;

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
        CategoryDao mockCategories = mock(CategoryDao.class);
        mockOffers = mock(OfferDao.class);
        service = new OfferPopularityServiceImpl(mockUsers, mockCategories, mockOffers);

        user = getUser("test");
        allActiveOffers = getActiveOffers(user);
        userPreferred = getUserPreferredOffers(user);

        user.setSubscribedOffers(userPreferred);

        when(mockOffers.getActiveOffers()).thenReturn(allActiveOffers);
    }

    @Test
    void activeByITCategory() {
        //Act
        List<Offer> filtered = service.getPopularOffersByPreferredCategory(getCategory("IT"));

        //Validate
        assertThat(filtered).isEmpty();
    }

    @Test
    void activeByEducationCategory() {
        //Setup
        //- Haskell is full so it is not returned!
        var manual = allActiveOffers.stream().filter(f -> f.getCategory().getName().equals("Education"))
                .filter(f -> !f.getTitle().equals("Haskell")).toArray(Offer[]::new);

        //Act
        List<Offer> filtered = service.getPopularOffersByPreferredCategory(getCategory("Education"));

        //Validate
        assertThat(filtered).containsOnly(manual);
    }

    @Test
    void mostPopularCategoryByUser() {
        //Setup
        var arr = new ArrayList<Offer>();
        arr.addAll(allActiveOffers);
        arr.addAll(userPreferred.stream().skip(3).collect(Collectors.toList()));
        when(mockOffers.getActiveOffers()).thenReturn(arr);
        when(mockOffers.getSubscribedOffers(user)).thenReturn(userPreferred);

        //- Haskell is full so it is not returned!
        var manual = allActiveOffers.stream().filter(f -> f.getCategory().getName().equals("Education"))
                .filter(f -> !f.getTitle().equals("Haskell")).toArray(Offer[]::new);

        //Act
        List<Offer> filtered = service.getPopularOffersByPreferredCategory(user);

        //Validate
        assertThat(filtered).containsOnly(manual);
    }

    private List<Offer> getActiveOffers(User defOwner) {
        Category edu = getCategory("Education");

        Offer o0 = getOffer(defOwner, edu, "C#");
        Offer o1 = getOffer(defOwner, edu, "Java");
        Offer o2 = getOffer(defOwner, edu, "Python");
        Offer o3 = getOffer(defOwner, edu, "Haskell");
        o3.setRegistered(10);

        Category sport = getCategory("Sport");
        Offer o4 = getOffer(defOwner, sport, "Yoga");
        o4.setCapacity(5);

        Offer o5 = getOffer(defOwner, sport, "Chess");
        o5.setCapacity(2);
        o5.setRegistered(1);

        Offer o6 = getOffer(defOwner, sport, "Tic-Tac-Toe");

        Offer o7 = getOffer(defOwner, sport, "DnD");

        Category leis = getCategory("Leisure");
        Offer o8 = getOffer(defOwner, leis, "Cooking");

        Offer o9 = getOffer(defOwner, leis, "Painting");

        return List.of(o0, o1, o2, o3, o4, o5, o6, o7, o8, o9);
    }

    private Category getCategory(String name) {
        Category cat = new Category();
        cat.setName(name);

        return cat;
    }

    private List<Offer> getUserPreferredOffers(User defOwner) {
        Category edu = getCategory("Education");
        Offer o0 = getOffer(defOwner, edu, "C#");
        Offer o1 = getOffer(defOwner, edu, "Java");
        Offer o2 = getOffer(defOwner, edu, "Python");

        Category it = getCategory("IT");
        Offer o3 = getOffer(defOwner, it, "C++");

        Category leis = getCategory("Leisure");
        Offer o4 = getOffer(defOwner, leis, "Chatting");

        return List.of(o0, o1, o2, o3, o4);
    }
}
