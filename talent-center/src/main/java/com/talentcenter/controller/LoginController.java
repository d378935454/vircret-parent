package com.talentcenter.controller;

import com.talentcenter.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController extends BaseController {

    @RequestMapping("/login_check.html")
    public String loginCheck(User user){
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUserName(), user.getPassword());
        String username = user.getUserName();
        try {
            //在调用了login方法后,SecurityManager会收到AuthenticationToken,并将其发送给已配置的Realm执行必须的认证检查
            //每个Realm都能在必要时对提交的AuthenticationTokens作出反应
            //所以这一步在调用login(token)方法时,它会走到MyRealm.doGetAuthenticationInfo()方法中,具体验证方式详见此方法
            subject.login(token);
        }catch(UnknownAccountException uae){
//            redirectAttributes.addFlashAttribute("message", "未知账户");
        }catch(IncorrectCredentialsException ice){
//            redirectAttributes.addFlashAttribute("message", "密码不正确");
        }catch(LockedAccountException lae){
//            redirectAttributes.addFlashAttribute("message", "账户已锁定");
        }catch(ExcessiveAttemptsException eae){
//            redirectAttributes.addFlashAttribute("message", "用户名或密码错误次数过多");
        }catch(AuthenticationException ae){
            //通过处理Shiro的运行时AuthenticationException就可以控制用户登录失败或密码错误时的情景
            ae.printStackTrace();
//            redirectAttributes.addFlashAttribute("message", "用户名或密码不正确");
        }
        //验证是否登录成功
        if(subject.isAuthenticated()){
            return "redirect:/";
        }else{
            token.clear();
            return "失败";
        }
    }

    @RequestMapping("/login.html")
    public String login(User user){
      /*  GroupTemplate groupTemplate =  new  GroupTemplate();
        Map<String,Object> shareVars = new HashMap<>();
        shareVars.put("school_name","来测试一吓");
        groupTemplate.setSharedVars(shareVars);*/
      User sessionUser = super.getSessionUser();
       return "/login.html";
    }
}
