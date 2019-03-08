package com.talentcenter.controller;

import RSTFul.RSTFulBody;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.talentcenter.entity.*;
import com.talentcenter.service.*;
import org.springframework.web.bind.annotation.RequestBody;
import util.DateHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/*import javax.jws.WebParam;*/
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static util.DateHelper.getDate4StrDate;

@Controller
@RequestMapping("/street")
public class StreetController extends BaseController{
    @Autowired
    private StreetService streetService;

    @Autowired
    private UserService userService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private CompanyUserItemService companyUserItemService;

    @Autowired
    private CompanyUserInfoService companyUserInfoService;

    @Autowired
    private CompanyUserFamilyService companyUserFamilyService;

    @Autowired
    private ItemConfigService itemConfigService;

    @Autowired
    private ItemTalentContentService itemTalentContentService;

    @Autowired
    private TypeCategoryService typeCategoryService;

    @Autowired
    private InfoChangeService infoChangeService;

    @Autowired
    private ItemUserTimeService itemUserTimeService;

    @Autowired
    private CompanyUserCertificateService companyUserCertificateService;

    @Autowired
    private SendLogService sendLogService;

    @Autowired
    private CompanyNatureService companyNatureService;

    @Autowired
    private CompanyTypeService companyTypeService;

    @Autowired
    private CompanyItemService companyItemService;
    /**
     * 证书列表页
     * @param model
     * @return
     */
    @RequestMapping("/check_company.html")
    public String checkCompany(Model model){
        return "/street/check_company.html";
    }

    @RequestMapping("/ajax_check_company")
    public String ajaxCheckCompany(Model model, int pageNum, int pageSize,
                            @RequestParam(required = false) String companyName
    ){
        //组装搜索条件
        /*Map<String,Object> map=new HashMap<>();
        if(userTrueName!=null && userTrueName!="") map.put("userTrueName",userTrueName);
        if(examId!=null && examId!="") map.put("examId",examId);
        if(userSex!=null && userSex!="") map.put("userSex",userSex);*/
        User sessionUser = getSessionUser();
        Company company = new Company();
        company.setDel(true);
        company.setStreetId(sessionUser.getStreetId());
        company.setState(1);
        if(companyName!=null && companyName!="") company.setCompanyName(companyName);
        //分页查询
        PageHelper.startPage(pageNum, pageSize);
        List<Company> companies = companyService.select(company);
//        List<Street> streets = streetService.select(street);

        PageInfo<Company> pageInfo= new PageInfo<>(companies);
        String pageStr = makePageHtml(pageInfo);
        model.addAttribute("page_info",pageInfo);
        model.addAttribute("pages",pageStr);
        return "/street/ajax_check_company.html";
    }

    @ResponseBody
    @RequestMapping("check")
    public Boolean check(Long companyId){
        Company company = new Company();
        company.setState(2);
        company.setCompanyId(companyId);
        int res = companyService.updateByPrimaryKeySelective(company);
        return true;
    }

    @RequestMapping("/index.html")
    public String index(Model model){
        return "/street/index.html";
    }

    @RequestMapping("/ajax_index")
    public String ajaxIndex(Model model, int pageNum, int pageSize,
                            @RequestParam(required = false) String streetName
    ){
        //组装搜索条件
        /*Map<String,Object> map=new HashMap<>();
        if(userTrueName!=null && userTrueName!="") map.put("userTrueName",userTrueName);
        if(examId!=null && examId!="") map.put("examId",examId);
        if(userSex!=null && userSex!="") map.put("userSex",userSex);*/
        Street street = new Street();
        street.setDel(true);
        if(streetName!=null && streetName!="") street.setStreetName(streetName);
        //分页查询
        PageHelper.startPage(pageNum, pageSize);
        List<Street> streets = streetService.select(street);

        PageInfo<Street> pageInfo= new PageInfo<>(streets);
        String pageStr = makePageHtml(pageInfo);
        model.addAttribute("page_info",pageInfo);
        model.addAttribute("pages",pageStr);
        return "/street/ajax_index.html";
    }

    @RequestMapping("add.html")
    public String addUI() {
        return "/street/add.html";
    }

    @RequestMapping("/company.html")
    public String company(Model model) {
        return "/street/company.html";
    }

