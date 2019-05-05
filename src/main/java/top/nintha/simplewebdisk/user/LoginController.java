package top.nintha.simplewebdisk.user;

import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import top.nintha.simplewebdisk.common.ExceptionType;
import top.nintha.simplewebdisk.common.Results;
import top.nintha.simplewebdisk.config.constant.UserConsts;
import top.nintha.simplewebdisk.config.props.WebdiskProps;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Objects;

@Api(tags = "Login")
@RestController
public class LoginController {
    @Autowired
    private HttpSession session;
    @Autowired
    private WebdiskProps webdiskProps;

    @PostMapping("/login")
    public Results login(String username, String password) {
        if (Strings.isBlank(username) || Strings.isBlank(password)) {
            throw ExceptionType.PASSWORD_MISMATCH_EXCEPTION.toException();
        }

        if (Objects.equals(username, webdiskProps.getDefaultUsername()) && Objects.equals(password, webdiskProps.getDefaultPassword())) {
            logout();
            session.setAttribute(UserConsts.LOGIN_NAME, username);
            session.setMaxInactiveInterval(3600 * 24 * 30); // 过期时间
            return Results.success(username);
        }
        throw ExceptionType.PASSWORD_MISMATCH_EXCEPTION.toException();
    }

    @GetMapping("/logout")
    public Results logout() {
        session.removeAttribute("user");
        session.invalidate();
        return Results.success(null);
    }

    @GetMapping("/userInfo")
    public Results userInfo() {
        String user = (String) session.getAttribute(UserConsts.LOGIN_NAME);
        HashMap<String, Object> map = Maps.newHashMap();
        map.put("username", user);
        return Results.success(map);
    }

}
