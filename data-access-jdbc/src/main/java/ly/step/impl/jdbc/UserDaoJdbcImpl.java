package ly.step.impl.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Map;

import ly.step.User;
import ly.step.UsernameAlreadyRegisteredException;
import ly.step.impl.UserDao;

import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class UserDaoJdbcImpl extends JdbcDaoSupport implements UserDao {

    private static final String SQL_FIND_BY_ID = "select * from User where id = ?";
    private static final String SQL_INSERT = "insert into user (id, name) values (?, ?)";

    /*
     * (non-Javadoc)
     * 
     * @see ly.step.impl.UserDao#create(ly.step.User)
     */
    @Override
    @Transactional
    public User create(User user) throws UsernameAlreadyRegisteredException {
	List<Map<String, Object>> userExists = getJdbcTemplate().queryForList(
	        "select * from user where name = ? for update",
	        new Object[] { user.getName() });
	if (!userExists.isEmpty()) {
	    throw new UsernameAlreadyRegisteredException(user.getName());
	}
	else {
	    PreparedStatementCreatorFactory preparedStatementCreatorFactory = new PreparedStatementCreatorFactory(
		    SQL_INSERT, new int[] { Types.BIGINT, Types.VARCHAR });
	    preparedStatementCreatorFactory.setReturnGeneratedKeys(true);
	    GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
	    getJdbcTemplate().update(
		    preparedStatementCreatorFactory
		            .newPreparedStatementCreator(
		            new Object[] { user.getId(), user.getName() }),
		    generatedKeyHolder);
	    return user.toBuilder()
		    .setId(generatedKeyHolder.getKey().longValue())
		    .build();
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see ly.step.impl.UserDao#findById(long)
     */
    @Override
    public User findById(long id) {
	return DataAccessUtils.requireSingleResult(getJdbcTemplate().query(
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

	        }));
    }

}
