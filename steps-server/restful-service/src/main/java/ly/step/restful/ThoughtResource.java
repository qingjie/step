package ly.step.restful;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

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
    @Produces("application/json")
    public Thought findByThoughtId(@PathParam("id") long id) {
	return thoughtService.findById(id);
    }
}
