package top.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.api.mapper.UserMapper;
import top.api.pojo.User;
import top.api.service.UserService;

import javax.servlet.http.HttpSession;

@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public User login(String phone, HttpSession session) {
        // 判断是否注册过
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getPhone,phone);
        User user = super.getOne(queryWrapper);

        if (user == null){
            // 注册
            user = new User();
            user.setPhone(phone);
            user.setStatus(1);
            super.save(user);
            return user;
        }
        session.setAttribute("user",user.getId());
        // 查询到了
        return user;
    }
}
