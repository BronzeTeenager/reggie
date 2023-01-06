package top.api;

import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Scanner;

@SpringBootTest
public class Test {

    public static void main(String[] args) {
        int data = 0;
        System.out.println("请输入一个整数: ");
        Scanner sc = new Scanner(System.in);
        data = sc.nextInt();
        System.out.println(data);
    }
}
