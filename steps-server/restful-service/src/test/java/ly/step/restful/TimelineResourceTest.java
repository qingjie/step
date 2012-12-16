package ly.step.restful;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.Arrays;

import javax.ws.rs.core.SecurityContext;

import ly.step.Thought;
import ly.step.ThoughtService;
import ly.step.User;
import ly.step.restful.TimelineResource.TimelineResult;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(MockitoJUnitRunner.class)
public class TimelineResourceTest {
    private TimelineResource timelineResource = new TimelineResource();
    @Mock
    private ThoughtService thoughtService;
    @Mock
    private SecurityContext securityContext;

    @Test
    public void jacksonMappingTest() throws JsonGenerationException,
	    JsonMappingException, IOException {
	TimelineResult timelineResult = new TimelineResource.TimelineResult(
	        Arrays.asList(
	                Thought.newBuilder().setId(1L).setText("Hello, World!")
	                        .build()));
	ObjectMapper objectMapper = new ObjectMapper();
	System.out.println(objectMapper.writeValueAsString(timelineResult));
    }

    @Test
    public void testFindInTimeline() {
	ReflectionTestUtils.setField(timelineResource, "thoughtService",
	        thoughtService);
	when(securityContext.getUserPrincipal())
	        .thenReturn(
	                new UserPrincipal(User.newBuilder().setId(10L).build()));
	when(thoughtService.findInTimeline(10, 20, 30, 50))
	        .thenReturn(Arrays.asList(1L, 2L, 3L));
	when(thoughtService.findById(anyLong()))
	        .thenReturn(Thought.newBuilder().setId(1L).build())
	        .thenReturn(Thought.newBuilder().setId(2L).build())
	        .thenReturn(Thought.newBuilder().setId(3L).build());
	TimelineResource.TimelineResult timelineResult = timelineResource
	        .findInTimeline(securityContext, 20L,
	                30L, 50);

	assertNotNull(timelineResult);
	assertNotNull(timelineResult.getResult());
	assertEquals(3, timelineResult.getResult().size());
    }
}
