package top.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import top.api.pojo.Dish;

import java.util.List;

@Mapper
public interface DishMapper extends BaseMapper<Dish> {


    // Page对象必须放在第一位
    Page<Dish> page(Page<Dish> pageInfo, @Param("page") int page, @Param("pageSize") int pageSize, @Param("name") String name);
}
