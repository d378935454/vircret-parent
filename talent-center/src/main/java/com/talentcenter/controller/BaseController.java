package com.talentcenter.controller;

import com.github.pagehelper.PageInfo;
import com.talentcenter.entity.User;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Controller
public class BaseController {

    protected static int pageSize = 20;
    protected User getSessionUser(){
        return (User) SecurityUtils.getSubject().getSession().getAttribute("user");
    }

    protected String makePageHtml(PageInfo pageInfo){
        String htmlStr="<ul class='pagination'>";
        if(pageInfo.getPages()<=0) return "";

        if(pageInfo.getPageNum()>5){
            htmlStr+="<li class='paginate_button previous'><a class='num' data-p='1' href='javascript:void(0)'>首页</a></li>";
        }
        if(pageInfo.isHasPreviousPage()){
            htmlStr+="<li class='paginate_button previous'><a class='num' data-p='"+pageInfo.getPrePage()+"' href='javascript:void(0)'>上一页</a></li>";
        }

        for (int i=0;i<pageInfo.getNavigatepageNums().length;i++){
            if(pageInfo.getPageNum()==pageInfo.getNavigatepageNums()[i]){
                htmlStr+="<li class='paginate_button active'><a tabindex='0' data-dt-idx='1' aria-controls='example1' data-p='"+pageInfo.getNavigatepageNums()[i]+"' href='javascript:void(0)'>"+pageInfo.getNavigatepageNums()[i]+"</a></li>";
            }else{
                htmlStr+="<li class='paginate_button'><a class='num' data-p='"+pageInfo.getNavigatepageNums()[i]+"' href='javascript:void(0)'>"+pageInfo.getNavigatepageNums()[i]+"</a></li>";
            }
        }

        if(pageInfo.isHasNextPage()){
            htmlStr+="<li class='paginate_button next'><a class='num' data-p='"+pageInfo.getNextPage()+"' href='javascript:void(0)'>下一页</a></li>";
        }
        if(pageInfo.getPages()-pageInfo.getPageNum()>3){
            htmlStr+="<li class='paginate_button next'><a class='num' data-p='"+pageInfo.getPages()+"' href='javascript:void(0)'>尾页</a></li>";
        }
        htmlStr+="<li class='paginate_button next'><a>共"+pageInfo.getTotal()+"条数据</a></li>";
        htmlStr+="</ul>";
        return htmlStr;
    }

    protected  Long[] removeDuplicates(Long [] arrStr) {
        List<Long> list = new ArrayList<Long>();
        for (int i=0; i<arrStr.length; i++) {
            if(!list.contains(arrStr[i])) {
                list.add(arrStr[i]);
            }
        }
        //返回一个包含所有对象的指定类型的数组
        Long[] newArrStr =  list.toArray(new Long[1]);
        return newArrStr;
    }
}