    @RequestMapping("/ajax_company")
    public String ajaxCompany(Model model, int pageNum, int pageSize,
                            @RequestParam(required = false) String companyName
    ) {
        //组装搜索条件
        /*Map<String,Object> map=new HashMap<>();
        if(userTrueName!=null && userTrueName!="") map.put("userTrueName",userTrueName);
        if(examId!=null && examId!="") map.put("examId",examId);
        if(userSex!=null && userSex!="") map.put("userSex",userSex);*/
        Company company = new Company();
        company.setDel(true);
        company.setStreetId(getSessionUser().getStreetId());
        if (companyName != null && companyName != "") company.setCompanyName(companyName);

        //分页查询
        PageHelper.startPage(pageNum, pageSize);
        List<Company> companys = companyService.selectByName(company);

        PageInfo<Company> pageInfo = new PageInfo<>(companys);
        String pageStr = makePageHtml(pageInfo);
        model.addAttribute("page_info", pageInfo);
        model.addAttribute("pages", pageStr);
        return "/street/ajax_company.html";
    }

    @RequestMapping("edit_company.html")
    public String editCompany(Model model, String companyId) {
        Company company = companyService.selectByPrimaryKey((long) Integer.parseInt(companyId));
        model.addAttribute("companyId", companyId);
        model.addAttribute("obj", company);
        List<CompanyNature> companyNatures = companyNatureService.selectAll();
        model.addAttribute("companyNatures", companyNatures);
        List<CompanyType> companyTypes = companyTypeService.selectAll();
        model.addAttribute("companyTypes", companyTypes);
        List<Street> streets = streetService.selectAll();
        model.addAttribute("streets", streets);

        CompanyItem companyItem = new CompanyItem();
        companyItem.setCompanyId((long) Integer.parseInt(companyId));
        List<CompanyItem> companyItems = companyItemService.select(companyItem);
        List<Item> items = itemService.selectAll();

        model.addAttribute("obj", company);
        return "/street/edit_company.html";
    }

    @ResponseBody
    @RequestMapping("edit_company")
    public RSTFulBody edit(Company company,String password) {
        User sessionUser = getSessionUser();
        company.setUpdateId(sessionUser.getUserId());
        company.setUpdateName(sessionUser.getUserName());
        company.setUpdateTime(DateHelper.getCurrentDate());

        CompanyNature companyNature = companyNatureService.selectByPrimaryKey(company.getCompanyNatureId());
        company.setCompanyNatureName(companyNature.getCompanyNatureName());
        CompanyType companyType = companyTypeService.selectByPrimaryKey(company.getCompanyTypeId());
        company.setCompanyTypeName(companyType.getCompanyTypeName());
        int res = companyService.updateByPrimaryKeySelective(company);

        if(password!=null && password!=""){
//            user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
            User updateUser = new User();
            updateUser.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
            Company c = companyService.selectByPrimaryKey(company.getCompanyId());
            updateUser.setUserId(c.getUserId());
            userService.updateByPrimaryKeySelective(updateUser);
        }
        RSTFulBody rstFulBody = new RSTFulBody();
        if (res > 0) rstFulBody.success("修改成功！");
        else rstFulBody.fail("修改失败！");
        return rstFulBody;
    }

    @ResponseBody
    @RequestMapping("add")
    public RSTFulBody add(Street street) {
        User sessionUser = getSessionUser();
        street.setCreateName(sessionUser.getUserName());
        street.setCreateId(sessionUser.getUserId());
        int res = streetService.insertSelective(street);
        RSTFulBody rstFulBody = new RSTFulBody();
        if (res > 0) rstFulBody.success("添加成功！");
        else rstFulBody.fail("添加失败！");
        return rstFulBody;
    }

    @RequestMapping("edit.html")
    public String editUI(Model model, String streetId) {
        Street street = streetService.selectByPrimaryKey((long) Integer.parseInt(streetId));
        model.addAttribute("streetId",streetId);
        model.addAttribute("obj",street);
        return "/street/edit.html";
    }

    @ResponseBody
    @RequestMapping("edit")
    public RSTFulBody edit(Street street) {
        User sessionUser = getSessionUser();
        street.setUpdateId(sessionUser.getUserId());
        street.setUpdateName(sessionUser.getUserName());
        street.setUpdateTime(DateHelper.getCurrentDate());
        int res = streetService.updateByPrimaryKeySelective(street);
        RSTFulBody rstFulBody = new RSTFulBody();
        if (res > 0) rstFulBody.success("修改成功！");
        else rstFulBody.fail("修改失败！");
        return rstFulBody;
    }

    @RequestMapping("del.html")
    public String delUser(Street street){
        street.setDel(false);
        int res = streetService.updateByPrimaryKeySelective(street);
        Map<String, Object> map = new HashMap<>();
        map.put("ids",street.getStreetId());
        streetService.delStreetUser(map);
        return "redirect:/street/index.html";
    }

