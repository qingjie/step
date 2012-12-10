package ly.step.impl.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import ly.step.Thought;
import ly.step.impl.ThoughtDao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

@Repository
public class ThoughtDaoJdbcImpl extends JdbcDaoSupport implements ThoughtDao {

    private static final String SQL_FIND_BY_USER_PREFIX = "select id from Thought"
	    + " where author_id = ? ";
    private static final String SQL_FIND_BY_USER_POSTFIX = " order by id desc limit ?";
    private static final String SQL_FIND_BY_ID = "select * from Thought " +
	    "where id = ? order by id desc";
    private static final String SQL_INSERT = "insert into Thought " +
	    "(id, `text`, author_id, created_at) values (?, ?, ?, ?)";

    @Override
    public Thought findById(long id) {
	return this.getJdbcTemplate().queryForObject(
	        SQL_FIND_BY_ID, new Object[] { id },
	        new RowMapper<Thought>() {

		    @Override
		    public Thought mapRow(ResultSet rs, int rowNum)
		            throws SQLException {
		        return Thought
		                .newBuilder()
		                .setId(rs.getLong("id"))
		                .setAuthorId(rs.getLong("author_id"))
		                .setText(rs.getString("text"))
		                .setCreatedAt(
		                        new Date(rs.getLong("created_at")))
		                .build();
		    }

	        });
    }

    @Override
    public List<Long> findByUser(long userId, long sinceId, long maxId,
	    int limit) {
	StringBuilder query = new StringBuilder(SQL_FIND_BY_USER_PREFIX);
	List<Object> params = new LinkedList<Object>();
	params.add(userId);
	if (sinceId > 0) {
	    query.append(" and id > ?");
	    params.add(sinceId);
	}
	if (maxId > 0) {
	    query.append(" and id <= ?");
	    params.add(maxId);
	}
	query.append(SQL_FIND_BY_USER_POSTFIX);
	params.add(limit);

	return this
	        .getJdbcTemplate()
	        .queryForList(
	                query.toString(),
	                params.toArray(),
	                Long.class);
    }

    @Override
    public Thought save(Thought thought) {
	// TODO: introducing a id generator will be help. :-)
	long id = System.currentTimeMillis();
	getJdbcTemplate().update(SQL_INSERT, new Object[] {
	        id,
	        thought.getText(),
	        thought.getAuthorId(),
	        thought.getCreatedAt().getTime()
	});
	return thought.toBuilder().setId(id).build();
    }
}
