package com.talentcenter.controller;

import RSTFul.RSTFulBody;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.talentcenter.entity.Certificate;
import com.talentcenter.entity.User;
import com.talentcenter.service.CertificateService;
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
@RequestMapping("/certificate")
public class CertificateController extends BaseController{
    @Autowired
    private CertificateService certificateService;

   /* @Autowired
    private RoleCertificateService roleCertificateService;*/

    /**
     * 证书列表页
     * @param model
     * @return
     */
    @RequestMapping("/index.html")
    public String index(Model model){
        return "/certificate/index.html";
    }

    @RequestMapping("/ajax_index")
    public String ajaxIndex(Model model, int pageNum, int pageSize,
                            @RequestParam(required = false) String certificateName
    ){
        //组装搜索条件
        /*Map<String,Object> map=new HashMap<>();
        if(userTrueName!=null && userTrueName!="") map.put("userTrueName",userTrueName);
        if(examId!=null && examId!="") map.put("examId",examId);
        if(userSex!=null && userSex!="") map.put("userSex",userSex);*/
        Certificate certificate = new Certificate();
        certificate.setDel(true);
        if(certificateName!=null && certificateName!="") certificate.setCertificateName(certificateName);
        //分页查询
        PageHelper.startPage(pageNum, pageSize);
        List<Certificate> certificates = certificateService.select(certificate);

        PageInfo<Certificate> pageInfo= new PageInfo<>(certificates);
        String pageStr = makePageHtml(pageInfo);
        model.addAttribute("page_info",pageInfo);
        model.addAttribute("pages",pageStr);
        return "/certificate/ajax_index.html";
    }

    @RequestMapping("add.html")
    public String addUI(Model model) {
        return "/certificate/add.html";
    }

    @ResponseBody
    @RequestMapping("add")
    public RSTFulBody add(Certificate certificate) {
        User sessionUser = getSessionUser();
        certificate.setCreateName(sessionUser.getUserName());
        certificate.setCreateId(sessionUser.getUserId());

        int res = certificateService.insertSelective(certificate);
        RSTFulBody rstFulBody = new RSTFulBody();
        if (res > 0) rstFulBody.success("添加成功！");
        else rstFulBody.fail("添加失败！");
        return rstFulBody;
    }

    @RequestMapping("edit.html")
    public String editUI(Model model, String certificateId) {
        Certificate certificate = certificateService.selectByPrimaryKey((long) Integer.parseInt(certificateId));
        model.addAttribute("certificateId",certificateId);
        model.addAttribute("obj",certificate);
        return "/certificate/edit.html";
    }

    @ResponseBody
    @RequestMapping("edit")
    public RSTFulBody edit(Certificate certificate) {
        User sessionUser = getSessionUser();
        certificate.setUpdateId(sessionUser.getUserId());
        certificate.setUpdateName(sessionUser.getUserName());
        certificate.setUpdateTime(DateHelper.getCurrentDate());
        int res = certificateService.updateByPrimaryKeySelective(certificate);
        RSTFulBody rstFulBody = new RSTFulBody();
        if (res > 0) rstFulBody.success("修改成功！");
        else rstFulBody.fail("修改失败！");
        return rstFulBody;
    }

    @RequestMapping("del.html")
    public String delUser(Certificate certificate){
        certificate.setDel(false);
        int res = certificateService.updateByPrimaryKeySelective(certificate);
        return "redirect:/certificate/index.html";
    }

    @ResponseBody
    @RequestMapping("batch_del")
    public RSTFulBody batchDel(@RequestParam(required = true) String ids){
        Map<String, Object> map = new HashMap<>();
        map.put("ids",ids);
        int res = certificateService.batchDel(map);
        RSTFulBody rstFulBody=new RSTFulBody();
        if(res>0) rstFulBody.success(res);
        else  rstFulBody.fail("删除失败！");
        return rstFulBody;
    }
}
