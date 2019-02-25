package com.talentcenter.controller;

import com.talentcenter.entity.User;
import com.talentcenter.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController extends BaseController {

    @Autowired
    UserService userService;

    @RequestMapping("/login_check.html")
    public String loginCheck(User user,Model model){
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUserName(), user.getPassword());


        User u = new User();
        u.setUserName(user.getUserName());
        User userInfo = userService.selectOne(u);
        if(userInfo!=null){
            if(userInfo.getUserNature()==1){
                Integer ifCheck = userService.ifCheck(user.getUserName());
                if(ifCheck==0) {
                    token.clear();
                    model.addAttribute("error", "请耐心等待审核后再尝试登陆。");
                    return "/login.html";
                }
            }
        }

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
            model.addAttribute("error","用户名密码错误");
            return "/login.html";
        }
    }

    @RequestMapping("/login.html")
    public String login(User user,Model model){
      /*  GroupTemplate groupTemplate =  new  GroupTemplate();
        Map<String,Object> shareVars = new HashMap<>();
        shareVars.put("school_name","来测试一吓");
        groupTemplate.setSharedVars(shareVars);*/
      User sessionUser = super.getSessionUser();
        model.addAttribute("error","");
       return "/login.html";
    }
}
