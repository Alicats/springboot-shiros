package cn.xej;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringbootShiroApplicationTests {

    @Test
    void contextLoads() {

        String password1 = new SimpleHash("MD5","123","teacher1",1024).toString();
        System.out.println("password1 "+password1);
        String password2 = new SimpleHash("MD5","123","admin",1024).toString();
        System.out.println("password1 "+password2);
        String password3 = new SimpleHash("MD5","123","user1",1024).toString();
        System.out.println("password1 "+password3);
    }

}
