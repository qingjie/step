package ly.step.impl.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import ly.step.Thought;
import ly.step.impl.ThoughtDao;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mysql.jdbc.Statement;

@Repository
public class ThoughtDaoJdbcImpl extends JdbcDaoSupport implements ThoughtDao {

    private static final String SQL_FIND_BY_USER_PREFIX = "select id from Thought"
	    + " where author_id = ? ";
    private static final String SQL_FIND_BY_USER_POSTFIX = " order by id desc limit ?";
    private static final String SQL_FIND_BY_ID = "select * from Thought " +
	    "where id = ? order by id desc";
    private static final String SQL_INSERT = "insert into Thought " +
	    "(id, `text`, author_id, created_at) values (?, ?, ?, ?)";

    /*
     * (non-Javadoc)
     * 
     * @see ly.step.impl.ThoughtDao#findById(long)
     */
    @Override
    public Thought findById(long id) {
	return DataAccessUtils.requireSingleResult(this.getJdbcTemplate()
	        .query(
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
		                                new Date(rs
		                                        .getLong("created_at")))
		                        .build();
		            }

	                }));
    }

    /*
     * (non-Javadoc)
     * 
     * @see ly.step.impl.ThoughtDao#findByUser(long, long, long, int)
     */
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

    /*
     * (non-Javadoc)
     * 
     * @see ly.step.impl.ThoughtDao#save(ly.step.Thought)
     */
    @Override
    @Transactional
    public Thought save(final Thought thought) {
	// TODO: introducing an id generator will be help. :-)
	GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
	getJdbcTemplate().update(
	        new PreparedStatementCreator() {

		    @Override
		    public PreparedStatement createPreparedStatement(
		            Connection con)
		            throws SQLException {
		        PreparedStatement preparedStatement = con.prepareStatement(
		                SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
		        preparedStatement.setObject(1, 0);
		        preparedStatement.setObject(2, thought.getText());
		        preparedStatement.setObject(3, thought.getAuthorId());
		        preparedStatement.setObject(4, thought.getCreatedAt()
		                .getTime());
		        return preparedStatement;
		    }
	        }, keyHolder);
	return thought.toBuilder()
	        .setId(keyHolder.getKey().longValue())
	        .build();
    }
}
