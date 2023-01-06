package top.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.rmi.runtime.Log;
import top.api.exception.ControllerException;
import top.api.mapper.CategoryMapper;
import top.api.pojo.Category;
import top.api.pojo.Dish;
import top.api.pojo.Setmeal;
import top.api.service.CategoryService;
import top.api.service.DishService;
import top.api.service.SetmealService;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private DishService dishService;

    @Autowired
    private SetmealService setmealService;

    @Override
    public void remove(Long id) {
        //查询是否关联菜品
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Dish::getCategoryId,id);
        int count1 = dishService.count(queryWrapper);

        if (count1 > 0){
            throw new ControllerException("已经关联菜品,不可删除!");
        }
        //查询是否关联套餐
        LambdaQueryWrapper<Setmeal> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.eq(Setmeal::getCategoryId,id);
        int count2 = setmealService.count(queryWrapper1);

        if (count2 > 0){
            throw new ControllerException("已经关联菜品,不可删除!");
        }

        //都没有关联则删除
        super.removeById(id);
    }
}
