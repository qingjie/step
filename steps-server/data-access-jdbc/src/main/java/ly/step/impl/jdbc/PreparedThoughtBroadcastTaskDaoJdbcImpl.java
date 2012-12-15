package ly.step.impl.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import ly.step.impl.PreparedThoughtBroadcastTask;
import ly.step.impl.PreparedThoughtBroadcastTaskDao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Joiner;

//TODO Write unit tests
@Repository
public class PreparedThoughtBroadcastTaskDaoJdbcImpl
        extends JdbcDaoSupport
        implements
        PreparedThoughtBroadcastTaskDao {

    private static final String SQL_DELETE_BY_ID = "delete from Prepared_Thought_Broadcast_Task where id = ?";
    private static final String SQL_INSERT = "insert into Prepared_Thought_Broadcast_Task"
	    + " (id, thought_id, recipient_list) values (?,?,?)";
    private static final String SQL_SELECT_BY_ID = "select * from Prepared_Thought_Broadcast_Task"
	    +
	    " where id = ?";
    private static final String SQL_SELECT_ALL = "select id from Prepared_Thought_Broadcast_Task"
	    + " order by id asc limit ?";

    /*
     * (non-Javadoc)
     * 
     * @see ly.step.impl.PreparedThoughtBroadcastTaskDao#findAll(int)
     */
    @Override
    public List<Long> findAll(int limit) {
	return getJdbcTemplate()
	        .queryForList(
	                SQL_SELECT_ALL,
	                new Object[] { limit }, Long.class);
    }

    /*
     * (non-Javadoc)
     * 
     * @see ly.step.impl.PreparedThoughtBroadcastTaskDao#findById(long)
     */
    @Override
    public PreparedThoughtBroadcastTask findById(long id) {
	return getJdbcTemplate().queryForObject(
	        SQL_SELECT_BY_ID,
	        new Object[] { id },
	        new RowMapper<PreparedThoughtBroadcastTask>() {

		    @Override
		    public PreparedThoughtBroadcastTask mapRow(ResultSet rs,
		            int rowNum) throws SQLException {
		        List<Long> recipientList = new LinkedList<Long>();
		        for (String userId : rs.getString("recipient_list")
		                .split(",")) {
			    recipientList.add(Long.valueOf(userId));
		        }
		        return PreparedThoughtBroadcastTask.newBuilder()
		                .setId(rs.getLong("id"))
		                .setRecipientList(recipientList)
		                .setThoughtId(rs.getLong("thought_id"))
		                .build();
		    }

	        });
    }

    /*
     * (non-Javadoc)
     * 
     * @see ly.step.impl.PreparedThoughtBroadcastTaskDao#remove(long)
     */
    @Override
    public void remove(long id) {
	getJdbcTemplate().update(
	        SQL_DELETE_BY_ID,
	        new Object[] { id });
    }

    /*
     * (non-Javadoc)
     * 
     * @see ly.step.impl.PreparedThoughtBroadcastTaskDao#save(ly.step.impl.
     * PreparedThoughtBroadcastTask)
     */
    @Override
    @Transactional
    public PreparedThoughtBroadcastTask save(
	    PreparedThoughtBroadcastTask preparedBroadcastTask) {
	getJdbcTemplate()
	        .update(SQL_INSERT,
	                new Object[] {
	                        0,
	                        preparedBroadcastTask.getThoughtId(),
	                        Joiner.on(',').join(
	                                preparedBroadcastTask
	                                        .getRecipientList()) });
	return preparedBroadcastTask.toBuilder()
	        .setId(getJdbcTemplate().queryForLong("select @@IDENTITY"))
	        .build();
    }
}
