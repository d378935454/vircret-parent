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

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static util.DateHelper.*;
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

    @Autowired
    private ItemTeamContentService itemTeamContentService;

    @Autowired
    private CertificateService certificateService;

    @Autowired
    private CompanyUserCertificateService companyUserCertificateService;

    @Autowired
    private InfoChangeService infoChangeService;

    @Autowired
    private TalentTypeService talentTypeService;

    @Autowired
    private ItemTalentContentService itemTalentContentService;

    private final Map<String,String> infoMap = new HashMap<String,String>(){{
        put("1", "company_user_card");
        put("3", "company_user_school");
        put("5", "company_user_hu_address");
        put("3", "company_user_card");
        put("2","company_user_home_address");
        put("4","company_user_contract_time_begin");
        put("6","company_user_school");
        put("7","company_user_positional");
    }};

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
            ItemConfig itemConfig = new ItemConfig();
            itemConfig.setItemId(i.getItemId());
            itemConfig.setItemConfigState(true);
            ItemConfig ic = itemConfigService.selectOne(itemConfig);
            if(ic.getItemConfigCompanyCheck()!=null) companyUserItem.setCompanyChecked(3);
            if(ic.getItemConfigStreetCheck()!=null) companyUserItem.setStreetChecked(3);
            if(ic.getItemConfigCenterCheck()!=null) companyUserItem.setCenterChecked(3);
            companyUserItem.setParentId((long)0);
            companyUserItem.setItemName(i.getItemName());
            companyUserItem.setConfigId(ic.getItemConfigId());
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
        user.setUpdateTime(getCurrentDate());
        if (user.getPassword() != null && user.getPassword()!="") user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        else user.setPassword(null);
        int res = userService.updateByPrimaryKeySelective(user);

        int del  = companyUserItemService.delByUserId(user.getUserId());

        ArrayList<CompanyUserItem> companyUserItems = new ArrayList<>();
        for (String item : itemId) {
            CompanyUserItem companyUserItem = new CompanyUserItem();
            companyUserItem.setItemId((long) Integer.parseInt(item));
            companyUserItem.setUserId(user.getUserId());
            Item i = itemService.selectByPrimaryKey((long) Integer.parseInt(item));
            ItemConfig itemConfig = new ItemConfig();
            itemConfig.setItemId(i.getItemId());
            itemConfig.setItemConfigState(true);
            ItemConfig ic = itemConfigService.selectOne(itemConfig);
            if(ic.getItemConfigCompanyCheck()!=null) companyUserItem.setCompanyChecked(3);
            if(ic.getItemConfigStreetCheck()!=null) companyUserItem.setStreetChecked(3);
            if(ic.getItemConfigCenterCheck()!=null) companyUserItem.setCenterChecked(3);
            companyUserItem.setParentId((long)0);
            companyUserItem.setItemName(i.getItemName());
            companyUserItem.setConfigId(ic.getItemConfigId());
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
                                 @RequestParam(required = false) String userHouseContractTime,
                                 @RequestParam(required = false) String userSocietySaveTime1,
                                 @RequestParam(required = false) String userSocietySaveTime2,
                                 @RequestParam(required = false, value = "companyUserFamilyType[]") String[] companyUserFamilyType,
                                 @RequestParam(required = false, value = "companyUserFamilyName[]") String[] companyUserFamilyName,
                                 @RequestParam(required = false, value = "companyUserFamilyCard[]") String[] companyUserFamilyCard,
                                 @RequestParam(required = false, value = "companyUserFamilySex[]") String[] companyUserFamilySex
                                 ) {
        String[] contractTimes = userContractTime.split("至");
        companyUserInfo.setCompanyUserContractTimeBegin(getDate4StrDate(contractTimes[0].trim(), "yyyy-MM-dd"));
        companyUserInfo.setCompanyUserContractTimeEnd(getDate4StrDate(contractTimes[1].trim(), "yyyy-MM-dd"));
        companyUserInfo.setUpdateTimes(companyUserInfo.getUpdateTimes()+1);

        if(userHouseContractTime!= null){
            String[] houseContractTimes = userHouseContractTime.split("至");
            companyUserInfo.setCompanyUserHouseContractTimeBegin(getDate4StrDate(houseContractTimes[0].trim(), "yyyy-MM-dd"));
            companyUserInfo.setCompanyUserHouseContractTimeEnd(getDate4StrDate(houseContractTimes[1].trim(), "yyyy-MM-dd"));
        }

        if(userSocietySaveTime1!=null){
            String[] userSocietySaveTime1s = userSocietySaveTime1.split("至");
            companyUserInfo.setCompanyUserSocietySaveTime1Begin(getDate4StrDate(userSocietySaveTime1s[0].trim(), "yyyy-MM-dd"));
            companyUserInfo.setCompanyUserSocietySaveTime1End(getDate4StrDate(userSocietySaveTime1s[1].trim(), "yyyy-MM-dd"));
        }

        if(userSocietySaveTime2!=null){
            String[] userSocietySaveTime2s = userSocietySaveTime2.split("至");
            companyUserInfo.setCompanyUserSocietySaveTime2Begin(getDate4StrDate(userSocietySaveTime2s[0].trim(), "yyyy-MM-dd"));
            companyUserInfo.setCompanyUserSocietySaveTime2End(getDate4StrDate(userSocietySaveTime2s[1].trim(), "yyyy-MM-dd"));
        }

        int res= companyUserInfoService.updateByUserId(companyUserInfo);

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
    public String items() {
        return "/company_user/items.html";
    }

    @RequestMapping("/ajax_items")
    public String ajaxItems(Model model, int pageNum, int pageSize) {

        PageHelper.startPage(pageNum, pageSize);
        List<Item> items = itemService.selectByUserId(getSessionUser().getUserId());

//        List<Item> items = itemService.select();
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

        Boolean ifShowSubmitButton=ifHaveOneSubmit(itemId,sessionUser.getUserId());

        List<ItemTeamContent> userItemTeam = new ArrayList<>();
        List<ItemTeamContent> itc = itemTeamContentService.selectTeam(itemId);
        CompanyUserItem companyUserItem = new CompanyUserItem();
        companyUserItem.setUserId(sessionUser.getUserId());
        List<CompanyUserItem> companyUserItems = companyUserItemService.select(companyUserItem);
        for (ItemTeamContent ii:itc) {
            for (CompanyUserItem cc: companyUserItems){
                if(ii.getItemId() == cc.getItemId() && ii.getItemId()!=itemId){
                    userItemTeam.add(ii);
                    continue;
                }
            }
        }

//        ItemConfig itemConfig = itemConfigService.
//        itemConfigService.se
        model.addAttribute("item",item);
        model.addAttribute("ifShowSubmitButton",ifShowSubmitButton);
        model.addAttribute("itemConfig",ic);
        model.addAttribute("userItemTeam",userItemTeam);
        return "/company_user/item_info.html";
    }

    @RequestMapping("/ask_for.html")
    public String askForUI(Model model,Long itemId){

        String url = "/company_user/ask_for.html";
        //判断是否有资格申请该补助
        User sessionUser = getSessionUser();
        if(!ifPermit(sessionUser.getUserId(),itemId) || !ifHaveOneSubmit(itemId,sessionUser.getUserId())){
            return "redirect:/company_user/items.html";
        }
        ItemConfig itemConfig = new ItemConfig();
        itemConfig.setItemId(itemId);
        itemConfig.setItemConfigState(true);
        ItemConfig ic = itemConfigService.selectOne(itemConfig);

        ItemCertificate itemCertificate = new ItemCertificate();
        itemCertificate.setItemConfigId(ic.getItemConfigId());
        List<ItemCertificate> itemCertificates = itemCertificateService.select(itemCertificate);

        List<ItemTalentContent> itemTalentContents = null;
        if(ic.getItemConfigTType()){
            ItemTalentContent itemTalentContent = new ItemTalentContent();
            itemTalentContent.setItemConfigId(ic.getItemConfigId());
//            talentTypes = talentTypeService.select(talentType);
            itemTalentContents = itemTalentContentService.select(itemTalentContent);
        }

        for (ItemCertificate ii : itemCertificates) {
            CompanyUserCertificate companyUserCertificate = new CompanyUserCertificate();
            companyUserCertificate.setUserId(sessionUser.getUserId());
            companyUserCertificate.setCertificateId(ii.getCertificateId());
            List<CompanyUserCertificate> cuc = companyUserCertificateService.select(companyUserCertificate);
            if(cuc!=null) ii.setCompanyUserCertificates(cuc);
        }

        int monthes = 0;
        if(ic.getItemConfigContactTime()!=2){
            int year = getCurrentYear();
            Date lastDay = getYearLast(year);
            Date currentDate = getCurrentDate();
            Date begin = null;
            Date end = null;
            CompanyUserInfo companyUserInfo = new CompanyUserInfo();
            companyUserInfo.setUserId(sessionUser.getUserId());
            CompanyUserInfo c = companyUserInfoService.selectOne(companyUserInfo);
            //计算当前时间到年底 与 劳动合同时间段重合月份数量
            if(ic.getItemConfigContactTime()==0){
                begin = c.getCompanyUserContractTimeBegin().getTime()>=currentDate.getTime() ? currentDate : c.getCompanyUserContractTimeBegin();
                end = c.getCompanyUserContractTimeEnd().getTime()>=lastDay.getTime() ? lastDay : c.getCompanyUserContractTimeEnd();
            }
            //计算当前时间到年底 与 租房合同时间段重合月份数量
            if(ic.getItemConfigContactTime()==1){
                begin = c.getCompanyUserHouseContractTimeBegin().getTime()>=currentDate.getTime() ? currentDate : c.getCompanyUserHouseContractTimeBegin();
                end = c.getCompanyUserHouseContractTimeEnd().getTime()>=lastDay.getTime() ? lastDay : c.getCompanyUserHouseContractTimeEnd();
            }

            int startMonth = getCustomMonth(begin);
            int endMonth = getCustomMonth(end);

            monthes = endMonth - startMonth;
        }
        model.addAttribute("certificates",itemCertificates);
        model.addAttribute("monthes",monthes);
        model.addAttribute("itemId",itemId);
        model.addAttribute("talentTypes",itemTalentContents);

        Item item = itemService.selectByPrimaryKey(itemId);
        if(item.getItemCategory()!=0) {
            Item i = new Item();
            i.setItemCategory(0);
            List<Item> items = itemService.select(i);
            model.addAttribute("items",items);
            url = "/company_user/ask_for_s.html";
        }
        return url;
    }

    @ResponseBody
    @RequestMapping("/ask_for")
    public RSTFulBody askFor(@RequestParam(required = false, value = "certificateId[]") Long[] certificateId,
                             @RequestParam(required = false, value = "imgUrl[]") String[] imgUrl,
                             @RequestParam(required = false, value = "items[]") Long[] items,
                             @RequestParam(required = false, value = "itemsName[]") String[] itemName,
                             Long itemId,
                             HttpServletRequest request,
                             String amount,
                             String memo,
                             Integer type,
                             Integer talentType
                             ){
        Long userId = getSessionUser().getUserId();
        RSTFulBody rstFulBody = new RSTFulBody();
        if(!ifPermit(userId,itemId) || !ifHaveOneSubmit(itemId,userId)){
            rstFulBody.fail("不能申请！");
            return rstFulBody;
        }
        ItemConfig itemConfig = new ItemConfig();
        itemConfig.setItemId(itemId);
        itemConfig.setItemConfigState(true);
        ItemConfig ic = itemConfigService.selectOne(itemConfig);
        CompanyUserItem cui = new CompanyUserItem();
        cui.setItemId(itemId);
        cui.setUserId(userId);
        CompanyUserItem ccc = companyUserItemService.selectOne(cui);

        companyUserItemService.delByParentId(ccc.getCompanyUserItemId());
        if(items!=null){
            List<CompanyUserItem> childItems = new ArrayList<>();
            for (int i=0;i<items.length;i++){
                CompanyUserItem companyUserItem = new CompanyUserItem();
                companyUserItem.setUserId(userId);
                companyUserItem.setItemId(items[i]);
                companyUserItem.setItemName(itemName[i]);
                companyUserItem.setParentId(ccc.getCompanyUserItemId());
                companyUserItem.setConfigId(ic.getItemConfigId());
                String talent = request.getParameter("talentType"+items[i]);
                if(talent!=null) companyUserItem.setTalentTypeContent((long)Integer.parseInt(talent));
                childItems.add(companyUserItem);
            }

            companyUserItemService.insertList(childItems);
        }
        List<CompanyUserCertificate> companyUserCertificates = new ArrayList<>();
        if(certificateId!=null){
            for (int i=0;i<certificateId.length;i++){
                Certificate certificate = certificateService.selectByPrimaryKey(certificateId[i]);
                CompanyUserCertificate companyUserCertificate = new CompanyUserCertificate();
                companyUserCertificate.setCertificateId(certificateId[i]);
                companyUserCertificate.setCertificateName(certificate.getCertificateName());
                companyUserCertificate.setImgUrl(imgUrl[i]);
                companyUserCertificate.setUserId(userId);
                companyUserCertificates.add(companyUserCertificate);
            }
        }
        companyUserCertificateService.deleteByUserId(userId);
        int res = companyUserCertificateService.insertList(companyUserCertificates);
        Long[] newCertificateId = removeDuplicates(certificateId);
        for (Long v : newCertificateId) {
            String change = request.getParameter("change_"+v);
            if(change.equals("1")){
                InfoChange infoChange = new InfoChange();
                infoChange.setFiledName(infoMap.get(v+""));
                infoChange.setUserId(userId);
                InfoChange i = infoChangeService.selectOne(infoChange);
                if(i==null) infoChangeService.insert(infoChange);
            }
        }
        Map<String,Object> map = new HashMap<>();
        map.put("userId",userId);
        map.put("itemId",itemId);
        map.put("type",type);
        map.put("haveSubmit",1);
        map.put("talentType",talentType);
        map.put("submitTime",getCurrentDate());
        if(ccc.getCompanyChecked()==0) map.put("companyChecked",3);
        if(ccc.getStreetChecked()==0) map.put("streetChecked",3);
        if(ccc.getCenterChecked()==0) map.put("centerChecked",3);
        if(amount!=null && amount!="") map.put("amount",amount);
        if(memo!=null && memo!="") map.put("memo",memo);
        companyUserItemService.updateByItemIdAndUserId(map);

        if (res > 0) rstFulBody.success("申请成功，请耐心等待审核！");
        else rstFulBody.fail("申请失败！");
        return rstFulBody;
    }

    @RequestMapping("my_app.html")
    public String myApp(){
        return "/company_user/my_app.html";
    }

    @RequestMapping("/ajax_app")
    public String ajaxApp(Model model, int pageNum, int pageSize
    ) {
        CompanyUserItem companyUserItem = new CompanyUserItem();
        companyUserItem.setUserId(getSessionUser().getUserId());
        companyUserItem.setParentId((long)0);
        companyUserItem.setHaveSubmit(true);
        //分页查询
        PageHelper.startPage(pageNum, pageSize);
        List<CompanyUserItem> companyUserItems = companyUserItemService.select(companyUserItem);

        PageInfo<CompanyUserItem> pageInfo = new PageInfo<>(companyUserItems);
        String pageStr = makePageHtml(pageInfo);
        model.addAttribute("page_info", pageInfo);
        model.addAttribute("pages", pageStr);
        return "/company_user/ajax_app.html";
    }

    @RequestMapping("/my_app_info.html")
    public String myAppInfo(Model model,Long itemUserId){
        CompanyUserItem companyUserItem = companyUserItemService.selectByPrimaryKey(itemUserId);
        model.addAttribute("cui", companyUserItem);
        return "/company_user/my_app_info.html";
    }

    @ResponseBody
    @RequestMapping("/get_reason")
    public CompanyUserItem getReason(Long userItemId){
        CompanyUserItem companyUserItem = companyUserItemService.selectByPrimaryKey(userItemId);
        return companyUserItem;
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

    private Boolean ifHaveOneSubmit(Long itemId,Long userId){

        CompanyUserItem c = new CompanyUserItem();
        c.setUserId(userId);
        c.setHaveSubmit(true);
        c.setItemId(itemId);
        if(companyUserItemService.selectOne(c)!=null) return false;

        Boolean res = true;
        ItemTeamContent itemTeamContent = new ItemTeamContent();
        itemTeamContent.setItemId(itemId);
        List<ItemTeamContent> itemTeamContents=itemTeamContentService.select(itemTeamContent);
        List<ItemTeamContent> itc = itemTeamContentService.selectTeam(itemId);
        CompanyUserItem companyUserItem = new CompanyUserItem();
        if(itemTeamContents.size()>0){
            for(ItemTeamContent i: itc){
                companyUserItem.setItemId(i.getItemId());
                companyUserItem.setHaveSubmit(true);
                companyUserItem.setUserId(userId);
                companyUserItem.setParentId((long)0);
                CompanyUserItem cui = companyUserItemService.selectOne(companyUserItem);
                if(cui!=null){
                    res=false;
                    break;
                }
            }
        }
        return res;
    }
}
