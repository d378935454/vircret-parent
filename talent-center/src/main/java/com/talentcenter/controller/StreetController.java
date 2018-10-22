package com.talentcenter.controller;

import RSTFul.RSTFulBody;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.talentcenter.entity.Street;
import com.talentcenter.entity.User;
import com.talentcenter.service.StreetService;
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
@RequestMapping("/street")
public class StreetController extends BaseController{
    @Autowired
    private StreetService streetService;

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
        return "redirect:/street/index.html";
    }

    @ResponseBody
    @RequestMapping("batch_del")
    public RSTFulBody batchDel(@RequestParam(required = true) String ids){

        Map<String, Object> map = new HashMap<>();
        map.put("ids",ids);
        int res = streetService.batchDel(map);
        RSTFulBody rstFulBody=new RSTFulBody();
        if(res>0) rstFulBody.success(res);
        else  rstFulBody.fail("删除失败！");
        return rstFulBody;
    }
}
