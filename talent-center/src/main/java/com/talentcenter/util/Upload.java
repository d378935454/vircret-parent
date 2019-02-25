package com.talentcenter.util;

import org.springframework.util.ClassUtils;
import org.springframework.web.multipart.MultipartFile;
import util.DateHelper;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Upload {

    private static String executeUpload(String uploadDir,MultipartFile file,int type) throws Exception
    {
        //文件后缀名
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        //上传文件名
        String filename="";
        if(type==0){
            filename = file.getOriginalFilename();
        }else {
            filename = DateHelper.getCurDateTimeMI() + suffix;
        }

        //服务器端保存的文件对象
        File serverFile = new File(uploadDir + filename);
        //将上传的文件写入到服务器端文件内
        file.transferTo(serverFile);

        return filename;
    }


    public static Map<String, Object> upload(MultipartFile file,int type)
    {
        Map<String,Object> map = new HashMap<>();
        map.put("status","1");
        map.put("oderName",file.getOriginalFilename());
        try {
            //上传目录地址
            String os = System.getProperty("os.name");
            String uploadDir = "";
            String dateStr = DateHelper.getCurDate();
            if (os.toLowerCase().startsWith("win")) {
                String[] path = ClassUtils.getDefaultClassLoader().getResource("").getPath().split("/");
                uploadDir = path[1] + "/talent/upload/"+dateStr+"/";
            }else{
                uploadDir = "/usr/talent/upload/"+dateStr+"/";
            }
//            System.out.println(path[]);



//            ClassUtils.get
            //如果目录不存在，自动创建文件夹
            File dir = new File(uploadDir);
            if(!dir.exists())
            {
                dir.mkdirs();
            }
            //调用上传方法
            String fileName = executeUpload(uploadDir+"",file,type);
            map.put("fileInfo","/upload/"+dateStr+"/"+fileName);
        }catch (Exception e)
        {
            //打印错误堆栈信息
            map.put("status","0");
            e.printStackTrace();
        }

        return map;
    }
}
