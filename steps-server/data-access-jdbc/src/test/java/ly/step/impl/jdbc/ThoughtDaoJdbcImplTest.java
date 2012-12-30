package ly.step.impl.jdbc;

import static org.junit.Assert.*;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import ly.step.Thought;

import org.junit.Before;
import org.junit.Test;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;

public class ThoughtDaoJdbcImplTest extends AbstractDaoTestCase {

    private ThoughtDaoJdbcImpl thoughtDaoJdbcImpl = new ThoughtDaoJdbcImpl();

    private TreeMap<Long, Object[]> thoughtList = new TreeMap<Long, Object[]>();
    private List<Long> thoughtIdList = new ArrayList<Long>();

    private void assertWithId(long i, Thought thought) {
	assertNotNull(thought);
	assertEquals(i, thought.getId());
	assertEquals(thoughtList.get(i)[0], thought.getAuthorId());
	assertEquals(thoughtList.get(i)[1], thought.getText());
	assertEquals(thoughtList.get(i)[2], thought.getCreatedAt().getTime());
    }

    @Before
    public void setup() throws DataAccessException, IOException {
	dbInit("Thought");
	getTransactionTemplate().execute(
	        new TransactionCallbackWithoutResult() {

		    @Override
		    protected void doInTransactionWithoutResult(
		            TransactionStatus status) {
		        for (long i = 0; i < 10; i++) {
			    Object[] thought = new Object[] {
			            i * 10,
			            "text-" + i,
			            i * 10000
			    };
			    getJdbcTemplate().update(
			            "insert into thought values(?,?,?,?)",
			            new Object[] {
			                    0,
			                    thought[0],
			                    thought[1],
			                    thought[2]
			            });
			    thoughtList.put(getJdbcTemplate().queryForLong(
			            "select @@IDENTITY"), thought);
		        }
		    }
	        });
	thoughtIdList.addAll(thoughtList.navigableKeySet());
	thoughtDaoJdbcImpl.setDataSource(getDataSource());
    }

    @Test
    public void testFindById() {
	for (Long i : thoughtList.navigableKeySet()) {
	    Thought thought = thoughtDaoJdbcImpl.findById(i);
	    assertWithId(i, thought);
	}
    }

    @Test
    public void testFindByUser() {
	List<Long> result = thoughtDaoJdbcImpl.findByUser(20, 0, 0, 10);
	assertEquals(1, result.size());
	assertEquals(3, result.get(0).intValue());

	getJdbcTemplate().update("update Thought set author_id = 2");

	result = thoughtDaoJdbcImpl.findByUser(2, thoughtIdList.get(5), 0, 10);
	assertEquals(4, result.size());
	for (int i = 9; i > 5; i--) {
	    assertEquals((long) thoughtIdList.get(i), result.get(9 - i)
		    .intValue());
	}

	result = thoughtDaoJdbcImpl.findByUser(2, 0, thoughtIdList.get(7), 10);
	assertEquals(8, result.size());
	for (int i = 7; i >= 0; i--) {
	    assertEquals((long) thoughtIdList.get(i), result.get(7 - i)
		    .intValue());
	}

	result = thoughtDaoJdbcImpl.findByUser(2, thoughtIdList.get(4),
	        thoughtIdList.get(7), 10);
	assertEquals(3, result.size());
	for (int i = 7; i > 4; i--) {
	    assertEquals((long) thoughtIdList.get(i), result.get(7 - i)
		    .intValue());
	}
    }

    @Test
    public void testSave() {
	Thought thought = Thought.newBuilder()
	        .setAuthorId(100)
	        .setCreatedAt(new Date())
	        .setText("Hello, World!")
	        .build();

	Thought newThought = thoughtDaoJdbcImpl.save(thought);

	assertTrue(newThought.getId() > 0);

	Map<String, Object> row = getJdbcTemplate().queryForMap(
	        "select * from thought where id = ?",
	        new Object[] { newThought.getId() });

	assertEquals(newThought.getId(),
	        ((BigInteger) row.get("id")).longValue());
	assertEquals(thought.getAuthorId(),
	        row.get("author_id"));
	assertEquals(thought.getText(), row.get("text"));
	assertEquals(thought.getCreatedAt().getTime(),
	        row.get("created_at"));
    }
}
