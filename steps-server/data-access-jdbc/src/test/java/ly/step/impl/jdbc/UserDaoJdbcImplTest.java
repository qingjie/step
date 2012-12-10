package ly.step.impl.jdbc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


import ly.step.User;

import org.junit.Before;
import org.junit.Test;

public class UserDaoJdbcImplTest extends AbstractDaoTestCase {

    private UserDaoJdbcImpl userDaoJdbcImpl = new UserDaoJdbcImpl();

    @Before
    public void setup() throws Exception {
	dbInit("User");
	for (long i = 0; i < 100; i++) {
	    getJdbcTemplate().update("insert into User values (?, ?)",
		    new Object[] { i, "User-" + i });
	}
	userDaoJdbcImpl.setDataSource(getDataSource());
    }

    @Test
    public void testCreate() {
	for (long i = 0; i < 10; i++) {
	    User user = userDaoJdbcImpl.findById(i);
	    assertNotNull(user);
	    assertEquals(i, user.getId());
	    assertEquals("User-" + i, user.getName());
	}

    }

}
