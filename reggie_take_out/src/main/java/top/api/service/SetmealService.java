package top.api.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import top.api.dto.DishDto;
import top.api.dto.SetmealDto;
import top.api.pojo.Setmeal;

import java.util.List;


public interface SetmealService extends IService<Setmeal> {
    /**
     * 新增套餐,同时保存套餐和菜品的关联
     */
    void saveWithDish(SetmealDto setmealDto);

    /**
     * 套餐分页动态模糊查询
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    Page<SetmealDto> page(int page,int pageSize, String name);

    /**
     * 批量删除套餐表和套餐菜品关联表
     * @param ids
     */
    void removeWithDish(List<Long> ids);

    /**
     * 查询套餐信息
     * @param id
     * @return
     */
    SetmealDto getSetmealInfo(Long id);

    /**
     * 修改套餐表,套餐菜品表信息
     * @param setmealDto
     */
    void updateSetmeal(SetmealDto setmealDto);



}
