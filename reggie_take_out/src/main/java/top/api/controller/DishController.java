package top.api.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.api.common.R;
import top.api.dto.DishDto;
import top.api.pojo.Dish;
import top.api.service.DishFlavorService;
import top.api.service.DishService;

@RestController
@RequestMapping("/dish")
@Slf4j
public class DishController {
    @Autowired
    private DishService dishService;

    @Autowired
    private DishFlavorService dishFlavorService;

    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto){

        dishService.saveWithFlavor(dishDto);

        return R.success("ok");
    }

    /**
     * 动态分页模糊查询
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page<DishDto>> page(int page,int pageSize, String name){

        Page<DishDto> pageInfo = dishService.page(page, pageSize, name);

        return R.success("ok",pageInfo);
    }

    /**
     * 根据id查询菜品信息 口味信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<DishDto> get (@PathVariable Long id){
        DishDto withFlavor = dishService.getByIdWithFlavor(id);
        return R.success("ok",withFlavor);
    }

    @PutMapping
    public R<String> update(@RequestBody DishDto dishDto){
        dishService.updateByIdWithFlavor(dishDto);
        return R.success("ok");
    }
}
