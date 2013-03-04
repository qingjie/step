package ly.step.impl.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import ly.step.UserCredential;
import ly.step.impl.UserCredentialDao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

@Repository
public class UserCredentialDaoJdbcImpl extends JdbcDaoSupport implements
        UserCredentialDao {

    /*
     * (non-Javadoc)
     * 
     * @see ly.step.impl.UserCredentialDao#findByUsername(java.lang.String)
     */
    @Override
    public UserCredential findByUsername(String username) {
	return DataAccessUtils.requireSingleResult(getJdbcTemplate().query(
	        "select * from user_credential where user_name = ?",
	        new Object[] { username }, new RowMapper<UserCredential>() {

		    @Override
		    public UserCredential mapRow(ResultSet rs, int rowNum)
		            throws SQLException {
		        return UserCredential.newBuilder()
		                .setPasswordHash(rs.getString("password_hash"))
		                .setUserId(rs.getLong("user_id"))
		                .setUsername(rs.getString("user_name"))
		                .build();
		    }

	        }));
    }

    /*
     * (non-Javadoc)
     * 
     * @see ly.step.impl.UserCredentialDao#save(ly.step.UserCredential)
     */
    @Override
    public void save(UserCredential userCrendential) {
	getJdbcTemplate()
	        .update("insert into user_credential (user_name, password_hash, user_id) values (?,?,?)",
	                new Object[] {
	                        userCrendential.getUsername(),
	                        userCrendential.getPasswordHash(),
	                        userCrendential.getUserId() });
    }

}
