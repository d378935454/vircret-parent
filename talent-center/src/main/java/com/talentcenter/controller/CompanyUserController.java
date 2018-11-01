package com.talentcenter.controller;

import RSTFul.RSTFulBody;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.talentcenter.entity.CompanyUserItem;
import com.talentcenter.entity.Item;
import com.talentcenter.entity.User;
import com.talentcenter.service.CompanyUserItemService;
import com.talentcenter.service.ItemService;
import com.talentcenter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import util.DateHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/company_user")
public class CompanyUserController extends BaseController {
    @Autowired
    private ItemService itemService;
    @Autowired
    private UserService userService;
    @Autowired
    private CompanyUserItemService companyUserItemService;


   /* @Autowired
    private RoleCompanyUserService roleCompanyUserService;*/

    /**
     * 证书列表页
     *
     * @param model
     * @return
     */
    @RequestMapping("/index.html")
    public String index(Model model) {
        return "/company_user/index.html";
    }

    @RequestMapping("/ajax_users")
    public String ajaxIndex(Model model, int pageNum, int pageSize,
                            @RequestParam(required = false) String companyUserName
    ) {
        //组装搜索条件
        /*Map<String,Object> map=new HashMap<>();
        if(userTrueName!=null && userTrueName!="") map.put("userTrueName",userTrueName);
        if(examId!=null && examId!="") map.put("examId",examId);
        if(userSex!=null && userSex!="") map.put("userSex",userSex);*/
        User companyUser = new User();
        companyUser.setDel(true);
        companyUser.setUserNature(3);
        companyUser.setCompanyId(getSessionUser().getUserId());


        if (companyUserName != null && companyUserName != "") companyUser.setUserName(companyUserName);
        //分页查询
        PageHelper.startPage(pageNum, pageSize);
        List<User> companyUsers = userService.select(companyUser);

        PageInfo<User> pageInfo = new PageInfo<>(companyUsers);
        String pageStr = makePageHtml(pageInfo);
        model.addAttribute("page_info", pageInfo);
        model.addAttribute("pages", pageStr);
        return "/company_user/ajax_users.html";
    }

    @RequestMapping("add_user.html")
    public String addUI(Model model) {
        List<Item> items = itemService.selectAll();
        model.addAttribute("items", items);
        return "/company_user/add_user.html";
    }

    @ResponseBody
    @RequestMapping("add_user")
    public RSTFulBody add(User user, @RequestParam(value = "itemId[]") String[] itemId) {
        User sessionUser = getSessionUser();
        user.setCreateName(sessionUser.getUserName());
        user.setCreateId(sessionUser.getUserId());
        user.setUserNature(3);
        user.setCompanyId(getSessionUser().getUserId());
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        int res = userService.insertSelective(user);

    /*    ArrayList<CompanyUserItem> companyUserItems = new ArrayList<>();
        //itemId = [1,2];
        for (String item: itemId) {
            CompanyUserItem companyUserItem = new CompanyUserItem();
            companyUserItem.setItemId((long)Integer.parseInt(item));
            companyUserItem.setItemName("");
            companyUserItem.setUserId(companyUser.getUserId());
            companyUserItems.add(companyUserItem);
        }
        int num = companyUserItemService.insertList(companyUserItems);*/
    /*
    for(int i=0;i<itemId.length;i++){
        CompanyUserItem companyUserItem = new CompanyUserItem();
        companyUserItem.setItemId((long)Integer.parseInt(itemId[i]));
    }*/
        ArrayList<CompanyUserItem> companyUserItems = new ArrayList<>();
        for (String item : itemId) {
            CompanyUserItem companyUserItem = new CompanyUserItem();
            companyUserItem.setItemId((long) Integer.parseInt(item));
            companyUserItem.setUserId(user.getUserId());
            Item i = itemService.selectByPrimaryKey((long) Integer.parseInt(item));
            companyUserItem.setItemName(i.getItemName());
            companyUserItems.add(companyUserItem);
        }
        int num = companyUserItemService.insertList(companyUserItems);

        RSTFulBody rstFulBody = new RSTFulBody();
        if (res > 0) rstFulBody.success("添加成功！");
        else rstFulBody.fail("添加失败！");
        return rstFulBody;
    }

    @RequestMapping("edit_user.html")
    public String editUI(Model model, String userId) {
        User user = userService.selectByPrimaryKey((long) Integer.parseInt(userId));
        CompanyUserItem companyUserItem = new CompanyUserItem();
        companyUserItem.setUserId((long) Integer.parseInt(userId));
        List<CompanyUserItem> companyUserItems = companyUserItemService.select(companyUserItem);
        List<Item> items = itemService.selectAll();

        for (Item i : items) {
            i.setChecked(false);
            for (CompanyUserItem cui : companyUserItems) {
                if (i.getItemId() == cui.getItemId()) {
                    i.setChecked(true);
                    continue;
                }
            }
        }
        model.addAttribute("obj", user);
        model.addAttribute("items", items);
        return "/company_user/edit_user.html";
    }

    @ResponseBody
    @RequestMapping("edit_user")
    public RSTFulBody edit(User user, @RequestParam(value = "itemId[]") String[] itemId) {
        User sessionUser = getSessionUser();
        user.setUpdateId(sessionUser.getUserId());
        user.setUpdateName(sessionUser.getUserName());
        user.setUpdateTime(DateHelper.getCurrentDate());
        if (user.getPassword() != null) user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        int res = userService.updateByPrimaryKeySelective(user);

        ArrayList<CompanyUserItem> companyUserItems = new ArrayList<>();
        for (String item : itemId) {
            CompanyUserItem companyUserItem = new CompanyUserItem();
            companyUserItem.setItemId((long) Integer.parseInt(item));
            companyUserItem.setUserId(user.getUserId());
            Item i = itemService.selectByPrimaryKey((long) Integer.parseInt(item));
            companyUserItem.setItemName(i.getItemName());
            companyUserItems.add(companyUserItem);

        }


        RSTFulBody rstFulBody = new RSTFulBody();
        if (res > 0) rstFulBody.success("修改成功！");
        else rstFulBody.fail("修改失败！");
        return rstFulBody;
    }

    @RequestMapping("del_user.html")
    public String delUser(User user) {
        user.setDel(false);
        int res = userService.updateByPrimaryKeySelective(user);
        return "redirect:/company_user/index.html";
    }

    @ResponseBody
    @RequestMapping("batch_del")
    public RSTFulBody batchDel(@RequestParam(required = true) String ids) {


        Map<String, Object> map = new HashMap<>();
        map.put("ids", ids);
        int res = userService.batchDel(map);
        RSTFulBody rstFulBody = new RSTFulBody();
        if (res > 0) rstFulBody.success(res);
        else rstFulBody.fail("删除失败！");
        return rstFulBody;
    }
}
