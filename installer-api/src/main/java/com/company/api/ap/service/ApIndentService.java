package com.company.api.ap.service;

import java.util.List;

import com.company.po.ap.ApIndent;
import com.company.api.fw.service.PagerService;
import com.company.api.fw.service.StringIdBaseService;

public interface ApIndentService extends StringIdBaseService<ApIndent>, //
        PagerService<ApIndent, ApIndent> {

    String BEAN_NAME = "apIndentService";

    /**
     * 根据批次ID删除数据
     * 
     * @param apBatchId
     */
    void deleteByBatchId(String apBatchId);

    List<ApIndent> findApIndentByBatchId(String apBatchId);
}