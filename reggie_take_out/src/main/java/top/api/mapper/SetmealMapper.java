package top.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import top.api.dto.SetmealDto;
import top.api.pojo.Setmeal;
import top.api.pojo.SetmealDish;

@Mapper
@Repository
public interface SetmealMapper extends BaseMapper<Setmeal> {

    Page<SetmealDto> page(Page<Setmeal> pageInfo, @Param("name") String name);
}
