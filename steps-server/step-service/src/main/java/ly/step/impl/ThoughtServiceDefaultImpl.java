package ly.step.impl;

import java.util.List;

import ly.step.Thought;
import ly.step.ThoughtService;

import org.springframework.beans.factory.annotation.Autowired;

public class ThoughtServiceDefaultImpl implements ThoughtService {

    @Autowired
    private ThoughtDao thoughtDAO;
    @Autowired
    private UserToThoughtDao userToThoughtDAO;

    @Override
    public Thought findById(long id) {
	return thoughtDAO.findById(id);
    }

    @Override
    public List<Thought> findByUser(long userId, long sinceId, long maxId,
	    int limit) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public long post(Thought thought) {
	// TODO Auto-generated method stub
	return 0;
    }

}
