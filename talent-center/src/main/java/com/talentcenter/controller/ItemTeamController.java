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

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static util.DateHelper.getDate4StrDate;

@Controller
@RequestMapping("/item_team")
public class ItemTeamController extends BaseController {
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

    @Autowired
    private ItemTeamService itemTeamService;

    @Autowired
    private ItemTeamContentService itemTeamContentService;
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
        return "/item_team/index.html";
    }

    @RequestMapping("/ajax_index")
    public String ajaxIndex(Model model, int pageNum, int pageSize,
                            @RequestParam(required = false) String itemTeamName
    ) {
        //组装搜索条件
        /*Map<String,Object> map=new HashMap<>();
        if(configTrueName!=null && configTrueName!="") map.put("configTrueName",configTrueName);
        if(examId!=null && examId!="") map.put("examId",examId);
        if(configSex!=null && configSex!="") map.put("configSex",configSex);*/
        ItemTeam itemTeam = new ItemTeam();
        itemTeam.setDel(true);
        if (itemTeamName != null && itemTeamName != "") itemTeam.setItemTeamName(itemTeamName);
        //分页查询
        PageHelper.startPage(pageNum, pageSize);
        List<ItemTeam> itemTeams = itemTeamService.select(itemTeam);

        for (ItemTeam it: itemTeams) {
            ItemTeamContent itemTeamContent = new ItemTeamContent();
            itemTeamContent.setItemTeamId(it.getItemTeamId());
            List<ItemTeamContent> itemTeamContents = itemTeamContentService.select(itemTeamContent);
            it.setItemTeamContents(itemTeamContents);
        }

        PageInfo<ItemTeam> pageInfo = new PageInfo<>(itemTeams);
        String pageStr = makePageHtml(pageInfo);
        model.addAttribute("page_info", pageInfo);
        model.addAttribute("pages", pageStr);
        return "/item_team/ajax_index.html";
    }

    @RequestMapping("add.html")
    public String addUI(Model model) {
        Item item = new Item();
        item.setDel(true);
        List<Item> items = itemService.select(item);
        model.addAttribute("items", items);
        return "/item_team/add.html";
    }

    @ResponseBody
    @RequestMapping("add")
    public RSTFulBody add(ItemTeam itemTeam,
                          @RequestParam(required = false, value = "items[]") Long[] items,
                          @RequestParam(required = false, value = "itemNames[]") String[] itemNames) {
        User sessionUser = getSessionUser();
        itemTeam.setCreateName(sessionUser.getUserName());
        itemTeam.setCreateId(sessionUser.getUserId());
        int res = itemTeamService.insertSelective(itemTeam);
        List<ItemTeamContent> itemTeamContents = new ArrayList<>();
        for(int i=0;i<items.length;i++){
            ItemTeamContent itemTeamContent = new ItemTeamContent();
            itemTeamContent.setItemId(items[i]);
            itemTeamContent.setItemName(itemNames[i]);
            itemTeamContent.setItemTeamId(itemTeam.getItemTeamId());
            itemTeamContents.add(itemTeamContent);
        }
        itemTeamContentService.insertList(itemTeamContents);
        RSTFulBody rstFulBody = new RSTFulBody();
        if (res > 0) rstFulBody.success("添加成功！");
        else rstFulBody.fail("添加失败！");
        return rstFulBody;
    }

    @RequestMapping("edit.html")
    public String editUI(Model model, Long itemTeamId) {
        Item item = new Item();
        item.setDel(true);
        List<Item> items = itemService.select(item);
        ItemTeam itemTeam = itemTeamService.selectByPrimaryKey(itemTeamId);
        ItemTeamContent itemTeamContent = new ItemTeamContent();
        itemTeamContent.setItemTeamId(itemTeam.getItemTeamId());
        List<ItemTeamContent> itemTeamContents = itemTeamContentService.select(itemTeamContent);
        for (Item i : items) {
            i.setChecked(false);
            for (ItemTeamContent itc : itemTeamContents) {
                if (i.getItemId() == itc.getItemId()) {
                    i.setChecked(true);
                    continue;
                }
            }
        }
        model.addAttribute("items", items);
        model.addAttribute("obj", itemTeam);
        return "/item_team/edit.html";
    }

    @ResponseBody
    @RequestMapping("edit")
    public RSTFulBody edit(ItemTeam itemTeam,HttpServletRequest request,
                           @RequestParam(required = false, value = "items[]") Long[] items) {
        User sessionUser = getSessionUser();
        itemTeam.setUpdateId(sessionUser.getUserId());
        itemTeam.setUpdateName(sessionUser.getUserName());
        int res = itemTeamService.updateByPrimaryKeySelective(itemTeam);

        itemTeamContentService.delByTeamId(itemTeam.getItemTeamId());
        List<ItemTeamContent> itemTeamContents = new ArrayList<>();
        for(int i=0;i<items.length;i++){
            ItemTeamContent itemTeamContent = new ItemTeamContent();
            itemTeamContent.setItemId(items[i]);
            itemTeamContent.setItemName(request.getParameter("itemName"+items[i]));
            itemTeamContent.setItemTeamId(itemTeam.getItemTeamId());
            itemTeamContents.add(itemTeamContent);
        }
        itemTeamContentService.insertList(itemTeamContents);

        RSTFulBody rstFulBody = new RSTFulBody();
        if (res > 0) rstFulBody.success("修改成功！");
        else rstFulBody.fail("修改失败！");
        return rstFulBody;
    }

    @RequestMapping("del.html")
    public String delConfig(ItemTeam itemTeam) {
        itemTeam.setDel(false);
        int res = itemTeamService.updateByPrimaryKeySelective(itemTeam);
        itemTeamContentService.delByTeamId(itemTeam.getItemTeamId());
        return "redirect:/item_team/index.html";
    }
}
