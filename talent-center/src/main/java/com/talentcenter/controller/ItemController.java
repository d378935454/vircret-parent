package com.talentcenter.controller;

import RSTFul.RSTFulBody;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.talentcenter.entity.*;
import com.talentcenter.service.*;
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

import static util.DateHelper.getDate4StrDate;

@Controller
@RequestMapping("/item")
public class ItemController extends BaseController {
    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemTypeService itemTypeService;

    @Autowired
    private ItemConfigService itemConfigService;

    @Autowired
    private CertificateService certificateService;

    @Autowired
    private ItemCertificateService itemCertificateService;

    @Autowired
    private TalentTypeService talentTypeService;

    @Autowired
    private TypeCategoryService typeCategoryService;

    @Autowired
    private ItemTalentContentService itemTalentContentService;
   /* @Autowired
    private RoleItemService roleItemService;*/

    /**
     * 政策列表页
     *
     * @param model
     * @return
     */

    @RequestMapping("/index.html")
    public String index(Model model) {
        return "/item/index.html";
    }

    @RequestMapping("/ajax_index")
    public String ajaxIndex(Model model, int pageNum, int pageSize,
                            @RequestParam(required = false) String itemName
    ) {
        //组装搜索条件
        /*Map<String,Object> map=new HashMap<>();
        if(configTrueName!=null && configTrueName!="") map.put("configTrueName",configTrueName);
        if(examId!=null && examId!="") map.put("examId",examId);
        if(configSex!=null && configSex!="") map.put("configSex",configSex);*/
        Item item = new Item();
        item.setDel(true);
        if (itemName != null && itemName != "") item.setItemName(itemName);
        //分页查询
        PageHelper.startPage(pageNum, pageSize);
        List<Item> items = itemService.select(item);

        PageInfo<Item> pageInfo = new PageInfo<>(items);
        String pageStr = makePageHtml(pageInfo);
        model.addAttribute("page_info", pageInfo);
        model.addAttribute("pages", pageStr);
        return "/item/ajax_index.html";
    }

    @RequestMapping("add.html")
    public String addUI(Model model) {
        ItemType itemType = new ItemType();
        itemType.setDel(true);
        List<ItemType> itemTypes = itemTypeService.select(itemType);
        model.addAttribute("itemTypes", itemTypes);
        return "/item/add.html";
    }

    @ResponseBody
    @RequestMapping("add")
    public RSTFulBody add(Item item) {
        User sessionUser = getSessionUser();
        item.setCreateName(sessionUser.getUserName());
        item.setCreateId(sessionUser.getUserId());
        ItemType itemType = itemTypeService.selectByPrimaryKey(item.getItemTypeId());
        item.setItemTypeName(itemType.getItemTypeName());
        int res = itemService.insertSelective(item);
        RSTFulBody rstFulBody = new RSTFulBody();
        if (res > 0) rstFulBody.success("添加成功！");
        else rstFulBody.fail("添加失败！");
        return rstFulBody;
    }

    @RequestMapping("edit.html")
    public String editUI(Model model, String itemId) {
        Item item = itemService.selectByPrimaryKey((long) Integer.parseInt(itemId));
        ItemType itemType = new ItemType();
        itemType.setDel(true);
        List<ItemType> itemTypes = itemTypeService.select(itemType);
        model.addAttribute("itemId", itemId);
        model.addAttribute("obj", item);
        model.addAttribute("itemTypes", itemTypes);
        return "/item/edit.html";
    }

    @ResponseBody
    @RequestMapping("edit")
    public RSTFulBody edit(Item item) {
        User sessionUser = getSessionUser();
        item.setUpdateId(sessionUser.getUserId());
        item.setUpdateName(sessionUser.getUserName());
        ItemType itemType = itemTypeService.selectByPrimaryKey(item.getItemTypeId());
        item.setItemTypeName(itemType.getItemTypeName());
        int res = itemService.updateByPrimaryKeySelective(item);
        RSTFulBody rstFulBody = new RSTFulBody();
        if (res > 0) rstFulBody.success("修改成功！");
        else rstFulBody.fail("修改失败！");
        return rstFulBody;
    }

    @RequestMapping("del.html")
    public String delConfig(Item item) {
        item.setDel(false);
        int res = itemService.updateByPrimaryKeySelective(item);
        return "redirect:/item/index.html";
    }

    @ResponseBody
    @RequestMapping("batch_del")
    public RSTFulBody batchDel(@RequestParam(required = true) String ids) {

        Map<String, Object> map = new HashMap<>();
        map.put("ids", ids);
        int res = itemService.batchDel(map);
        RSTFulBody rstFulBody = new RSTFulBody();
        if (res > 0) rstFulBody.success(res);
        else rstFulBody.fail("删除失败！");
        return rstFulBody;

    }

    @RequestMapping("config.html")
    public String config(Model model, Integer itemId) {
        model.addAttribute("itemId", itemId);
        return "/item/config.html";
    }

