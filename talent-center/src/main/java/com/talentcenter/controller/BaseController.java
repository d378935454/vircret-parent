package com.talentcenter.controller;

import com.github.pagehelper.PageInfo;
import com.talentcenter.entity.User;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;

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



    /*private String executeUpload(String uploadDir,MultipartFile file) throws Exception
    {
        //文件后缀名
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        //上传文件名
        String filename = DateHelper.getCurDateTimeMI() + suffix;
        //服务器端保存的文件对象
        File serverFile = new File(uploadDir + filename);
        //将上传的文件写入到服务器端文件内
        file.transferTo(serverFile);

        return filename;
    }


    protected Map<String, Object> upload(MultipartFile file)
    {
        Map<String,Object> map = new HashMap<>();
        map.put("status","1");
        map.put("oderName",file.getOriginalFilename());
        try {
            //上传目录地址
            String uploadDir = ClassUtils.getDefaultClassLoader().getResource("").getPath() +"upload/"+DateHelper.getCurDate()+"/";
            //如果目录不存在，自动创建文件夹
            File dir = new File(uploadDir);
            if(!dir.exists())
            {
                dir.mkdirs();
            }
            //调用上传方法
            map.put("fileInfo",dir+"\\"+executeUpload(uploadDir+"",file));
        }catch (Exception e)
        {
            //打印错误堆栈信息
            map.put("status","0");
            e.printStackTrace();
        }

        return map;
    }*/
}