    @ResponseBody
    @RequestMapping("batch_del")
    public RSTFulBody batchDel(@RequestParam(required = true) String ids){
        Map<String, Object> map = new HashMap<>();
        map.put("ids",ids);
        int res = streetService.batchDel(map);
        streetService.delStreetUser(map);
        RSTFulBody rstFulBody=new RSTFulBody();
        if(res>0) rstFulBody.success(res);
        else  rstFulBody.fail("删除失败！");
        return rstFulBody;
    }

    @RequestMapping("users.html")
    public String users(Model model,Integer streetId){
        model.addAttribute("streetId",streetId);
        return "/street/users.html";
    }

    @RequestMapping("/ajax_users")
    public String ajaxUsers(Model model, int pageNum, int pageSize,
                            @RequestParam(required = false) String userName,
                            @RequestParam(required = false) String realName,
                            @RequestParam(required = false) String userTel,
                            @RequestParam(required = false) long streetId
    ){
        //组装搜索条件
        User user = new User();
        user.setDel(true);
        if(userName!=null && userName!="") user.setUserName(userName);
        if(realName!=null && realName!="") user.setRealName(realName);
        if(userTel!=null && userTel!="") user.setUserTel(userTel);
        user.setStreetId(streetId);
//        user.setStreetId((long)Integer.parseInt(streetId));

        //分页查询
        PageHelper.startPage(pageNum, pageSize);
        List<User> users = userService.select(user);

        PageInfo<User> pageInfo= new PageInfo<>(users);
        String pageStr = makePageHtml(pageInfo);
        model.addAttribute("page_info",pageInfo);
        model.addAttribute("pages",pageStr);
        return "/street/ajax_users.html";
    }

    @RequestMapping("add_user.html")
    public String addUserUI(Model model,String streetId) {
        model.addAttribute("streetId",streetId);
        return "/street/add_user.html";
    }

    @ResponseBody
    @RequestMapping("add_user")
    public RSTFulBody addUser(User user) {
        User sessionUser = getSessionUser();
        user.setCreateName(sessionUser.getUserName());
        user.setCreateId(sessionUser.getUserId());
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        user.setUserNature(2);
        int res = userService.insertSelective(user);
        RSTFulBody rstFulBody = new RSTFulBody();
        rstFulBody.data(user.getStreetId()+"");
        if (res > 0) rstFulBody.success("添加成功！");
        else rstFulBody.fail("添加失败！");
        return rstFulBody;
    }

    @RequestMapping("edit_user.html")
    public String editUserUI(Model model, long primaryKey) {
//        User user = userService.selectByPrimaryKey((long)Integer.parseInt(primaryKey));
        User user = userService.selectByPrimaryKey(primaryKey);
        model.addAttribute("obj",user);
        return "/street/edit_user.html";
    }

    @ResponseBody
    @RequestMapping("edit_user")
    public RSTFulBody editUser(User user) {
        User sessionUser = getSessionUser();
        user.setUpdateId(sessionUser.getUserId());
        user.setUpdateName(sessionUser.getUserName());
        user.setUpdateTime(DateHelper.getCurrentDate());
        if(user.getPassword()!=null) user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        int res = userService.updateByPrimaryKeySelective(user);
        RSTFulBody rstFulBody = new RSTFulBody();
        rstFulBody.data(user.getStreetId()+"");
        if (res > 0) rstFulBody.success("修改成功！");
        else rstFulBody.fail("修改失败！");
        return rstFulBody;
    }

    @RequestMapping("del_user.html")
    public String del(User user,String streetId){
        user.setDel(false);
        int res = userService.updateByPrimaryKeySelective(user);
        return "redirect:/street/users.html?streetId="+streetId;
    }

    @ResponseBody
    @RequestMapping("batch_del_user")
    public RSTFulBody batchDelUser(@RequestParam(required = true) String ids,String streetId){

        Map<String, Object> map = new HashMap<>();
        map.put("ids",ids);
        int res = userService.batchDel(map);
        RSTFulBody rstFulBody=new RSTFulBody();
        rstFulBody.data(streetId);
        if(res>0) rstFulBody.success(res);
        else  rstFulBody.fail("删除失败！");
        return rstFulBody;
    }

    @RequestMapping("check_item.html")
    public String checkItem(Model model){
        List<Item> items = itemService.selectAll();
        model.addAttribute("items",items);
       /* Street street = new Street();
        street.setDel(true);
        List<Street> streets = streetService.select(street);
        model.addAttribute("streets",streets);*/
        return "/street/check_item.html";
    }

