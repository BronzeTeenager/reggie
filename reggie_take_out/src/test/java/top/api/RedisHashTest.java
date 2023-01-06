package top.api;

import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class RedisHashTest {
    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    void hashPut(){
    redisTemplate.opsForHash().put("user","张三","");
    }

    @Test
    void hashGet(){

    }

    @Test
    void hashSize(){

    }
}
