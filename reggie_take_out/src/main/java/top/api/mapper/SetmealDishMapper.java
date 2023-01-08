package top.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.api.dto.SetmealDto;
import top.api.pojo.SetmealDish;

@Mapper
public interface SetmealDishMapper extends BaseMapper<SetmealDish> {
}
