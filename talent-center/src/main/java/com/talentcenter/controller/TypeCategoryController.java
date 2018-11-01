package com.talentcenter.controller;

import RSTFul.RSTFulBody;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.talentcenter.entity.TypeCategory;
import com.talentcenter.entity.User;
import com.talentcenter.service.TypeCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import util.DateHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/type_category")
public class TypeCategoryController extends BaseController{
    @Autowired
    private TypeCategoryService typeCategoryService;

   /* @Autowired
    private RoleTypeCategoryService roleTypeCategoryService;*/

    /**
     * 人才分类列类型表页
     * @param model
     * @return
     */
    @RequestMapping("/index.html")
    public String index(Model model){
        return "/type_category/index.html";
    }

    @RequestMapping("/ajax_index")
    public String ajaxIndex(Model model, int pageNum, int pageSize,
                            @RequestParam(required = false) String typeCategoryName
    ){
        //组装搜索条件
        /*Map<String,Object> map=new HashMap<>();
        if(userTrueName!=null && userTrueName!="") map.put("userTrueName",userTrueName);
        if(examId!=null && examId!="") map.put("examId",examId);
        if(userSex!=null && userSex!="") map.put("userSex",userSex);*/
        TypeCategory typeCategory = new TypeCategory();
        typeCategory.setDel(true);
        if(typeCategoryName!=null && typeCategoryName!="") typeCategory.setTypeCategoryName(typeCategoryName);
        //分页查询
        PageHelper.startPage(pageNum, pageSize);
        List<TypeCategory> typeCategorys = typeCategoryService.select(typeCategory);

        PageInfo<TypeCategory> pageInfo= new PageInfo<>(typeCategorys);
        String pageStr = makePageHtml(pageInfo);
        model.addAttribute("page_info",pageInfo);
        model.addAttribute("pages",pageStr);
        return "/type_category/ajax_index.html";
    }

    @RequestMapping("add.html")
    public String addUI(Model model) {
        return "/type_category/add.html";
    }

    @ResponseBody
    @RequestMapping("add")
    public RSTFulBody add(TypeCategory typeCategory) {
        User sessionUser = getSessionUser();
        typeCategory.setCreateName(sessionUser.getUserName());
        typeCategory.setCreateId(sessionUser.getUserId());

        int res = typeCategoryService.insertSelective(typeCategory);
        RSTFulBody rstFulBody = new RSTFulBody();
        if (res > 0) rstFulBody.success("添加成功！");
        else rstFulBody.fail("添加失败！");
        return rstFulBody;
    }

    @RequestMapping("edit.html")
    public String editUI(Model model, String typeCategoryId) {
        TypeCategory typeCategory = typeCategoryService.selectByPrimaryKey((long) Integer.parseInt(typeCategoryId));
        model.addAttribute("obj",typeCategory);
        return "/type_category/edit.html";
    }

    @ResponseBody
    @RequestMapping("edit")
    public RSTFulBody edit(TypeCategory typeCategory) {
        User sessionUser = getSessionUser();
        typeCategory.setUpdateId(sessionUser.getUserId());
        typeCategory.setUpdateName(sessionUser.getUserName());
        typeCategory.setUpdateTime(DateHelper.getCurrentDate());
        int res = typeCategoryService.updateByPrimaryKeySelective(typeCategory);
        RSTFulBody rstFulBody = new RSTFulBody();
        if (res > 0) rstFulBody.success("修改成功！");
        else rstFulBody.fail("修改失败！");
        return rstFulBody;
    }

    @RequestMapping("del.html")
    public String delUser(TypeCategory typeCategory){
        typeCategory.setDel(false);
        int res = typeCategoryService.updateByPrimaryKeySelective(typeCategory);
        return "redirect:/type_category/index.html";
    }

    @ResponseBody
    @RequestMapping("batch_del")
    public RSTFulBody batchDel(@RequestParam(required = true) String ids){

        Map<String, Object> map = new HashMap<>();
        map.put("ids",ids);
        int res = typeCategoryService.batchDel(map);
        RSTFulBody rstFulBody=new RSTFulBody();
        if(res>0) rstFulBody.success(res);
        else  rstFulBody.fail("删除失败！");
        return rstFulBody;
    }
}
