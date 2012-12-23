package ly.step.impl.jdbc;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class UserToThoughtDaoJdbcImplTest extends AbstractDaoTestCase {

    private UserToThoughtDaoJdbcImpl userToThoughtDaoJdbcImpl = new UserToThoughtDaoJdbcImpl();

    @Before
    public void setup() throws Exception {
	dbInit("User_To_Thought");
	for (long i = 0; i < 10; i++) {
	    for (long j = 0; j < 10; j++) {
		getJdbcTemplate().update(
			"insert into User_To_Thought values (?, ?, ?)",
			new Object[] { i, j + i * 10, new Date().getTime() });
	    }
	}
	userToThoughtDaoJdbcImpl.setDataSource(getDataSource());
    }

    @Test
    public void testFindByUserId() {
	for (int i = 0; i < 10; i++) {
	    List<Long> result = userToThoughtDaoJdbcImpl.findByUserId(i, 0, 0,
		    20);
	    assertEquals(10, result.size());
	    for (int j = 0; j < 10; j++) {
		// descending order
		assertEquals(9 + i * 10 - j, result.get(j).intValue());
	    }
	}
    }

    @Test
    public void testSave() {
	userToThoughtDaoJdbcImpl.save(2000, 4000);
	long actual = getJdbcTemplate()
		.queryForLong(
			"select thought_id from user_to_thought where user_id = ? and thought_id = ?",
			new Object[] { 2000, 4000 });

	assertEquals(4000L, actual);
    }

}
