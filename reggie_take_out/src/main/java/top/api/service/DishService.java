package top.api.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import top.api.dto.DishDto;
import top.api.pojo.Dish;

public interface DishService extends IService<Dish> {
    void saveWithFlavor(DishDto dishDto);


    /**
     * 分页查询
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    Page<Dish> page(int page, int pageSize, String name);
}
