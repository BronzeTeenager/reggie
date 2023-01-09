package top.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.api.dto.SetmealDto;
import top.api.exception.ControllerException;
import top.api.mapper.SetmealMapper;
import top.api.pojo.Setmeal;
import top.api.pojo.SetmealDish;
import top.api.service.SetmealDishService;
import top.api.service.SetmealService;

import java.util.List;

@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {
    @Autowired
    private SetmealDishService setmealDishService;

    @Autowired
    private SetmealMapper setmealMapper;

    @Override
    @Transactional
    public void saveWithDish(SetmealDto setmealDto) {
        //保存套餐表
        super.save(setmealDto);

        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        for (SetmealDish setmealDish : setmealDishes) {
            setmealDish.setSetmealId(setmealDto.getId());
        }

        //保存套餐菜品关联表
        setmealDishService.saveBatch(setmealDto.getSetmealDishes());

    }

    @Override
    public Page<SetmealDto> page(int page, int pageSize, String name) {
        Page<Setmeal> pageInfo = new Page<>(page,pageSize);

        if (name != null){
            name = "%"+name+"%";
        }

        Page<SetmealDto> setmealDtoPage = setmealMapper.page(pageInfo, name);


        return setmealDtoPage;

    }

    @Override
    public void removeWithDish(List<Long> ids) {
        // 查询是否存在起售套餐
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.in(Setmeal::getId,ids);
        // 是否起售
        setmealLambdaQueryWrapper.eq(Setmeal::getStatus,1);
        int count = super.count(setmealLambdaQueryWrapper);

        if (count > 0){
            throw new ControllerException("套餐正在售卖中,不可删除");
        }

        // 删除套餐表
        super.removeByIds(ids);

        // 删除套餐菜品关联表
        LambdaQueryWrapper<SetmealDish> setmealDishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealDishLambdaQueryWrapper.in(SetmealDish::getSetmealId,ids);
        setmealDishService.remove(setmealDishLambdaQueryWrapper);

    }
}
