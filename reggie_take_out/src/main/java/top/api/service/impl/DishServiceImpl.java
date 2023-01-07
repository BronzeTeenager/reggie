package top.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.api.dto.DishDto;
import top.api.mapper.DishMapper;
import top.api.pojo.Dish;
import top.api.pojo.DishFlavor;
import top.api.service.DishFlavorService;
import top.api.service.DishService;

import java.util.List;

@Slf4j
@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private DishFlavorService dishFlavorService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveWithFlavor(DishDto dishDto) {
        //保存菜品的基本信息到菜皮你个表dish
        this.save(dishDto);

        //菜品id
        Long dishId = dishDto.getId();

        List<DishFlavor> flavors = dishDto.getFlavors();
        for (DishFlavor flavor : flavors) {
            flavor.setDishId(dishId);
        }

        // 保存菜品口味数据到菜品口味表dish_flavor
        dishFlavorService.saveBatch(flavors);
    }

    @Override
    public Page<Dish> page(int page, int pageSize, String name) {
        /**
         * 踩坑提示
         * 使用mp分页 xml只需要写入模糊查询等条件即可,limit 分页 Page会自动帮我们加上
         */

        Page<Dish> pageInfo = new Page<>(page,pageSize);

        if (name != null){
            name = "%"+name+"%";
        }

        //Page 对象必须放在第一位
        Page<Dish> pageDish = dishMapper.page(pageInfo, page, pageSize, name);

        return pageDish;
    }
}
