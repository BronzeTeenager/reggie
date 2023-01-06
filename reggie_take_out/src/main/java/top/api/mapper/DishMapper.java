package top.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.api.pojo.Dish;

@Mapper
public interface DishMapper extends BaseMapper<Dish> {
}
