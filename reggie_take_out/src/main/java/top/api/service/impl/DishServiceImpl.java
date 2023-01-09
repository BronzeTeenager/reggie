package top.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
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

    @Transactional
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
    public Page<DishDto> page(int page, int pageSize, String name) {
        /**
         * 踩坑提示
         * 使用mp分页 xml只需要写入模糊查询等条件即可,limit 分页 Page会自动帮我们加上
         */

        Page<Dish> pageInfo = new Page<>(page,pageSize);

        if (name != null){
            name = "%"+name+"%";
        }

        //Page 对象必须放在第一位
        Page<DishDto> pageDish = dishMapper.page(pageInfo, name);

        return pageDish;
    }

    @Override
    public DishDto getByIdWithFlavor(Long id) {
        // 获取菜品信息
        Dish dish = super.getById(id);

        // 获取口味信息
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId,dish.getId());
        List<DishFlavor> flavorList = dishFlavorService.list(queryWrapper);

        DishDto dishDto = new DishDto();

        //对象拷贝
        BeanUtils.copyProperties(dish,dishDto);

        dishDto.setFlavors(flavorList);

        return dishDto;
    }

    @Override
    @Transactional
    public void updateByIdWithFlavor(DishDto dishDto) {
        // 修改dish表
        super.updateById(dishDto);

        // 删除口味表信息,在保存
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId,dishDto.getId());
        dishFlavorService.remove(queryWrapper);

        //插入dish_id
        for (DishFlavor flavor : dishDto.getFlavors()) {
            flavor.setDishId(dishDto.getId());
        }

        // 批量保存
        dishFlavorService.saveBatch(dishDto.getFlavors());

    }
}
