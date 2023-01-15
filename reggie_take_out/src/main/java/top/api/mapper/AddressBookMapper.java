package top.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.api.pojo.AddressBook;

@Mapper
public interface AddressBookMapper extends BaseMapper<AddressBook> {
}
