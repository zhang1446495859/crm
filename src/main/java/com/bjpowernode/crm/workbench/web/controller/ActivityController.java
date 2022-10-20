package com.bjpowernode.crm.workbench.web.controller;

import com.bjpowernode.crm.commons.constants.constants;
import com.bjpowernode.crm.commons.domain.ReturnObject;
import com.bjpowernode.crm.commons.utils.DateUtils;
import com.bjpowernode.crm.commons.utils.UUIDUtils;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.Impl.UserServiceImpl;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class ActivityController {

    @Autowired
    private UserService activityUserService;

    @Autowired
    private ActivityService activityService;

    @RequestMapping("/workbench/activity/index")
    public String activityIndex(HttpServletRequest request){
        List<User> users = activityUserService.queryAllUsers();
        request.setAttribute("users",users);
        return "workbench/activity/index";
    }

    @RequestMapping("/workbench/activity/saveCreateActivity")
    @ResponseBody
    public Object saveCreateActivity(Activity activity, HttpSession session){
        User user = (User) session.getAttribute(constants.SESSION_USER);
        //uuid 随机生成32位id
        activity.setId(UUIDUtils.getUUID());
        activity.setCreateTime(DateUtils.formateDateTime(new Date()));
        activity.setCreateBy(user.getId());
        ReturnObject returnObject = new ReturnObject();
        try {
            //调用service层
            int i = activityService.saveCreateActivity(activity);
            if(i > 0){
               returnObject.setCode(constants.RETURN_OBJECT_CODE_SUCCESS);
            }else {
                returnObject.setCode(constants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("系统忙，请稍后重试....");
            }
        }catch (Exception e){
            e.printStackTrace();
            returnObject.setCode(constants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("系统忙，请稍后重试....");
        }

        return returnObject;
    }

    @RequestMapping("/workbench/activity/queryActivityByConditionForPage")
    @ResponseBody
    public Object queryActivityByConditionForPage(String name,String owner,String startDate,String endDate,
                                                  int pageNo,int pageSize){
        Map<String,Object> map = new HashMap<>();
        map.put("name",name);
        map.put("owner",owner);
        map.put("startDate",startDate);
        map.put("endDate",endDate);
        map.put("beginNo",(pageNo-1)*pageSize);
        map.put("pageSize",pageSize);
//        System.out.println(map);
        //调用service方法执行sql语句
        List<Activity> activityList = activityService.queryActivityByConditionForPage(map);
        int i = activityService.queryCountOfActivityByCondition(map);
        //根据查询结果，返回响应信息

        Map<String,Object> retMap = new HashMap<>();

        retMap.put("activityList",activityList);
        retMap.put("totalRows",i);

        return retMap;
    }


}