    @RequestMapping("check_item")
    public String ajaxCheckItem(Model model,
                                @RequestParam(required = false) Long itemId,
                                @RequestParam(required = false) Integer streetChecked,
                                @RequestParam(required = false) String companyName){

        User sessionUser = getSessionUser();
        Map<String,Object> map = new HashMap<>();
        if(sessionUser.getUserType()==2) {
            map.put("streetChecked",3);
        }else if(sessionUser.getUserType()==1){
            map.put("streetChecked",2);
        }
        if(itemId!=null) map.put("itemId",itemId);
        if(streetChecked!=null) map.put("streetChecked",streetChecked);
        Company company = new Company();
//        if(companyName!=null && companyName!="") map.put("companyName",companyName);
        if(companyName!=null && companyName!="") company.setCompanyName(companyName);
        company.setStreetId(sessionUser.getStreetId());
        List<Company> companies = companyService.selectByName(company);

        List<Map<String,Object>> maps = new ArrayList<>();
        if(companies.size()>0){
            String companyIds = "";
            for (Company c : companies) {
                companyIds+=c.getUserId()+",";
            }
            companyIds = companyIds.substring(0,companyIds.length() - 1);
            map.put("companyIds",companyIds);

            maps = streetService.selectStreetItems(map);
        }


        PageInfo<Map<String,Object>> pageInfo= new PageInfo<>(maps);
        String pageStr = makePageHtml(pageInfo);
        model.addAttribute("page_info",pageInfo);
        model.addAttribute("pages",pageStr);
        return "/street/ajax_items.html";
    }

    @RequestMapping("check_info.html")
    public String checkInfo(Long companyUserItemId,Model model){
        CompanyUserItem companyUserItem = companyUserItemService.selectByPrimaryKey(companyUserItemId);
        User user = userService.selectByPrimaryKey(companyUserItem.getUserId());
        CompanyUserInfo companyUserInfo = new CompanyUserInfo();
        companyUserInfo.setUserId(companyUserItem.getUserId());
        CompanyUserInfo cui = companyUserInfoService.selectOne(companyUserInfo);
        Company company = new Company();
        company.setUserId(user.getCompanyId());
        Company c = companyService.selectOne(company);
        CompanyUserFamily companyUserFamily = new CompanyUserFamily();
        companyUserFamily.setUserId(user.getUserId());
        List<CompanyUserFamily> cuf= companyUserFamilyService.select(companyUserFamily);

//        ItemConfig itemConfig = item
        ItemConfig itemConfig = new ItemConfig();
        itemConfig.setItemConfigState(true);
        itemConfig.setItemId(companyUserItem.getItemId());
        ItemConfig ic = itemConfigService.selectOne(itemConfig);
//        TypeCategory typeCategory = typeCategoryService.selectByPrimaryKey(ic.getTypeCategoryId());
        List<ItemTalentContent> itemTalentContents = null;
        if(ic.getItemConfigTType()){
            ItemTalentContent itemTalentContent = new ItemTalentContent();
            itemTalentContent.setItemConfigId(ic.getItemConfigId());
//            talentTypes = talentTypeService.select(talentType);
            itemTalentContents = itemTalentContentService.select(itemTalentContent);
        }

        TypeCategory typeCategory = typeCategoryService.selectByPrimaryKey(ic.getTypeCategoryId());

        InfoChange infoChange = new InfoChange();
        infoChange.setUserId(user.getUserId());
        List<InfoChange> infoChanges = infoChangeService.select(infoChange);
        Map<String,Object> ics = new HashMap<>();
        for (InfoChange change : infoChanges) {
            ics.put(change.getFiledName(),1);
        }

        Item item = itemService.selectByPrimaryKey(companyUserItem.getItemId());
        List<CompanyUserItem> childUserItem=null;
        if(item.getItemCategory()==1){
            CompanyUserItem ccui = new CompanyUserItem();
            ccui.setParentId(companyUserItem.getCompanyUserItemId());
            childUserItem = companyUserItemService.select(ccui);
        }

        ItemUserTime itemUserTime = new ItemUserTime();
        itemUserTime.setUserId(user.getUserId());
        itemUserTime.setCompanyUserItemId(companyUserItem.getCompanyUserItemId());
//        ItemUserTime iut = itemUserTimeService.selectOne(itemUserTime);

        Item ii = new Item();
        ii.setItemCategory(0);
        List<Item> items = itemService.select(ii);

        model.addAttribute("user",user);
        model.addAttribute("cui",cui);
        model.addAttribute("company",c);
        model.addAttribute("cufs",cuf);
        model.addAttribute("ic",ic);
        model.addAttribute("itcs",itemTalentContents);
        model.addAttribute("items",items);
        model.addAttribute("tc",typeCategory);
        model.addAttribute("ics",ics);
        model.addAttribute("item",item);
        model.addAttribute("companyUserItem",companyUserItem);
        model.addAttribute("childUserItems",childUserItem);
//        model.addAttribute("iut",iut);
        return "/street/check_info.html";
    }

