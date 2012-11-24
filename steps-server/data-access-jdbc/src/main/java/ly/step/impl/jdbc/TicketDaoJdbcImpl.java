package ly.step.impl.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import ly.step.Ticket;
import ly.step.impl.TicketDao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

@Repository
public class TicketDaoJdbcImpl extends JdbcDaoSupport implements TicketDao {

    private static final String SQL_FIND_BY_CODE = "select * from Ticket where code = ?";

    @Override
    public Ticket findByCode(String code) {
	return getJdbcTemplate().queryForObject(
	        SQL_FIND_BY_CODE,
	        new RowMapper<Ticket>() {

		    @Override
		    public Ticket mapRow(ResultSet rs, int rowNum)
		            throws SQLException {
		        return Ticket.newBuilder()
		                .setCode(rs.getString("code"))
		                .setUserId(rs.getLong("user_id"))
		                .build();
		    }

	        });
    }

}
