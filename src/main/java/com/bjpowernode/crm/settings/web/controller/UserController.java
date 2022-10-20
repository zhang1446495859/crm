package com.bjpowernode.crm.settings.web.controller;

import com.bjpowernode.crm.commons.constants.constants;
import com.bjpowernode.crm.commons.domain.ReturnObject;
import com.bjpowernode.crm.commons.utils.DateUtils;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    //一个资源目录 一个Controller

    /**
     * url 要和controller 方法处理完请求之后，响应信息返回的页面的资源目录保持一致
     * @return
     */
    @RequestMapping("/settings/qx/user/login")
    public String login(){
        return "settings/qx/user/login";
    }

    @ResponseBody
    @RequestMapping("/settings/qx/user/toLogin")
    public Object toLogin(String loginAct, String loginPwd, String isRemPwd, HttpServletRequest request, HttpSession session, HttpServletResponse response){
        //封装参数
        Map<String,Object> map = new HashMap<>();
        map.put("loginAct",loginAct);
        map.put("loginPwd",loginPwd);
        //调用service层 方法 查询用户
        User user = userService.queryUserByLoginActAndPwd(map);
        //根据查询结果，生成响应信息
        ReturnObject returnObject = new ReturnObject();
        if( user == null){
            //登录失败，用户名或密码错误
            returnObject.setCode(constants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("登录失败，用户名或密码错误");
        }else {
            String nowDate = DateUtils.formateDateTime(new Date());
            if(nowDate.compareTo(user.getExpireTime()) > 0){
                //登录失败，账号过期
                returnObject.setCode(constants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("登录失败，账号过期");
            }else {
                if("0".equals(user.getLockState())){
                    //登录失败，账号被锁
                    returnObject.setCode(constants.RETURN_OBJECT_CODE_FAIL);
                    returnObject.setMessage("登录失败，账号被锁");
                }else {
                    if(!user.getAllowIps().contains(request.getRemoteAddr())){
                        //登录失败，ip被限制
                        returnObject.setCode(constants.RETURN_OBJECT_CODE_FAIL);
                        returnObject.setMessage("登录失败，ip被限制");
                    }else {
                        //登录成功
                        returnObject.setCode(constants.RETURN_OBJECT_CODE_SUCCESS);
                        //把user保存到session中
                        session.setAttribute(constants.SESSION_USER,user);

                        //如果需要记住密码，则往外写cookie request 请求 response 响应
                        if("true".equals(isRemPwd)){
                            Cookie cookie = new Cookie("loginAct",user.getLoginAct());
                            cookie.setMaxAge(60*60*24*10);
                            response.addCookie(cookie);
                            Cookie cookie2 = new Cookie("loginPwd", user.getLoginPwd());
                            cookie2.setMaxAge(60*60*24*10);
                            response.addCookie(cookie2);
                        }else{
                            //没有记住密码 把没有过期的cookie删除
                            Cookie cookie = new Cookie("loginAct","1");
                            cookie.setMaxAge(0);
                            response.addCookie(cookie);
                            Cookie cookie2 = new Cookie("loginPwd", "1");
                            cookie2.setMaxAge(0);
                            response.addCookie(cookie2);
                        }
                    }
                }
            }
        }
        return returnObject;
    }

    @RequestMapping("/settings/qx/user/logout")
    public String logOUt(HttpServletResponse response,HttpSession session){
        //清除cookie
        Cookie cookie = new Cookie("loginAct","1");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        Cookie cookie2 = new Cookie("loginPwd", "1");
        cookie2.setMaxAge(0);
        response.addCookie(cookie2);

        //销毁session
        session.invalidate();
        //跳转首页
        return "redirect:/"; //借助springmvc 来重定向，response.sendRedirect("/crm/")
    }
}
