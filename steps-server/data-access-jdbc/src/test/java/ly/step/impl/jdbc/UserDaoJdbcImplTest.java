package ly.step.impl.jdbc;

import static org.junit.Assert.*;
import ly.step.User;

import org.junit.Before;
import org.junit.Test;

public class UserDaoJdbcImplTest extends AbstractDaoTestCase {

    private UserDaoJdbcImpl userDaoJdbcImpl = new UserDaoJdbcImpl();

    @Before
    public void setup() throws Exception {
	dbInit("User");
	for (long i = 0; i < 10; i++) {
	    getJdbcTemplate().update(
		    "insert into User (id, name) values (?, ?)",
		    new Object[] { 0, "User-" + i });
	}
	userDaoJdbcImpl.setDataSource(getDataSource());
    }

    @Test
    public void testCreate() {
	User user = User.newBuilder()
	        .setName("Dante")
	        .build();
	User actual = userDaoJdbcImpl.create(user);
	assertNotNull(actual);
	assertEquals(user.getName(), actual.getName());
	assertTrue(actual.getId() > 0);

	assertEquals(
	        1,
	        getJdbcTemplate().queryForInt(
	                "select count(id) from user where name = ?",
	                new Object[] { user.getName() }));
    }

    @Test
    public void testFindById() {
	for (long i = 1; i <= 10; i++) {
	    User user = userDaoJdbcImpl.findById(i);
	    assertNotNull(user);
	    assertEquals(i, user.getId());
	    assertEquals("User-" + (i - 1), user.getName());
	}

    }
}
