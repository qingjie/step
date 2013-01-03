package ly.step.restful;

import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import ly.step.User;
import ly.step.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * 帐户的资源
 * 
 * @author Leon
 * 
 */
@Controller
@Path("/service/account")
public class AccountResource {
    public static class RegisterRequest {
	private String username;
	private String password;

	public String getPassword() {
	    return password;
	}

	public String getUsername() {
	    return username;
	}

	public RegisterRequest setPassword(String password) {
	    this.password = password;
	    return this;
	}

	public RegisterRequest setUsername(String username) {
	    this.username = username;
	    return this;
	}

    }

    @Autowired
    private UserService userService;

    @Path("/")
    @POST
    @Consumes("application/json")
    @Produces("application/json;charset=utf-8")
    public Response register(RegisterRequest registerRequest)
	    throws URISyntaxException {
	userService.register(
	        User.newBuilder().setName(registerRequest.getUsername())
	                .build(), registerRequest.getPassword());
	return Response.created(new URI(".")).build();
    }
}