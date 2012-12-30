package ly.step.restful;

import java.net.URI;
import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import ly.step.Thought;
import ly.step.ThoughtService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
@Path("/service/thought")
public class ThoughtResource {

    @Autowired
    private ThoughtService thoughtService;

    @Path("/{id}")
    @GET
    @Produces("application/thought+json")
    public Thought findByThoughtId(@PathParam("id") long id) {
	return thoughtService.findById(id);
    }

    @Path("/")
    @POST
    @Produces("application/json")
    @Consumes("application/thought+json")
    public Response post(Thought thought,
	    @Context SecurityContext securityContext) {
	UserPrincipal userPrincipal = (UserPrincipal) securityContext
	        .getUserPrincipal();
	final long id = thoughtService.post(thought.toBuilder()
	        .setAuthorId(userPrincipal.getUser().getId())
	        .setCreatedAt(new Date())
	        .build());
	return Response.created(URI.create("./service/thought/" + id))
	        .entity(new Object() {
		    @SuppressWarnings("unused")
		    public long getId() {
		        return id;
		    }
	        })
	        .build();
    }
}
