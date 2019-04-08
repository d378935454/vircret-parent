package com.talentcenter.controller;

import RSTFul.RSTFulBody;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.talentcenter.entity.*;
import com.talentcenter.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import util.DateHelper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static util.DateHelper.getDate4StrDate;

@Controller
@RequestMapping("/company")
public class CompanyController extends BaseController {
    @Autowired
    private CompanyService companyService;

    @Autowired
    private CompanyNatureService companyNatureService;

    @Autowired
    private CompanyTypeService companyTypeService;

    @Autowired
    private StreetService streetService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private CompanyItemService companyItemService;

    @Autowired
    private UserService userService;

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
    private CompanyUserCertificateService companyUserCertificateService;

    @Autowired
    private SendLogService sendLogService;

    @Autowired
    private ItemCertificateService itemCertificateService;

    /* @Autowired
    private ItemUserTimeService itemUserTimeService;
   @Autowired
    private RoleCompanyService roleCompanyService;*/

    /**
     * 企业列表页
     *
     * @param model
     * @return
     */
    @RequestMapping("/index.html")
    public String index(Model model) {
        Street condition = new Street();
        condition.setDel(true);
        List<Street> streets = streetService.select(condition);
        model.addAttribute("streets", streets);
        return "/company/index.html";
    }

    @RequestMapping("/ajax_index")
    public String ajaxIndex(Model model, int pageNum, int pageSize,
                            @RequestParam(required = false) String companyName,
                            @RequestParam(required = false) Long streetId

    ) {
        //组装搜索条件
        /*Map<String,Object> map=new HashMap<>();
        if(userTrueName!=null && userTrueName!="") map.put("userTrueName",userTrueName);
        if(examId!=null && examId!="") map.put("examId",examId);
        if(userSex!=null && userSex!="") map.put("userSex",userSex);*/
        Company company = new Company();
        company.setDel(true);
        if (companyName != null && companyName != "") company.setCompanyName(companyName);
        if (streetId != null && streetId != 0) company.setStreetId(streetId);

        //分页查询
        PageHelper.startPage(pageNum, pageSize);
        List<Company> companys = companyService.selectByName(company);
        for (Company c : companys) {
            CompanyItem condition = new CompanyItem();
            condition.setCompanyId(c.getCompanyId());
            List<CompanyItem> companyItems = companyItemService.select(condition);
            c.setCompanyItems(companyItems);
        }

        PageInfo<Company> pageInfo = new PageInfo<>(companys);
        String pageStr = makePageHtml(pageInfo);
        model.addAttribute("page_info", pageInfo);
        model.addAttribute("pages", pageStr);
        return "/company/ajax_index.html";
    }

    @RequestMapping("add.html")
    public String addUI(Model model) {
        List<CompanyNature> companyNatures = companyNatureService.selectAll();
        model.addAttribute("companyNatures", companyNatures);
        List<CompanyType> companyTypes = companyTypeService.selectAll();
        model.addAttribute("companyTypes", companyTypes);
        List<Street> streets = streetService.selectAll();
        model.addAttribute("streets", streets);
        List<Item> items = itemService.selectAll();
        model.addAttribute("items", items);

        return "/company/add.html";
    }

