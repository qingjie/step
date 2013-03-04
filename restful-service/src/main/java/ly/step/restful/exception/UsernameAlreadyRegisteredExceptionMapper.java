package ly.step.restful.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import ly.step.UsernameAlreadyRegisteredException;

import org.springframework.stereotype.Component;

@Provider
@Component
public class UsernameAlreadyRegisteredExceptionMapper implements
        ExceptionMapper<UsernameAlreadyRegisteredException> {

    @Override
    public Response toResponse(UsernameAlreadyRegisteredException exception) {
	return Response.status(Status.CONFLICT).entity(new Object() {
	    @SuppressWarnings("unused")
	    public String getCause() {
		return "Username already registered.";
	    }
	}).build();
    }

}
