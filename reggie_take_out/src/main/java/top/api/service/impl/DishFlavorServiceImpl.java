package top.api.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.api.mapper.DishFlavorMapper;
import top.api.mapper.DishMapper;
import top.api.pojo.DishFlavor;
import top.api.service.DishFlavorService;

@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {
}
