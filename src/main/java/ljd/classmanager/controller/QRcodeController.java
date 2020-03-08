package ljd.classmanager.controller;

/**
 * @program: classmanager
 * @description:
 * @author: liu yan
 * @create: 2020-02-29 16:13
 */
import ljd.classmanager.Service.QRCode.QRcodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

/**
 *  获取二维码
 * @author yushen
 *
 */
@RestController
@RequestMapping("/qrcode")
public class QRcodeController {

    @Autowired
    QRcodeService qrCodeService;

    /**
     * 传入二维码要生成的内容 返回base64图片
     * @param req
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/getQRCode")
    @ResponseBody
    public String getQRCode(HttpServletRequest req) throws IOException {
        String qrCodeValue = req.getParameter("value") == null ? "nul" : req.getParameter("value");
        String CodeBase64=qrCodeService.crateQRCode(qrCodeValue, 250, 250);
        return CodeBase64;
    }

}
