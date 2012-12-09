package ly.step.impl.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import ly.step.User;
import ly.step.impl.UserDao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoJdbcImpl extends JdbcTemplate implements UserDao {

    private static final String SQL_FIND_BY_ID = "select * from User where id = ?";

    @Override
    public User findById(long id) {
	return this.queryForObject(
		SQL_FIND_BY_ID,
		new Object[] { id },
		new RowMapper<User>() {

		    @Override
		    public User mapRow(ResultSet rs, int rowNum)
			    throws SQLException {
			return User.newBuilder()
				.setId(rs.getLong("id"))
				.setName(rs.getString("name"))
				.build();
		    }

		});
    }

}
