package ly.step.impl;

import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ThoughtBroadcastService {

    private static final Logger logger = LoggerFactory
	    .getLogger(ThoughtBroadcastService.class);

    @Autowired
    private UserToThoughtDao userToThoughtDao;

    public void broadcast(long thoughtId, Iterator<Long> recipients) {
	logger.debug("{} begins. ", thoughtId);
	int counter = 0;
	while (recipients.hasNext()) {
	    userToThoughtDao.save(recipients.next(), thoughtId);
	    counter++;
	}
	logger.info("{} finished. Size of recipient list: {}", thoughtId,
	        counter);
    }
}