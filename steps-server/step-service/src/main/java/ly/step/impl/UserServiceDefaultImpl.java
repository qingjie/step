package ly.step.impl;

import java.util.Date;
import java.util.List;

import ly.step.Ticket;
import ly.step.User;
import ly.step.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceDefaultImpl implements UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private UserRelationDao userRelationDao;
    @Autowired
    private TicketDao ticketDao;

    @Override
    public User findById(long id) {
	return userDao.findById(id);
    }

    @Override
    public User findByTicketCode(String code) {
	Ticket ticket = ticketDao.findByCode(code);
	if (ticket == null) {
	    return null;
	}
	else {
	    return userDao.findById(ticket.getUserId());
	}
    }

    @Override
    public List<Long> findFriend(long userId) {
	return userRelationDao.findFriendsByUserId(userId);
    }

    @Override
    public void makeFriend(long friendA, long friendB) {
	userRelationDao.save(friendA, friendB, new Date());
    }

    @Override
    public void separate(long friendA, long friendB) {
	userRelationDao.remove(friendA, friendB);
    }

}
