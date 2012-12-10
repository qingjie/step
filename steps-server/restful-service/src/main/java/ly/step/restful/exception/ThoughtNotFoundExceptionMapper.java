package ly.step.restful.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import ly.step.ThoughtNotFoundException;

@Provider
public class ThoughtNotFoundExceptionMapper implements
        ExceptionMapper<ThoughtNotFoundException> {

    @Override
    public Response toResponse(ThoughtNotFoundException exception) {
	return Response.status(Status.NOT_FOUND).build();
    }

}