    @RequestMapping("/ajax_config")
    public String ajaxConfigs(Model model, int pageNum, int pageSize,
                              @RequestParam(required = false) String itemId

    ) {
        //分页查询
        ItemConfig itemConfig = new ItemConfig();
        itemConfig.setItemId((long) Integer.parseInt(itemId));
        PageHelper.startPage(pageNum, pageSize);
        List<ItemConfig> config = itemConfigService.select(itemConfig);
        PageInfo<ItemConfig> pageInfo = new PageInfo<>(config);
        String pageStr = makePageHtml(pageInfo);
        model.addAttribute("page_info", pageInfo);
        model.addAttribute("pages", pageStr);
        return "/item/ajax_config.html";
    }

    @RequestMapping("add_config.html")
    public String addConfig(Model model, String itemId) {
        Item item = itemService.selectByPrimaryKey((long) Integer.parseInt(itemId));
        Certificate certificate = new Certificate();
        certificate.setDel(true);
        List<Certificate> certificates = certificateService.select(certificate);
        List<TypeCategory> typeCategories = typeCategoryService.selectAll();
        model.addAttribute("item_length", item.getItemLength());
        model.addAttribute("itemId", itemId);
        model.addAttribute("certificates", certificates);
        model.addAttribute("typeCategories", typeCategories);
        return "/item/add_config.html";
    }

    @ResponseBody
    @RequestMapping("add_config")
    public RSTFulBody addConfig(
            ItemConfig config,
            @RequestParam(required = false, value = "certificates[]") String[] certificates,
            @RequestParam(required = false) String itemConfigAccept,
            @RequestParam(required = false) String itemConfigCheckTime,
            @RequestParam(required = false, value = "talentTypeIds[]") String[] talentTypeIds,
            @RequestParam(required = false, value = "talentTypes[]") String[] talentTypes,
            @RequestParam(required = false, value = "talentTypeNames[]") String[] talentTypeNames
    ) {

        String[] acceptTimes = itemConfigAccept.split("至");
        String[] checkTimes = itemConfigCheckTime.split("至");
        config.setItemConfigAcceptBegin(getDate4StrDate(acceptTimes[0], "MM-dd"));
        config.setItemConfigAcceptEnd(getDate4StrDate(acceptTimes[1], "MM-dd"));

        config.setItemConfigCheckBegin(getDate4StrDate(checkTimes[0], "MM-dd"));
        config.setItemConfigCheckEnd(getDate4StrDate(checkTimes[1], "MM-dd"));

        User sessionUser = getSessionUser();
        config.setCreateName(sessionUser.getUserName());
        config.setCreateId(sessionUser.getUserId());
        int res = itemConfigService.insertSelective(config);

        if (config.getItemConfigTType()) {
            updateItemTalentContent(talentTypes, talentTypeIds, talentTypeNames, config.getItemConfigId());
        }
        if (certificates != null) {
            updateItemCertificate(certificates, config.getItemConfigId());
        }

        RSTFulBody rstFulBody = new RSTFulBody();
        rstFulBody.data(config.getItemId() + "");
        if (res > 0) rstFulBody.success("添加成功！");
        else rstFulBody.fail("添加失败！");
        return rstFulBody;
    }

    @RequestMapping("edit_config.html")
    public String editConfig(Model model, long primaryKey) {
//        Config config = configService.selectByPrimaryKey((long)Integer.parseInt(primaryKey));
        ItemConfig config = itemConfigService.selectByPrimaryKey(primaryKey);
        Certificate certificate = new Certificate();
        certificate.setDel(true);

        List<Certificate> certificates = certificateService.select(certificate);
        ItemCertificate itemCertificate = new ItemCertificate();
        itemCertificate.setItemConfigId(config.getItemConfigId());
        List<ItemCertificate> itemCertificates = itemCertificateService.select(itemCertificate);

        for (Certificate c : certificates) {
            c.setChecked(false);
            for (ItemCertificate ic : itemCertificates) {
                if (c.getCertificateId() == ic.getCertificateId()) {
                    c.setChecked(true);
                    continue;
                }
            }
        }

        List<TypeCategory> typeCategories = typeCategoryService.selectAll();
        model.addAttribute("certificates", certificates);
        model.addAttribute("typeCategories", typeCategories);
        model.addAttribute("obj", config);
        return "/item/edit_config.html";
    }

