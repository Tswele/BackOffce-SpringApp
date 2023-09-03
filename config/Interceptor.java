//package za.co.wirecard.channel.backoffice.config;
//
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.util.Enumeration;
//
//@Configuration
//public class Interceptor extends HandlerInterceptorAdapter {
//
//    private static final Logger logger = LogManager.getLogger(Interceptor.class);
//    private final MutableHttpServletRequest mutableHttpServletRequest;
//
//    public Interceptor(SecurityFilter securityFilter) {
//        this.mutableHttpServletRequest = mutableHttpServletRequest;
//    }
//
//    @Override
//    public boolean preHandle(
//            HttpServletRequest request,
//            HttpServletResponse response,
//            Object handler) throws Exception {
//
//        logger.info("[preHandle][" + request + "]" + "[" + request.getMethod()
//                + "]" + request.getRequestURI() + getParameters(request));
//        if (request.getSession() != null) {
//            try {
//                JwtSession jwtSession = (JwtSession) request.getSession().getAttribute("token");
//                this.mutableHttpServletRequest.putHeader("Authorization", "Bearer " + jwtSession.getToken().getAccess_token());
//                logger.info(request.getHeader("Authorization"));
//            } catch (Exception e) {
//                return true;
//            }
//        }
//        return true;
//    }
//
//    private String getParameters(HttpServletRequest request) {
//        StringBuffer posted = new StringBuffer();
//        Enumeration<?> e = request.getParameterNames();
//        if (e != null) {
//            posted.append("?");
//        }
//        while (e.hasMoreElements()) {
//            if (posted.length() > 1) {
//                posted.append("&");
//            }
//            String curr = (String) e.nextElement();
//            posted.append(curr + "=");
//            if (curr.contains("password")
//                    || curr.contains("pass")
//                    || curr.contains("pwd")) {
//                posted.append("*****");
//            } else {
//                posted.append(request.getParameter(curr));
//            }
//        }
//        String ip = request.getHeader("X-FORWARDED-FOR");
//        String ipAddr = (ip == null) ? getRemoteAddr(request) : ip;
//        if (ipAddr!=null && !ipAddr.equals("")) {
//            posted.append("&_psip=" + ipAddr);
//        }
//        return posted.toString();
//    }
//
//    private String getRemoteAddr(HttpServletRequest request) {
//        String ipFromHeader = request.getHeader("X-FORWARDED-FOR");
//        if (ipFromHeader != null && ipFromHeader.length() > 0) {
//            logger.debug("ip from proxy - X-FORWARDED-FOR : " + ipFromHeader);
//            return ipFromHeader;
//        }
//        return request.getRemoteAddr();
//    }
//
//}
