package top.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import top.api.common.BaseContext;
import top.api.exception.ControllerException;
import top.api.mapper.OrdersMapper;
import top.api.pojo.*;
import top.api.service.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Slf4j
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements OrdersService {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private UserService userService;

    @Autowired
    private AddressBookService addressBookService;

    @Autowired
    private OrderDetailService orderDetailService;

    @Override
    @Transactional
    public void submit(Orders orders) {
        // 获取用户id
        Long userId = BaseContext.get();

        // 获取购物车id
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId, userId);
        List<ShoppingCart> shoppingCarts = shoppingCartService.list(queryWrapper);
        if (shoppingCarts == null || shoppingCarts.size() == 0) {
            throw new ControllerException("购物车为空,不可下单");
        }

        // 获取用户信息
        User user = userService.getById(userId);

        // 获取地址信息 前端上传的
        AddressBook addressBook = addressBookService.getById(orders.getAddressBookId());
        if (addressBook == null) {
            throw new ControllerException("地址为空,不可下单");
        }


        long orderId = IdWorker.getId(); //生成雪花算法id

        // 原子操作,保证多线程数据安全
        AtomicInteger amount = new AtomicInteger(0);

        // 计算金额及生成订单明细list
        List<OrderDetail> orderDetailList = new ArrayList<>();
        for (ShoppingCart shoppingCart : shoppingCarts) {
            // 塞入数据
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderId(orderId);
            orderDetail.setNumber(shoppingCart.getNumber());
            orderDetail.setDishFlavor(shoppingCart.getDishFlavor());
            orderDetail.setDishId(shoppingCart.getDishId());
            orderDetail.setSetmealId(shoppingCart.getSetmealId());
            orderDetail.setName(shoppingCart.getName());
            orderDetail.setImage(shoppingCart.getImage());
            orderDetail.setAmount(shoppingCart.getAmount());
            //计算金额 金额 * 数量
            amount.addAndGet(shoppingCart.getAmount().multiply(new BigDecimal(shoppingCart.getNumber())).intValue());

            // 加入集合
            orderDetailList.add(orderDetail);
        }
        // 向订单表插入一条数据
        orders.setId(orderId);
        orders.setOrderTime(LocalDateTime.now());
        orders.setCheckoutTime(LocalDateTime.now());
        orders.setStatus(2); //订单状态 1待付款，2待派送，3已派送，4已完成，5已取消
        orders.setAmount(new BigDecimal(amount.get()));
        orders.setUserId(userId);
        orders.setNumber(String.valueOf(orderId));
        orders.setUserName(user.getName());
        orders.setConsignee(addressBook.getConsignee()); //收货人
        orders.setPhone(addressBook.getPhone());
        // 省级名称+市级名称+区级名称+详细地址
        orders.setAddress((addressBook.getProvinceName() == null ? "" : addressBook.getProvinceName()) + (addressBook.getCityName() == null ? "" : addressBook.getCityName()) + (addressBook.getDistrictName() == null ? "" : addressBook.getDistrictName()) + (addressBook.getDetail() == null ? "" : addressBook.getDetail()));
        super.save(orders);

        // 向订单明细表插入多条数据
        orderDetailService.saveBatch(orderDetailList);

        // 清空购物车
        shoppingCartService.remove(queryWrapper);

    }


}
