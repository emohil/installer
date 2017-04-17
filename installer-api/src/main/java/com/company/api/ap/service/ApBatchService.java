package com.company.api.ap.service;

import com.company.po.ap.ApBatch;
import com.company.sf.ap.ApBatchSf;
import com.company.api.fw.service.PagerService;
import com.company.api.fw.service.StringIdBaseService;
import com.company.util.Dto;

public interface ApBatchService extends StringIdBaseService<ApBatch>, //
        PagerService<ApBatchSf, ApBatch> {

    String BEAN_NAME = "apBatchService";

    /**
     * 预览结算计算
     * 
     * @param data
     * @return
     */
    Dto doPreview(ApBatch data);

    /**
     * 保存结算批次
     * 
     * @param data
     * @return
     */
    Dto doSave(ApBatch data);

    /**
     * 删除批次
     * 
     * @param id
     * @return
     */
    Dto doDelete(String id);

    /**
     * 批次过账
     * 
     * @param id
     * @return
     */
    Dto doPost(String id);

}