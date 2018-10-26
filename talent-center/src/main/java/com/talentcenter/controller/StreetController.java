package com.talentcenter.controller;

import RSTFul.RSTFulBody;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.talentcenter.entity.Street;
import com.talentcenter.entity.User;
import com.talentcenter.service.StreetService;
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
@RequestMapping("/street")
public class StreetController extends BaseController{
    @Autowired
    private StreetService streetService;

    @Autowired
    private UserService userService;

   /* @Autowired
    private RoleStreetService roleStreetService;*/

    /**
     * 证书列表页
     * @param model
     * @return
     */
    @RequestMapping("/index.html")
    public String index(Model model){
        return "/street/index.html";
    }

    @RequestMapping("/ajax_index")
    public String ajaxIndex(Model model, int pageNum, int pageSize,
                            @RequestParam(required = false) String streetName
    ){
        //组装搜索条件
        /*Map<String,Object> map=new HashMap<>();
        if(userTrueName!=null && userTrueName!="") map.put("userTrueName",userTrueName);
        if(examId!=null && examId!="") map.put("examId",examId);
        if(userSex!=null && userSex!="") map.put("userSex",userSex);*/
        Street street = new Street();
        street.setDel(true);
        if(streetName!=null && streetName!="") street.setStreetName(streetName);
        //分页查询
        PageHelper.startPage(pageNum, pageSize);
        List<Street> streets = streetService.selectByInfo(street);

        PageInfo<Street> pageInfo= new PageInfo<>(streets);
        String pageStr = makePageHtml(pageInfo);
        model.addAttribute("page_info",pageInfo);
        model.addAttribute("pages",pageStr);
        return "/street/ajax_index.html";
    }

    @RequestMapping("add.html")
    public String addUI() {
        return "/street/add.html";
    }

    @ResponseBody
    @RequestMapping("add")
    public RSTFulBody add(Street street) {
        User sessionUser = getSessionUser();
        street.setCreateName(sessionUser.getUserName());
        street.setCreateId(sessionUser.getUserId());
        int res = streetService.insertSelective(street);
        RSTFulBody rstFulBody = new RSTFulBody();
        if (res > 0) rstFulBody.success("添加成功！");
        else rstFulBody.fail("添加失败！");
        return rstFulBody;
    }

    @RequestMapping("edit.html")
    public String editUI(Model model, String streetId) {
        Street street = streetService.selectByPrimaryKey((long) Integer.parseInt(streetId));
        model.addAttribute("streetId",streetId);
        model.addAttribute("obj",street);
        return "/street/edit.html";
    }

    @ResponseBody
    @RequestMapping("edit")
    public RSTFulBody edit(Street street) {
        User sessionUser = getSessionUser();
        street.setUpdateId(sessionUser.getUserId());
        street.setUpdateName(sessionUser.getUserName());
        street.setUpdateTime(DateHelper.getCurrentDate());
        int res = streetService.updateByPrimaryKeySelective(street);
        RSTFulBody rstFulBody = new RSTFulBody();
        if (res > 0) rstFulBody.success("修改成功！");
        else rstFulBody.fail("修改失败！");
        return rstFulBody;
    }

    @RequestMapping("del.html")
    public String delUser(Street street){
        street.setDel(false);
        int res = streetService.updateByPrimaryKeySelective(street);
        Map<String, Object> map = new HashMap<>();
        map.put("ids",street.getStreetId());
        streetService.delStreetUser(map);
        return "redirect:/street/index.html";
    }

    @ResponseBody
    @RequestMapping("batch_del")
    public RSTFulBody batchDel(@RequestParam(required = true) String ids){
        Map<String, Object> map = new HashMap<>();
        map.put("ids",ids);
        int res = streetService.batchDel(map);
        streetService.delStreetUser(map);
        RSTFulBody rstFulBody=new RSTFulBody();
        if(res>0) rstFulBody.success(res);
        else  rstFulBody.fail("删除失败！");
        return rstFulBody;
    }

    @RequestMapping("users.html")
    public String users(Model model,Integer streetId){
        model.addAttribute("streetId",streetId);
        return "/street/users.html";
    }

    @RequestMapping("/ajax_users")
    public String ajaxUsers(Model model, int pageNum, int pageSize,
                            @RequestParam(required = false) String userName,
                            @RequestParam(required = false) String realName,
                            @RequestParam(required = false) String userTel,
                            @RequestParam(required = false) long streetId
    ){
        //组装搜索条件
        User user = new User();
        user.setDel(true);
        if(userName!=null && userName!="") user.setUserName(userName);
        if(realName!=null && realName!="") user.setRealName(realName);
        if(userTel!=null && userTel!="") user.setUserTel(userTel);
        user.setStreetId(streetId);
//        user.setStreetId((long)Integer.parseInt(streetId));

        //分页查询
        PageHelper.startPage(pageNum, pageSize);
        List<User> users = userService.select(user);

        PageInfo<User> pageInfo= new PageInfo<>(users);
        String pageStr = makePageHtml(pageInfo);
        model.addAttribute("page_info",pageInfo);
        model.addAttribute("pages",pageStr);
        return "/street/ajax_users.html";
    }

    @RequestMapping("add_user.html")
    public String addUserUI(Model model,String streetId) {
        model.addAttribute("streetId",streetId);
        return "/street/add_user.html";
    }

    @ResponseBody
    @RequestMapping("add_user")
    public RSTFulBody addUser(User user) {
        User sessionUser = getSessionUser();
        user.setCreateName(sessionUser.getUserName());
        user.setCreateId(sessionUser.getUserId());
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        user.setUserNature(2);
        int res = userService.insertSelective(user);
        RSTFulBody rstFulBody = new RSTFulBody();
        rstFulBody.data(user.getStreetId()+"");
        if (res > 0) rstFulBody.success("添加成功！");
        else rstFulBody.fail("添加失败！");
        return rstFulBody;
    }

    @RequestMapping("edit_user.html")
    public String editUserUI(Model model, long primaryKey) {
//        User user = userService.selectByPrimaryKey((long)Integer.parseInt(primaryKey));
        User user = userService.selectByPrimaryKey(primaryKey);
        model.addAttribute("obj",user);
        return "/street/edit_user.html";
    }

    @ResponseBody
    @RequestMapping("edit_user")
    public RSTFulBody editUser(User user) {
        User sessionUser = getSessionUser();
        user.setUpdateId(sessionUser.getUserId());
        user.setUpdateName(sessionUser.getUserName());
        user.setUpdateTime(DateHelper.getCurrentDate());
        if(user.getPassword()!=null) user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        int res = userService.updateByPrimaryKeySelective(user);
        RSTFulBody rstFulBody = new RSTFulBody();
        rstFulBody.data(user.getStreetId()+"");
        if (res > 0) rstFulBody.success("修改成功！");
        else rstFulBody.fail("修改失败！");
        return rstFulBody;
    }

    @RequestMapping("del_user.html")
    public String del(User user,String streetId){
        user.setDel(false);
        int res = userService.updateByPrimaryKeySelective(user);
        return "redirect:/street/users.html?streetId="+streetId;
    }

    @ResponseBody
    @RequestMapping("batch_del_user")
    public RSTFulBody batchDelUser(@RequestParam(required = true) String ids,String streetId){

        Map<String, Object> map = new HashMap<>();
        map.put("ids",ids);
        int res = userService.batchDel(map);
        RSTFulBody rstFulBody=new RSTFulBody();
        rstFulBody.data(streetId);
        if(res>0) rstFulBody.success(res);
        else  rstFulBody.fail("删除失败！");
        return rstFulBody;
    }
}
