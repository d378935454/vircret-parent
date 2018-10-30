package com.talentcenter.controller;

import RSTFul.RSTFulBody;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.talentcenter.entity.Company;
import com.talentcenter.entity.User;
import com.talentcenter.service.CompanyService;
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
@RequestMapping("/company")
public class CompanyController extends BaseController{
    @Autowired
    private CompanyService companyService;

   /* @Autowired
    private RoleCompanyService roleCompanyService;*/

    /**
     * 企业列表页
     * @param model
     * @return
     */
    @RequestMapping("/index.html")
    public String index(Model model){
        return "/company/index.html";
    }

    @RequestMapping("/ajax_index")
    public String ajaxIndex(Model model, int pageNum, int pageSize,
                            @RequestParam(required = false) String companyName
    ){
        //组装搜索条件
        /*Map<String,Object> map=new HashMap<>();
        if(userTrueName!=null && userTrueName!="") map.put("userTrueName",userTrueName);
        if(examId!=null && examId!="") map.put("examId",examId);
        if(userSex!=null && userSex!="") map.put("userSex",userSex);*/
        Company company = new Company();
        company.setDel(true);
        if(companyName!=null && companyName!="") company.setCompanyName(companyName);

        //分页查询
        PageHelper.startPage(pageNum, pageSize);
        List<Company> companys = companyService.select(company);

        PageInfo<Company> pageInfo= new PageInfo<>(companys);
        String pageStr = makePageHtml(pageInfo);
        model.addAttribute("page_info",pageInfo);
        model.addAttribute("pages",pageStr);
        return "/company/ajax_index.html";
    }

    @RequestMapping("add.html")
    public String addUI(Model model) {
        return "/company/add.html";
    }

    @ResponseBody
    @RequestMapping("add")
    public RSTFulBody add(Company company) {
        User sessionUser = getSessionUser();
        company.setCreateName(sessionUser.getUserName());
        company.setCreateId(sessionUser.getUserId());

        int res = companyService.insertSelective(company);
        RSTFulBody rstFulBody = new RSTFulBody();
        if (res > 0) rstFulBody.success("添加成功！");
        else rstFulBody.fail("添加失败！");
        return rstFulBody;
    }

    @RequestMapping("edit.html")
    public String editUI(Model model, String companyId) {
        Company company = companyService.selectByPrimaryKey((long) Integer.parseInt(companyId));
        model.addAttribute("companyId",companyId);
        model.addAttribute("obj",company);
        return "/company/edit.html";
    }

    @ResponseBody
    @RequestMapping("edit")
    public RSTFulBody edit(Company company) {
        User sessionUser = getSessionUser();
        company.setUpdateId(sessionUser.getUserId());
        company.setUpdateName(sessionUser.getUserName());
        company.setUpdateTime(DateHelper.getCurrentDate());
        int res = companyService.updateByPrimaryKeySelective(company);
        RSTFulBody rstFulBody = new RSTFulBody();
        if (res > 0) rstFulBody.success("修改成功！");
        else rstFulBody.fail("修改失败！");
        return rstFulBody;
    }

    @RequestMapping("del.html")
    public String delUser(Company company){
        company.setDel(false);
        int res = companyService.updateByPrimaryKeySelective(company);
        return "redirect:/company/index.html";
    }

    @ResponseBody
    @RequestMapping("batch_del")
    public RSTFulBody batchDel(@RequestParam(required = true) String ids){

        Map<String, Object> map = new HashMap<>();
        map.put("ids",ids);
        int res = companyService.batchDel(map);
        RSTFulBody rstFulBody=new RSTFulBody();
        if(res>0) rstFulBody.success(res);
        else  rstFulBody.fail("删除失败！");
        return rstFulBody;
    }


}
