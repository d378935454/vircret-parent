package com.talentcenter.controller;

import RSTFul.RSTFulBody;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.talentcenter.entity.ItemType;
import com.talentcenter.entity.User;
import com.talentcenter.service.ItemTypeService;
import util.DateHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/itemType")
public class ItemTypeController extends BaseController{
    @Autowired
    private ItemTypeService itemTypeService;

   /* @Autowired
    private RoleItemTypeService roleItemTypeService;*/

    /**
     * 证书列表页
     * @param model
     * @return
     */
    @RequestMapping("/index.html")
    public String index(Model model){
        return "/itemType/index.html";
    }

    @RequestMapping("/ajax_index")
    public String ajaxIndex(Model model, int pageNum, int pageSize,
                            @RequestParam(required = false) String itemTypeName
    ){
        //组装搜索条件
        /*Map<String,Object> map=new HashMap<>();
        if(userTrueName!=null && userTrueName!="") map.put("userTrueName",userTrueName);
        if(examId!=null && examId!="") map.put("examId",examId);
        if(userSex!=null && userSex!="") map.put("userSex",userSex);*/
        ItemType itemType = new ItemType();
        itemType.setDel(true);
        if(itemTypeName!=null && itemTypeName!="") itemType.setItemTypeName(itemTypeName);
        //分页查询
        PageHelper.startPage(pageNum, pageSize);
        List<ItemType> itemTypes = itemTypeService.select(itemType);

        PageInfo<ItemType> pageInfo= new PageInfo<>(itemTypes);
        String pageStr = makePageHtml(pageInfo);
        model.addAttribute("page_info",pageInfo);
        model.addAttribute("pages",pageStr);
        return "/itemType/ajax_index.html";
    }

    @RequestMapping("add.html")
    public String addUI(Model model) {
        return "/itemType/add.html";
    }

    @ResponseBody
    @RequestMapping("add")
    public RSTFulBody add(ItemType itemType) {
        User sessionUser = getSessionUser();
        itemType.setCreateName(sessionUser.getUserName());
        itemType.setCreateId(sessionUser.getUserId());

        int res = itemTypeService.insertSelective(itemType);
        RSTFulBody rstFulBody = new RSTFulBody();
        if (res > 0) rstFulBody.success("添加成功！");
        else rstFulBody.fail("添加失败！");
        return rstFulBody;
    }

    @RequestMapping("edit.html")
    public String editUI(Model model, String itemTypeId) {
        ItemType itemType = itemTypeService.selectByPrimaryKey((long) Integer.parseInt(itemTypeId));
        model.addAttribute("itemTypeId",itemTypeId);
        model.addAttribute("obj",itemType);
        return "/itemType/edit.html";
    }

    @ResponseBody
    @RequestMapping("edit")
    public RSTFulBody edit(ItemType itemType) {
        User sessionUser = getSessionUser();
        itemType.setUpdateId(sessionUser.getUserId());
        itemType.setUpdateName(sessionUser.getUserName());
        itemType.setUpdateTime(DateHelper.getCurrentDate());
        int res = itemTypeService.updateByPrimaryKeySelective(itemType);
        RSTFulBody rstFulBody = new RSTFulBody();
        if (res > 0) rstFulBody.success("修改成功！");
        else rstFulBody.fail("修改失败！");
        return rstFulBody;
    }

    @RequestMapping("del.html")
    public String delUser(ItemType itemType){
        itemType.setDel(false);
        int res = itemTypeService.updateByPrimaryKeySelective(itemType);
        return "redirect:/itemType/index.html";
    }

    @ResponseBody
    @RequestMapping("batch_del")
    public RSTFulBody batchDel(@RequestParam(required = true) String ids){

        Map<String, Object> map = new HashMap<>();
        map.put("ids",ids);
        int res = itemTypeService.batchDel(map);
        RSTFulBody rstFulBody=new RSTFulBody();
        if(res>0) rstFulBody.success(res);
        else  rstFulBody.fail("删除失败！");
        return rstFulBody;
    }
}
