package ru.ndg.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter(filterName = "mainFilter", urlPatterns = "/*")
public class MainFilter implements Filter {

    private final static String CHARACTER_ENCODING = "UTF-8";

    private transient FilterConfig filterConfig;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        response.setCharacterEncoding(MainFilter.CHARACTER_ENCODING);
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {}
}