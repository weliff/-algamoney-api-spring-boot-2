package com.algamoney.api.token;

import org.apache.catalina.util.ParameterMap;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.util.Map;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RefreshTokenPreProcessorFilter implements Filter{
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) servletRequest;

        if ("/oauth/token".equalsIgnoreCase(req.getRequestURI())
                && "refresh_token".equals(req.getParameter("grant_type"))
                && req.getCookies() != null) {

            for (Cookie cookie : req.getCookies()) {
                if (cookie.getName().equals("refreshToken")) {
                    req = new MyHttpServletRequestWrapper(req, cookie.getValue());
                }
            }
        }

        filterChain.doFilter(req, servletResponse);
    }

    @Override
    public void destroy() {
    }

    static class MyHttpServletRequestWrapper extends HttpServletRequestWrapper {

        private String refreshToken;

        public MyHttpServletRequestWrapper(HttpServletRequest request, String refreshToken) {
            super(request);
            this.refreshToken = refreshToken;
        }

        @Override
        public Map<String, String[]> getParameterMap() {
            ParameterMap<String, String[]> parameterMap = new ParameterMap<>(getRequest().getParameterMap());
            parameterMap.put("refresh_token", new String[]{ refreshToken });
            parameterMap.setLocked(true);
            return parameterMap;
        }
    }
}
