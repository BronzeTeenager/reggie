package top.api.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MP分页查询拦截器
 */
@Configuration
public class MyBatisPlusConfig {

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(){
        MybatisPlusInterceptor MybatisPlusInterceptor = new MybatisPlusInterceptor();
        MybatisPlusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        return MybatisPlusInterceptor;
    }
}
