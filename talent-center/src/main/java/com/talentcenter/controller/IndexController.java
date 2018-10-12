package com.talentcenter.controller;

import com.talentcenter.entity.Menu;
import com.talentcenter.service.CommonService;
import com.talentcenter.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by dell on 2017/11/21.
 */
@Controller
public class IndexController {

    @Autowired
    private MenuService menuService;

    @Autowired
    private CommonService commonService;

    @RequestMapping("/")
    public  String index(Model model){

        //获取所有顶级菜单
        Map<String,Object> map = new HashMap<>();
        Menu m = new Menu();
        m.setParentId((long)0);
        m.setMenuType(1);
        map.put("parentId","0");
        map.put("menuType",1);
//        map.put('status',);
//        List<Menu> menus=menuService.selectByParentId(0);
        List<Menu> menus = menuService.select(m);
        /*for (Menu menu : menus) {
            
        }*/
        for (int i = 0; i < menus.size(); i++) {
            Menu menu = new Menu();
            menu.setParentId(menus.get(i).getMenuId());
            menu.setMenuType(1);
//            List<Menu> childMenus = menuService.selectByParentId(menus.get(i).getMenuId());
            List<Menu> childMenus = menuService.select(menu);
            if(childMenus.size()>0) menus.get(i).setChildmenus(childMenus);
        }
        model.addAttribute("menus",menus);
        //        menuService.select();
        return "/index.html";
    }

    @ResponseBody
    @RequestMapping("/checkUnique")
    public Boolean checkUnique(HttpServletRequest request){
        List<String> fieldList = new ArrayList();
        Enumeration e = request.getParameterNames();
        String table = "";
        while(e.hasMoreElements()) {
            String parametName = (String)e.nextElement();
            if (parametName.equals("table")) {
                table = request.getParameter("table");
            } else if (parametName.equals("id")) {
                fieldList.add(request.getParameter("id"));
            }else if (parametName.equals("date_column")) {
                fieldList.add(request.getParameter("date_column"));
            } else {
                fieldList.add(parametName + "=" + "'" + request.getParameter(parametName) + "'");
            }
        }
        fieldList.remove(0);
        Map<String, Object> map = new HashMap();
        map.put("table", table);
        map.put("fieldArray", fieldList);

        boolean result = commonService.checkUnique(map);
        return result;
    }

    /**
     * 获取用户列表
     * @param
     * @return
     */
   /* @ApiOperation(value="获取用户列表", notes="分页获取用户列表")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long", paramType = "path")
    @RequestMapping("getAllUser")
    public void getAllUser(){
        PageHelper.startPage(1, 5);
        *//*List<User> users=userService.getAllUser();
//        PageInfo
        System.out.println("-----------"+users);*//*
    }*/

    @RequestMapping("beetl.html")
    public String beetlTest(Model model){
        model.addAttribute("name","hello,world");
        return "/beetl.html";
    }
}
