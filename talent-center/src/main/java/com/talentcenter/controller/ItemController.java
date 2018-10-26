package com.talentcenter.controller;

import RSTFul.RSTFulBody;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.talentcenter.entity.Item;
import com.talentcenter.entity.ItemType;
import com.talentcenter.entity.User;
import com.talentcenter.service.ItemService;
import com.talentcenter.service.ItemTypeService;
import util.DateHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/item")
public class ItemController extends BaseController{
    @Autowired
    private ItemService itemService;
    @Autowired
    private ItemTypeService itemTypeService;

    public ItemController(ItemTypeService itemTypeService) {
        this.itemTypeService = itemTypeService;
    }

   /* @Autowired
    private RoleItemService roleItemService;*/

    /**
     * 政策列表页
     * @param model
     * @return
     */
    @RequestMapping("/index.html")
    public String index(Model model){
        return "/item/index.html";
    }

    @RequestMapping("/ajax_index")
    public String ajaxIndex(Model model, int pageNum, int pageSize,
                            @RequestParam(required = false) String itemName
    ){
        //组装搜索条件
        /*Map<String,Object> map=new HashMap<>();
        if(userTrueName!=null && userTrueName!="") map.put("userTrueName",userTrueName);
        if(examId!=null && examId!="") map.put("examId",examId);
        if(userSex!=null && userSex!="") map.put("userSex",userSex);*/
        Item item = new Item();
        item.setDel(true);
        if(itemName!=null && itemName!="") item.setItemName(itemName);
        //分页查询
        PageHelper.startPage(pageNum, pageSize);
        List<Item> items = itemService.select(item);

        PageInfo<Item> pageInfo= new PageInfo<>(items);
        String pageStr = makePageHtml(pageInfo);
        model.addAttribute("page_info",pageInfo);
        model.addAttribute("pages",pageStr);
        return "/item/ajax_index.html";
    }

    @RequestMapping("add.html")
    public String addUI(Model model) {
        List<ItemType> itemTypes= itemTypeService.selectAll();
        model.addAttribute("itemTypes",itemTypes);
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
        model.addAttribute("itemId",itemId);
        model.addAttribute("obj",item);
        List<ItemType> itemTypes= itemTypeService.selectAll();
        model.addAttribute("itemTypes",itemTypes);
        return "/item/edit.html";
    }

    @ResponseBody
    @RequestMapping("edit")
    public RSTFulBody edit(Item item) {
        User sessionUser = getSessionUser();
        item.setUpdateId(sessionUser.getUserId());
        item.setUpdateName(sessionUser.getUserName());
        item.setUpdateTime(DateHelper.getCurrentDate());
        ItemType itemType = itemTypeService.selectByPrimaryKey(item.getItemTypeId());
        item.setItemTypeName(itemType.getItemTypeName());
        int res = itemService.updateByPrimaryKeySelective(item);
        RSTFulBody rstFulBody = new RSTFulBody();
        if (res > 0) rstFulBody.success("修改成功！");
        else rstFulBody.fail("修改失败！");
        return rstFulBody;
    }

    @RequestMapping("del.html")
    public String delUser(Item item){
        item.setDel(false);
        int res = itemService.updateByPrimaryKeySelective(item);
        return "redirect:/item/index.html";
    }

    @ResponseBody
    @RequestMapping("batch_del")
    public RSTFulBody batchDel(@RequestParam(required = true) String ids){

        Map<String, Object> map = new HashMap<>();
        map.put("ids",ids);
        int res = itemService.batchDel(map);
        RSTFulBody rstFulBody=new RSTFulBody();
        if(res>0) rstFulBody.success(res);
        else  rstFulBody.fail("删除失败！");
        return rstFulBody;
    }

    public void setItemTypeService(ItemTypeService itemTypeService) {
        this.itemTypeService = itemTypeService;
    }
}
