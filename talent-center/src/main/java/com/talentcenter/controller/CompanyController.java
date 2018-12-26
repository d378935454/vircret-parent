package com.talentcenter.controller;

import RSTFul.RSTFulBody;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.talentcenter.entity.*;
import com.talentcenter.service.*;
import org.springframework.util.DigestUtils;
import util.DateHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/company")
public class CompanyController extends BaseController{
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
   /* @Autowired
    private RoleCompanyService roleCompanyService;*/

    /**
     * 企业列表页
     * @param model
     * @return
     */
    @RequestMapping("/index.html")
    public String index(Model model){
        return "/company/index.html";
    }

    @RequestMapping("/ajax_index")
    public String ajaxIndex(Model model, int pageNum, int pageSize,
                            @RequestParam(required = false) String companyName
    ){
        //组装搜索条件
        /*Map<String,Object> map=new HashMap<>();
        if(userTrueName!=null && userTrueName!="") map.put("userTrueName",userTrueName);
        if(examId!=null && examId!="") map.put("examId",examId);
        if(userSex!=null && userSex!="") map.put("userSex",userSex);*/
        Company company = new Company();
        company.setDel(true);
        if(companyName!=null && companyName!="") company.setCompanyName(companyName);

        //分页查询
        PageHelper.startPage(pageNum, pageSize);
        List<Company> companys = companyService.select(company);

        PageInfo<Company> pageInfo= new PageInfo<>(companys);
        String pageStr = makePageHtml(pageInfo);
        model.addAttribute("page_info",pageInfo);
        model.addAttribute("pages",pageStr);
        return "/company/ajax_index.html";
    }

