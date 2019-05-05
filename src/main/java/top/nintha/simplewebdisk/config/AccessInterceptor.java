package top.nintha.simplewebdisk.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import top.nintha.simplewebdisk.common.ExceptionType;
import top.nintha.simplewebdisk.config.constant.UserConsts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class AccessInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        log.debug("[AccessInterceptor] uri={}", request.getRequestURI());
        Object loginName = request.getSession().getAttribute(UserConsts.LOGIN_NAME);
        if (loginName == null) {
            log.warn("[AccessInterceptor] Unauthorized access, uri={}", request.getRequestURI());
            throw ExceptionType.UNLOGIN_EXCEPTION.toException(request.getRequestURI());
        }

        return true;
    }

}