    @ResponseBody
    @RequestMapping("add")
    public RSTFulBody add(Company company, @RequestParam(value = "itemId[]") String[] itemId) {
        User sessionUser = getSessionUser();
        company.setCreateName(sessionUser.getUserName());
        company.setCreateId(sessionUser.getUserId());
        if(company.getCompanyNatureId()!=null){
            CompanyNature companyNature = companyNatureService.selectByPrimaryKey(company.getCompanyNatureId());
            company.setCompanyNatureName(companyNature.getCompanyNatureName());
        }
        if(company.getCompanyTypeId()!=null) {
            CompanyType companyType = companyTypeService.selectByPrimaryKey(company.getCompanyTypeId());
            company.setCompanyTypeName(companyType.getCompanyTypeName());
        }

        if(company.getStreetId()!=null) {
            Street street = streetService.selectByPrimaryKey(company.getStreetId());
            company.setStreetName(street.getStreetName());
        }

        Company checkCompany = new Company();
        checkCompany.setCompanyCode(company.getCompanyCode());

        Company check = companyService.selectOne(checkCompany);

        Integer res = null;
        if (check != null) {
            company.setCompanyId(check.getCompanyId());
            res = companyService.updateByPrimaryKeySelective(company);
        } else {
            res = companyService.insertSelective(company);
        }
        /**
         * 政策添加
         */
        ArrayList<CompanyItem> companyItems = new ArrayList<>();
        for (String item : itemId) {
            CompanyItem companyItem = new CompanyItem();
            companyItem.setItemId((long) Integer.parseInt(item));
            companyItem.setCompanyId(company.getCompanyId());
            Item i = itemService.selectByPrimaryKey((long) Integer.parseInt(item));
            companyItem.setItemName(i.getItemName());
            companyItems.add(companyItem);
        }
        int num = companyItemService.insertList(companyItems);

        RSTFulBody rstFulBody = new RSTFulBody();
        if (res > 0) rstFulBody.success("添加成功！");
        else rstFulBody.fail("添加失败！");
        return rstFulBody;
    }

    @RequestMapping("edit.html")
    public String editUI(Model model, String companyId) {
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

        for (Item i : items) {
            i.setChecked(false);
            for (CompanyItem cui : companyItems) {
                if (i.getItemId() == cui.getItemId()) {
                    i.setChecked(true);
                    continue;
                }
            }
        }
        model.addAttribute("obj", company);
        model.addAttribute("items", items);
        return "/company/edit.html";
    }

