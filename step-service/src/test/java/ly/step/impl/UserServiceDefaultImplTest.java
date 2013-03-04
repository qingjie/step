package ly.step.impl;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.Date;

import ly.step.AccessToken;
import ly.step.AuthenticationException;
import ly.step.User;
import ly.step.UserCredential;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.test.util.ReflectionTestUtils;

import com.google.common.base.Strings;
import com.google.common.hash.Hashing;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceDefaultImplTest {
    private static final UserCredential SAMPLE_USER_CREDENTIAL = UserCredential
	    .newBuilder()
	    .setPassword("abc")
	    .setUsername("dante")
	    .setUserId(123L)
	    .build();
    private UserServiceDefaultImpl userServiceDefaultImpl = new UserServiceDefaultImpl();
    @Mock
    private UserDao userDao;
    @Mock
    private FriendDao friendDao;
    @Mock
    private AccessTokenDao accessTokenDao;
    @Mock
    private UserCredentialDao userCredentialDao;

    private static final User SAMPLE_USER = User.newBuilder()
	    .setId(123L)
	    .setName("dante")
	    .build();

    @Before
    public void setup() throws Exception {
	ReflectionTestUtils
	        .setField(userServiceDefaultImpl, "userDao", userDao);
	ReflectionTestUtils.setField(userServiceDefaultImpl, "friendDao",
	        friendDao);
	ReflectionTestUtils.setField(userServiceDefaultImpl, "accessTokenDao",
	        accessTokenDao);
	ReflectionTestUtils.setField(userServiceDefaultImpl,
	        "userCredentialDao", userCredentialDao);
    }

    @Test
    public void testAllocateAccessToken() {
	when(accessTokenDao.create(any(AccessToken.class))).thenAnswer(
	        new Answer<AccessToken>() {

		    @Override
		    public AccessToken answer(InvocationOnMock invocation)
		            throws Throwable {
		        return (AccessToken) invocation.getArguments()[0];
		    }
	        });

	AccessToken actual = userServiceDefaultImpl.allocateAccessToken(123L);
	assertNotNull(actual);
	assertNotNull(actual.getAccessToken());
	assertNotNull(actual.getCreatedAt());
	assertEquals(123L, actual.getUserId());
	assertTrue(!Strings.isNullOrEmpty(actual.getAccessToken()));
	assertEquals(
	        UserServiceDefaultImpl.ACCESS_TOKEN_DEFAULT_EXPIRE_IN_SECONDS,
	        actual.getExpiredInSecond());

	ArgumentCaptor<AccessToken> argumentCaptor = ArgumentCaptor
	        .forClass(AccessToken.class);
	verify(accessTokenDao).create(argumentCaptor.capture());

	assertEquals(actual, argumentCaptor.getValue());
    }

    @Test
    public void testAuth() throws AuthenticationException {
	when(userCredentialDao.findByUsername("dante"))
	        .thenReturn(
	                SAMPLE_USER_CREDENTIAL);
	User user = User.newBuilder().setId(123L).setName("dante").build();
	when(userDao.findById(123L))
	        .thenReturn(
	                user);
	User actual = userServiceDefaultImpl.auth("dante", "abc");
	assertEquals(user, actual);
    }

    @Test(expected = AuthenticationException.class)
    public void testAuthWithCorrectCredentialButUserNotExists()
	    throws AuthenticationException {
	when(userCredentialDao.findByUsername("dante"))
	        .thenReturn(SAMPLE_USER_CREDENTIAL);
	when(userDao.findById(123L)).thenReturn(null);
	userServiceDefaultImpl.auth("dante", "abc");
    }

    @Test(expected = AuthenticationException.class)
    public void testAuthWithIncorrectCredential()
	    throws AuthenticationException {
	when(userCredentialDao.findByUsername("dante"))
	        .thenReturn(
	                SAMPLE_USER_CREDENTIAL);

	userServiceDefaultImpl.auth("dante", "cba");
    }

    @Test(expected = AuthenticationException.class)
    public void testAuthWithNoneExistsCredential()
	    throws AuthenticationException {
	when(userCredentialDao.findByUsername("dante")).thenReturn(null);
	userServiceDefaultImpl.auth("dante", "123L");
    }

    @Test
    public void testFindByAccessCode() {
	when(accessTokenDao.findByAccessToken("access-token"))
	        .thenReturn(AccessToken.newBuilder()
	                .setAccessToken("access-token")
	                .setCreatedAt(new Date())
	                .setExpiredInSecond(1000).setUserId(123L)
	                .build());
	when(userDao.findById(123L)).thenReturn(SAMPLE_USER);

	User user = userServiceDefaultImpl.findByAccessToken("access-token");
	assertEquals(SAMPLE_USER, user);
    }

    @Test(expected = AuthenticationException.class)
    public void testFindByAccessTokenWhenUserDoesNotExists() {
	when(accessTokenDao.findByAccessToken("access-token"))
	        .thenReturn(AccessToken.newBuilder()
	                .setUserId(123L)
	                .setExpiredInSecond(1000)
	                .setCreatedAt(new Date())
	                .build());
	when(userDao.findById(123L)).thenReturn(null);
	userServiceDefaultImpl.findByAccessToken("access-token");
    }

    @Test(expected = AuthenticationException.class)
    public void testFindByExpiredAccessToken() {
	when(accessTokenDao.findByAccessToken("access-token"))
	        .thenReturn(AccessToken.newBuilder()
	                .setCreatedAt(new Date())
	                .setExpiredInSecond(0)
	                .build());
	userServiceDefaultImpl.findByAccessToken("access-token");
    }

    @Test
    public void testFindById() {
	when(userDao.findById(123L))
	        .thenReturn(SAMPLE_USER);

	User actual = userServiceDefaultImpl.findById(123L);
	assertEquals(SAMPLE_USER, actual);
    }

    @Test(expected = AuthenticationException.class)
    public void testFindByInvalidAccessToken() {
	when(accessTokenDao.findByAccessToken("access-token"))
	        .thenReturn(null);

	userServiceDefaultImpl.findByAccessToken("access-token");
    }

    @Test(expected = UserNotFoundException.class)
    public void testFindByInvalidId() throws AuthenticationException {
	when(userDao.findById(123L)).thenReturn(null);
	userServiceDefaultImpl.findById(123L);
    }

    @Test
    public void testMakeFriend() {
	userServiceDefaultImpl.makeFriend(123L, 321L);
	verify(friendDao).save(eq(123L), eq(321L), any(Date.class));
    }

    @Test
    public void testRegister() {
	when(userDao.create(SAMPLE_USER))
	        .thenReturn(SAMPLE_USER);
	User actual = userServiceDefaultImpl.register(SAMPLE_USER, "password");
	assertEquals(SAMPLE_USER, actual);
	ArgumentCaptor<UserCredential> argumentCaptor = ArgumentCaptor
	        .forClass(UserCredential.class);
	verify(userCredentialDao).save(argumentCaptor.capture());
	assertEquals(actual.getId(), argumentCaptor.getValue().getUserId());
	assertEquals(actual.getName(), argumentCaptor.getValue().getUsername());
	assertEquals(Hashing.sha1().hashString("password").toString(),
	        argumentCaptor.getValue().getPasswordHash());
    }

    @Test
    public void testSeparate() {
	userServiceDefaultImpl.separate(123L, 312L);
	verify(friendDao).remove(123L, 312L);
    }

}
