package top.api.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.api.common.R;
import top.api.dto.DishDto;
import top.api.dto.SetmealDto;
import top.api.pojo.Setmeal;
import top.api.pojo.SetmealDish;
import top.api.service.SetmealDishService;
import top.api.service.SetmealService;

import java.util.List;

@RestController
@RequestMapping("/setmeal")
@Slf4j
public class SetmealController {

    @Autowired
    private SetmealService setmealService;


    @Autowired
    private SetmealDishService SetmealDishService;

    /**
     * 新增套餐
     * @param setmealDto
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody SetmealDto setmealDto){

        setmealService.saveWithDish(setmealDto);

        return R.success("ok");
    }

    /**
     * 套餐分页动态模糊查询
     * @param page
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page<SetmealDto>> page(int page, int pageSize, String name){

        Page<SetmealDto> pageInfo = setmealService.page(page, pageSize, name);

        return R.success("ok",pageInfo);
    }

    /**
     * 批量删除套餐表和套餐菜品关联表
     * @param ids
     * @return
     */
    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids){

        setmealService.removeWithDish(ids);
        return R.success("ok");
    }

    /**
     * 批量 起售 停售
     * @param status
     * @param ids
     * @return
     */
    @PostMapping("/status/{status}")
    public R<String> updateStatus(@PathVariable Integer status,@RequestParam List<Long> ids){
        LambdaUpdateWrapper<Setmeal> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(Setmeal::getStatus,status);
        updateWrapper.in(Setmeal::getId,ids);

        setmealService.update(updateWrapper);
        return R.success("ok");
    }

    /**
     *查询套餐信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<SetmealDto> getSetmealInfo(@PathVariable Long id){

        SetmealDto setmealDto = setmealService.getSetmealInfo(id);
        return R.success("ok",setmealDto);
    }

    /**
     * 修改套餐, 套餐菜品 信息
     * @param setmealDto
     * @return
     */
    @PutMapping
    public R<String> updateSetmeal(@RequestBody SetmealDto setmealDto){
        setmealService.updateSetmeal(setmealDto);
        return R.success("ok");
    }

    /**
     * 根据菜品分类id查询套餐
     * @return
     */
    @GetMapping("/list")
    public R<List<Setmeal>> list(Setmeal setmeal){
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(setmeal.getCategoryId()!=null,Setmeal::getCategoryId,setmeal.getCategoryId());
        queryWrapper.eq(setmeal.getStatus()!=null,Setmeal::getStatus,setmeal.getStatus());
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);

        List<Setmeal> setmealList = setmealService.list(queryWrapper);

        return R.success("ok",setmealList);
    }
}
