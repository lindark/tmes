package cc.jiuyi.common;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletResponse;

public class NewXssFilter implements Filter {  
	  
    FilterConfig filterConfig = null;  
  
    @Override  
    public void destroy() {  
        this.filterConfig = null;  
    }  
  
    @Override  
    public void doFilter(ServletRequest request, ServletResponse response,  
            FilterChain chain) throws IOException, ServletException {  
        String path = ((HttpServletRequest)request).getContextPath();  
        String basePath = request.getScheme() + "://" + request.getServerName()  
                + ":" + request.getServerPort() + path + "/";  
  
        // HTTP 头设置 Referer过滤  
        /*
        String referer = ((HttpServletRequest) request).getHeader("Referer"); // REFRESH  
        if (referer != null && referer.indexOf(basePath) < 0) {  
            ((HttpServletRequest) request).getRequestDispatcher(  
                    ((HttpServletRequest) request).getRequestURI()).forward(  
                    ((HttpServletRequest) request), response);  
            System.out.println("referer不为空，referer >>>>>>>>>>>>>> " + referer);  
        }  
        */
        NewXssHttpServletRequestWrapper xssRequest = new NewXssHttpServletRequestWrapper((HttpServletRequest) request);  
        chain.doFilter(xssRequest, response);  
    }  
  
    @Override  
    public void init(FilterConfig filterConfig) throws ServletException {  
        this.filterConfig = filterConfig;  
    }  
  
}