package ly.step.restful;

import java.io.IOException;
import java.security.Principal;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import ly.step.AuthenticationException;
import ly.step.User;
import ly.step.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.filter.OncePerRequestFilter;

import com.google.common.base.Strings;

@Controller("securityFilter")
public class SecurityFilter extends OncePerRequestFilter {

    private static class AuthenticationHeader {
	private final String accessToken;

	public AuthenticationHeader(String headerValue) {
	    if (Strings.isNullOrEmpty(headerValue)) {
		throw new AuthenticationException();
	    }
	    String[] splited = headerValue.split(" ");
	    if (splited.length != 2
		    || !"Bearer".equalsIgnoreCase(splited[0].trim())) {
		throw new AuthenticationException();
	    }

	    this.accessToken = splited[1];
	}

	public String getAccessToken() {
	    return accessToken;
	}

    }

    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
	    HttpServletResponse response, FilterChain filterChain)
	    throws ServletException, IOException {
	try {
	    AuthenticationHeader authenticationHeader = new AuthenticationHeader(
		    request.getHeader("Authentication"));
	    final User user = userService
		    .findByAccessToken(authenticationHeader
		            .getAccessToken());

	    HttpServletRequestWrapper httpServletRequestWrapper = new HttpServletRequestWrapper(
		    request) {
		@Override
		public Principal getUserPrincipal() {
		    return new UserPrincipal(user);
		}
	    };

	    filterChain.doFilter(httpServletRequestWrapper, response);
	} catch (AuthenticationException e) {
	    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	}
    }
}
