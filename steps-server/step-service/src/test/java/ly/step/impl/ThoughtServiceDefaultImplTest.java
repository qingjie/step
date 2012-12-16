package ly.step.impl;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ly.step.Thought;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(MockitoJUnitRunner.class)
public class ThoughtServiceDefaultImplTest {

    private ThoughtServiceDefaultImpl thoughtServiceDefaultImpl = new ThoughtServiceDefaultImpl();
    @Mock
    private UserToThoughtDao userToThoughtDao;
    @Mock
    private UserRelationDao userRelationDao;
    @Mock
    private ThoughtDao thoughtDao;
    private Thought thought;

    @Before
    public void setup() {
	ReflectionTestUtils.setField(thoughtServiceDefaultImpl,
	        "userToThoughtDao",
	        userToThoughtDao);
	ReflectionTestUtils.setField(thoughtServiceDefaultImpl,
	        "userRelationDao", userRelationDao);
	ReflectionTestUtils.setField(thoughtServiceDefaultImpl, "thoughtDao",
	        thoughtDao);

	thought = Thought.newBuilder()
	        .setAuthorId(123)
	        .setCreatedAt(new Date())
	        .setText("text")
	        .build();
    }

    private void setupImpl(int friendCount) {
	List<Long> friendList = new ArrayList<Long>(friendCount);
	for (long i = 1; i <= friendCount; i++) {
	    friendList.add(i);
	}

	when(thoughtDao.save(thought))
	        .thenReturn(thought.toBuilder().setId(789L).build());
	when(userRelationDao.findFriendsByUserId(123))
	        .thenReturn(friendList);

	thoughtServiceDefaultImpl.post(thought);
    }

    private void verifyFriendShare(int friendCount) {
	verify(userToThoughtDao).save(123L, 789L);
	verify(thoughtDao).save(thought);
	verify(userRelationDao).findFriendsByUserId(123L);
	for (long i = 1; i <= friendCount; i++) {
	    verify(userToThoughtDao).save(i, 789L);
	}
	verifyNoMoreInteractions(userToThoughtDao);
    }

    @Test
    public void 发送数量超过了最大限额() {
	int max = (Integer) ReflectionTestUtils.getField(
	        thoughtServiceDefaultImpl,
	        "MAX_BROADCAST");

	setupImpl(max + 1);

	verifyFriendShare(15);
    }

    @Test
    public void 正常发送Thought() {
	setupImpl(4);

	verifyFriendShare(4);
    }
}
