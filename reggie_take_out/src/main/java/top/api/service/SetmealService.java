package top.api.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.api.dto.SetmealDto;
import top.api.pojo.Setmeal;


public interface SetmealService extends IService<Setmeal> {
    /**
     * 新增套餐,同时保存套餐和菜品的关联
     */
    void saveWithDish(SetmealDto setmealDto);
}
