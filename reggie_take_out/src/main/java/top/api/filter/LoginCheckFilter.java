package top.api.filter;


import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import top.api.common.BaseContext;
import top.api.common.R;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录过滤器
 */
@Slf4j
@WebFilter(filterName = "LoginCheckFilter",urlPatterns = "/*")
@Component //还可以在启动类添加 @ServletComponentScan
public class LoginCheckFilter implements Filter {

    // 路径匹配器,支持通配符
    private static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String requestURI = request.getRequestURI();

        // 不需要拦截的url
        String[] urls = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/common/**",
                "/user/login"
        };

        // 检测是否需要拦截
        if (check(urls,requestURI)){
            filterChain.doFilter(request,response);
            return;
        }

        // 检测是否登录
        Long empId = (Long) request.getSession().getAttribute("employee");
        if (empId != null){

            //将empId存入到线程副本
            BaseContext.set(empId);

            filterChain.doFilter(request,response);
            return;
        }

        // 检测是否登录
        Long userId = (Long) request.getSession().getAttribute("user");
        if (userId != null){

            //将empId存入到线程副本
            BaseContext.set(userId);

            filterChain.doFilter(request,response);
            return;
        }

        // 运行到这里都是需要拦截的
        log.warn("拦截到该请求: {}",requestURI);
        response.getWriter().print(JSON.toJSONString(R.error("NOTLOGIN")));

    }

    /**
     * 路径匹配,检测本次请求是否需要放行
     * @param urls
     * @param requestURI
     * @return
     */
    private boolean check(String[] urls, String requestURI){
        for (String url : urls) {
            // 匹配成功返回true
            boolean flag = PATH_MATCHER.match(url, requestURI);
            if (flag){
                return true;
            }
        }
        return false;
    }
}
