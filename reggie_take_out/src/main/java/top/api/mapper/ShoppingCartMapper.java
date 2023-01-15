package top.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.api.pojo.ShoppingCart;
@Mapper
public interface ShoppingCartMapper extends BaseMapper<ShoppingCart> {
}
