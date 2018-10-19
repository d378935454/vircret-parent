package com.talentcenter.controller;

import RSTFul.RSTFulBody;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.talentcenter.common.UserLogic;
import com.talentcenter.entity.Certificate;
import com.talentcenter.entity.Menu;
import com.talentcenter.entity.User;
import com.talentcenter.service.MenuService;
import com.talentcenter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController{

    @Autowired
    private UserService userService;

    /**
     * 管理员列表
     * @param model
     * @return
     */
    @RequestMapping("/index.html")
    public String index(Model model){
        User user = new User();
        user.setDel(1);
        user.setUserNature(0);
        List<User> users = userService.select(user);
        model.addAttribute("users",users);
        return "/user/index.html";
    }

    @RequestMapping("/ajax_index")
    public String ajaxIndex(Model model, int pageNum, int pageSize,
                            @RequestParam(required = false) String userName,
                            @RequestParam(required = false) String realName,
                            @RequestParam(required = false) String userTel
    ){
        //组装搜索条件
        User user = new User();
        if(userName!=null && userName!="") user.setUserName(userName);
        if(realName!=null && realName!="") user.setRealName(realName);
        if(userTel!=null && userTel!="") user.setUserTel(userTel);

        //分页查询
        PageHelper.startPage(pageNum, pageSize);
        List<User> users = userService.select(user);

        PageInfo<User> pageInfo= new PageInfo<>(users);
        String pageStr = makePageHtml(pageInfo);
        model.addAttribute("page_info",pageInfo);
        model.addAttribute("pages",pageStr);
        return "/user/ajax_index.html";
    }

    @RequestMapping("add.html")
    public String addUI() {
        return "/user/add.html";
    }

    @RequestMapping("add_info.html")
    public String addUI1() {
        return "/user/add_info.html";
    }

    @RequestMapping("accept.html")
    public String accept() {
        return "/user/accept.html";
    }

    @RequestMapping("zhengce.html")
    public String zhengCe() {
        return "/user/zhengce.html";
    }

    @RequestMapping("accept_info.html")
    public String accept_info() {
        return "/user/accept_info.html";
    }

    @RequestMapping("data.html")
    public String data() {
        return "/user/data.html";
    }

    @ResponseBody
    @RequestMapping("add")
    public RSTFulBody add(User user) {
        User sessionUser = getSessionUser();
        user.setCreateName(sessionUser.getUserName());
        user.setCreateId(sessionUser.getUserId());
        int res = userService.insertSelective(user);
        RSTFulBody rstFulBody = new RSTFulBody();
        if (res > 0) rstFulBody.success("添加成功！");
        else rstFulBody.fail("添加失败！");
        return rstFulBody;
    }
}
