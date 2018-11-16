package com.talentcenter.controller;

import RSTFul.RSTFulBody;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.talentcenter.entity.User;
import com.talentcenter.service.UserService;
import util.DateHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        user.setDel(true);
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
        user.setDel(true);
        user.setUserNature(0);
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

    @ResponseBody
    @RequestMapping("add")
    public RSTFulBody add(User user) {
        User sessionUser = getSessionUser();
        user.setCreateName(sessionUser.getUserName());
        user.setCreateId(sessionUser.getUserId());
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        int res = userService.insertSelective(user);
        RSTFulBody rstFulBody = new RSTFulBody();
        if (res > 0) rstFulBody.success("添加成功！");
        else rstFulBody.fail("添加失败！");
        return rstFulBody;
    }

    @RequestMapping("edit.html")
    public String editUI(Model model, String primaryKey) {
        User user = userService.selectByPrimaryKey((long)Integer.parseInt(primaryKey));
        model.addAttribute("obj",user);
        return "/user/edit.html";
    }

    @ResponseBody
    @RequestMapping("edit")
    public RSTFulBody edit(User user) {
        User sessionUser = getSessionUser();
        user.setUpdateId(sessionUser.getUserId());
        user.setUpdateName(sessionUser.getUserName());
        user.setUpdateTime(DateHelper.getCurrentDate());
        if(user.getPassword()!=null) user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        int res = userService.updateByPrimaryKeySelective(user);
        RSTFulBody rstFulBody = new RSTFulBody();
        if (res > 0) rstFulBody.success("修改成功！");
        else rstFulBody.fail("修改失败！");
        return rstFulBody;
    }

    @RequestMapping("del.html")
    public String delUser(User user){
        user.setDel(false);
        int res = userService.updateByPrimaryKeySelective(user);
        return "redirect:/user/index.html";
    }

    @ResponseBody
    @RequestMapping("batch_del")
    public RSTFulBody batchDel(@RequestParam(required = true) String ids){

        Map<String, Object> map = new HashMap<>();
        map.put("ids",ids);
        int res = userService.batchDel(map);
        RSTFulBody rstFulBody=new RSTFulBody();
        if(res>0) rstFulBody.success(res);
        else  rstFulBody.fail("删除失败！");
        return rstFulBody;
    }
}
