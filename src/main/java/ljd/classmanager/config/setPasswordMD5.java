package ljd.classmanager.config;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * @program: classmanager
 * @description: 设置MD5密码加盐
 * @author: liu yan
 * @create: 2020-01-14 14:45
 */
public class setPasswordMD5 {
    public String setMD5(String userCode,String pd){
        String hashAlgorithName = "MD5";
        String password = pd;
        //加密次数
        int hashIterations = 1;
        ByteSource credentialsSalt = ByteSource.Util.bytes(userCode);
        String string = String.valueOf(new SimpleHash(hashAlgorithName, password, credentialsSalt, hashIterations));
        System.out.println(string);
        return string;
    }
}