package com.talentcenter.controller;

import RSTFul.RSTFulBody;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.talentcenter.entity.ItemConfig;
import com.talentcenter.entity.User;
import com.talentcenter.service.ItemConfigService;
import com.talentcenter.util.DateHelper;
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
@RequestMapping("/itemConfig")
public class ItemConfigController extends BaseController{
    @Autowired
    private ItemConfigService itemConfigService;

   /* @Autowired
    private RoleItemConfigService roleItemConfigService;*/

    /**
     * 政策配置列表页
     * @param model
     * @return
     */
    @RequestMapping("/index.html")
    public String index(Model model){
        return "/itemConfig/index.html";
    }

    @RequestMapping("/ajax_index")
    public String ajaxIndex(Model model, int pageNum, int pageSize,
                            @RequestParam(required = false) String itemConfigName
    ){
        //组装搜索条件
        /*Map<String,Object> map=new HashMap<>();
        if(userTrueName!=null && userTrueName!="") map.put("userTrueName",userTrueName);
        if(examId!=null && examId!="") map.put("examId",examId);
        if(userSex!=null && userSex!="") map.put("userSex",userSex);*/
        ItemConfig itemConfig = new ItemConfig();
        itemConfig.setDel(true);
//        if(itemConfigName!=null && itemConfigName!="") itemConfig.setItemConfigName(itemConfigName);
        //分页查询
        PageHelper.startPage(pageNum, pageSize);
        List<ItemConfig> itemConfigs = itemConfigService.select(itemConfig);

        PageInfo<ItemConfig> pageInfo= new PageInfo<>(itemConfigs);
        String pageStr = makePageHtml(pageInfo);
        model.addAttribute("page_info",pageInfo);
        model.addAttribute("pages",pageStr);
        return "/itemConfig/ajax_index.html";
    }

    @RequestMapping("add.html")
    public String addUI(Model model) {
        return "/itemConfig/add.html";
    }

    @ResponseBody
    @RequestMapping("add")
    public RSTFulBody add(ItemConfig itemConfig) {
        User sessionUser = getSessionUser();
        itemConfig.setCreateName(sessionUser.getUserName());
        itemConfig.setCreateId(sessionUser.getUserId());

        int res = itemConfigService.insertSelective(itemConfig);
        RSTFulBody rstFulBody = new RSTFulBody();
        if (res > 0) rstFulBody.success("添加成功！");
        else rstFulBody.fail("添加失败！");
        return rstFulBody;
    }

    @RequestMapping("edit.html")
    public String editUI(Model model, String itemConfigId) {
        ItemConfig itemConfig = itemConfigService.selectByPrimaryKey((long) Integer.parseInt(itemConfigId));
        model.addAttribute("itemConfigId",itemConfigId);
        model.addAttribute("obj",itemConfig);
        return "/itemConfig/edit.html";
    }

    @ResponseBody
    @RequestMapping("edit")
    public RSTFulBody edit(ItemConfig itemConfig) {
        User sessionUser = getSessionUser();
        itemConfig.setUpdateId(sessionUser.getUserId());
        itemConfig.setUpdateName(sessionUser.getUserName());
        itemConfig.setUpdateTime(DateHelper.getCurrentDate());
        int res = itemConfigService.updateByPrimaryKeySelective(itemConfig);
        RSTFulBody rstFulBody = new RSTFulBody();
        if (res > 0) rstFulBody.success("修改成功！");
        else rstFulBody.fail("修改失败！");
        return rstFulBody;
    }

    @RequestMapping("del.html")
    public String delUser(ItemConfig itemConfig){
        itemConfig.setDel(false);
        int res = itemConfigService.updateByPrimaryKeySelective(itemConfig);
        return "redirect:/itemConfig/index.html";
    }

    @ResponseBody
    @RequestMapping("batch_del")
    public RSTFulBody batchDel(@RequestParam(required = true) String ids){

        Map<String, Object> map = new HashMap<>();
        map.put("ids",ids);
        int res = itemConfigService.batchDel(map);
        RSTFulBody rstFulBody=new RSTFulBody();
        if(res>0) rstFulBody.success(res);
        else  rstFulBody.fail("删除失败！");
        return rstFulBody;
    }
}
