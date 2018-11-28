package com.talentcenter.controller;

import RSTFul.RSTFulBody;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.talentcenter.entity.*;
import com.talentcenter.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import util.DateHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static util.DateHelper.getDate4StrDate;

@Controller
@RequestMapping("/company_user")
public class CompanyUserController extends BaseController {
    @Autowired
    private ItemService itemService;
    @Autowired
    private UserService userService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private CompanyItemService companyItemService;
    @Autowired
    private CompanyUserItemService companyUserItemService;
    @Autowired
    private CompanyUserInfoService companyUserInfoService;
    @Autowired
    private RegionService regionService;
    @Autowired
    private CompanyUserFamilyService companyUserFamilyService;

    @Autowired
    private ItemConfigService itemConfigService;

    @Autowired
    private ItemCertificateService itemCertificateService;
   /* @Autowired
    private RoleCompanyUserService roleCompanyUserService;*/

    /**
     * 证书列表页
     *
     * @param model
     * @return
     */
    @RequestMapping("/index.html")
    public String index(Model model) {
        return "/company_user/index.html";
    }

    @RequestMapping("/ajax_users")
    public String ajaxIndex(Model model, int pageNum, int pageSize,
                            @RequestParam(required = false) String companyUserName
    ) {
        //组装搜索条件
        /*Map<String,Object> map=new HashMap<>();
        if(userTrueName!=null && userTrueName!="") map.put("userTrueName",userTrueName);
        if(examId!=null && examId!="") map.put("examId",examId);
        if(userSex!=null && userSex!="") map.put("userSex",userSex);*/
        User companyUser = new User();
        companyUser.setDel(true);
        companyUser.setUserNature(3);
        companyUser.setCompanyId(getSessionUser().getUserId());


        if (companyUserName != null && companyUserName != "") companyUser.setUserName(companyUserName);
        //分页查询
        PageHelper.startPage(pageNum, pageSize);
        List<User> companyUsers = userService.select(companyUser);

        PageInfo<User> pageInfo = new PageInfo<>(companyUsers);
        String pageStr = makePageHtml(pageInfo);
        model.addAttribute("page_info", pageInfo);
        model.addAttribute("pages", pageStr);
        return "/company_user/ajax_users.html";
    }

    @RequestMapping("add_user.html")
    public String addUI(Model model) {
        CompanyItem companyItem = new CompanyItem();

        Company c = new Company();
        c.setUserId(getSessionUser().getUserId());
        Company company = companyService.selectOne(c);

        companyItem.setCompanyId(company.getCompanyId());

        List<CompanyItem> companyItems =  companyItemService.select(companyItem);
        model.addAttribute("companyItems", companyItems);
        return "/company_user/add_user.html";
    }

    @ResponseBody
    @RequestMapping("add_user")
    public RSTFulBody add(User user, @RequestParam(value = "itemId[]") String[] itemId) {
        User sessionUser = getSessionUser();
        user.setCreateName(sessionUser.getUserName());
        user.setCreateId(sessionUser.getUserId());
        user.setUserNature(3);
        user.setCompanyId(getSessionUser().getUserId());
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        int res = userService.insertSelective(user);

        CompanyUserInfo companyUserInfo = new CompanyUserInfo();
        companyUserInfo.setUserId(user.getUserId());
        companyUserInfoService.insertSelective(companyUserInfo);
        ArrayList<CompanyUserItem> companyUserItems = new ArrayList<>();
        for (String item : itemId) {
            CompanyUserItem companyUserItem = new CompanyUserItem();
            companyUserItem.setItemId((long) Integer.parseInt(item));
            companyUserItem.setUserId(user.getUserId());
            Item i = itemService.selectByPrimaryKey((long) Integer.parseInt(item));
            companyUserItem.setItemName(i.getItemName());
            companyUserItems.add(companyUserItem);
        }
        int num = companyUserItemService.insertList(companyUserItems);


        RSTFulBody rstFulBody = new RSTFulBody();
        if (res > 0) rstFulBody.success("添加成功！");
        else rstFulBody.fail("添加失败！");
        return rstFulBody;
    }

