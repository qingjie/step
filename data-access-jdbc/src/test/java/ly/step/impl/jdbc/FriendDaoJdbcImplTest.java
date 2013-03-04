package ly.step.impl.jdbc;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class FriendDaoJdbcImplTest extends AbstractDaoTestCase {

    private FriendDaoJdbcImpl friendDaoJdbcImpl = new FriendDaoJdbcImpl();

    private int count(long userId, long friendId) {
	return getJdbcTemplate()
	        .queryForInt(
	                "select count(*) from friend where user_id = ? and friend_id = ?",
	                new Object[] { userId, friendId });
    }

    @Before
    public void setup() throws Exception {
	dbInit("Friend");
	List<Object[]> sampleData = new ArrayList<Object[]>(10);
	for (int i = 0; i < 10; i++) {
	    for (int j = 0; j < 10; j++) {
		sampleData
		        .add(new Object[] { i, j, System.currentTimeMillis() });
	    }
	}
	getJdbcTemplate()
	        .batchUpdate(
	                "insert into friend (user_id, friend_id, created_at) values (?,?,?)",
	                sampleData);
	friendDaoJdbcImpl.setDataSource(getDataSource());
    }

    @Test
    public void testFindFriendsByUserId() {
	List<Long> actual = friendDaoJdbcImpl.findFriendsByUserId(1L);
	assertEquals(10, actual.size());
	for (int i = 0; i < 10; i++) {
	    assertEquals((long) i, (long) actual.get(i));
	}
    }

    @Test
    public void testRemove() {
	assertEquals(1, count(1L, 2L));
	friendDaoJdbcImpl.remove(1L, 2L);
	assertEquals(0, count(1L, 2L));
    }

    @Test
    public void testSave() {
	assertEquals(0, count(100L, 200L));
	friendDaoJdbcImpl.save(100L, 200L, new Date());
	assertEquals(1, count(100L, 200L));
    }

}
