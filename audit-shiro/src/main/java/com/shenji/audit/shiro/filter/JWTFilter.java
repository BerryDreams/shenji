package com.shenji.audit.shiro.filter;

import com.shenji.audit.shiro.util.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.util.ObjectUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * TODO
 *
 * @author misxr
 * @version 1.0
 * @date 2021/3/20 8:46
 */
@Slf4j
public class JWTFilter extends BasicHttpAuthenticationFilter {

    private static final String LOGIN = "/api/shiro/login";
    private static final String SECRET_KEY = "Authorization";
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        log.info("isAccessAllowed");
        HttpServletRequest r = (HttpServletRequest) request;
        System.out.println(r.getRequestURI());
        System.out.println(r.getRequestURI().equals(LOGIN));
        if (LOGIN.equals(r.getRequestURI())){
            return true;
        }
        String token = r.getHeader(SECRET_KEY).substring(7);
        try {
            Boolean expired = JWTUtil.isTokenExpired(token);
            if (!expired){
                String username = JWTUtil.getUsernameFromToken(token);
                log.info(username + "--->进行jwt认证");
                return true;
            }
            return false;
        } catch (Exception e) {
            request.setAttribute("error",e.getMessage());
            return false;
        }
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        response.setContentType("application/json;charset=utf8");
        Object error = request.getAttribute("error");
        if(!ObjectUtils.isEmpty(error)){
            response.getWriter().println(error);
            return false;
        }
        response.getWriter().println("error");
        return false;
    }
}
