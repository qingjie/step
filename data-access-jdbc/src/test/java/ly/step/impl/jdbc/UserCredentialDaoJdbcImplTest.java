package ly.step.impl.jdbc;

import static org.junit.Assert.*;

import java.util.Map;

import ly.step.UserCredential;

import org.junit.Before;
import org.junit.Test;

import com.google.common.hash.Hashing;

public class UserCredentialDaoJdbcImplTest extends AbstractDaoTestCase {

    private UserCredentialDaoJdbcImpl userCredentialDaoJdbcImpl = new UserCredentialDaoJdbcImpl();

    @Before
    public void setup() throws Exception {
	dbInit("user_credential");
	getJdbcTemplate()
	        .update("insert into user_credential (user_id, user_name, password_hash) values (?,?,?)",
	                new Object[] {
	                        123L,
	                        "dante",
	                        Hashing.sha1().hashString("hello,world.")
	                                .toString()
	                });
	userCredentialDaoJdbcImpl.setDataSource(getDataSource());
    }

    @Test
    public void testFindByUsername() {
	UserCredential actual = userCredentialDaoJdbcImpl
	        .findByUsername("dante");
	assertNotNull(actual);
	assertEquals(123L, actual.getUserId());
	assertEquals("dante", actual.getUsername());
	assertEquals(Hashing.sha1().hashString("hello,world.").toString(),
	        actual.getPasswordHash());

	assertNull(userCredentialDaoJdbcImpl.findByUsername("Ken"));
    }

    @Test
    public void testSave() {
	userCredentialDaoJdbcImpl.save(UserCredential.newBuilder()
	        .setPassword("password")
	        .setUserId(789L)
	        .setUsername("ken")
	        .build());
	Map<String, Object> actual = getJdbcTemplate().queryForMap(
	        "select * from user_credential where user_name = 'ken'");
	assertNotNull(actual);
	assertEquals("ken", actual.get("user_name"));
	assertEquals(789L, actual.get("user_id"));
	assertEquals(Hashing.sha1().hashString("password").toString(),
	        actual.get("password_hash"));
	assertFalse(actual.containsKey("password"));
    }

}
