package top.api.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;

import org.springframework.web.bind.annotation.*;
import top.api.common.R;
import top.api.pojo.Employee;
import top.api.service.EmployeeService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    /**
     * 登录
     * @param employee
     * @param request
     * @return
     */
    @PostMapping("/login")
    public R<Employee> login(@RequestBody Employee employee, HttpServletRequest request){
        // 将密码进行md5加密
        String md5Password = DigestUtils.md5DigestAsHex(employee.getPassword().getBytes());

        // 查询数据
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername,employee.getUsername());


        Employee dbEmployee = employeeService.getOne(queryWrapper);

        if (dbEmployee == null){
            return R.error("账号不存在!");
        }

        if (! dbEmployee.getPassword().equals(md5Password)){
            return R.error("密码错误!");
        }

        if (dbEmployee.getStatus() == 0){
            return R.error("账号异常!");
        }

        // 将信息保存到session
        request.getSession().setAttribute("employee",dbEmployee.getId());

        return R.success("登录成功",dbEmployee);
    }

    /**
     * 退出登录
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request){
        // 将session中保存的employee清除
        request.getSession().removeAttribute("employee");

        return R.success("退出成功!");
    }

    /**
     * 添加员工
     * @param employee
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody Employee employee, HttpServletRequest request){
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername,employee.getUsername());
        Employee dbEmployee = employeeService.getOne(queryWrapper);

        if (dbEmployee != null){
            return R.error("该账号已存在!");
        }

        // 进行添加

        // 将密码设置成初始值
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        // 获得当前登录用户的id
        Long empId = (Long) request.getSession().getAttribute("employee");

        try {
            employeeService.save(employee);
        } catch (Exception e) {
            return R.error("添加失败");
        }

        return R.success("添加成功");
    }

    /**
     * 员工分页模糊查询
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize,String name){
        // 构建分页类
        Page pageInfo = new Page(page,pageSize);

        // 构建条件
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.like(name != null,Employee::getName,name);

        // 添加排序条件
        queryWrapper.orderByDesc(Employee::getUpdateTime);

        employeeService.page(pageInfo,queryWrapper);

        return R.success("ok", pageInfo);
    }

    /**
     * 根据id修改员工信息
     * @param employee
     * @param request
     * @return
     */
    @PutMapping
    public R<String> update(@RequestBody Employee employee,HttpServletRequest request){

        Long empId = (Long) request.getSession().getAttribute("employee");
        //employee.setUpdateTime(LocalDateTime.now());
        employee.setUpdateUser(empId);


        boolean index = employeeService.updateById(employee);

        return index ?R.success("信息修改成功"):R.error("信息修改失败");
    }

    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable String id){
        Employee employee = employeeService.getById(id);
        return R.success("ok",employee);
    }
}
