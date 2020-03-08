package ljd.classmanager.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import ljd.classmanager.Entity.CourseEntity;
import ljd.classmanager.Entity.UserEntity;
import ljd.classmanager.Service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @program: springboot-shiro-demo
 * @description: 认证、授权页面跳转控制层
 * @author: liu yan
 * @create: 2019-11-15 21:03
 */
@Controller
public class ShiroController {
    /**
     * session中的验证码
     */
    private String SHIRO_VERIFY_SESSION = "verifySessionCode";
    /**
     * 验证失败提示
     */
    private String ERROR_KAPTCHA = "验证码不正确";

    @Autowired
    private DefaultKaptcha defaultKaptcha;
    @Autowired
    private UserService userService;
    /**
     * 获取验证码
     * @param response
     */
    @GetMapping("/getCode")
    public void getGifCode(HttpServletResponse response, HttpServletRequest request) throws IOException {
        byte[] verByte = null;
        ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
        try {
            //生产验证码字符串并保存到session中
            String createText = defaultKaptcha.createText();
            request.getSession().setAttribute(SHIRO_VERIFY_SESSION,createText);
            //使用生产的验证码字符串返回一个BufferedImage对象并转为byte写入到byte数组中
            BufferedImage challenge = defaultKaptcha.createImage(createText);
            ImageIO.write(challenge,"jpg",jpegOutputStream);
        } catch (IllegalArgumentException e){
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        } catch (IOException e){
            e.printStackTrace();
        }
        //定义response输出类型为image/jpeg类型，使用response输出流输出图片的byte数组
        verByte = jpegOutputStream.toByteArray();
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        ServletOutputStream responseOutputStream = response.getOutputStream();
        responseOutputStream.write(verByte);
        responseOutputStream.flush();
        responseOutputStream.close();
    }
    @RequestMapping("/login")
    public String login(String username, String password, String verifyCode, Model model, HttpSession session){
        //获取subject
        Subject subject=SecurityUtils.getSubject();
        UsernamePasswordToken token=new UsernamePasswordToken(username,password);
        // 获取session中的验证码
        String verCode = (String) subject.getSession().getAttribute(SHIRO_VERIFY_SESSION);
//        if("".equals(verifyCode)||(!verCode.equals(verifyCode))){
//            model.addAttribute("msg",ERROR_KAPTCHA);
//            return "/login";
//        }
        try {
            subject.login(token);
            UserEntity user=userService.Byusername(username);
            session.setAttribute("userName","欢迎您，"+user.getUserName());
            session.setAttribute("userCode",username);
            System.out.println("登录成功");
            return "redirect:/index";
        }catch (UnknownAccountException e){
            System.out.println("用户名不存在");
            model.addAttribute("msg","用户名不存在");
            return "/login";
        }catch (IncorrectCredentialsException e){
            System.out.println("密码错误");
            model.addAttribute("msg","密码错误");
            return "/login";
        }
    }
    @RequestMapping("/index")
    public String index(){
        return "index";
    }
    @RequestMapping("/tologin")
    public String tologin(){
        return "login";
    }
//    @RequestMapping("/add")
//    public  String add(){
//        return "/user/add";
//    }
//    @RequestMapping("/update")
//    public  String update(){
//        return "/user/update";
//    }
}