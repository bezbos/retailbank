package com.codecentric.retailbank.web.filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter
public class AddResponseHeaderFilter implements Filter {

    @Override public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        // NOTE: DEVELOPMENT ONLY!!!
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");

        chain.doFilter(request, response);
    }

    @Override public void destroy() {

    }
}
