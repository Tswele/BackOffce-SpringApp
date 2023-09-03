package za.co.wirecard.channel.backoffice.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Service
public class SecurityFilter implements javax.servlet.Filter {

    private static final Logger logger = LogManager.getLogger(SecurityFilter.class);

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        MutableHttpServletRequest mutableRequest = new MutableHttpServletRequest(req);
        if (mutableRequest.getSession() != null) {
            try {
                JwtSession jwtSession = (JwtSession) mutableRequest.getSession().getAttribute("token");
                mutableRequest.putHeader("Authorization", "Bearer " + jwtSession.getToken().getAccess_token());
            } catch (Exception ignored) {

            }
        }
        chain.doFilter(mutableRequest, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

}
