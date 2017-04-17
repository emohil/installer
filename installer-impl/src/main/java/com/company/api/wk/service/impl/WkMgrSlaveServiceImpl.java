package com.company.api.wk.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.api.fs.service.EnumSlaveStatus;
import com.company.api.fw.service.impl.BaseServiceImpl;
import com.company.api.wk.service.WkMgrSlaveService;
import com.company.po.wk.WkMgrSlave;
import com.company.repo.fw.dao.SqlDao;
import com.company.repo.wk.dao.WkMgrSlaveDao;

@Service(WkMgrSlaveService.BEAN_NAME)
public class WkMgrSlaveServiceImpl extends BaseServiceImpl<WkMgrSlave, String> //
        implements WkMgrSlaveService {
    
    @Autowired
    private SqlDao sqlDao;

    @Autowired
    public void setBaseDao(WkMgrSlaveDao dao) {
        super.setBaseDao(dao);
    }
    
    @Override
    public WkMgrSlave findByMIdAndWId(String managerId, String workerId, String status) {
        String sql = "select * from ZL_WK_MGR_SLAVE where MANAGER_ID=? and WORKER_ID=? and STATUS=?";
        return sqlDao.unique(sql, WkMgrSlave.class, managerId, workerId, status);
    }

    @Override
    public List<WkMgrSlave> findByWkId(String workerId) {
//        String sql = "select account.NAME1 name1, wkMgrSlave.apply_date applyDate" + 
//                    ", wkMgrSlave.modify_date modifyDate, wkMgrSlave.desc1 desc1,"+
//                    " wkMgrSlave.STATUS status FROM zl_account account,("+
//                    "select * from ZL_WK_MGR_SLAVE where WORKER_ID=?"+
//                    ") wkMgrSlave WHERE ACCOUNT.MANAGER_ID=wkMgrSlave.MANAGER_ID ORDER BY wkMgrSlave.apply_date DESC";
//        
        return null;//sqlDao.listAll(sql, type, workerId);
    }
    

    @Override
    public List<WkMgrSlave> findOverTimeApply(Date date) {
        String sql = "SELECT * FROM ZL_WK_MGR_SLAVE WHERE APPLY_DATE < ? AND `STATUS` = ?";
        return sqlDao.listAll(sql, WkMgrSlave.class, date, EnumSlaveStatus.APPLY.name());
    }
}