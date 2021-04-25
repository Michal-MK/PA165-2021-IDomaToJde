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

import static cz.idomatojde.TestMocks.mockUser;
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
import static org.mockito.Mockito.when;

/**
 * @author Michal Hazdra
 */
@ContextConfiguration("classpath:applicationConfig.xml")
@TestExecutionListeners(TransactionalTestExecutionListener.class)
public class UserServiceTest extends AbstractTestNGSpringContextTests {

    private UserDao mockDao;

    private UserService service;

    private User DEF_USER;

    final String PHONE_NUM = "+420987654321";

    @BeforeMethod
    void setup() {

        //This is used as @Inject and @InjectMock are not compatible with constructor injection...
        mockDao = mock(UserDao.class);
        service = new UserServiceImpl(mockDao);

        doAnswer(params -> {
            when(DEF_USER.getPhoneNumber()).thenReturn(params.getArgument(0));
            return null;
        }).when(mockDao).addPhone(anyString(), anyLong());

        doAnswer(params -> {
            int currentCredits = DEF_USER.getCredits();
            when(DEF_USER.getCredits()).thenReturn(currentCredits + params.getArgument(0, Integer.class));
            return null;
        }).when(mockDao).addCredits(anyInt(), anyLong());


        DEF_USER = mockUser("username");
    }

    @Test
    void addingPhoneNumber() {
        assertThat(DEF_USER.getPhoneNumber()).isEqualTo("+420123456789");

        verifyNoInteractions(mockDao);
        service.addPhone(PHONE_NUM, DEF_USER.getId());

        verify(mockDao, times(1)).addPhone(PHONE_NUM, DEF_USER.getId());
        verifyNoMoreInteractions(mockDao);

        assertThat(DEF_USER.getPhoneNumber()).isEqualTo(PHONE_NUM);
    }

    @Test
    void addingCredits() {
        assertThat(DEF_USER.getCredits()).isEqualTo(123);

        verifyNoInteractions(mockDao);
        service.addCredits(1, DEF_USER.getId());

        verify(mockDao, times(1)).addCredits(1, DEF_USER.getId());
        verifyNoMoreInteractions(mockDao);

        assertThat(DEF_USER.getCredits()).isEqualTo(124);
    }
}
