package top.api.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.api.dto.DishDto;
import top.api.pojo.Dish;

public interface DishService extends IService<Dish> {
    void saveWithFlavor(DishDto dishDto);
}
