package ly.step.impl.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import ly.step.AccessToken;
import ly.step.impl.AccessTokenDao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

@Repository
public class AccessTokenDaoJdbcImpl extends JdbcDaoSupport implements
        AccessTokenDao {

    private static final String SQL_FIND_BY_ACCESS_TOKEN = "select * from access_token where access_token = ?";
    private static final String SQL_INSERT = "insert into access_token (access_token, user_id, created_at, expired_in) values (?,?,?,?)";

    /*
     * (non-Javadoc)
     * 
     * @see ly.step.impl.AccessTokenDao#create(ly.step.AccessToken)
     */
    @Override
    public AccessToken create(AccessToken ticket) {
	getJdbcTemplate().update(SQL_INSERT, new Object[] {
	        ticket.getAccessToken(),
	        ticket.getUserId(),
	        ticket.getCreatedAt().getTime(),
	        ticket.getExpiredInSecond()
	});
	return ticket;
    }

    /*
     * (non-Javadoc)
     * 
     * @see ly.step.impl.AccessTokenDao#findByAccessToken(java.lang.String)
     */
    @Override
    public AccessToken findByAccessToken(String accessToken) {
	return DataAccessUtils.requireSingleResult(getJdbcTemplate().query(
	        SQL_FIND_BY_ACCESS_TOKEN, new Object[] { accessToken },
	        new RowMapper<AccessToken>() {

		    @Override
		    public AccessToken mapRow(ResultSet rs, int rowNum)
		            throws SQLException {
		        return AccessToken
		                .newBuilder()
		                .setAccessToken(rs.getString("access_token"))
		                .setUserId(rs.getLong("user_id"))
		                .setCreatedAt(
		                        new Date(rs.getLong("created_at")))
		                .setExpiredInSecond(rs.getInt("expired_in"))
		                .build();
		    }

	        }));
    }

}