    @RequestMapping("edit_user.html")
    public String editUI(Model model, String userId) {
        User user = userService.selectByPrimaryKey((long) Integer.parseInt(userId));
        CompanyItem companyItem = new CompanyItem();

        Company c = new Company();
        c.setUserId(getSessionUser().getUserId());
        Company company = companyService.selectOne(c);

        companyItem.setCompanyId(company.getCompanyId());

        List<CompanyItem> companyItems =  companyItemService.select(companyItem);

        CompanyUserItem companyUserItem = new CompanyUserItem();
        companyUserItem.setUserId((long) Integer.parseInt(userId));
        List<CompanyUserItem> companyUserItems = companyUserItemService.select(companyUserItem);

        for (CompanyItem i : companyItems) {
            i.setChecked(false);
            for (CompanyUserItem cui : companyUserItems) {
                if (i.getItemId() == cui.getItemId()) {
                    i.setChecked(true);
                    continue;
                }
            }
        }

        model.addAttribute("companyItems", companyItems);
        model.addAttribute("obj", user);

        return "/company_user/edit_user.html";
    }

    @ResponseBody
    @RequestMapping("edit_user")
    public RSTFulBody edit(User user, @RequestParam(value = "itemId[]") String[] itemId) {
        User sessionUser = getSessionUser();
        user.setUpdateId(sessionUser.getUserId());
        user.setUpdateName(sessionUser.getUserName());
        user.setUpdateTime(DateHelper.getCurrentDate());
        if (user.getPassword() != null) user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        int res = userService.updateByPrimaryKeySelective(user);

        int del  = companyUserItemService.delByUserId(user.getUserId());

        ArrayList<CompanyUserItem> companyUserItems = new ArrayList<>();
        for (String item : itemId) {
            CompanyUserItem companyUserItem = new CompanyUserItem();
            companyUserItem.setItemId((long) Integer.parseInt(item));
            companyUserItem.setUserId(user.getUserId());
            Item i = itemService.selectByPrimaryKey((long) Integer.parseInt(item));
            companyUserItem.setItemName(i.getItemName());
            companyUserItems.add(companyUserItem);
        }
        int num = companyUserItemService.insertList(companyUserItems);

        RSTFulBody rstFulBody = new RSTFulBody();
        if (res > 0) rstFulBody.success("修改成功！");
        else rstFulBody.fail("修改失败！");
        return rstFulBody;
    }

    @RequestMapping("del_user.html")
    public String delUser(User user) {
        user.setDel(false);
        int res = userService.updateByPrimaryKeySelective(user);
        return "redirect:/company_user/index.html";
    }

    @ResponseBody
    @RequestMapping("batch_del")
    public RSTFulBody batchDel(@RequestParam(required = true) String ids) {


        Map<String, Object> map = new HashMap<>();
        map.put("ids", ids);
        int res = userService.batchDel(map);
        RSTFulBody rstFulBody = new RSTFulBody();
        if (res > 0) rstFulBody.success(res);
        else rstFulBody.fail("删除失败！");
        return rstFulBody;

    }
    @RequestMapping("/info.html")
    public String info(Model model) {
        User sessionUser = getSessionUser();
        CompanyUserInfo cui = new CompanyUserInfo();
        cui.setUserId(sessionUser.getUserId());
        CompanyUserInfo companyUserInfo = companyUserInfoService.selectOne(cui);
        Region region = new Region();
        region.setParentId((long) 0);
        List<Region> provinces = regionService.select(region);
        List<Region> cities = new ArrayList<>();
        List<Region> districts = new ArrayList<>();
        if(companyUserInfo.getCompanyUserHomeProvinceId()!=null){
            region.setParentId(companyUserInfo.getCompanyUserHomeProvinceId());
            cities = regionService.select(region);
        }

        if(companyUserInfo.getCompanyUserHomeCityId()!=null){
            region.setParentId(companyUserInfo.getCompanyUserHomeCityId());
            districts = regionService.select(region);
        }

        CompanyUserFamily companyUserFamily = new CompanyUserFamily();
        companyUserFamily.setUserId(sessionUser.getUserId());
        List<CompanyUserFamily> companyUserFamilies = companyUserFamilyService.select(companyUserFamily);
        model.addAttribute("provinces",provinces);
        model.addAttribute("companyUserFamilies",companyUserFamilies);
        model.addAttribute("cities",cities);
        model.addAttribute("districts",districts);
        model.addAttribute("obj",companyUserInfo);
        return "/company_user/info.html";
    }