    @RequestMapping("add.html")
    public String addUI(Model model) {
        List<CompanyNature> companyNatures = companyNatureService.selectAll();
        model.addAttribute("companyNatures",companyNatures);
        List<CompanyType> companyTypes = companyTypeService.selectAll();
        model.addAttribute("companyTypes",companyTypes);
        List<Street> streets = streetService.selectAll();
        model.addAttribute("streets",streets);
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
        CompanyNature companyNature = companyNatureService.selectByPrimaryKey(company.getCompanyNatureId());
        company.setCompanyNatureName(companyNature.getCompanyNatureName());
        CompanyType companyType = companyTypeService.selectByPrimaryKey(company.getCompanyTypeId());
        company.setCompanyTypeName(companyType.getCompanyTypeName());
        Street street = streetService.selectByPrimaryKey(company.getStreetId());
        company.setStreetName(street.getStreetName());
        int res = companyService.insertSelective(company);
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
        model.addAttribute("companyId",companyId);
        model.addAttribute("obj",company);
        List<CompanyNature> companyNatures = companyNatureService.selectAll();
        model.addAttribute("companyNatures",companyNatures);
        List<CompanyType> companyTypes = companyTypeService.selectAll();
        model.addAttribute("companyTypes",companyTypes);
        List<Street> streets = streetService.selectAll();
        model.addAttribute("streets",streets);

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
    public RSTFulBody edit(Company company,@RequestParam(value = "itemId[]") String[] itemId) {
        User sessionUser = getSessionUser();
        company.setUpdateId(sessionUser.getUserId());
        company.setUpdateName(sessionUser.getUserName());
        company.setUpdateTime(DateHelper.getCurrentDate());

        CompanyNature companyNature = companyNatureService.selectByPrimaryKey(company.getCompanyNatureId());
        company.setCompanyNatureName(companyNature.getCompanyNatureName());
        CompanyType companyType = companyTypeService.selectByPrimaryKey(company.getCompanyTypeId());
        company.setCompanyTypeName(companyType.getCompanyTypeName());
        Street street = streetService.selectByPrimaryKey(company.getStreetId());
        company.setStreetName(street.getStreetName());

        int del  = companyItemService.delByCompanyId(company.getCompanyId());
        /**
         * 政策编辑
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
        companyItemService.insertList(companyItems);
        int res = companyService.updateByPrimaryKeySelective(company);
        RSTFulBody rstFulBody = new RSTFulBody();
        if (res > 0) rstFulBody.success("修改成功！");
        else rstFulBody.fail("修改失败！");
        return rstFulBody;
    }

    @RequestMapping("del.html")
    public String delUser(Company company){
        company.setDel(false);
        int res = companyService.updateByPrimaryKeySelective(company);
        return "redirect:/company/index.html";
    }

    @ResponseBody
    @RequestMapping("batch_del")
    public RSTFulBody batchDel(@RequestParam(required = true) String ids){

        Map<String, Object> map = new HashMap<>();
        map.put("ids",ids);
        int res = companyService.batchDel(map);
        RSTFulBody rstFulBody=new RSTFulBody();
        if(res>0) rstFulBody.success(res);
        else  rstFulBody.fail("删除失败！");
        return rstFulBody;
    }
    @RequestMapping("/nature.html")
    public String type(Model model)
    {
        return "/company/nature.html";
    }

   @RequestMapping("/ajax_nature")
    public String ajaxType(Model model, int pageNum, int pageSize,
                            @RequestParam(required = false) String companyNatureName
    ){
        //组装搜索条件
      /*  Map<String,Object> map=new HashMap<>();
        if(userTrueName!=null && userTrueName!="") map.put("userTrueName",userTrueName);
        if(examId!=null && examId!="") map.put("examId",examId);
        if(userSex!=null && userSex!="") map.put("userSex",userSex);*/
       CompanyNature companyNature = new CompanyNature();
       companyNature.setDel(true);
        if(companyNatureName!=null && companyNatureName!="") companyNature.setCompanyNatureName(companyNatureName);

        //分页查询
        PageHelper.startPage(pageNum, pageSize);

        List<CompanyNature> companyNatures = companyNatureService.select(companyNature);

        PageInfo<CompanyNature> pageInfo= new PageInfo<>(companyNatures);
        String pageStr = makePageHtml(pageInfo);
        model.addAttribute("page_info",pageInfo);
        model.addAttribute("pages",pageStr);
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
        model.addAttribute("obj",companyNature);
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
    public String delUser(CompanyNature companyNature){
        companyNature.setDel(false);
        int res = companyNatureService.updateByPrimaryKeySelective(companyNature);
        return "redirect:/company/nature.html";
    }
    @RequestMapping("/type.html")
    public String nature(Model model)
    {
        return "/company/type.html";
    }

    @RequestMapping("/ajax_type")
    public String ajaxNature(Model model, int pageNum, int pageSize,
                             @RequestParam(required = false) String companyTypeName
    ){
        //组装搜索条件
      /*  Map<String,Object> map=new HashMap<>();
        if(userTrueName!=null && userTrueName!="") map.put("userTrueName",userTrueName);
        if(examId!=null && examId!="") map.put("examId",examId);
        if(userSex!=null && userSex!="") map.put("userSex",userSex);*/
        CompanyType companyType = new CompanyType();
        companyType.setDel(true);
        if(companyTypeName!=null && companyTypeName!="") companyType.setCompanyTypeName(companyTypeName);

        //分页查询
        PageHelper.startPage(pageNum, pageSize);

        List<CompanyType> companyTypes = companyTypeService.select(companyType);

        PageInfo<CompanyType> pageInfo= new PageInfo<>(companyTypes);
        String pageStr = makePageHtml(pageInfo);
        model.addAttribute("page_info",pageInfo);
        model.addAttribute("pages",pageStr);
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
        model.addAttribute("obj",companyType);
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
    public String delUser(CompanyType companyType){
        companyType.setDel(false);
        int res = companyTypeService.updateByPrimaryKeySelective(companyType);
        return "redirect:/company/type.html";
    }



    @RequestMapping("register.html")
    public String register(Model model){
        model.addAttribute("user","");
        model.addAttribute("userName","");
        model.addAttribute("password","");
        model.addAttribute("companyCode","");
        return "/register.html";
    }

    @RequestMapping("reg")
    public String reg(String userName,String password,String companyCode,Model model){
        User user = new User();
        user.setUserName(userName);
        User u = userService.selectOne(user);
        if(u!=null){
            model.addAttribute("user","用户名已存在");
            model.addAttribute("userName",userName);
            model.addAttribute("password",password);
            model.addAttribute("companyCode",companyCode);
            return "/register.html";
        }else {
            Company company = new Company();
            company.setCompanyCode(companyCode);
            Company c = companyService.selectOne(company);
            User uu = new User();
            uu.setUserName(userName);
            uu.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
            uu.setUserNature(1);
            int res = userService.insertSelective(uu);
            Company cc = new Company();
            if(c!=null){
                cc.setCompanyId(c.getCompanyId());
                cc.setUserId(uu.getUserId());
                companyService.updateByPrimaryKeySelective(cc);
            }else{
                cc.setUserId(uu.getUserId());
                companyService.insertSelective(cc);
            }
            return "/login.html";
        }
    }

    @RequestMapping("check_item.html")
    public String checkItem(Model model){
        List<Item> items = itemService.selectAll();
        model.addAttribute("items",items);
        return "/company/check_item.html";
    }

    @RequestMapping("check_item")
    public String ajaxCheckItem(Model model,
                                @RequestParam(required = false) Long itemId,
                                @RequestParam(required = false) Integer companyChecked){

        Map<String,Object> map = new HashMap<>();
        map.put("companyChecked",3);
        map.put("companyId",23);

        List<Map<String,Object>> maps = companyService.selectCompanyCheckedItem(map);

        PageInfo<Map<String,Object>> pageInfo= new PageInfo<>(maps);
        String pageStr = makePageHtml(pageInfo);
        model.addAttribute("page_info",pageInfo);
        model.addAttribute("pages",pageStr);
        return "/company/ajax_items.html";
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
        ItemTalentContent itemTalentContent = new ItemTalentContent();
        itemTalentContent.setItemConfigId(ic.getItemConfigId());
        List<ItemTalentContent> itemTalentContents = itemTalentContentService.select(itemTalentContent);

        TypeCategory typeCategory = typeCategoryService.selectByPrimaryKey(ic.getTypeCategoryId());

        InfoChange infoChange = new InfoChange();
        infoChange.setUserId(user.getUserId());
        List<InfoChange> infoChanges = infoChangeService.select(infoChange);
        Map<String,Object> ics = new HashMap<>();
        for (InfoChange change : infoChanges) {
            ics.put(change.getFiledName(),1);
        }
//        itemConfigService.s
        model.addAttribute("user",user);
        model.addAttribute("cui",cui);
        model.addAttribute("company",c);
        model.addAttribute("cufs",cuf);
        model.addAttribute("ic",ic);
        model.addAttribute("itcs",itemTalentContents);
        model.addAttribute("tc",typeCategory);
        model.addAttribute("ics",ics);
        return "/company/check_info.html";
    }
}
