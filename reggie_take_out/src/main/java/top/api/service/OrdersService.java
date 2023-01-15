package top.api.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.api.pojo.Orders;

public interface OrdersService extends IService<Orders> {
    /**
     * 下单
     * @param orders
     */
    void submit(Orders orders);
}
