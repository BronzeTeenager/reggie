package top.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import top.api.common.R;
import top.api.pojo.User;
import top.api.service.UserService;

import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 阉割版登录,无需验证码
     *
     * @param map
     * @return
     */
    @PostMapping("/login")
    public R<User> login(@RequestBody Map<String, String> map, HttpSession session) {
        String phone = map.get("phone");

        if (StringUtils.isEmpty(phone)) {
            return R.error("phone is null");
        }

        userService.login(phone,session);

        return R.success("success");
    }
}
