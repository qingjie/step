package ly.step.impl.jdbc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import ly.step.Thought;

import org.junit.Before;
import org.junit.Test;
import org.springframework.dao.DataAccessException;

public class ThoughtDaoJdbcImplTest extends AbstractDaoTestCase {

    private ThoughtDaoJdbcImpl thoughtDaoJdbcImpl = new ThoughtDaoJdbcImpl();

    private void assertWithIndex(int i, Thought thought) {
	assertNotNull(thought);
	assertEquals(i, thought.getId());
	assertEquals(i * 10, thought.getAuthorId());
	assertEquals("text-" + i, thought.getText());
	assertEquals(i * 10000, thought.getCreatedAt().getTime());
    }

    @Before
    public void setup() throws DataAccessException, IOException {
	dbInit("Thought");

	for (int i = 0; i < 10; i++) {
	    getJdbcTemplate().update("insert into thought values(?,?,?,?)",
		    new Object[] {
		            i,
		            i * 10,
		            "text-" + i,
		            i * 10000
		    });
	}

	thoughtDaoJdbcImpl.setDataSource(getDataSource());
    }

    @Test
    public void testFindById() {
	for (int i = 0; i < 10; i++) {
	    Thought thought = thoughtDaoJdbcImpl.findById(i);
	    assertWithIndex(i, thought);
	}
    }

    @Test
    public void testFindByUser() {
	List<Long> result = thoughtDaoJdbcImpl.findByUser(20, 0, 0, 10);
	assertEquals(1, result.size());
	assertEquals(2, result.get(0).intValue());

	getJdbcTemplate().update("update Thought set author_id = 2");

	result = thoughtDaoJdbcImpl.findByUser(2, 5, 0, 10);
	assertEquals(4, result.size());
	for (int i = 9; i > 5; i--) {
	    assertEquals(i, result.get(9 - i).intValue());
	}

	result = thoughtDaoJdbcImpl.findByUser(2, 0, 7, 10);
	assertEquals(8, result.size());
	for (int i = 7; i >= 0; i--) {
	    assertEquals(i, result.get(7 - i).intValue());
	}

	result = thoughtDaoJdbcImpl.findByUser(2, 4, 7, 10);
	assertEquals(3, result.size());
	for (int i = 7; i > 4; i--) {
	    assertEquals(i, result.get(7 - i).intValue());
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

	assertEquals(newThought.getId(), row.get("id"));
	assertEquals(thought.getAuthorId(), row.get("author_id"));
	assertEquals(thought.getText(), row.get("text"));
	assertEquals(thought.getCreatedAt().getTime(), row.get("created_at"));
    }
}
