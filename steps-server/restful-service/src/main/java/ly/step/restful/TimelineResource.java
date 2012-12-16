package ly.step.restful;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

import ly.step.Thought;
import ly.step.ThoughtService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
@Path("/service/timeline")
public class TimelineResource {
    public static class TimelineResult {
	private final List<Thought> result;

	public TimelineResult(List<Thought> result) {
	    this.result = result;
	}

	public List<Thought> getResult() {
	    return result;
	}
    }

    @Autowired
    private ThoughtService thoughtService;

    @Path("/")
    @GET
    @Produces({ "application/thought-list+json", "application/thought-list+xml" })
    public TimelineResult findInTimeline(
	    @Context SecurityContext securityContext,
	    @DefaultValue("0") @QueryParam("since_id") long sinceId,
	    @QueryParam("max_id") long maxId,
	    @DefaultValue("25") @QueryParam("limit") int limit) {
	UserPrincipal principal = (UserPrincipal) securityContext
	        .getUserPrincipal();
	List<Long> thoughtIdList = thoughtService.findInTimeline(principal
	        .getUser().getId(), sinceId, maxId, limit);

	final List<Thought> result = new ArrayList<Thought>(
	        thoughtIdList.size());
	for (long id : thoughtIdList) {
	    result.add(thoughtService.findById(id));
	}

	return new TimelineResult(result);
    }

}
