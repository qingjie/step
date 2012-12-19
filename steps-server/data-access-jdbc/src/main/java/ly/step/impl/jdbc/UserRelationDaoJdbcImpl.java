package ly.step.impl.jdbc;

import java.util.Date;
import java.util.List;

import ly.step.impl.UserRelationDao;

import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class UserRelationDaoJdbcImpl extends JdbcDaoSupport implements
        UserRelationDao {

    private static final String SELECT_BY_FRIEND_A = "select friend_b from User_Relation where friend_a = ?";
    private static final String SQL_DELETE = "delete from User_Relation where friend_a = ? and friend_b = ?";
    private static final String SQL_INSERT = "insert into User_Relation (friend_a, friend_b, created_at) values (?, ?, ?)";

    @Override
    public List<Long> findFriendsByUserId(long userId) {
	return getJdbcTemplate().queryForList(
	        SELECT_BY_FRIEND_A,
	        new Object[] { userId }, Long.class);
    }

    @Override
    @Transactional
    public void remove(long friendA, long friendB) {
	getJdbcTemplate()
	        .update(SQL_DELETE,
	                new Object[] { friendA, friendB });
	getJdbcTemplate()
	        .update(SQL_DELETE,
	                new Object[] { friendB, friendA });
    }

    @Override
    @Transactional
    public void save(long friendA, long friendB, Date createdAt) {
	getJdbcTemplate().update(SQL_INSERT,
	        new Object[] {
	                friendA,
	                friendB,
	                createdAt });

	getJdbcTemplate().update(SQL_INSERT,
	        new Object[] {
	                friendA,
	                friendB,
	                createdAt });
    }

}
