package top.api.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.api.pojo.ShoppingCart;

public interface ShoppingCartService extends IService<ShoppingCart> {
    /**
     * 添加购物车 菜品/套餐
     * @param shoppingCart
     * @return
     */
    ShoppingCart add(ShoppingCart shoppingCart);
}
