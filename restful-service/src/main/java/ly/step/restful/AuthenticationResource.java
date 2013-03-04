package ly.step.restful;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import ly.step.AccessToken;
import ly.step.User;
import ly.step.UserService;

import org.codehaus.jackson.annotate.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
@Path("/auth")
public class AuthenticationResource {
    public static class AccessTokenResponse {
	private final AccessToken accessToken;

	private AccessTokenResponse(AccessToken accessToken) {
	    this.accessToken = accessToken;
	}

	@JsonProperty("access_token")
	public String getAccessToken() {
	    return accessToken.getAccessToken();
	}

	@JsonProperty("expires_in")
	public int getExpiresIn() {
	    return accessToken.getExpiredInSecond();
	}

	@JsonProperty("token_type")
	public String getTokenType() {
	    return "Bearer";
	}
    }

    @Autowired
    private UserService userService;

    @Path("/password-grant")
    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Produces("application/json;charset=utf-8")
    public Object passwordGrant(
	    @FormParam("grant_type") String grantType,
	    @FormParam("username") String username,
	    @FormParam("password") String password,
	    @FormParam("scope") String scope) {
	if (!"password".equals(grantType)) {
	    return Response.status(Status.BAD_REQUEST).build();
	}
	User user = userService.auth(username, password);
	final AccessToken accessToken = userService.allocateAccessToken(user
	        .getId());

	return new AccessTokenResponse(accessToken);
    }
}