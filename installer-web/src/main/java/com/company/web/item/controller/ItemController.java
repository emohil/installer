package com.company.web.item.controller;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.company.api.aparty.service.ApartyContactsService;
import com.company.api.aparty.service.ApartyService;
import com.company.api.fs.service.FileIndexService;
import com.company.api.fw.DictCodes;
import com.company.api.fw.EnumCodes;
import com.company.api.item.service.ItemPriceService;
import com.company.api.item.service.ItemService;
import com.company.api.sctype.service.SctypeContentService;
import com.company.api.sctype.service.SctypeService;
import com.company.api.sctype.service.SctypeSortService;
import com.company.po.aparty.Aparty;
import com.company.po.fs.FileIndex;
import com.company.po.item.Item;
import com.company.po.item.ItemPrice;
import com.company.po.sctype.Sctype;
import com.company.po.sctype.SctypeContent;
import com.company.po.sctype.SctypeSort;
import com.company.sf.item.ItemSf;
import com.company.web.BasePagerController;
import com.company.api.fw.service.SysDictService;
import com.company.dto.Filter;
import com.company.dto.Order;
import com.company.dto.SysDict;
import com.company.dto.ValueTextPair;
import com.company.util.Dto;
import com.company.util.New;
import com.company.util.StringUtil;
import com.company.util.json.JacksonHelper;

@Controller("webItemController")
@RequestMapping(value = "/item")
public class ItemController extends BasePagerController<ItemSf, Item> {

    private static final String PATH = "item";

    @Autowired
    private ItemService service;

    @Autowired
    private ItemPriceService itemPriceService;

    @Autowired
    private SctypeService sctypeService;

    @Autowired
    private SctypeSortService sctypeSortService;

    @Autowired
    private SctypeContentService sctypeContentService;

    @Autowired
    private FileIndexService fileIndexService;

    @Autowired
    public void setBaseService(ItemService service) {
        super.setBaseService(service);
        this.service = service;
    }

    @Autowired
    private ApartyService apartyService;

    @Autowired
    private ApartyContactsService acService;

    @Autowired
    private SysDictService sysDictService;

    @Override
    protected int doCount(ItemSf sf) throws Exception {
        return service.count(sf);
    }

    @Override
    protected List<?> doList(ItemSf sf, int start, int limit, List<Order> orders) throws Exception {
        return service.list(sf, start, limit, orders);
    }

    @RequestMapping(value = "itemList")
    protected String itemList(HttpServletRequest request, ModelMap model) {
        model.addAttribute("itemStatusList", sysDictService.listGroupCopy(DictCodes.ITEM_STATUS, SysDict.CHECK));
        model.addAttribute("payStatusList", sysDictService.listGroupCopy(EnumCodes.PAY_STATUS, SysDict.CHECK));
        model.addAttribute(TAG_PATH, PATH);
        return PATH + "/itemList";
    }

    @RequestMapping(value = "/itemAdd")
    public String itemAdd(HttpServletRequest request, ModelMap model, //
            @RequestParam(value = "apartyId", required = false) String apartyId) {

        Item data = new Item();

        if (StringUtil.isNotEmpty(apartyId)) {
            Aparty aparty = apartyService.find(apartyId);
            data.setApartyId(aparty.getId());
            data.setApartyIdDisp(aparty.getName1());
            model.put("aparty", "BE");
        }

        List<Sctype> serveTypeList = sctypeService.findAll();
        for (Sctype sctype : serveTypeList) {
            List<SctypeSort> sortList = sctypeSortService.findList(Filter.eq("sctypeId", sctype.getId()));
            sctype.setSortList(sortList);
        }

        model.put("serveTypeList", serveTypeList);
        model.put(TAG_DATA, data);
        model.put(TAG_PATH, PATH);
        return PATH + "/itemAdd";
    }

    @RequestMapping(value = "/itemEdit")
    public String itemEdit(HttpServletRequest request, ModelMap model, //
            @RequestParam(value = "id") String id) {

        List<Sctype> serveTypeList = sctypeService.findAll();
        for (Sctype sctype : serveTypeList) {
            List<SctypeSort> sortList = sctypeSortService.findList(Filter.eq("sctypeId", sctype.getId()));
            sctype.setSortList(sortList);
        }

        List<ItemPrice> itemPriceList = itemPriceService.findList(Filter.eq("itemId", id));
        Set<String> serveContentIds = New.hashSet();
        for (ItemPrice ItemPrice : itemPriceList) {
            serveContentIds.add(ItemPrice.getServeContentId());
        }

        if (serveContentIds.size() > 0) {
            List<SctypeContent> sctypeContents = sctypeContentService.findList(Filter.in("id", serveContentIds));
            Set<String> serveSortIds = New.hashSet();
            for (SctypeContent sctypeContent : sctypeContents) {
                serveSortIds.add(sctypeContent.getSctypeSortId());
            }
            model.put("serveSortIds", serveSortIds);
        }
        model.put("itemPriceList", itemPriceList);
        model.put("serveTypeList", serveTypeList);
        model.put(TAG_ID, id);
        model.put(TAG_PATH, PATH);
        return PATH + "/itemEdit";
    }

    @RequestMapping(value = "/doSave")
    @ResponseBody
    public Dto doSave(HttpServletRequest request, //
            @RequestParam(value = "imgFiles", required = false) MultipartFile[] imgFiles, //
            @RequestParam(value = "data", required = false) String data) {

        Item item = JacksonHelper.toObject(data, Item.class);

        service.save(item);

        service.saveContracts(item.getId(), imgFiles);

        Dto ret = new Dto();
        ret.put(TAG_SUCCESS, true);

        return ret;
    }
    
    @RequestMapping(value = "/doUpdate")
    @ResponseBody
    public Dto doUpdate(HttpServletRequest request, //
            @RequestParam(value = "imgFiles", required = false) MultipartFile[] imgFiles, //
            @RequestParam(value = "data", required = false) String data) {
        
        Item item = JacksonHelper.toObject(data, Item.class);
        service.update(item);
        service.saveContracts(item.getId(), imgFiles);
        Dto rt = new Dto();
        rt.put(TAG_SUCCESS, true);
        return rt;
    }

    @RequestMapping(value = "/doTypeahead")
    @ResponseBody
    public List<ValueTextPair> doTypeahead(@RequestBody Dto params) {
        return service.doTypeahead(params);
    }

    @RequestMapping(value = "/deleteItem")
    @ResponseBody
    public Dto deleteItem(@RequestParam(value = "id") String id) {
        return service.deleteItem(id);
    }

    @RequestMapping(value = "/loadContractFiles")
    @ResponseBody
    public List<FileIndex> loadContractFiles(@RequestParam(value = "id") String id) {
        return service.loadContractFiles(id);
    }
    
    @RequestMapping(value = "/delPact")
    @ResponseBody
    public Dto delPact(@RequestParam(value = "id") String id) {
        Dto rt = new Dto();
        fileIndexService.delfile(id);
        rt.put(TAG_SUCCESS, true);
        return rt;
    }
}
