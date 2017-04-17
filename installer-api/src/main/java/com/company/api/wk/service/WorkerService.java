package com.company.api.wk.service;

import java.util.List;

import com.company.dto.role.WorkerData;
import com.company.dto.vehicle.VehicleData;
import com.company.dto.wk.WkData;
import com.company.po.account.Account;
import com.company.po.admin.Admin;
import com.company.po.bankcard.BankCard;
import com.company.po.indent.Indent;
import com.company.po.wk.WkMgrSlave;
import com.company.po.wk.Worker;
import com.company.po.wk.WorkerAstrict;
import com.company.sf.account.WorkerSf;
import com.company.api.fw.service.StringIdBaseService;
import com.company.dto.Order;
import com.company.util.Dto;

public interface WorkerService extends StringIdBaseService<Worker> {

    String BEAN_NAME = "workerService";

    int count(WorkerSf sf);

    List<?> list(WorkerSf sf, int start, int limit, List<Order> orders);

    String findWorkerSkill(String managerId);

    List<String> findServiceArea(String managerId);

    List<VehicleData> findVehicle(String managerId);

    Dto checkVehicle(Admin admin, Worker worker);

    List<Worker> searchWorkerByName(String searchValue, String managerId);

    Dto astrictIndent(WorkerAstrict workerAstrict);

    void saveWorker(Worker worker, Account account);

    void bindBankCard(BankCard bankCard, Worker worker);

    List<Worker> findRegistrationIds(Indent indent);
    
    void reSetRank();
    
    Dto relieveBankCard(String tradePwd, Worker worker);
    
    Worker findByWorkerId(String id);
    
    Dto changeManager(Worker worker, WkMgrSlave newSlave);

    Worker dealOverTimeApply(String id, String workerId);

    List<WorkerData> workerData(String managerId, String serveType, String regionDist, List<String> sortList);

    List<WkData> findListByManagerId(String managerId);

    void updateDepositAmt(Worker worker);

}