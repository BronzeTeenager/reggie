package top.api.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.api.common.BaseContext;
import top.api.common.R;
import top.api.pojo.ShoppingCart;
import top.api.service.ShoppingCartService;

import javax.annotation.PostConstruct;
import javax.net.ssl.SNIHostName;
import java.util.List;

@RestController
@RequestMapping("/shoppingCart")
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService shoppingCartService;

    /**
     * 添加购物车
     * @param shoppingCart
     * @return
     */
    @PostMapping("/add")
    public R<ShoppingCart> add(@RequestBody ShoppingCart shoppingCart){
        ShoppingCart shoppingCart1 = shoppingCartService.add(shoppingCart);
        return R.success("ok",shoppingCart1);
    }

    /**
     * 获取购物车数据
     * @return
     */
    @GetMapping("/list")
    public R<List<ShoppingCart>> list(){
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId, BaseContext.get());
        queryWrapper.orderByAsc(ShoppingCart::getCreateTime);

        List<ShoppingCart> shoppingCarts = shoppingCartService.list(queryWrapper);
        return R.success("ok",shoppingCarts);
    }

    /**
     * 清空购物车
     * @return
     */
    @DeleteMapping("/clean")
    public R<String> clean(){
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId,BaseContext.get());

        shoppingCartService.remove(queryWrapper);
        return R.success("清空购物车成功");
    }
}
