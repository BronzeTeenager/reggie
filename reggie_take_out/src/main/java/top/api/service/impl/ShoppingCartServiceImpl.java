package top.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.api.common.BaseContext;
import top.api.mapper.ShoppingCartMapper;
import top.api.pojo.ShoppingCart;
import top.api.service.ShoppingCartService;

import java.time.LocalDateTime;

@Service
@Slf4j
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {
    @Override
    @Transactional
    public ShoppingCart add(ShoppingCart shoppingCart) {
        // 获取userId
        Long userId = BaseContext.get();
        shoppingCart.setUserId(userId);

        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId,userId);
        // 判断该是菜品还是套餐
        if (shoppingCart.getDishId() != null){
            // 添加菜品
            queryWrapper.eq(ShoppingCart::getDishId,shoppingCart.getDishId());
        }else if (shoppingCart.getSetmealId() != null){
            // 添加套餐
            queryWrapper.eq(ShoppingCart::getSetmealId,shoppingCart.getSetmealId());
        }

        ShoppingCart dbShoppingCart = super.getOne(queryWrapper);

        // 判断数据库是否存在, 不存在添加, 存在number + 1
        if (dbShoppingCart != null){
            // 存在 +1
            dbShoppingCart.setNumber(dbShoppingCart.getNumber() + 1);
            super.updateById(dbShoppingCart);
        }else{
            // 不存在添加
            shoppingCart.setCreateTime(LocalDateTime.now());
            super.save(shoppingCart);
            // 赋值 return
            dbShoppingCart = shoppingCart;
        }

        return dbShoppingCart;
    }
}