    @ResponseBody
    @RequestMapping("edit_config")
    public RSTFulBody editConfig(
            ItemConfig config,
            @RequestParam(required = false, value = "certificates[]") String[] certificates,
            @RequestParam(required = false) String itemConfigAccept,
            @RequestParam(required = false) String itemConfigCheckTime,
            @RequestParam(required = false, value = "talentTypeIds[]") String[] talentTypeIds,
            @RequestParam(required = false, value = "talentTypes[]") String[] talentTypes,
            @RequestParam(required = false, value = "talentTypeNames[]") String[] talentTypeNames) {

        String[] acceptTimes = itemConfigAccept.split("至");
        String[] checkTimes = itemConfigCheckTime.split("至");
        config.setItemConfigAcceptBegin(getDate4StrDate(acceptTimes[0], "MM-dd"));
        config.setItemConfigAcceptEnd(getDate4StrDate(acceptTimes[1], "MM-dd"));

        config.setItemConfigCheckBegin(getDate4StrDate(checkTimes[0], "MM-dd"));
        config.setItemConfigCheckEnd(getDate4StrDate(checkTimes[1], "MM-dd"));

        User sessionUser = getSessionUser();
        config.setCreateName(sessionUser.getUserName());
        config.setCreateId(sessionUser.getUserId());

        int res = itemConfigService.updateByPrimaryKey(config);

        itemTalentContentService.delByItemConfigId(config.getItemConfigId());
        itemCertificateService.delByItemConfigId(config.getItemConfigId());
        if (config.getItemConfigTType()) {
            updateItemTalentContent(talentTypes, talentTypeIds, talentTypeNames, config.getItemConfigId());
        }

        if (certificates != null) {
            updateItemCertificate(certificates, config.getItemConfigId());
        }

        RSTFulBody rstFulBody = new RSTFulBody();
        rstFulBody.data(config.getItemId() + "");
        if (res > 0) rstFulBody.success("修改成功！");
        else rstFulBody.fail("修改失败！");
        return rstFulBody;
    }

    @RequestMapping("del_config.html")
    public String del(ItemConfig config, String itemId) {
        config.setDel(false);
        int res = itemConfigService.updateByPrimaryKeySelective(config);
        return "redirect:/item/config.html?itemId=" + itemId;
    }

    @ResponseBody
    @RequestMapping("batch_del_config")
    public RSTFulBody batchDelConfig(@RequestParam(required = true) String ids, String itemId) {

        Map<String, Object> map = new HashMap<>();
        map.put("ids", ids);
        int res = itemConfigService.batchDel(map);
        RSTFulBody rstFulBody = new RSTFulBody();
        rstFulBody.data(itemId);
        if (res > 0) rstFulBody.success(res);
        else rstFulBody.fail("删除失败！");
        return rstFulBody;
    }

    @ResponseBody
    @RequestMapping("talent_type")
    public List<TalentType> talentTypes(TalentType talentType) {

        List<TalentType> talentTypes = talentTypeService.select(talentType);
        return talentTypes;
    }

    @ResponseBody
    @RequestMapping("talent_type_content")
    public Map<String,Object> talentTypeContent(Long itemId){

        Map<String,Object> map = new HashMap<>();
        map.put("state",0);
        map.put("itemId",itemId);
        ItemConfig itemConfig = new ItemConfig();
        itemConfig.setItemId(itemId);
        itemConfig.setItemConfigState(true);
        ItemConfig ic = itemConfigService.selectOne(itemConfig);

        if(ic.getItemConfigTType()){
            ItemTalentContent itemTalentContent = new ItemTalentContent();
            itemTalentContent.setItemConfigId(ic.getItemConfigId());
//            talentTypes = talentTypeService.select(talentType);
            List<ItemTalentContent> itemTalentContents = itemTalentContentService.select(itemTalentContent);
            map.put("itemTalentContents",itemTalentContents);
            map.put("state",1);
        }
        return  map;
    }

    @ResponseBody
    @RequestMapping("item_record")
    public List<ItemTalentContent> itemRecord(ItemTalentContent itemTalentContent) {
        List<ItemTalentContent> itemTalentContents = itemTalentContentService.select(itemTalentContent);
        return itemTalentContents;
    }

    @ResponseBody
    @RequestMapping("config_state")
    public RSTFulBody configState(ItemConfig itemConfig){
        int res = itemConfigService.updateByPrimaryKeySelective(itemConfig);
        RSTFulBody rstFulBody = new RSTFulBody();
        if (res > 0) rstFulBody.success(res);
        else rstFulBody.fail("删除失败！");
        return rstFulBody;
    }

    private Boolean updateItemTalentContent(
            String[] talentTypes,
            String[] talentTypeIds,
            String[] talentTypeNames,
            Long configId) {

        List<ItemTalentContent> itemTalentContents = new ArrayList<>();
        for (int i = 0; i < talentTypeIds.length; i++) {
            ItemTalentContent itemTalentContent = new ItemTalentContent();
            itemTalentContent.setItemConfigId(configId);
            itemTalentContent.setTalentTypeId((long) Integer.parseInt(talentTypeIds[i]));
            itemTalentContent.setTalentTypeName(talentTypeNames[i]);
            itemTalentContent.setItemTalentContent(talentTypes[i]);
            itemTalentContents.add(itemTalentContent);
        }

        int res = itemTalentContentService.insertList(itemTalentContents);
        if (res > 0) return true;
        else return false;
    }

    private Boolean updateItemCertificate(String[] certificates, Long configId) {
        ArrayList<ItemCertificate> cs = new ArrayList<>();
        for (String c : certificates) {
            ItemCertificate itemCertificate = new ItemCertificate();
            itemCertificate.setItemConfigId(configId);
            itemCertificate.setCertificateId((long) Integer.parseInt(c));
            Certificate certificate = certificateService.selectByPrimaryKey((long) Integer.parseInt(c));
            itemCertificate.setCertificateName(certificate.getCertificateName());
            cs.add(itemCertificate);
        }
        int num = itemCertificateService.insertList(cs);
        if (num > 0) return true;
        return false;
    }
}
