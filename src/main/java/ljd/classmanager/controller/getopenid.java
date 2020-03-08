package ljd.classmanager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ljd.classmanager.Entity.OpenIdJson;
import ljd.classmanager.Entity.StudentsEntity;
import ljd.classmanager.Service.StudentsService;
import ljd.classmanager.config.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

/**
 * @program: mini_service
 * @description:
 * @author: liu yan
 * @create: 2019-11-25 15:49
 */
@RestController
public class getopenid {
    @Autowired
    private StudentsService studentsService;
    private String appID = "wx471347ec1886ea02";
    private String appSecret = "973a7eebd5093fd231e3550c7a9926e8";
    @PostMapping("/wechat/getopenid")
    public StudentsEntity getopenid(@RequestParam String code)throws IOException {
        String result = "";
        try{//请求微信服务器，用code换取openid。HttpUtil是工具类，后面会给出实现，Configure类是小程序配置信息，后面会给出代码
            result = HttpUtil.doGet(
                    "https://api.weixin.qq.com/sns/jscode2session?appid="
                            + this.appID + "&secret="
                            + this.appSecret + "&js_code="
                            + code
                            + "&grant_type=authorization_code", null);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        ObjectMapper mapper = new ObjectMapper();
        OpenIdJson openIdJson = mapper.readValue(result,OpenIdJson.class);
        System.out.println(result);
        System.out.println(openIdJson.getOpenid());
        StudentsEntity stu=new StudentsEntity();
        if (studentsService.getStuByOpenid(openIdJson.getOpenid()).size()==0){
           stu.setsOpenid(openIdJson.getOpenid());
           stu.setSessionId(openIdJson.getSession_key());
           return stu;
        }else {
        StudentsEntity studentsEntity=studentsService.getStuByOpenid(openIdJson.getOpenid()).get(0);
        studentsEntity.setsOpenid(openIdJson.getOpenid());
        studentsEntity.setSessionId(openIdJson.getSession_key());
        return studentsEntity;
        }
    }
}