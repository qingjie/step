package ly.step.restful;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.InvocationTargetException;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import ly.step.Thought;
import ly.step.ThoughtService;
import ly.step.User;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(MockitoJUnitRunner.class)
public class ThoughtResourceTest {
    private ThoughtResource thoughtResource = new ThoughtResource();
    @Mock
    private ThoughtService thoughtService;
    @Mock
    private SecurityContext securityContext;
    private UserPrincipal userPrincipal;

    @Before
    public void setup() throws Exception {
	ReflectionTestUtils.setField(thoughtResource, "thoughtService",
	        thoughtService);
	userPrincipal = new UserPrincipal(User.newBuilder()
	        .setId(20).setName("Dante").build());
	when(securityContext.getUserPrincipal()).thenReturn(userPrincipal);
	ReflectionTestUtils.setField(thoughtResource, "securityContext",
	        securityContext);
    }

    @Test
    public void testFindByThoughtId() {
	Thought sample = Thought.newBuilder().setId(1L).setText("Hello, World")
	        .build();
	when(thoughtService.findById(1)).thenReturn(sample);
	Thought actual = thoughtService.findById(1);
	assertEquals(sample, actual);

	when(thoughtService.findById(2)).thenReturn(null);
	actual = thoughtService.findById(2);
	assertNull(actual);
    }

    @Test
    public void testPost() throws SecurityException, NoSuchMethodException,
	    IllegalArgumentException, IllegalAccessException,
	    InvocationTargetException {
	Thought sample = Thought.newBuilder().setId(1L).setText("Hello, World")
	        .build();
	when(thoughtService.post(any(Thought.class))).thenReturn(1000L);
	Response result = thoughtResource.post(sample);
	assertEquals(1000L, result.getEntity().getClass().getMethod("getId")
	        .invoke(result.getEntity()));
	assertEquals(201, result.getStatus());

	ArgumentCaptor<Thought> argumentCaptor = ArgumentCaptor
	        .forClass(Thought.class);
	verify(thoughtService).post(argumentCaptor.capture());
	assertEquals(userPrincipal.getUser().getId(), argumentCaptor.getValue()
	        .getAuthorId());
	assertNotNull(argumentCaptor.getValue().getCreatedAt());
    }
}
