package ly.step.impl.jdbc;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import ly.step.impl.UserToThoughtDao;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

public class UserToThoughtDaoJdbcImpl extends JdbcDaoSupport implements
	UserToThoughtDao {

    private static final String SQL_INSERT = "insert into user_to_thought (user_id, thought_id, createdAt) values (?,?,?)";
    private static final String SQL_FIND_BY_USER_ID_PREFIX = "select thought_id from user_to_thought where user_id = ?";
    private static final String SQL_FIND_BY_USER_ID_POSTFIX = " order by thought_id desc limit ?";

    @Override
    public void save(long userId, long thoughtId) {
	getJdbcTemplate().update(SQL_INSERT, new Object[] {
		userId, thoughtId, new Date().getTime() });
    }

    @Override
    public List<Long> findByUserId(long userId, long sinceId, long maxId,
	    int limit) {
	StringBuilder query = new StringBuilder(SQL_FIND_BY_USER_ID_PREFIX);
	List<Object> params = new LinkedList<Object>();
	if (sinceId > 0) {
	    query.append(" and thought_id > ?");
	    params.add(sinceId);
	}
	if (maxId > 0) {
	    query.append(" and thought_id <= ?");
	    params.add(maxId);
	}
	query.append(SQL_FIND_BY_USER_ID_POSTFIX);
	params.add(limit);

	return getJdbcTemplate().queryForList(query.toString(),
		params.toArray(), Long.class);
    }

}