package top.api;

import org.apache.tomcat.util.security.MD5Encoder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import top.api.mapper.EmployeeMapper;
import top.api.pojo.Employee;

import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.spec.AlgorithmParameterSpec;

@SpringBootTest
public class Test2 {

    @Autowired
    private  EmployeeMapper employeeMapper;

    /**
     * 输出 hello world
     */
    @Test
    void one(){
        System.out.print("hello world");
    }

    /**
     * 两个整数,判断哪个是大值
     */
    @Test
    void two(){
      int num1 = 51;
      int num2 = 8;
      if (num1 > num2){
          System.out.print(num1+">"+num2);
      }else {
          System.out.print("num1<num2");
      }
    }

    /** 大题
    *
     * 判断这个数是否为奇数还是偶数
     */
    @Test
    void three(){
//        int num = 0;
//        if(num%2!=0){
//            System.out.print("奇数");
//        }else{
//            System.out.println("偶数");
//        }


        String md5 = DigestUtils.md5DigestAsHex("1234".getBytes());
        System.out.println(md5);
    }

    @Test
    void aes(){
        Employee employee = employeeMapper.selectOne("管理员");
        System.out.println(employee);
    }
}
