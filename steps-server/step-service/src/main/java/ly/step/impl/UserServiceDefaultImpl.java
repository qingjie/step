package ly.step.impl;

import ly.step.Ticket;
import ly.step.User;
import ly.step.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceDefaultImpl implements UserService {

    @Autowired
    private UserDao userDAO;
    @Autowired
    private TicketDao ticketDAO;

    @Override
    public User findById(long id) {
	return userDAO.findById(id);
    }

    @Override
    public User findByTicketCode(String code) {
	Ticket ticket = ticketDAO.findByCode(code);
	if (ticket == null) {
	    return null;
	}
	else {
	    return userDAO.findById(ticket.getUserId());
	}
    }

}
