package com.itfusen.controller;


import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.itfusen.domain.TextMessage;
import com.itfusen.utils.HttpClientUtil;
import com.itfusen.utils.HttpsGetUtil;
import com.itfusen.utils.WeChatUtils;
import com.itfusen.utils.XmlUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.jws.WebMethod;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

@RestController
public class WeChatController {

    public static String token_now ;

    /*
     * 自定义token, 用作生成签名,从而验证安全性
     * */
    private final String TOKEN = "itfusen";

    @RequestMapping(value="/wechat",method = RequestMethod.GET)
    public String index (@Param("signature") String signature,@Param("timestamp") String timestamp ,@Param("nonce") String nonce ,@Param("echostr") String echostr){
        System.out.println("-----开始校验签名-----");

        /**
         * 将token、timestamp、nonce三个参数进行字典序排序
         * 并拼接为一个字符串
         */
        String sortStr = WeChatUtils.sort(TOKEN,timestamp,nonce);
        /**
         * 字符串进行shal加密
         */
        String mySignature = WeChatUtils.shal(sortStr);
        /**
         * 校验微信服务器传递过来的签名 和  加密后的字符串是否一致, 若一致则签名通过
         */
        if(!"".equals(signature) && !"".equals(mySignature) && signature.equals(mySignature)){
            return echostr;
        }else {
            System.out.println("-----校验签名失败-----");
            return null;
        }

    }

    @RequestMapping(value="/wechat",method = RequestMethod.POST)
    public void index (HttpServletRequest reqest, HttpServletResponse response) throws Exception {
        reqest.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        // 1.将xml转换成Map格式
        Map<String, String> resultMap = XmlUtils.parseXml(reqest);
        // 2.判断消息类型
        String msgType = resultMap.get("MsgType");
        // 3.如果是文本类型，返回结果给微信服务端
        PrintWriter writer = response.getWriter();
        switch (msgType) {
            case "text":
                // 开发者微信公众号
                String toUserName = resultMap.get("ToUserName");
                // 消息来自公众号
                String fromUserName = resultMap.get("FromUserName");
                // 消息内容
                String content = resultMap.get("Content");
//                String resultJson = HttpClientUtil.doGet(REQESTURL + content);
//                JSONObject jsonObject = JSONObject.parseObject(resultJson);
//                Integer resultCode = jsonObject.getInteger("result");
                String msg = null;
                if ("123".equals(content)) {
                    String resultContent = "123456";
                    msg = setText(resultContent, toUserName,fromUserName);
                }else {
                    msg = setText("我现在有点忙.稍后回复您!", toUserName,fromUserName);
                }
                writer.println(msg);
                break;

            default:
                break;
        }
        writer.close();
    }

    public String setText(String content, String fromUserName, String toUserName) {
        TextMessage textMessage = new TextMessage();
        textMessage.setContent(content);
        textMessage.setMsgType("text");
        textMessage.setCreateTime(new Date().getTime());
        textMessage.setFromUserName(fromUserName);
        textMessage.setToUserName(toUserName);
        // 将实体类转换成xml格式
        String msg = XmlUtils.messageToXml(textMessage);
        return msg;
    }
        /**
         *
          * @return String
         */
    @RequestMapping("getWxAccesstoken")
    public String getWxAccesstoken()
    {
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=" +
                "client_credential" +
                "&appid=" +
                "wx9307bff081b4ffe7" +
                "&secret=" +
                "62f396c91d35abcb561e6ceee21acd03";
        JSONObject json = JSONObject.parseObject(HttpsGetUtil.doHttpsGetJson(url));

        System.out.println(json.getString("access_token"));
        token_now = json.getString("access_token");
        System.out.println(json.getInteger("expires_in"));
        return null;
    }

    @RequestMapping("addWxButton")
    public String addWxButton(String paramter_now)
    {
        String url = " https://api.weixin.qq.com/cgi-bin/menu/create?access_token=" + token_now;
        JSONObject json = JSONObject.parseObject(HttpsGetUtil.sendPost(url,paramter_now));
        System.out.println(json.toJSONString());
        System.out.println(json.getString("errcode"));
        System.out.println(json.getInteger("errmsg"));
        return null;
    }

    public static void main(String[] args) {
        new WeChatController().getWxAccesstoken();
    }
}
