package top.api.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.api.pojo.Category;

public interface CategoryService extends IService<Category> {

    void remove(Long id);
}
