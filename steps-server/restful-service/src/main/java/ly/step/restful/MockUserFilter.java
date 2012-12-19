package ly.step.restful;

import java.io.IOException;
import java.security.Principal;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import ly.step.User;

import org.springframework.web.filter.OncePerRequestFilter;

/**
 * 在没有身份认证系统的时候，这个类用来填补需要用到的用户身份
 * 
 * @author Leon
 * 
 */
public class MockUserFilter extends OncePerRequestFilter {

    private long userId;

    private static final UserPrincipal mockedUserPrincipal = new UserPrincipal(
	    User.newBuilder()
	            .setId(-1L).setName("Dante").build());

    @Override
    protected void doFilterInternal(final HttpServletRequest request,
	    HttpServletResponse response, FilterChain filterChain)
	    throws ServletException, IOException {
	HttpServletRequestWrapper httpServletRequestWrapper = new HttpServletRequestWrapper(
	        request) {
	    @Override
	    public Principal getUserPrincipal() {
		return mockedUserPrincipal;
	    }
	};

	filterChain.doFilter(httpServletRequestWrapper, response);

    }

    public long getUserId() {
	return userId;
    }

    public MockUserFilter setUserId(long userId) {
	this.userId = userId;
	return this;
    }

}