    @ResponseBody
    @RequestMapping("/update_user")
    public RSTFulBody updateUser(CompanyUserInfo companyUserInfo,
                                 @RequestParam(required = false) String userContractTime,
                                 @RequestParam(required = false, value = "companyUserFamilyType[]") String[] companyUserFamilyType,
                                 @RequestParam(required = false, value = "companyUserFamilyName[]") String[] companyUserFamilyName,
                                 @RequestParam(required = false, value = "companyUserFamilyCard[]") String[] companyUserFamilyCard,
                                 @RequestParam(required = false, value = "companyUserFamilySex[]") String[] companyUserFamilySex
                                 ){
        String[] contractTimes = userContractTime.split("至");
        companyUserInfo.setCompanyUserContractTimeBegin(getDate4StrDate(contractTimes[0], "yyyy-MM-dd"));
        companyUserInfo.setCompanyUserContractTimeEnd(getDate4StrDate(contractTimes[1], "yyyy-MM-dd"));
        int res = companyUserInfoService.updateByUserId(companyUserInfo);

        companyUserFamilyService.delByUserId(companyUserInfo.getUserId());
        if (companyUserFamilyName != null) {
            List<CompanyUserFamily> companyUserFamilies = new ArrayList<>();
            for(int i=0;i<companyUserFamilyName.length;i++){
                CompanyUserFamily companyUserFamily = new CompanyUserFamily();
                companyUserFamily.setCompanyUserFamilyName(companyUserFamilyName[i]);
                companyUserFamily.setCompanyUserFamilyCard(companyUserFamilyCard[i]);
                companyUserFamily.setCompanyUserFamilySex(Integer.parseInt(companyUserFamilySex[i]));
                companyUserFamily.setCompanyUserFamilyType(Integer.parseInt(companyUserFamilyType[i]));
                companyUserFamily.setUserId(companyUserInfo.getUserId());
                companyUserFamilies.add(companyUserFamily);
            }
            companyUserFamilyService.insertList(companyUserFamilies);
        }

        RSTFulBody rstFulBody = new RSTFulBody();
        if (res > 0) rstFulBody.success("提交成功！");
        else rstFulBody.fail("提交失败！");
        return rstFulBody;
    }

    @RequestMapping("/items.html")
    public String items(Model model) {
        return "/company_user/items.html";
    }

    @RequestMapping("/ajax_items")
    public String ajaxItems(Model model, int pageNum, int pageSize) {

        PageHelper.startPage(pageNum, pageSize);
        List<Item> items = itemService.selectByUserId(getSessionUser().getUserId());
        PageInfo<Item> pageInfo = new PageInfo<>(items);
        String pageStr = makePageHtml(pageInfo);
        model.addAttribute("page_info", pageInfo);
        model.addAttribute("pages", pageStr);
        return "/company_user/ajax_items.html";
    }

    @RequestMapping("/item_info.html")
    public String itemInfo(Model model,Long itemId){
        User sessionUser = getSessionUser();
        if(!ifPermit(sessionUser.getUserId(),itemId)){
            return "redirect:/company_user/items.html";
        }
        Item item = itemService.selectByPrimaryKey(itemId);
        ItemConfig itemConfig = new ItemConfig();
        itemConfig.setItemId(itemId);
        itemConfig.setItemConfigState(true);
        ItemConfig ic = itemConfigService.selectOne(itemConfig);
//        ItemConfig itemConfig = itemConfigService.
//        itemConfigService.se
        model.addAttribute("item",item);
        model.addAttribute("itemConfig",ic);
        return "/company_user/item_info.html";
    }

    @RequestMapping("/ask_for.html")
    public String askFor(Model model,Long itemId){
        //判断是否有资格申请该补助
        User sessionUser = getSessionUser();
        if(!ifPermit(sessionUser.getUserId(),itemId)){
            return "redirect:/company_user/items.html";
        }
        ItemConfig itemConfig = new ItemConfig();
        itemConfig.setItemId(itemId);
        itemConfig.setItemConfigState(true);
        ItemConfig ic = itemConfigService.selectOne(itemConfig);

        ItemCertificate itemCertificate = new ItemCertificate();
        itemCertificate.setItemConfigId(ic.getItemConfigId());
        List<ItemCertificate> itemCertificates = itemCertificateService.select(itemCertificate);

        model.addAttribute("certificates",itemCertificates);
        return "/company_user/ask_for.html";
    }


    private Boolean ifPermit(Long userId,Long itemId){
        Boolean res = true;
        Map<String,Object> map = new HashMap<>();
        map.put("userId",userId);
        map.put("itemId",itemId);
        List<CompanyUserItem> companyUserItems = companyUserItemService.selectByInfo(map);
        if(companyUserItems==null) res = false;
        return res;
    }
}
