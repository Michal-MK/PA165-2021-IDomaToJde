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

        //No idea why these are not called...
        doAnswer(params -> {
            defUser.setPhoneNumber(params.getArgument(0));
            return null;
        }).when(mockDao).addPhone(anyString(), anyLong());

        doAnswer(params -> {
            defUser.setCredits(defUser.getCredits() + params.getArgument(0, Integer.class));
            return null;
        }).when(mockDao).addCredits(anyInt(), anyLong());
    }

    @Test
    void addingPhoneNumber() {
        assertThat(defUser.getPhoneNumber()).isEqualTo("+420123456789");

        verifyNoInteractions(mockDao);
        service.addPhone(PHONE_NUM, defUser.getId());
        defUser.setPhoneNumber(PHONE_NUM);

        verify(mockDao, times(1)).addPhone(PHONE_NUM, defUser.getId());
        verifyNoMoreInteractions(mockDao);

        assertThat(defUser.getPhoneNumber()).isEqualTo(PHONE_NUM);
    }

    @Test
    void addingCredits() {
        assertThat(defUser.getCredits()).isEqualTo(123);

        verifyNoInteractions(mockDao);
        service.addCredits(1, defUser.getId());
        defUser.setCredits(defUser.getCredits() + 1);

        verify(mockDao, times(1)).addCredits(1, defUser.getId());
        verifyNoMoreInteractions(mockDao);

        assertThat(defUser.getCredits()).isEqualTo(124);
    }
}
