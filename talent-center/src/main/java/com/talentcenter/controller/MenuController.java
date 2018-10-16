package com.talentcenter.controller;

import RSTFul.RSTFulBody;
import com.talentcenter.entity.Menu;
import com.talentcenter.entity.User;
import com.talentcenter.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/menu")
public class MenuController extends BaseController{
    @Autowired
    private MenuService menuService;

   /* @Autowired
    private RoleMenuService roleMenuService;*/

    @RequestMapping("/index.html")
    public String index(Model model){
        //获取所有顶级菜单
        List<Menu> folders=menuService.selectByParentId((long) 0);

        for (int i = 0; i < folders.size(); i++) {
            List<Menu> childMenus = menuService.selectByParentId(folders.get(i).getMenuId());
            if(childMenus.size()>0) folders.get(i).setChildMenus(childMenus);
        }
        model.addAttribute("menus",folders);
        return "/menu/index.html";
    }

    @RequestMapping("add_menu.html")
    public String addUI(Model model){
        List<Menu> folders=menuService.selectByParentId((long)0);
        model.addAttribute("top_menus",folders);
        return "/menu/add_menu.html";
    }

    @ResponseBody
    @RequestMapping("add_menu")
    public RSTFulBody addMenu(Menu folder){
        User sessionUser = getSessionUser();
        folder.setCreateId(sessionUser.getUserId());
        folder.setCreateName(sessionUser.getRealName());
        int res=menuService.insertSelective(folder);
        RSTFulBody rstFulBody=new RSTFulBody();
        if(res>0) rstFulBody.success("添加成功！");
        else  rstFulBody.fail("添加失败！");
        return rstFulBody;
    }

    @RequestMapping("edit_menu.html")
    public String editUI(Model model, String menuId){

        Menu menu=menuService.selectByPrimaryKey((long)Integer.parseInt(menuId));
        List<Menu> menus=menuService.selectByParentId((long)0);
        model.addAttribute("top_menus",menus);
        model.addAttribute("menu",menu);
        return "/menu/edit_menu.html";
    }

    @ResponseBody
    @RequestMapping("eidt_menu")
    public RSTFulBody editMenu(Menu folder){
        User sessionUser = getSessionUser();
        folder.setUpdateName(sessionUser.getRealName());
        folder.setUpdateId(sessionUser.getUserId());
        folder.setUpdateTime(new Date());
        int res = menuService.updateByPrimaryKeySelective(folder);
        RSTFulBody rstFulBody=new RSTFulBody();
        if(res>0) rstFulBody.success("修改成功！");
        else  rstFulBody.fail("修改失败！");
        return rstFulBody;
    }

    @RequestMapping("del_menu.html")
    public String delMenu(Menu folder){
        User sessionUser = getSessionUser();
        folder.setUpdateName(sessionUser.getRealName());
        folder.setUpdateId(sessionUser.getUserId());
        folder.setUpdateTime(new Date());
        folder.setDel(0);

        int res = menuService.updateByPrimaryKeySelective(folder);
        return "redirect:/menu/index.html";
    }
}
