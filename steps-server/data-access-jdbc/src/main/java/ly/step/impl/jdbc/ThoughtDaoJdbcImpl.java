package ly.step.impl.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import ly.step.Thought;
import ly.step.impl.ThoughtDao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

@Repository
public class ThoughtDaoJdbcImpl extends JdbcDaoSupport implements ThoughtDao {

    private static final String SQL_FIND_BY_USER = "select id from User_To_Thought"
	    + " where user_id = ? order by thought_id desc limit ?, ?";
    private static final String SQL_FIND_BY_ID = "select * from Thought " +
	    "where id = ?";

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
    public List<Thought> findByUser(long userId, int offset, int limit) {
	return this
	        .getJdbcTemplate()
	        .query(
	                SQL_FIND_BY_USER,
	                new Object[] { userId },
	                new RowMapper<Thought>() {

		            @Override
		            public Thought mapRow(ResultSet rs, int rowNum)
		                    throws SQLException {
		                return findById(rs.getLong("thought_id"));
		            }

	                });
    }
}