    @RequestMapping("/modal_content")
    public String modalContent(Model model,Long userId,Long certificateId){
        CompanyUserCertificate companyUserCertificate = new CompanyUserCertificate();
        companyUserCertificate.setUserId(userId);
        companyUserCertificate.setCertificateId(certificateId);
        List<CompanyUserCertificate> companyUserCertificates = companyUserCertificateService.select(companyUserCertificate);
        model.addAttribute("cucs",companyUserCertificates);
        return "/street/modal_content.html";
    }

    @ResponseBody
    @RequestMapping("/pass")
    public Boolean pass(@RequestBody JSONObject jsonParam){

        User sessionUser = getSessionUser();
        PassJson passJson = JSON.parseObject(jsonParam.toJSONString(), new TypeReference<PassJson>() {});
        CompanyUserItem cui = companyUserItemService.selectByPrimaryKey(passJson.getUserItemId());
        CompanyUserItem companyUserItem = new CompanyUserItem();
        companyUserItem.setCompanyUserItemId(passJson.getUserItemId());
        companyUserItem.setType(passJson.getType());
        companyUserItem.setAmount(passJson.getAmount());
        companyUserItem.setMemo(passJson.getMemo());
        companyUserItem.setTalentType((long)Integer.parseInt(passJson.getTalentType()));
        companyUserItem.setTalentTypeContent(passJson.getTalentTypeContent());
        if(getSessionUser().getUserType()==2) {
            companyUserItem.setStreetChecked(2);
        }else if(getSessionUser().getUserType()==1){
            companyUserItem.setStreetChecked(4);
        }

        if(passJson.getItemTime().length()>0){
            String[] houseContractTimes = passJson.getItemTime().split("至");
            companyUserItem.setStart(getDate4StrDate(houseContractTimes[0].trim(), "yyyy-MM-dd"));
            companyUserItem.setEnd(getDate4StrDate(houseContractTimes[1].trim(), "yyyy-MM-dd"));
        }

//        companyUserItemService.updateUserItem(companyUserItem);
        companyUserItemService.updateByPrimaryKeySelective(companyUserItem);
        Map<String,Object> map = new HashMap<>();
        map.put("userId",cui.getUserId());
        map.put("companyUserItmeId",cui.getCompanyUserItemId());
        map.put("amount",passJson.getTalentTypeContent());
        sendLogService.updateAmount(map);
        companyUserItemService.delByParentId(passJson.getUserItemId());
        /*if(passJson.getItems()!=null){
            List<CompanyUserItem> companyUserItems = new ArrayList<>();
            for (ItemJson itemJson : passJson.getItems()) {
                Item item = itemService.selectByPrimaryKey(itemJson.getItem());
                ItemConfig itemConfig = new ItemConfig();
                itemConfig.setItemId(item.getItemId());
                itemConfig.setItemConfigState(true);
                ItemConfig ic = itemConfigService.selectOne(itemConfig);
                CompanyUserItem c = new CompanyUserItem();
                c.setParentId(passJson.getUserItemId());
                c.setItemId(item.getItemId());
                c.setConfigId(ic.getItemConfigId());
                c.setItemName(item.getItemName());
                c.setUserId(cui.getUserId());
                if(itemJson.getTypeContent()!=null) c.setTalentTypeContent(itemJson.getTypeContent());
                companyUserItems.add(c);
            }
            companyUserItemService.insertList(companyUserItems);
        }*/

        return true;
    }

    @ResponseBody
    @RequestMapping("/back_npass_pass")
    public Boolean backNPassPass(Integer state,Long userItemId,String reason){
        CompanyUserItem companyUserItem = new CompanyUserItem();
        companyUserItem.setCompanyUserItemId(userItemId);
        companyUserItem.setStreetChecked(state);
        companyUserItem.setStreetReason(reason);
        companyUserItem.setHaveSubmit(false);
        companyUserItemService.updateByPrimaryKeySelective(companyUserItem);
        return true;
    }
}
