package com.talentcenter.controller;

import RSTFul.RSTFulBody;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.talentcenter.entity.NoticeTemplate;
import com.talentcenter.entity.User;
import com.talentcenter.service.NoticeTemplateService;
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

import static util.HtmlUtil.delHTMLTag;

@Controller
@RequestMapping("/noticeTemplate")
public class NoticeTemplateController extends BaseController{
    @Autowired
    private NoticeTemplateService noticeTemplateService;

   /* @Autowired
    private RoleNoticeTemplateService roleNoticeTemplateService;*/

    /**
     *
     * @param model
     * @return
     */
    @RequestMapping("/index.html")
    public String index(Model model){
        return "/noticeTemplate/index.html";
    }

    @RequestMapping("/ajax_index")
    public String ajaxIndex(Model model, int pageNum, int pageSize,
                            @RequestParam(required = false) String noticeTemplateName
    ){
        //组装搜索条件
        /*Map<String,Object> map=new HashMap<>();
        if(userTrueName!=null && userTrueName!="") map.put("userTrueName",userTrueName);
        if(examId!=null && examId!="") map.put("examId",examId);
        if(userSex!=null && userSex!="") map.put("userSex",userSex);*/
        NoticeTemplate noticeTemplate = new NoticeTemplate();
        noticeTemplate.setDel(true);
        if(noticeTemplateName!=null && noticeTemplateName!="") noticeTemplate.setNoticeTemplateName(noticeTemplateName);
        //分页查询
        PageHelper.startPage(pageNum, pageSize);
        List<NoticeTemplate> noticeTemplates = noticeTemplateService.select(noticeTemplate);

        for (NoticeTemplate nt: noticeTemplates) {
            nt.setNoticeTemplateContent(delHTMLTag(nt.getNoticeTemplateContent()));
        }

        PageInfo<NoticeTemplate> pageInfo= new PageInfo<>(noticeTemplates);
        String pageStr = makePageHtml(pageInfo);
        model.addAttribute("page_info",pageInfo);
        model.addAttribute("pages",pageStr);
        return "/noticeTemplate/ajax_index.html";
    }

    @RequestMapping("add.html")
    public String addUI(Model model) {
        return "/noticeTemplate/add.html";
    }

    @ResponseBody
    @RequestMapping("add")
    public RSTFulBody add(NoticeTemplate noticeTemplate) {
        User sessionUser = getSessionUser();
        noticeTemplate.setCreateName(sessionUser.getUserName());
        noticeTemplate.setCreateId(sessionUser.getUserId());

        int res = noticeTemplateService.insertSelective(noticeTemplate);
        RSTFulBody rstFulBody = new RSTFulBody();
        if (res > 0) rstFulBody.success("添加成功！");
        else rstFulBody.fail("添加失败！");
        return rstFulBody;
    }

    @RequestMapping("edit.html")
    public String editUI(Model model, String noticeTemplateId) {
        NoticeTemplate noticeTemplate = noticeTemplateService.selectByPrimaryKey((long) Integer.parseInt(noticeTemplateId));
        model.addAttribute("noticeTemplateId",noticeTemplateId);
        model.addAttribute("obj",noticeTemplate);
        return "/noticeTemplate/edit.html";
    }

    @ResponseBody
    @RequestMapping("edit")
    public RSTFulBody edit(NoticeTemplate noticeTemplate) {
        User sessionUser = getSessionUser();
        noticeTemplate.setUpdateId(sessionUser.getUserId());
        noticeTemplate.setUpdateName(sessionUser.getUserName());
        noticeTemplate.setUpdateTime(DateHelper.getCurrentDate());
        int res = noticeTemplateService.updateByPrimaryKeySelective(noticeTemplate);
        RSTFulBody rstFulBody = new RSTFulBody();
        if (res > 0) rstFulBody.success("修改成功！");
        else rstFulBody.fail("修改失败！");
        return rstFulBody;
    }

    @RequestMapping("del.html")
    public String delUser(NoticeTemplate noticeTemplate){
        noticeTemplate.setDel(false);
        int res = noticeTemplateService.updateByPrimaryKeySelective(noticeTemplate);
        return "redirect:/noticeTemplate/index.html";
    }

    @ResponseBody
    @RequestMapping("batch_del")
    public RSTFulBody batchDel(@RequestParam(required = true) String ids){

        Map<String, Object> map = new HashMap<>();
        map.put("ids",ids);
        int res = noticeTemplateService.batchDel(map);
        RSTFulBody rstFulBody=new RSTFulBody();
        if(res>0) rstFulBody.success(res);
        else  rstFulBody.fail("删除失败！");
        return rstFulBody;
    }
}
