package com.talentcenter.controller;

import RSTFul.RSTFulBody;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.talentcenter.entity.TalentType;
import com.talentcenter.entity.User;
import com.talentcenter.service.TalentTypeService;
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
@RequestMapping("/talent_type")
public class TalentTypeController extends BaseController{
    @Autowired
    private TalentTypeService talentTypeService;

   /* @Autowired
    private RoleTalentTypeService roleTalentTypeService;*/

    /**
     * 人才分类列表页
     * @param model
     * @return
     */
    @RequestMapping("/index.html")
    public String index(Model model){
        return "/talent_type/index.html";
    }

    @RequestMapping("/ajax_index")
    public String ajaxIndex(Model model, int pageNum, int pageSize,
                            @RequestParam(required = false) String talentTypeName
    ){
        //组装搜索条件
        /*Map<String,Object> map=new HashMap<>();
        if(userTrueName!=null && userTrueName!="") map.put("userTrueName",userTrueName);
        if(examId!=null && examId!="") map.put("examId",examId);
        if(userSex!=null && userSex!="") map.put("userSex",userSex);*/
        TalentType talentType = new TalentType();
        talentType.setDel(true);
        if(talentTypeName!=null && talentTypeName!="") talentType.setTalentTypeName(talentTypeName);
        //分页查询
        PageHelper.startPage(pageNum, pageSize);
        List<TalentType> talentTypes = talentTypeService.select(talentType);

        PageInfo<TalentType> pageInfo= new PageInfo<>(talentTypes);
        String pageStr = makePageHtml(pageInfo);
        model.addAttribute("page_info",pageInfo);
        model.addAttribute("pages",pageStr);
        return "/talent_type/ajax_index.html";
    }

    @RequestMapping("add.html")
    public String addUI(Model model) {
        return "/talent_type/add.html";
    }

    @ResponseBody
    @RequestMapping("add")
    public RSTFulBody add(TalentType talentType) {
        User sessionUser = getSessionUser();
        talentType.setCreateName(sessionUser.getUserName());
        talentType.setCreateId(sessionUser.getUserId());

        int res = talentTypeService.insertSelective(talentType);
        RSTFulBody rstFulBody = new RSTFulBody();
        if (res > 0) rstFulBody.success("添加成功！");
        else rstFulBody.fail("添加失败！");
        return rstFulBody;
    }

    @RequestMapping("edit.html")
    public String editUI(Model model, String talentTypeId) {
        TalentType talentType = talentTypeService.selectByPrimaryKey((long) Integer.parseInt(talentTypeId));
        model.addAttribute("obj",talentType);
        return "/talent_type/edit.html";
    }

    @ResponseBody
    @RequestMapping("edit")
    public RSTFulBody edit(TalentType talentType) {
        User sessionUser = getSessionUser();
        talentType.setUpdateId(sessionUser.getUserId());
        talentType.setUpdateName(sessionUser.getUserName());
        talentType.setUpdateTime(DateHelper.getCurrentDate());
        int res = talentTypeService.updateByPrimaryKeySelective(talentType);
        RSTFulBody rstFulBody = new RSTFulBody();
        if (res > 0) rstFulBody.success("修改成功！");
        else rstFulBody.fail("修改失败！");
        return rstFulBody;
    }

    @RequestMapping("del.html")
    public String delUser(TalentType talentType){
        talentType.setDel(false);
        int res = talentTypeService.updateByPrimaryKeySelective(talentType);
        return "redirect:/talent_type/index.html";
    }

    @ResponseBody
    @RequestMapping("batch_del")
    public RSTFulBody batchDel(@RequestParam(required = true) String ids){

        Map<String, Object> map = new HashMap<>();
        map.put("ids",ids);
        int res = talentTypeService.batchDel(map);
        RSTFulBody rstFulBody=new RSTFulBody();
        if(res>0) rstFulBody.success(res);
        else  rstFulBody.fail("删除失败！");
        return rstFulBody;
    }
}
