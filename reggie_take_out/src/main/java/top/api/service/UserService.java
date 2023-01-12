package top.api.service;


import com.baomidou.mybatisplus.extension.service.IService;
import top.api.pojo.User;

import javax.servlet.http.HttpSession;

public interface UserService extends IService<User> {
    User login(String phone, HttpSession session);
}
