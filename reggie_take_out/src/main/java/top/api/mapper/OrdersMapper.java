package top.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sun.tracing.dtrace.ModuleAttributes;
import org.apache.ibatis.annotations.Mapper;
import top.api.pojo.Orders;

@Mapper
public interface OrdersMapper extends BaseMapper<Orders> {
}
