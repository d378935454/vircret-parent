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

   /* @Autowired
    private RoleItemService roleItemService;*/

    /**
     * 政策列表页
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
        List<ItemType> itemTypes = itemTypeService.selectAll();
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
        model.addAttribute("itemId", itemId);
        model.addAttribute("obj", item);
        List<ItemType> itemTypes = itemTypeService.selectAll();
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
    public String config(Model model,Integer itemId){
        model.addAttribute("itemId",itemId);
        return "/item/config.html";
    }

    @RequestMapping("/ajax_config")
    public String ajaxConfigs(Model model, int pageNum, int pageSize,
                              @RequestParam(required = false) String configName,
                              @RequestParam(required = false) String realName,
                              @RequestParam(required = false) String itemId

    ) {


        //分页查询
        PageHelper.startPage(pageNum, pageSize);
        List<ItemConfig> config = itemConfigService.selectAll();
        PageInfo<ItemConfig> pageInfo = new PageInfo<>(config);
        String pageStr = makePageHtml(pageInfo);
        model.addAttribute("page_info", pageInfo);
        model.addAttribute("pages", pageStr);
        return "/item/ajax_config.html";
    }

    @RequestMapping("add_config.html")
    public String addConfig(Model model, String itemId) {
        Item item = itemService.selectByPrimaryKey((long)Integer.parseInt(itemId));
        Certificate certificate = new Certificate();
        certificate.setDel(true);
        List<Certificate> certificates = certificateService.select(certificate);
        model.addAttribute("item_length",item.getItemLength());
        model.addAttribute("itemId", itemId);
        model.addAttribute("certificates", certificates);
        return "/item/add_config.html";
    }

    @ResponseBody
    @RequestMapping("add_config")
    public RSTFulBody addConfig(
                                ItemConfig config,
                                @RequestParam(value = "certificates[]") String[] certificates) {
        
        int res = itemConfigService.insertSelective(config);
        ArrayList<ItemCertificate> cs = new ArrayList<>();
        for (String c: certificates) {
            ItemCertificate itemCertificate = new ItemCertificate();
            itemCertificate.setItemConfigId(config.getItemConfigId());
            itemCertificate.setCertificateId((long)Integer.parseInt(c));
            Certificate certificate = certificateService.selectByPrimaryKey((long)Integer.parseInt(c));
            itemCertificate.setCertificateName(certificate.getCertificateName());
            cs.add(itemCertificate);
        }
        int num = itemCertificateService.insertList(cs);
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
        model.addAttribute("obj", config);
        return "/item/edit_config.html";
    }

    @ResponseBody
    @RequestMapping("edit_config")
    public RSTFulBody editConfig(ItemConfig config) {

        int res = itemConfigService.updateByPrimaryKeySelective(config);
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

}
