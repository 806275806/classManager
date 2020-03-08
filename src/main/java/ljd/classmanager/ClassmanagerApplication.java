package ljd.classmanager;

import ljd.classmanager.config.setPasswordMD5;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("ljd.classmanager.Dao")
public class ClassmanagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClassmanagerApplication.class, args);
    }
}
