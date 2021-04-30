package cz.idomatojde;

import cz.idomatojde.dao.UserDao;
import cz.idomatojde.entity.User;
import cz.idomatojde.services.UserService;
import cz.idomatojde.services.UserServiceImpl;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static cz.idomatojde.TestObjects.getUser;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 * @author Michal Hazdra
 */
@ContextConfiguration("classpath:applicationConfig.xml")
@TestExecutionListeners(TransactionalTestExecutionListener.class)
public class UserServiceTest extends AbstractTestNGSpringContextTests {

    private UserDao mockDao;

    private UserService service;

    private User defUser;

    private static final String PHONE_NUM = "+420987654321";

    @BeforeMethod
    void setup() {

        //This is used as @Inject and @InjectMock are not compatible with constructor injection...
        mockDao = mock(UserDao.class);
        service = new UserServiceImpl(mockDao);

        defUser = getUser("username");

        doAnswer(params -> {
            defUser.setPhoneNumber(params.getArgument(1));
            return null;
        }).when(mockDao).addPhone(anyLong(), anyString());

        doAnswer(params -> {
            defUser.setCredits(defUser.getCredits() + params.getArgument(1, Integer.class));
            return null;
        }).when(mockDao).addCredits(anyLong(), anyInt());
    }

    @Test
    void addingPhoneNumber() {
        assertThat(defUser.getPhoneNumber()).isEqualTo("+420123456789");
        long userId = 1;
        verifyNoInteractions(mockDao);
        service.addPhone(userId, PHONE_NUM);

        verify(mockDao, times(1)).addPhone(userId, PHONE_NUM);
        verifyNoMoreInteractions(mockDao);

        assertThat(defUser.getPhoneNumber()).isEqualTo(PHONE_NUM);
    }

    @Test
    void addingCredits() {
        assertThat(defUser.getCredits()).isEqualTo(123);
        long userId = 1;

        verifyNoInteractions(mockDao);
        service.addCredits(userId, 1);

        verify(mockDao, times(1)).addCredits(userId, 1);
        verifyNoMoreInteractions(mockDao);

        assertThat(defUser.getCredits()).isEqualTo(124);
    }
}
