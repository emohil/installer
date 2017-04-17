package com.company.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.api.fw.service.PagerService;
import com.company.dto.Order;
import com.company.dto.Pager;
import com.company.po.base.StringIdPo;
import com.company.util.Dto;

public abstract class BasePagerController<SF, BEAN extends StringIdPo> extends BaseEntryController<BEAN> {
    protected static final String PAGE_PARAM = "page";
    protected static final String ROWS_PARAM = "rows";
    protected static final String COUNT_PARAM = "countall";
    
    private PagerService<SF, BEAN> baseService;
    
    public void setPagerService(PagerService<SF, BEAN> baseService) {
        this.baseService = baseService;
    }

    protected PagerService<SF, BEAN> getPagerService() {
        if (this.baseService == null) {
            throw new RuntimeException("Please use setBaseService specify service");
        }
        return this.baseService;
    }

    /**
     * Transfer pager query parameter. Cause of some web framework(eg. EasyUI)
     * passed page parameter(not start) Formula : start = (page - 1) * limit + 1
     * 
     * @param page
     * @param limit
     * @return
     */
    protected int calculateStart(int page, int limit) {
        if (page <= 1)
            return 1;

        return (page - 1) * limit + 1;
    }

    @RequestMapping(value = "/list")
    @ResponseBody
    public Dto list(HttpServletRequest request, //
            @RequestBody SF searchForm,
            @RequestParam(required = false, defaultValue = "1", value = PAGE_PARAM) int page, // 记录页数
            @RequestParam(required = false, defaultValue = "20", value = ROWS_PARAM) int limit, // 记录条数
            @RequestParam(required = false, value = "order") List<Order> orders, //
            @RequestParam(required = false, defaultValue = "true", value = COUNT_PARAM) boolean countall)
                    throws Exception {

        int start = this.calculateStart(page, limit);

        Dto ret = new Dto();

        ret.put(TAG_LIST, doList(searchForm, start, limit, orders));
        if (countall) {
            Integer total = doCount(searchForm);
            ret.put(TAG_PAGE, new Pager(total, limit).setPage(page));
        }
        return ret;
    }

    protected int doCount(SF searchForm) throws Exception {
        return getPagerService().count(searchForm);
    }

    protected List<?> doList(SF searchForm, int start, int limit, List<Order> orders) throws Exception {
        return getPagerService().list(searchForm, start, limit, orders);
    }
}
