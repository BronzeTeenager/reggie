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

    @GetMapping("/page")
    public R<Page<Dish>> page(int page,int pageSize, String name){

        Page<Dish> pageInfo = dishService.page(page, pageSize, name);

        return R.success("ok",pageInfo);
    }
}
