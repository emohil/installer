package com.company.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.api.fw.service.StringIdBaseService;
import com.company.po.base.StringIdPo;
import com.company.util.Dto;

public abstract class BaseEntryController<BEAN extends StringIdPo> extends BaseController {

    private StringIdBaseService<BEAN> baseService;

    public void setBaseService(StringIdBaseService<BEAN> baseService) {
        this.baseService = baseService;
    }

    protected StringIdBaseService<BEAN> getBaseService() {
        if (this.baseService == null) {
            throw new RuntimeException("Please use setBaseService specify service");
        }
        return this.baseService;
    }

    @RequestMapping(value = "/save")
    @ResponseBody
    public Dto save(HttpServletRequest request, @RequestBody BEAN data) {

        Dto ret = new Dto();

        String currentUser = this.getCurrentUser(request);

        data.setCreateBy(currentUser);
        data.setModifyBy(currentUser);

        this.getBaseService().save(data);

        ret.put(TAG_SUCCESS, true);

        return ret;
    }

    @RequestMapping(value = "/update")
    @ResponseBody
    public Dto update(HttpServletRequest request, @RequestBody BEAN data) {

        Dto ret = new Dto();

        String currentUser = this.getCurrentUser(request);

        data.setModifyBy(currentUser);

        this.getBaseService().update(data);

        ret.put(TAG_SUCCESS, true);

        return ret;
    }

    @RequestMapping(value = "/delete")
    @ResponseBody
    public Dto delete(@RequestParam(value = "id") String id) {

        Dto ret = new Dto();

        this.getBaseService().delete(id);

        ret.put(TAG_SUCCESS, true);

        return ret;
    }

    @RequestMapping("/load")
    @ResponseBody
    public BEAN load(@RequestParam("id") String id) {
        return getBaseService().find(id);
    }
}
