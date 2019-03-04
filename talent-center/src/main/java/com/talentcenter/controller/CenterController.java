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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static util.DateHelper.getDate4StrDate;

@Controller
@RequestMapping("/center")
public class CenterController extends BaseController{
    @Autowired
    private CompanyService companyService;

    @Autowired
    private ItemService itemService;

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
    private ItemUserTimeService itemUserTimeService;

    @Autowired
    private CenterService centerService;

    @Autowired
    private SendLogService sendLogService;

    @Autowired
    private StreetService streetService;

    @RequestMapping("check_item.html")
    public String checkItem(Model model){
        List<Item> items = itemService.selectAll();
        Street street = new Street();
        street.setDel(true);
        List<Street> streets=streetService.select(street);
        model.addAttribute("streets",streets);
        model.addAttribute("items",items);
        return "/center/check_item.html";
    }

    @RequestMapping("check_item")
    public String ajaxCheckItem(Model model,
                                @RequestParam(required = false) Long itemId,
                                @RequestParam(required = false) Integer centerChecked,
                                @RequestParam(required = false) String companyName){
        User sessionUser = getSessionUser();
        Map<String,Object> map = new HashMap<>();
        if(sessionUser.getUserType()==2) {
            map.put("centerChecked",3);
        }else if(sessionUser.getUserType()==1 || sessionUser.getUserType()==0){
            map.put("centerChecked",2);
        }
        if(itemId!=null) map.put("itemId",itemId);
        if(centerChecked!=null) map.put("centerChecked",centerChecked);
        Company company = new Company();
        if(companyName!=null && companyName!="") company.setCompanyName(companyName);
        company.setStreetId(sessionUser.getStreetId());
        List<Map<String,Object>> maps = centerService.selectCenterItemCheckedItem(map);
        PageInfo<Map<String,Object>> pageInfo= new PageInfo<>(maps);
        String pageStr = makePageHtml(pageInfo);
        model.addAttribute("page_info",pageInfo);
        model.addAttribute("pages",pageStr);
        return "/center/ajax_items.html";
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

        /*ItemUserTime itemUserTime = new ItemUserTime();
        itemUserTime.setUserId(user.getUserId());
        itemUserTime.setCompanyUserItemId(companyUserItem.getCompanyUserItemId());
        ItemUserTime iut = itemUserTimeService.selectOne(itemUserTime);
*/
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
        return "/center/check_info.html";
    }

    @RequestMapping("/modal_content")
    public String modalContent(Model model,Long userId,Long certificateId){
        CompanyUserCertificate companyUserCertificate = new CompanyUserCertificate();
        companyUserCertificate.setUserId(userId);
        companyUserCertificate.setCertificateId(certificateId);
        List<CompanyUserCertificate> companyUserCertificates = companyUserCertificateService.select(companyUserCertificate);
        model.addAttribute("cucs",companyUserCertificates);
        return "/center/modal_content.html";
    }

    @ResponseBody
    @RequestMapping("/pass")
    public Boolean pass(@RequestBody JSONObject jsonParam){

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
            companyUserItem.setCenterChecked(2);
        }else if(getSessionUser().getUserType()==1 || getSessionUser().getUserType()==0){
            companyUserItem.setCenterChecked(4);
        }
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
        /*if(passJson.getItems()!=null && passJson.getItems().size()>0){
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
                if(itemJson.getTypeContent()!=null) c.setTalentTypeContent(itemJson.getTypeContent());
                c.setUserId(cui.getUserId());
                companyUserItems.add(c);
            }
            companyUserItemService.insertList(companyUserItems);
        }*/
        infoChangeService.deleteByUserId(cui.getUserId());
        return true;
    }

    @ResponseBody
    @RequestMapping("/back_npass_pass")
    public Boolean backNPassPass(Integer state,Long userItemId,String reason){
        CompanyUserItem companyUserItem = new CompanyUserItem();
        companyUserItem.setCompanyUserItemId(userItemId);
        companyUserItem.setCenterChecked(state);
        companyUserItem.setCenterReason(reason);
        companyUserItemService.updateByPrimaryKeySelective(companyUserItem);
        CompanyUserItem cui = companyUserItemService.selectByPrimaryKey(userItemId);
        infoChangeService.deleteByUserId(cui.getUserId());
        return true;
    }
}