    @ResponseBody
    @RequestMapping("edit")
    public RSTFulBody edit(Company company,
                           @RequestParam(required = false,value = "itemId[]") String[] itemId,
                           String password) {
        User sessionUser = getSessionUser();
        company.setUpdateId(sessionUser.getUserId());
        company.setUpdateName(sessionUser.getUserName());
        company.setUpdateTime(DateHelper.getCurrentDate());

        if(company.getCompanyNatureId()!=0 ){
            CompanyNature companyNature = companyNatureService.selectByPrimaryKey(company.getCompanyNatureId());
            company.setCompanyNatureName(companyNature.getCompanyNatureName());
        }
        if(company.getCompanyTypeId()!=0) {
            CompanyType companyType = companyTypeService.selectByPrimaryKey(company.getCompanyTypeId());
            company.setCompanyTypeName(companyType.getCompanyTypeName());
        }

        if(company.getStreetId()!=0) {
            Street street = streetService.selectByPrimaryKey(company.getStreetId());
            company.setStreetName(street.getStreetName());
        }

        int del = companyItemService.delByCompanyId(company.getCompanyId());
        /**
         * 政策编辑
         */
        ArrayList<CompanyItem> companyItems = new ArrayList<>();
        if(itemId!=null && itemId.length>0){
            for (String item : itemId) {
                CompanyItem companyItem = new CompanyItem();
                companyItem.setItemId((long) Integer.parseInt(item));
                companyItem.setCompanyId(company.getCompanyId());
                Item i = itemService.selectByPrimaryKey((long) Integer.parseInt(item));
                companyItem.setItemName(i.getItemName());
                companyItems.add(companyItem);
            }
            companyItemService.insertList(companyItems);
        }

        int res = companyService.updateByPrimaryKeySelective(company);

        if(password!=null && password!=""){
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

    @RequestMapping("del.html")
    public String delUser(Company company) {
        /*company.setDel(false);
        int res = companyService.updateByPrimaryKeySelective(company);*/
        Company c = companyService.selectByPrimaryKey(company.getCompanyId());
        userService.deleteByPrimaryKey(c.getUserId());
//        userService.d
        companyService.deleteByPrimaryKey(company.getCompanyId());
        return "redirect:/company/index.html";
    }

    @ResponseBody
    @RequestMapping("batch_del")
    public RSTFulBody batchDel(@RequestParam(required = true) String ids) {

        Map<String, Object> map = new HashMap<>();
        map.put("ids", ids);
        int res = companyService.batchDel(map);
        RSTFulBody rstFulBody = new RSTFulBody();
        if (res > 0) rstFulBody.success(res);
        else rstFulBody.fail("删除失败！");
        return rstFulBody;
    }

    @RequestMapping("/nature.html")
    public String type(Model model) {
        return "/company/nature.html";
    }

    @RequestMapping("/ajax_nature")
    public String ajaxType(Model model, int pageNum, int pageSize,
                           @RequestParam(required = false) String companyNatureName
    ) {
        //组装搜索条件
      /*  Map<String,Object> map=new HashMap<>();
        if(userTrueName!=null && userTrueName!="") map.put("userTrueName",userTrueName);
        if(examId!=null && examId!="") map.put("examId",examId);
        if(userSex!=null && userSex!="") map.put("userSex",userSex);*/
        CompanyNature companyNature = new CompanyNature();
        companyNature.setDel(true);
        if (companyNatureName != null && companyNatureName != "") companyNature.setCompanyNatureName(companyNatureName);

        //分页查询
        PageHelper.startPage(pageNum, pageSize);

        List<CompanyNature> companyNatures = companyNatureService.select(companyNature);

        PageInfo<CompanyNature> pageInfo = new PageInfo<>(companyNatures);
        String pageStr = makePageHtml(pageInfo);
        model.addAttribute("page_info", pageInfo);
        model.addAttribute("pages", pageStr);
        return "/company/ajax_nature.html";
    }

    @RequestMapping("add_nature.html")
    public String addNature(Model model) {

        return "/company/add_nature.html";
    }

    @ResponseBody
    @RequestMapping("add_nature")
    public RSTFulBody addNature(CompanyNature companyNature) {
        User sessionUser = getSessionUser();
        companyNature.setCreateName(sessionUser.getUserName());
        companyNature.setCreateId(sessionUser.getUserId());

        int res = companyNatureService.insertSelective(companyNature);
        RSTFulBody rstFulBody = new RSTFulBody();
        if (res > 0) rstFulBody.success("添加成功！");
        else rstFulBody.fail("添加失败！");
        return rstFulBody;
    }

    @RequestMapping("edit_nature.html")
    public String editNature(Model model, String companyNatureId) {
        CompanyNature companyNature = companyNatureService.selectByPrimaryKey((long) Integer.parseInt(companyNatureId));
        model.addAttribute("obj", companyNature);
        return "/company/edit_nature.html";
    }

    @ResponseBody
    @RequestMapping("edit_nature")
    public RSTFulBody editNature(CompanyNature companyNature) {
        User sessionUser = getSessionUser();
        companyNature.setUpdateId(sessionUser.getUserId());
        companyNature.setUpdateName(sessionUser.getUserName());
        companyNature.setUpdateTime(DateHelper.getCurrentDate());
        int res = companyNatureService.updateByPrimaryKeySelective(companyNature);
        RSTFulBody rstFulBody = new RSTFulBody();
        if (res > 0) rstFulBody.success("修改成功！");
        else rstFulBody.fail("修改失败！");
        return rstFulBody;
    }

    @RequestMapping("del_nature.html")
    public String delUser(CompanyNature companyNature) {
        companyNature.setDel(false);
        int res = companyNatureService.updateByPrimaryKeySelective(companyNature);
        return "redirect:/company/nature.html";
    }

    @RequestMapping("/type.html")
    public String nature(Model model) {
        return "/company/type.html";
    }

    @RequestMapping("/ajax_type")
    public String ajaxNature(Model model, int pageNum, int pageSize,
                             @RequestParam(required = false) String companyTypeName
    ) {
        //组装搜索条件
      /*  Map<String,Object> map=new HashMap<>();
        if(userTrueName!=null && userTrueName!="") map.put("userTrueName",userTrueName);
        if(examId!=null && examId!="") map.put("examId",examId);
        if(userSex!=null && userSex!="") map.put("userSex",userSex);*/
        CompanyType companyType = new CompanyType();
        companyType.setDel(true);
        if (companyTypeName != null && companyTypeName != "") companyType.setCompanyTypeName(companyTypeName);

        //分页查询
        PageHelper.startPage(pageNum, pageSize);

        List<CompanyType> companyTypes = companyTypeService.select(companyType);

        PageInfo<CompanyType> pageInfo = new PageInfo<>(companyTypes);
        String pageStr = makePageHtml(pageInfo);
        model.addAttribute("page_info", pageInfo);
        model.addAttribute("pages", pageStr);
        return "/company/ajax_type.html";
    }

    @RequestMapping("add_type.html")
    public String addType(Model model) {

        return "/company/add_type.html";
    }

    @ResponseBody
    @RequestMapping("add_type")
    public RSTFulBody addType(CompanyType companyType) {
        User sessionUser = getSessionUser();
        companyType.setCreateName(sessionUser.getUserName());
        companyType.setCreateId(sessionUser.getUserId());

        int res = companyTypeService.insertSelective(companyType);
        RSTFulBody rstFulBody = new RSTFulBody();
        if (res > 0) rstFulBody.success("添加成功！");
        else rstFulBody.fail("添加失败！");
        return rstFulBody;
    }

    @RequestMapping("edit_type.html")
    public String editType(Model model, String companyTypeId) {
        CompanyType companyType = companyTypeService.selectByPrimaryKey((long) Integer.parseInt(companyTypeId));
        model.addAttribute("obj", companyType);
        return "/company/edit_type.html";
    }

    @ResponseBody
    @RequestMapping("edit_type")
    public RSTFulBody editType(CompanyType companyType) {
        User sessionUser = getSessionUser();
        companyType.setUpdateId(sessionUser.getUserId());
        companyType.setUpdateName(sessionUser.getUserName());
        companyType.setUpdateTime(DateHelper.getCurrentDate());
        int res = companyTypeService.updateByPrimaryKeySelective(companyType);
        RSTFulBody rstFulBody = new RSTFulBody();
        if (res > 0) rstFulBody.success("修改成功！");
        else rstFulBody.fail("修改失败！");
        return rstFulBody;
    }

    @RequestMapping("del_type.html")
    public String delUser(CompanyType companyType) {
        companyType.setDel(false);
        int res = companyTypeService.updateByPrimaryKeySelective(companyType);
        return "redirect:/company/type.html";
    }

    @RequestMapping("register.html")
    public String register(Model model) {
        List<Street> streets = streetService.selectAll();
        List<CompanyNature> companyNatures = companyNatureService.selectAll();
        List<CompanyType> companyTypes = companyTypeService.selectAll();
        model.addAttribute("companyTypes", companyTypes);
        model.addAttribute("companyNatures", companyNatures);
        model.addAttribute("streets", streets);
        model.addAttribute("user", "");
        model.addAttribute("userName", "");
        model.addAttribute("password", "");
        model.addAttribute("companyCode", "");
        model.addAttribute("uError", "");
        model.addAttribute("cError", "");
        model.addAttribute("companyName", "");
        return "/register.html";
    }

    @RequestMapping("reg")
    public String reg(String userName, String password, Company company, Model model) {
        User user = new User();
        user.setUserName(userName);
        User u = userService.selectOne(user);

        Company cCode = new Company();
        cCode.setCompanyCode(company.getCompanyCode());
        cCode.setState(2);
        Company checkCode = companyService.selectOne(cCode);

        String uError = u==null ? "":"该用户名已存在";
        String cError = checkCode==null ? "":"该信誉代码已审核，请勿重复注册";

        if(uError!="" || cError!=""){
            List<Street> streets = streetService.selectAll();
            model.addAttribute("streets", streets);
            model.addAttribute("uError", uError);
            model.addAttribute("cError", cError);
            model.addAttribute("userName", userName);
            model.addAttribute("company", company);
            model.addAttribute("password", password);
            model.addAttribute("companyCode", "");
            model.addAttribute("companyName", "");
            List<CompanyNature> companyNatures = companyNatureService.selectAll();
            List<CompanyType> companyTypes = companyTypeService.selectAll();
            model.addAttribute("companyTypes", companyTypes);
            model.addAttribute("companyNatures", companyNatures);
            return "/register.html";
        }

        /*if (u != null) {

        }

        if (checkCode != null) {
            List<Street> streets = streetService.selectAll();
            model.addAttribute("streets", streets);
            model.addAttribute("cError", "该信誉代码已审核，请勿重复注册");
            model.addAttribute("userName", userName);
            model.addAttribute("companyName", companyName);
            model.addAttribute("streetId", streetId);
            model.addAttribute("password", password);
            model.addAttribute("companyCode", companyCode);
            return "/register.html";
        }*/


        Company cCompany = new Company();
        cCompany.setCompanyCode(company.getCompanyCode());
        Company c = companyService.selectOne(cCompany);
        User uu = new User();
        uu.setUserName(userName);
        uu.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
        uu.setUserNature(1);
        int res = userService.insertSelective(uu);

        CompanyNature companyNature = companyNatureService.selectByPrimaryKey(company.getCompanyNatureId());
        company.setCompanyNatureName(companyNature.getCompanyNatureName());
        CompanyType companyType = companyTypeService.selectByPrimaryKey(company.getCompanyTypeId());
        company.setCompanyTypeName(companyType.getCompanyTypeName());
        Street street = streetService.selectByPrimaryKey(company.getStreetId());
        company.setStreetName(street.getStreetName());

        company.setUserId(uu.getUserId());
        if (c != null) {
            company.setCompanyId(c.getCompanyId());
            company.setState(1);
            companyService.updateByPrimaryKeySelective(company);
        } else {
            company.setState(1);
            companyService.insertSelective(company);
        }
        return "/reg_success.html";
}

    @RequestMapping("check_item.html")
    public String checkItem(Model model) {
        List<Item> items = itemService.selectAll();
        model.addAttribute("items", items);
        return "/company/check_item.html";
    }

    @RequestMapping("check_item")
    public String ajaxCheckItem(Model model,
                                @RequestParam(required = false) Long itemId,
                                @RequestParam(required = false) Integer companyChecked) {

        User s = getSessionUser();
        Map<String, Object> map = new HashMap<>();
        map.put("companyChecked", 3);
        map.put("companyId", getSessionUser().getUserId());
        if(itemId!=null) map.put("itemId",itemId);
        if(companyChecked!=null) map.put("companyChecked",companyChecked);

        List<Map<String, Object>> maps = companyService.selectCompanyCheckedItem(map);

        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(maps);
        String pageStr = makePageHtml(pageInfo);
        model.addAttribute("page_info", pageInfo);
        model.addAttribute("pages", pageStr);
        return "/company/ajax_items.html";
    }

    @RequestMapping("check_info.html")
    public String checkInfo(Long companyUserItemId, Model model) {
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
        List<CompanyUserFamily> cuf = companyUserFamilyService.select(companyUserFamily);

//        ItemConfig itemConfig = item
        ItemConfig itemConfig = new ItemConfig();
        itemConfig.setItemConfigState(true);
        itemConfig.setItemId(companyUserItem.getItemId());
        ItemConfig ic = itemConfigService.selectOne(itemConfig);
//        TypeCategory typeCategory = typeCategoryService.selectByPrimaryKey(ic.getTypeCategoryId());
        List<ItemTalentContent> itemTalentContents = null;
        if (ic.getItemConfigTType()!=null && ic.getItemConfigTType()) {
            ItemTalentContent itemTalentContent = new ItemTalentContent();
            itemTalentContent.setItemConfigId(ic.getItemConfigId());
//            talentTypes = talentTypeService.select(talentType);
            itemTalentContents = itemTalentContentService.select(itemTalentContent);
        }

        List<TalentType> talentTypes = null;


        TypeCategory typeCategory = typeCategoryService.selectByPrimaryKey(ic.getTypeCategoryId());

        InfoChange infoChange = new InfoChange();
        infoChange.setUserId(user.getUserId());
        List<InfoChange> infoChanges = infoChangeService.select(infoChange);
        Map<String, Object> ics = new HashMap<>();
        for (InfoChange change : infoChanges) {
            ics.put(change.getFiledName(), 1);
        }

        Item item = itemService.selectByPrimaryKey(companyUserItem.getItemId());
        List<CompanyUserItem> childUserItem = null;
        if (item.getItemCategory() == 1) {
            CompanyUserItem ccui = new CompanyUserItem();
            ccui.setParentId(companyUserItem.getCompanyUserItemId());
            childUserItem = companyUserItemService.select(ccui);
        }

        ItemUserTime itemUserTime = new ItemUserTime();
        itemUserTime.setUserId(user.getUserId());
        itemUserTime.setCompanyUserItemId(companyUserItem.getCompanyUserItemId());

        Item ii = new Item();
        ii.setItemCategory(0);
        List<Item> items = itemService.select(ii);

        List<ItemCertificate> itemCertificates = itemCertificateService.selectByItemId(companyUserItem.getConfigId());
        List<CompanyUserCertificate> companyUserCertificates=null;
        if(itemCertificates!=null){
            String ids = "";
            for (ItemCertificate itemCertificate : itemCertificates) {
                ids+=itemCertificate.getCertificateId()+",";
            }
            ids = ids.substring(0,ids.length() - 1);
            Map<String,Object> cerficateMap = new HashMap<>();
            cerficateMap.put("ids",ids);
            cerficateMap.put("userId",companyUserItem.getUserId());

            companyUserCertificates = companyUserCertificateService.selectByUserId(cerficateMap);
        }

        if(companyUserItem.getItemId()==3){
            BigDecimal amount = null;
            BigDecimal amountTemp = null;
            BigDecimal compareNum = new BigDecimal(300000);
            if(cui.getMonthSalary().compareTo(compareNum)==0) {
                amount = new BigDecimal(5000);
            }else {
               amountTemp = cui.getTotalTax().multiply(ic.getItemConfigAmountPer());
               BigDecimal compareNum1000 = new BigDecimal(1000);
               if(amountTemp.compareTo(compareNum1000)==1){
                    BigDecimal amountTemp1 = amountTemp.divide(compareNum1000);
                    BigDecimal amountTemp2 = amountTemp1.setScale(0,BigDecimal.ROUND_UP);
                    amount = amountTemp2.multiply(compareNum1000);
               }
            }
            model.addAttribute("amount", amount);
            model.addAttribute("amountTemp", amountTemp);
        }
        model.addAttribute("cucs", companyUserCertificates);
        model.addAttribute("user", user);
        model.addAttribute("cui", cui);
        model.addAttribute("company", c);
        model.addAttribute("cufs", cuf);
        model.addAttribute("ic", ic);
        model.addAttribute("itcs", itemTalentContents);
        model.addAttribute("items", items);
        model.addAttribute("tc", typeCategory);
        model.addAttribute("ics", ics);
        model.addAttribute("item", item);
        model.addAttribute("companyUserItem", companyUserItem);
        model.addAttribute("childUserItems", childUserItem);
        return "/company/check_info.html";
    }

    @RequestMapping("/modal_content")
    public String modalContent(Model model, Long userId, Long certificateId) {
        CompanyUserCertificate companyUserCertificate = new CompanyUserCertificate();
        companyUserCertificate.setUserId(userId);
        companyUserCertificate.setCertificateId(certificateId);
        List<CompanyUserCertificate> companyUserCertificates = companyUserCertificateService.select(companyUserCertificate);
        model.addAttribute("cucs", companyUserCertificates);
        return "/company/modal_content.html";
    }

    @ResponseBody
    @RequestMapping("/if_show")
    public Boolean ifShow(Long userId, Long certificateId) {
        Boolean res = false;
        CompanyUserCertificate companyUserCertificate = new CompanyUserCertificate();
        companyUserCertificate.setUserId(userId);
        companyUserCertificate.setCertificateId(certificateId);
        List<CompanyUserCertificate> companyUserCertificates = companyUserCertificateService.select(companyUserCertificate);
        if (companyUserCertificates.size() > 0) res = true;
        return res;
    }

    @ResponseBody
    @RequestMapping("/pass")
    public Boolean pass(@RequestBody JSONObject jsonParam) {

        PassJson passJson = JSON.parseObject(jsonParam.toJSONString(), new TypeReference<PassJson>() {
        });
        CompanyUserItem cui = companyUserItemService.selectByPrimaryKey(passJson.getUserItemId());
        CompanyUserItem companyUserItem = new CompanyUserItem();
        companyUserItem.setCompanyUserItemId(passJson.getUserItemId());
        companyUserItem.setType(passJson.getType());
        companyUserItem.setAmount(passJson.getAmount());
        companyUserItem.setMemo(passJson.getMemo());
        if(passJson.getTalentType()!=null){
            companyUserItem.setTalentType((long)Integer.parseInt(passJson.getTalentType()));
            companyUserItem.setTalentTypeContent(passJson.getTalentTypeContent());
        }
        companyUserItem.setCompanyChecked(2);

        if(passJson.getItemTime().length()>0){
            String[] houseContractTimes = passJson.getItemTime().split("至");
            companyUserItem.setStart(getDate4StrDate(houseContractTimes[0].trim(), "yyyy-MM-dd"));
            companyUserItem.setEnd(getDate4StrDate(houseContractTimes[1].trim(), "yyyy-MM-dd"));
        }

        companyUserItemService.updateUserItem(companyUserItem);
        Map<String,Object> map = new HashMap<>();
        map.put("userId",cui.getUserId());
        map.put("companyUserItmeId",cui.getCompanyUserItemId());
        map.put("amount",passJson.getTalentTypeContent());
        sendLogService.updateAmount(map);
        companyUserItemService.delByParentId(passJson.getUserItemId());
        /*if (passJson.getItems() != null) {
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
                if (itemJson.getTypeContent() != null) c.setTalentTypeContent(itemJson.getTypeContent());
                companyUserItems.add(c);
            }
            companyUserItemService.insertList(companyUserItems);
        }*/

        return true;
    }

    @ResponseBody
    @RequestMapping("/back_npass_pass")
    public Boolean backNPassPass(Integer state, Long userItemId, String reason) {
        CompanyUserItem companyUserItem = new CompanyUserItem();
        companyUserItem.setCompanyUserItemId(userItemId);
        companyUserItem.setCompanyChecked(state);
        companyUserItem.setCompanyReason(reason);
        companyUserItemService.updateByPrimaryKeySelective(companyUserItem);
        return true;
    }


    @RequestMapping("/census.html")
    public String census(Long itemId,Model model){
        User sessionUser = getSessionUser();
        Company condition = new Company();
        condition.setUserId(sessionUser.getUserId());
        Company company = companyService.selectOne(condition);

        CompanyItem item = new CompanyItem();
        item.setCompanyId(company.getCompanyId());

        List<CompanyItem> items = companyItemService.select(item);

        model.addAttribute("items",items);
        return "/company/census.html";
    }

    @RequestMapping("ajax_census")
    public String ajaxCensus(Model model, int pageNum, int pageSize, @RequestParam(required = false) Long itemId){

        String url = "/company/ajax_census.html";

        User sessionUser = getSessionUser();
        Map<String,Object> map = new HashMap<>();
        String title=null;
        if(itemId!=null && itemId!=0) {
            map.put("itemId",itemId);
            Item item = itemService.selectByPrimaryKey(itemId);
            title = item.getItemName();
            if(itemId==3){
                url = "/company/ajax_jintie.html";

            }else if(itemId==4){
                url = "/company/ajax_lanka.html";
            }
        }
        map.put("streetId",sessionUser.getStreetId());

        PageHelper.startPage(pageNum, pageSize);
        List<Map<String,Object>> census=streetService.census(map);
        if(itemId==3) {
            for (Map<String, Object> obj : census) {
                BigDecimal amountTemp = null;
                amountTemp = ((BigDecimal)obj.get("total_tax")).multiply((BigDecimal)obj.get("item_config_amount_per"));
                obj.put("amountTemp",amountTemp);
            }
        }

        PageInfo<Map<String,Object>> pageInfo= new PageInfo<>(census);
        String pageStr = makePageHtml(pageInfo);

        model.addAttribute("page_info",pageInfo);
        model.addAttribute("pages",pageStr);
        model.addAttribute("title",title);
        return url;
    }
}
