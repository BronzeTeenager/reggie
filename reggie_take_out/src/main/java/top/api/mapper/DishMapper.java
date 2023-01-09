package top.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.springframework.stereotype.Repository;
import top.api.dto.DishDto;
import top.api.pojo.Dish;

import javax.annotation.Resources;
import java.util.List;

@Repository
@Mapper
public interface DishMapper extends BaseMapper<Dish> {


    // Page对象必须放在第一位
    Page<DishDto> page(Page<Dish> pageInfo, @Param("name") String name);
}
