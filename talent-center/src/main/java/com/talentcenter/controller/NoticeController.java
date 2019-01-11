package com.talentcenter.controller;

import RSTFul.RSTFulBody;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.talentcenter.entity.Notice;
import com.talentcenter.entity.NoticeTemplate;
import com.talentcenter.entity.User;
import com.talentcenter.service.NoticeService;

import com.talentcenter.service.NoticeTemplateService;
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

import static util.HtmlUtil.delHTMLTag;

@Controller
@RequestMapping("/notice")
public class NoticeController extends BaseController{
    @Autowired
    private NoticeService noticeService;
    @Autowired
    private NoticeTemplateService noticeTemplateService;
   /* @Autowired
    private RoleNoticeService roleNoticeService;*/

    /**
     * 公告列表页
     * @param model
     * @return
     */
    @RequestMapping("/index.html")
    public String index(Model model){
        return "/notice/index.html";
    }

    @RequestMapping("/ajax_index")
    public String ajaxIndex(Model model, int pageNum, int pageSize,
                            @RequestParam(required = false) String noticeName
    ){
        //组装搜索条件
        /*Map<String,Object> map=new HashMap<>();
        if(userTrueName!=null && userTrueName!="") map.put("userTrueName",userTrueName);
        if(examId!=null && examId!="") map.put("examId",examId);
        if(userSex!=null && userSex!="") map.put("userSex",userSex);*/
        Notice notice = new Notice();
        notice.setDel(true);
        if(noticeName!=null && noticeName!="") notice.setNoticeName(noticeName);
        //分页查询
        PageHelper.startPage(pageNum, pageSize);
        List<Notice> notices = noticeService.select(notice);

        for (Notice n: notices) {
            n.setNoticeContent(delHTMLTag(n.getNoticeContent()));
        }

        PageInfo<Notice> pageInfo= new PageInfo<>(notices);
        String pageStr = makePageHtml(pageInfo);
        model.addAttribute("page_info",pageInfo);
        model.addAttribute("pages",pageStr);
        return "/notice/ajax_index.html";
    }

    @RequestMapping("/ajax_index_content")
    public String ajaxIndexContent(Model model, int pageNum, int pageSize,
                            @RequestParam(required = false) String noticeName
    ){
        //组装搜索条件
        User sessionUser = getSessionUser();
        Notice notice = new Notice();
        notice.setDel(true);
        if(sessionUser.getUserNature()==1){
            notice.setNoticeType(0);
        }
        if(sessionUser.getUserNature()==3){
            notice.setNoticeType(1);
        }
        //分页查询
        PageHelper.startPage(pageNum, pageSize);
        List<Notice> notices = noticeService.selectIndexNotic(notice);

        for (Notice n: notices) {
            n.setRealNoticeContent(n.getNoticeContent());
            n.setNoticeContent(delHTMLTag(n.getNoticeContent()));
        }

        PageInfo<Notice> pageInfo= new PageInfo<>(notices);
        String pageStr = makePageHtml(pageInfo);
        model.addAttribute("page_info",pageInfo);
        model.addAttribute("pages",pageStr);
        return "/notice/ajax_index_content.html";
    }

    @RequestMapping("add.html")
    public String addUI(Model model) {

        List<NoticeTemplate> noticeTemplateTypes= noticeTemplateService.selectAll();
        model.addAttribute("noticeTemplateTypes",noticeTemplateTypes);
        return "/notice/add.html";
    }

    @ResponseBody
    @RequestMapping("add")
    public RSTFulBody add(Notice notice) {
        User sessionUser = getSessionUser();
        notice.setCreateName(sessionUser.getUserName());
        notice.setCreateId(sessionUser.getUserId());
        /*NoticeTemplate noticeTemplate = noticeTemplateService.selectByPrimaryKey(notice.getNoticeType());
         long str = Long.parseLong(noticeTemplate.getNoticeTemplateName());
        notice.setNoticeType(str);*/
        int res = noticeService.insertSelective(notice);
        RSTFulBody rstFulBody = new RSTFulBody();
        if (res > 0) rstFulBody.success("添加成功！");
        else rstFulBody.fail("添加失败！");
        return rstFulBody;
    }

