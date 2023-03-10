package top.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.api.pojo.Employee;

/**
 * 员工实体类
 */
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}