    @RequestMapping("edit.html")
    public String editUI(Model model, String noticeId) {
        Notice notice = noticeService.selectByPrimaryKey((long) Integer.parseInt(noticeId));
        model.addAttribute("noticeId",noticeId);
        model.addAttribute("obj",notice);
        List<NoticeTemplate> noticeTemplateTypes= noticeTemplateService.selectAll();
        model.addAttribute("noticeTemplateTypes",noticeTemplateTypes);
        return "/notice/edit.html";
    }

    @ResponseBody
    @RequestMapping("edit")
    public RSTFulBody edit(Notice notice) {
        User sessionUser = getSessionUser();
        notice.setUpdateId(sessionUser.getUserId());
        notice.setUpdateName(sessionUser.getUserName());
        notice.setUpdateTime(DateHelper.getCurrentDate());
       /* NoticeTemplate noticeTemplate = noticeTemplateService.selectByPrimaryKey(notice.getNoticeType());
        long str = Long.parseLong(noticeTemplate.getNoticeTemplateName());
        notice.setNoticeType(str);*/
        int res = noticeService.updateByPrimaryKeySelective(notice);
        RSTFulBody rstFulBody = new RSTFulBody();
        if (res > 0) rstFulBody.success("修改成功！");
        else rstFulBody.fail("修改失败！");
        return rstFulBody;
    }

    @RequestMapping("del.html")
    public String delUser(Notice notice){
        notice.setDel(false);
        int res = noticeService.updateByPrimaryKeySelective(notice);
        return "redirect:/notice/index.html";
    }

    @ResponseBody
    @RequestMapping("batch_del")
    public RSTFulBody batchDel(@RequestParam(required = true) String ids){

        Map<String, Object> map = new HashMap<>();
        map.put("ids",ids);
        int res = noticeService.batchDel(map);
        RSTFulBody rstFulBody=new RSTFulBody();
        if(res>0) rstFulBody.success(res);
        else  rstFulBody.fail("删除失败！");
        return rstFulBody;
    }

    @RequestMapping("/company.html")
    public String company(Model model){
        return "/notice/company.html";
    }

    @RequestMapping("/ajax_company")
    public String ajaxCompany(Model model, int pageNum, int pageSize,
                            @RequestParam(required = false) String noticeName
    ){
        //组装搜索条件
        /*Map<String,Object> map=new HashMap<>();
        if(userTrueName!=null && userTrueName!="") map.put("userTrueName",userTrueName);
        if(examId!=null && examId!="") map.put("examId",examId);
        if(userSex!=null && userSex!="") map.put("userSex",userSex);*/
        Notice notice = new Notice();
        notice.setDel(true);
        if(noticeName!=null && noticeName!="") notice.setNoticeName(noticeName);
        //分页查询
        PageHelper.startPage(pageNum, pageSize);
        List<Notice> notices = noticeService.select(notice);

        for (Notice n: notices) {
            n.setNoticeContent(delHTMLTag(n.getNoticeContent()));
        }

        PageInfo<Notice> pageInfo= new PageInfo<>(notices);
        String pageStr = makePageHtml(pageInfo);
        model.addAttribute("page_info",pageInfo);
        model.addAttribute("pages",pageStr);
        return "/notice/ajax_company.html";
    }
    @RequestMapping("watch.html")
    public String watchUI(Model model, String noticeId) {
        Notice notice = noticeService.selectByPrimaryKey((long) Integer.parseInt(noticeId));
        model.addAttribute("noticeId",noticeId);
        model.addAttribute("obj",notice);
        List<NoticeTemplate> noticeTemplateTypes= noticeTemplateService.selectAll();
        model.addAttribute("noticeTemplateTypes",noticeTemplateTypes);
        return "/notice/watch.html";
    }
}
