package com.company.api.wk.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.api.account.service.AccountService;
import com.company.api.account.service.EnumAccountStatus;
import com.company.api.account.service.EnumAccountType;
import com.company.api.account.service.EnumCheckStatus;
import com.company.api.bankcard.service.BankCardService;
import com.company.api.dict.service.EnumTradeType;
import com.company.api.dict.service.EnumYesno;
import com.company.api.district.service.AreaService;
import com.company.api.district.service.CityService;
import com.company.api.district.service.ProvService;
import com.company.api.fs.service.EnumSlaveStatus;
import com.company.api.fs.service.FileManagerService;
import com.company.api.fw.Constants;
import com.company.api.fw.DictCodes;
import com.company.api.fw.service.SysDictService;
import com.company.api.fw.service.impl.StringIdBaseServiceImpl;
import com.company.api.fw.service.impl.query.QueryInfo;
import com.company.api.fw.service.impl.query.QueryInfoBuilder;
import com.company.api.fw.util.EnvConfig;
import com.company.api.fw.util.MessagePush;
import com.company.api.indent.service.EnumJpushType;
import com.company.api.indent.service.IndentFreightService;
import com.company.api.indent.service.IndentSctypeSortService;
import com.company.api.mgr.service.ManagerService;
import com.company.api.sctype.service.SctypeService;
import com.company.api.wk.service.WkMgrSlaveService;
import com.company.api.wk.service.WorkerAstrictService;
import com.company.api.wk.service.WorkerService;
import com.company.dto.role.WorkerData;
import com.company.dto.vehicle.VehicleData;
import com.company.dto.wk.WkData;
import com.company.po.account.Account;
import com.company.po.admin.Admin;
import com.company.po.bankcard.BankCard;
import com.company.po.district.Area;
import com.company.po.district.City;
import com.company.po.indent.Indent;
import com.company.po.indent.IndentFreight;
import com.company.po.indent.IndentSort;
import com.company.po.mgr.Manager;
import com.company.po.sctype.Sctype;
import com.company.po.wk.WkMgrSlave;
import com.company.po.wk.Worker;
import com.company.po.wk.WorkerAstrict;
import com.company.sf.account.WorkerSf;
import com.company.dto.Filter;
import com.company.dto.Order;
import com.company.repo.fw.dao.SqlDao;
import com.company.repo.wk.dao.WorkerDao;
import com.company.util.DateUtil;
import com.company.util.DigestUtil;
import com.company.util.Dto;
import com.company.util.New;
import com.company.util.StringUtil;

@Service(WorkerService.BEAN_NAME)
public class WorkerServiceImpl extends StringIdBaseServiceImpl<Worker> implements WorkerService {

    @Autowired
    private SqlDao sqlDao;

    @Autowired
    private AccountService accountService;

    @Autowired
    private ManagerService managerService;

    @Autowired
    private WorkerAstrictService workerAstrictService;

    @Autowired
    private IndentSctypeSortService indentSctypeSortService;

    @Autowired
    private IndentFreightService indentFreightService;

    @Autowired
    private ProvService provService;

    @Autowired
    private CityService cityService;

    @Autowired
    private AreaService areaService;

    @Autowired
    private SctypeService sctypeService;

    @Autowired
    private SysDictService sysDictService;

    @Autowired
    private FileManagerService fileManagerService;

    @Autowired
    private BankCardService bankCardService;

    @Autowired
    private WkMgrSlaveService slaveService;

    @Autowired
    public void setBaseDao(WorkerDao dao) {
        super.setBaseDao(dao);
    }

    @Override
    public List<Worker> list(WorkerSf sf, int start, int limit, List<Order> orders) {
        QueryInfo queryInfo = prepareQuery(sf).order(orders).build();

        List<Worker> _list = sqlDao.list(queryInfo.getSql(), start, limit, Worker.class, queryInfo.getParArr());
        List<Worker> list = New.list();
        for (Worker worker : _list) {
            String accountId = worker.getAccountId();
            worker.setAccount(accountService.find(accountId));
            list.add(worker);
        }

        return list;
    }

    @Override
    public int count(WorkerSf sf) {
        QueryInfo queryInfo = prepareQuery(sf).build();
        return sqlDao.count(queryInfo.getCountSql(), queryInfo.getParArr());
    }

    private QueryInfoBuilder prepareQuery(WorkerSf sf) {
        if (sf == null) {
            sf = new WorkerSf();
        }
        if (sf.getAccount() == null) {
            sf.setAccount(new Account());
        }
        StringBuilder sql = new StringBuilder("select worker.CODE1, worker.ACCOUNT_ID, worker.STATUS, worker.ROLE_STATUS, worker.VERIFIER, worker.ID, worker.VEHICLE_STATUS, worker.HOLD_WAY from ZL_WORKER worker, ZL_ACCOUNT account where worker.ID = account.WORKER_ID ");//

        List<Object> sqlPars = New.list();

        if (StringUtil.isNotBlank(sf.getAccount().getName1())) {
            sql.append(" and account.NAME1 LIKE ?");
            sqlPars.add("%" + sf.getAccount().getName1() + "%");
        }
        if (StringUtil.isNotBlank(sf.getAccount().getMobile())) {
            sql.append(" and account.MOBILE LIKE ?");
            sqlPars.add("%" + sf.getAccount().getMobile() + "%");
        }

        if (StringUtil.isNotBlank(sf.getServiceCategory()) && !StringUtil.isNotBlank(sf.getServiceCategory2())) {
            sql.append(" and worker.SERVICE_CATEGORY=?");
            sqlPars.add(sf.getServiceCategory());
        }
        if (StringUtil.isNotBlank(sf.getServiceCategory()) && StringUtil.isNotBlank(sf.getServiceCategory2())) {
            sql.append(" and (worker.SERVICE_CATEGORY=? or worker.SERVICE_CATEGORY=?)");
            sqlPars.add(sf.getServiceCategory());
            sqlPars.add(sf.getServiceCategory2());
        }

        QueryInfoBuilder builder = QueryInfoBuilder.ins(sql.toString()).sqlPars(sqlPars)//
                .andContains("worker.CODE1", sf.getCode1()) // 工人工号
                .andEq("worker.STATUS", sf.getStatus()) // 审核状态
                .andEq("worker.ROLE_STATUS", sf.getRoleStatus()) // 角色状态
                .andEq("worker.MANAGER_ID", sf.getManagerId());
        return builder;
    }

    @Override
    public Worker findByWorkerId(String id) {
        if (StringUtil.isEmpty(id)) {
            return null;
        }
        Worker worker = super.find(id);
        Account account = accountService.find(worker.getAccountId());
        if (StringUtil.isNotBlank(account.getIdImgUrl())) {
            account.setIdImg(fileManagerService.getFileUrlByFilepath(account.getIdImgUrl()));
        }
        if (StringUtil.isNotBlank(account.getAvatar())) {
            account.setAvatarImg(EnvConfig.DOWNLOAD_BASEPATH + account.getAvatar());
        }
        if (worker.getDrivingImg() != null) {
            worker.setLicence(fileManagerService.getFileUrlByFilepath(worker.getDrivingImg()));
        }
        if (worker.getVehicleImg() != null) {
            worker.setLicence1(fileManagerService.getFileUrlByFilepath(worker.getVehicleImg()));
        }
        City city = cityService.find(worker.getServiceCity());
        String serviceCounty = worker.getServiceCounty();
        StringBuilder areas = new StringBuilder();
        if (StringUtil.isNotBlank(serviceCounty)) {
            String[] countys = serviceCounty.split("\\|");
            List<String> list = Arrays.asList(countys);
            List<Area> areaList = areaService.findList(Filter.in("code1", list));
            for (Area area : areaList) {
                areas.append(area.getName1() + "、");
            }
        }
        String serveArea = areas.toString();
        if (serveArea.length() > 0) {
            serveArea = serveArea.substring(0, serveArea.length() - 1);
        }
        String serviceRegion = "";
        if (city != null && serveArea != null) {
            serviceRegion = city.getName1() + "--" + serveArea;
        } else if (city != null) {
            serviceRegion = city.getName1();
        }
        worker.setServiceRegion(serviceRegion);
        String serviceCategorys = worker.getServiceCategory();

        if (StringUtil.isNotEmpty(serviceCategorys) && serviceCategorys.length() > 1) {
            serviceCategorys = serviceCategorys.substring(1, serviceCategorys.length() - 1);
            String[] serviceCategory = serviceCategorys.split("\\|");
            String serviceType = "";
            for (String string : serviceCategory) {
                Sctype sctype = sctypeService.findOne(Filter.eq("code1", string));
                String type = sctype.getName1();
                if (StringUtil.isEmpty(type)) {
                    continue;
                }
                serviceType += type + "，";
            }
            if (serviceType.length() > 0) {
                serviceType = serviceType.substring(0, serviceType.length() - 1);
                worker.setServiceType(serviceType);
            }
        }

        String skillTypes = worker.getSkillType();
        skillTypes = skillTypes.substring(0, skillTypes.length() - 1);
        if (StringUtil.isNotEmpty(skillTypes)) {
            String[] skillType = skillTypes.split("\\|");
            Set<String> skillTypeSet = New.hashSet();
            for (String string : skillType) {
                skillTypeSet.add(string);
            }

            String serviceCraft = "";
            for (String string : skillTypeSet) {
                String sort = sysDictService.text(DictCodes.SERVICE_SORT, string);
                if (StringUtil.isEmpty(sort)) {
                    continue;
                }
                serviceCraft += sort + "，";
            }
            if (serviceCraft.length() > 0) {
                serviceCraft = serviceCraft.substring(0, serviceCraft.length() - 1);
                worker.setServiceCraft(serviceCraft);
            }
        }
        worker.setAccount(account);
        return worker;
    }

    @Override
    public Worker update(Worker entity) {
        return super.update(entity);
    }

    @Override
    public String findWorkerSkill(String managerId) {
        String sql = "select distinct SKILL_TYPE from ZL_WORKER where MANAGER_ID=?";
        List<String> skillList = sqlDao.listAll(sql, null, managerId);
        Set<String> skillSet = New.hashSet();
        for (String skill : skillList) {
            skill = skill.substring(1, skill.length() - 1);
            String[] split = skill.split("\\|");
            for (String string : split) {
                skillSet.add(string);
            }
        }
        String serviceCraft = "";
        for (String string : skillSet) {
            String sort = sysDictService.text(DictCodes.SERVICE_SORT, string);
            if (StringUtil.isEmpty(sort)) {
                continue;
            }
            serviceCraft += sort + "、";
        }
        if (serviceCraft.length() > 0) {
            serviceCraft = serviceCraft.substring(0, serviceCraft.length() - 1);
        }
        return serviceCraft;

    }

    @Override
    public List<String> findServiceArea(String managerId) {

        String citysql = "select distinct SERVICE_CITY from ZL_WORKER where MANAGER_ID=?";
        List<String> cityList = sqlDao.listAll(citysql, null, managerId);

        String countysql = "select distinct SERVICE_COUNTY from ZL_WORKER where MANAGER_ID=?";
        List<String> countyList = sqlDao.listAll(countysql, null, managerId);
        Set<String> citySet = New.hashSet();
        for (String city : cityList) {
            citySet.add(city);
        }
        List<String> serviceCityList = New.list();
        for (String city : citySet) {

            City findCity = cityService.findCityByCode1(city);

            String serviceCity = findCity.getName1() + "-";
            Set<String> countySet = New.hashSet();
            for (String countys : countyList) {
                if (countys.indexOf(city) != -1) {
                    String[] county = countys.split("\\|");
                    for (String string : county) {
                        countySet.add(string);
                    }
                    List<String> list = New.list(countySet);
                    List<Area> areaList = areaService.findList(Filter.in("code1", list));
                    for (Area area : areaList) {
                        serviceCity += area.getName1() + "、";
                    }
                    if (serviceCity.length() > 0) {
                        serviceCity = serviceCity.substring(0, serviceCity.length() - 1);
                    }
                    serviceCityList.add(serviceCity);
                }
            }

        }
        return serviceCityList;
    }

    @Override
    public List<VehicleData> findVehicle(String managerId) {
        String sql = "SELECT VEHICLE as value,COUNT(*) AS text FROM ZL_WORKER WHERE MANAGER_ID=? AND VEHICLE_STATUS = 'PASS' GROUP BY VEHICLE";

        return sqlDao.listAll(sql, VehicleData.class, managerId);
    }

    @Override
    public Dto checkVehicle(Admin admin, Worker worker) {
        Dto ret = new Dto();
        ret.put(TAG_SUCCESS, false);
        String sql = "UPDATE ZL_WORKER SET VEHICLE_STATUS = ?, VERIFIER = ?, VERIFIER_ID = ?, MODIFY_DATE = ? WHERE ID = ?";
        int execUpdate = sqlDao.execUpdate(sql, worker.getVehicleStatus(), admin.getName1(), admin.getId(), new Date(), worker.getId());
        if (execUpdate > 0) {
            ret.put(TAG_SUCCESS, true);
            return ret;
        }
        return ret;

    }

    @Override
    public List<Worker> searchWorkerByName(String searchValue, String managerId) {
        String sql = "SELECT worker.ID, worker.TOTAL_INDENT, worker.TOTAL_PROFIT, worker.RANK, worker.STAR_LEVEL, account.ID as 'accountId' from ZL_WORKER worker, ZL_ACCOUNT account WHERE worker.ACCOUNT_ID = account.ID AND worker.MANAGER_ID = ? AND account.NAME1 like ?";
        List<Worker> list = sqlDao.listAll(sql, Worker.class, managerId, "%" + searchValue + "%");
        return list;
    }

    @Override
    public Dto astrictIndent(WorkerAstrict workerAstrict) {
        Dto ret = new Dto();
        ret.put(TAG_SUCCESS, true);
        workerAstrictService.save(workerAstrict);
        String sql = "UPDATE ZL_WORKER SET ASTRICT_DATE = ? WHERE ID = ?";
        int i = sqlDao.execUpdate(sql, DateUtil.addDays(new Date(), workerAstrict.getAstrictTime()), workerAstrict.getWorkerId());
        if (i != 1) {
            ret.put(TAG_SUCCESS, false);
            ret.put(TAG_MSG, "操作失败");
            return ret;
        }
        {
            Account account = accountService.find(workerAstrict.getwAccountId());
            Map<String, String> extras = New.hashMap();
            extras.put("id", "");
            extras.put("pushType", EnumJpushType.HOME_PAGE.name());
            extras.put("accountType", EnumAccountType.WORKER.name());
            List<String> registrationIds = new ArrayList<String>();
            if (StringUtil.isNotBlank(account.getRegistrationId())) {
                registrationIds.add(account.getRegistrationId());
            }
            if (registrationIds.size() > 0) {
                MessagePush push = new MessagePush("经理人已限制您抢单，期限" + workerAstrict.getAstrictTime() + "天", "众联工匠", extras);
                push.pushMsgToRegistrationIds(registrationIds);
            }
        }

        return ret;
    }

    @Override
    public void saveWorker(Worker worker, Account account) {
        Dto skill = new Dto();
        String[] skills = worker.getSkillType().split("\\|");
        for (int i = 0; i < skills.length; i++) {
            skill.put(skills[i], skills[i]);
        }
        String string = Constants.MULTI_VALUE_SEP;
        Iterator<String> iterator = skill.keySet().iterator();
        while (iterator.hasNext()) {
            string += (String) iterator.next() + Constants.MULTI_VALUE_SEP;
        }
        worker.setSkillType(string);
        worker.setServiceCategory(Constants.MULTI_VALUE_SEP + worker.getServiceCategory());
        worker.setStatus(EnumCheckStatus.UNCHECK.name());
        worker.setRoleStatus(EnumAccountStatus.ENABLED.name());
        worker.setVehicleStatus(EnumCheckStatus.UNCHECK.name());
        worker.setHoldWay("NONE");
        worker.setAccountId(account.getId());
        worker.setStarLevel(5);
        worker.setBalAmt(BigDecimal.ZERO);
        worker.setCarNumber("");
        worker.setCreditScore(new BigDecimal(5));
        worker.setDepositAmt(BigDecimal.ZERO);
        worker.setTotalIndent(0);
        worker.setTotalProfit(BigDecimal.ZERO);
        worker.setAstrictDate(new Date());
        super.save(worker);
        account.setWorkerType(1);
        account.setWorkerId(worker.getId());
        accountService.update(account, "pwd");

        WkMgrSlave slave = new WkMgrSlave();
        slave.setWorkerId(worker.getId());
        slave.setManagerId(worker.getManagerId());
        slave.setApplyDate(new Date());
        if (worker.getManagerId().equals(EnumYesno.YES.name())) {
            slave.setStatus(EnumSlaveStatus.AGREE.name());
        } else {
            slave.setStatus(EnumSlaveStatus.APPLY.name());
        }
        slaveService.save(slave);

        Account findAccount = accountService.findOne(Filter.eq("managerId", worker.getManagerId()));
        Map<String, String> extras = New.hashMap();
        extras.put("id", "");
        extras.put("pushType", EnumJpushType.WORKER_APPLY.name());
        extras.put("accountType", EnumAccountType.MANAGER.name());
        List<String> registrationIds = new ArrayList<String>();
        if (StringUtil.isNotBlank(findAccount.getRegistrationId())) {
            registrationIds.add(findAccount.getRegistrationId());
        }
        if (registrationIds.size() > 0) {
            MessagePush messagePush = new MessagePush("您有新的工匠申请", "众联工匠", extras);
            //如果自己已经是经理人，并且经理人审核通过，不再推送
            if (account.getManagerType().equals(1) && StringUtil.isNotBlank(account.getManagerId())) {
                Manager manager = managerService.findByManagerId(account.getManagerId());
                if (!manager.getStatus().equals(EnumCheckStatus.PASS.name())) {
                    messagePush.pushMsgToRegistrationIds(registrationIds);
                }
            } else {
                messagePush.pushMsgToRegistrationIds(registrationIds);
            }
        }
    }

    @Override
    public void bindBankCard(BankCard bankCard, Worker worker) {
        bankCardService.save(bankCard);
        worker.setBankId(bankCard.getId());
        update(worker);
    }

    @Override
    public List<Worker> findRegistrationIds(Indent indent) {
        QueryInfo queryInfo = registrationPrepareQuery(indent).build();
        List<Worker> workerAll = sqlDao.listAll(queryInfo.getSql(), Worker.class, queryInfo.getParArr());
        return workerAll;
    }

    private QueryInfoBuilder registrationPrepareQuery(Indent indent) {
        if (indent == null) {
            indent = new Indent();
        }
        List<Object> sqlPars = New.list();
        StringBuilder sql = new StringBuilder("SELECT account.REGISTRATION_ID as 'registrationId' from ZL_WORKER worker LEFT JOIN ZL_ACCOUNT account on worker.ACCOUNT_ID = account.ID WHERE 1=1")//
        .append(" AND worker.SERVICE_COUNTY like ? AND worker.SERVICE_CATEGORY like ? ");
        sqlPars.add("%" + indent.getRegionDist() + "%");
        sqlPars.add("%" + indent.getServeType() + "%");
        List<IndentSort> indentSortList = indentSctypeSortService.findList(Filter.eq("indentId", indent.getId()));
        for (IndentSort indentSort : indentSortList) {
            sql.append(" AND worker.SKILL_TYPE like ?");
            sqlPars.add("%" + indentSort.getCode1() + "%");
        }
        if (EnumYesno.YES.name().equals(indent.getIndentAssign())) {
            sql.append(" AND worker.MANAGER_ID = ?");
            sqlPars.add(indent.getManagerId());
        }
        IndentFreight indentFreight = indentFreightService.findOne(Filter.eq("indentId", indent.getId()));
        if (indentFreight != null) {
            sql.append(" AND VEHICLE_STATUS = ?");
            sqlPars.add(EnumCheckStatus.PASS.name());
        }
        QueryInfoBuilder builder = QueryInfoBuilder.ins(sql.toString()).sqlPars(sqlPars);
        return builder;
    }

    @Override
    public Dto relieveBankCard(String tradePwd, Worker worker) {
        Dto rt = new Dto();
        rt.put(TAG_SUCCESS, false);
        // 验证密码
        if (!DigestUtil.md5(tradePwd).equals(worker.getTradePwd())) {
            rt.put(TAG_MSG, "密码不正确，请输入密码");
            return rt;
        }
        bankCardService.delByOwnerId(worker.getId());
        worker.setBankId("");
        update(worker);
        rt.put(TAG_MSG, "成功解除绑定");
        rt.put(TAG_SUCCESS, true);
        return rt;
    }

    @Override
    public void reSetRank() {

        // 更新工匠的排名
        QueryInfo queryInfo = rankPrepareQuery().build();
        sqlDao.execUpdate(queryInfo.getSql(), queryInfo.getParArr());
    }

    private QueryInfoBuilder rankPrepareQuery() {
        StringBuilder sql = new StringBuilder("UPDATE ZL_WORKER INNER JOIN (")//
        .append(" SELECT @rownum/*'*/:=/*'*/@rownum+1 as ROWNUM, ID from (")//
        .append(" SELECT @rownum/*'*/:=/*'*/0,ID FROM ZL_WORKER")//
        .append(" LEFT JOIN (SELECT WORKER_ID, SUM(CUR_AMT) AS AMT")//
        .append(" FROM ZL_WORKER_TRADE WHERE 1=1 AND TRADE_TYPE=? AND date_format(TRADE_DATE,'%Y-%m')=date_format(now(),'%Y-%m') GROUP BY WORKER_ID) SUM_TRADE");//
        List<Object> sqlPars = new ArrayList<Object>();
        sqlPars.add(EnumTradeType.EARNING.name());
        sql.append(" ON ID = WORKER_ID")//
        .append(" ORDER BY AMT DESC")//
        .append(" ) RAND_WORKER")//
        .append(" ) RANK")//
        .append(" SET PERIOD_RANK = RANK.ROWNUM")//
        .append(" WHERE RANK.ID = ZL_WORKER.ID");

        QueryInfoBuilder builder = QueryInfoBuilder.ins(sql.toString()).sqlPars(sqlPars);
        return builder;
    }

    public Dto changeManager(Worker worker, WkMgrSlave newSlave) {
        Dto ret = new Dto();
        ret.put(TAG_SUCCESS, false);

        WkMgrSlave slave = slaveService.findByMIdAndWId(worker.getManagerId(), worker.getId(), EnumSlaveStatus.AGREE.name());
        Account managerAccount = accountService.findByManagerId(newSlave.getManagerId());
        Account workerAccount = accountService.findByWorkerId(worker.getId());
        Account managerAccount1 = accountService.findByManagerId(worker.getManagerId());
        if (slave != null) {
            slave.setStatus(EnumSlaveStatus.ABOLISH.name());
            slaveService.update(slave);
            {// 推送给原经理人，有工匠退出
                Map<String, String> extras = new HashMap<String, String>();
                extras.put("id", "");
                extras.put("pushType", EnumJpushType.WORKER_LIST.name());
                List<String> registrationIds = new ArrayList<String>();
                if (StringUtil.isNotBlank(managerAccount1.getRegistrationId())) {
                    registrationIds.add(managerAccount1.getRegistrationId());
                    MessagePush push = new MessagePush("工匠" + workerAccount.getName1() + "已退出您的团队", "众联工匠", extras);
                    push.pushMsgToRegistrationIds(registrationIds);
                }
            }
        }

        newSlave.setApplyDate(new Date());
        newSlave.setWorkerId(worker.getId());
        newSlave.setStatus(EnumSlaveStatus.APPLY.name());
        slaveService.save(newSlave);
        worker.setManagerId(newSlave.getManagerId());
        worker.setManagerIdea("");
        update(worker);

        {// 推送给新经理人，有工匠申请加入
            Map<String, String> extras = new HashMap<String, String>();
            extras.put("id", "");
            extras.put("pushType", EnumJpushType.WORKER_APPLY.name());
            List<String> registrationIds = new ArrayList<String>();
            if (StringUtil.isNotBlank(managerAccount.getRegistrationId())) {
                registrationIds.add(managerAccount.getRegistrationId());
            }
            MessagePush push = new MessagePush("工匠" + workerAccount.getName1() + "申请加入您的团队", "众联工匠", extras);
            if (registrationIds.size() > 0) {
                push.pushMsgToRegistrationIds(registrationIds);
            }
        }

        ret.put(TAG_SUCCESS, true);
        return ret;
    }

    @Override
    public Worker dealOverTimeApply(String id, String workerId) {
        // 忽略工匠申请
        String sql1 = "UPDATE ZL_WORKER SET MANAGER_IDEA = ? WHERE ID = ?";
        sqlDao.execUpdate(sql1, EnumYesno.NO.name(), workerId);

        String sql2 = "UPDATE ZL_WK_MGR_SLAVE SET `STATUS` = ? WHERE ID = ?";
        sqlDao.execUpdate(sql2, EnumSlaveStatus.NEGLECT.name(), id);

        return findByWorkerId(workerId);
    }

    @Override
    public List<WorkerData> workerData(String managerId, String serveType, String regionDist, List<String> sortList) {
        QueryInfo queryInfo = PrepareQueryWorkerData(managerId, serveType, regionDist, sortList).build();


        List<WorkerData> _list = sqlDao.listAll(queryInfo.getSql(), WorkerData.class, queryInfo.getParArr());
        return _list;
    }

    private QueryInfoBuilder PrepareQueryWorkerData(String managerId, String serveType, String regionDist, List<String> sortList) {

        List<Object> sqlPars = new ArrayList<Object>();
        StringBuilder sql = new StringBuilder() //
        .append("select worker.ID as V, account.NAME1 as N") //
        .append(" from ZL_WORKER worker left join ZL_ACCOUNT account on worker.ACCOUNT_ID = account.ID") //
        .append(" where worker.STATUS='").append(EnumCheckStatus.PASS.name()).append("'") //
        .append(" and worker.ROLE_STATUS='").append(EnumAccountStatus.ENABLED.name()).append("'")//
        .append(" AND worker.MANAGER_ID = ? AND worker.SERVICE_COUNTY like ? AND worker.SERVICE_CATEGORY like ? ");
        sqlPars.add(managerId);
        sqlPars.add("%" + regionDist + "%");
        sqlPars.add("%" + serveType + "%");
        for (String sort : sortList) {
            sql.append(" AND worker.SKILL_TYPE like ?");
            sqlPars.add("%" + sort + "%");
        }
        sql.append(" and worker.ROLE_STATUS='").append(EnumAccountStatus.ENABLED.name()).append("'") //
        .append(" order by CODE1 ASC");

        QueryInfoBuilder builder = QueryInfoBuilder.ins(sql.toString()).sqlPars(sqlPars);
        return builder;
    }

    @Override
    public List<WkData> findListByManagerId(String managerId) {
        String sql = "SELECT account.NAME1 name1, ACCOUNT.MOBILE mobile, wkmgr.desc1 desc1,"
                +" wkmgr.apply_date applyDate, wkmgr.status status FROM zl_account account, ("
                +"SELECT * from zl_wk_mgr_slave WHERE MANAGER_ID=?"
                +" AND `STATUS` <> 'NEGLECT' ORDER BY APPLY_DATE DESC) wkmgr where"
                +" ACCOUNT.WORKER_ID=wkmgr.WORKER_ID";

        return sqlDao.listAll(sql, WkData.class, managerId);
    }

    @Override
    public void updateDepositAmt(Worker worker) {
        String sql = "update ZL_WORKER set DEPOSIT_AMT = ? where ID = ?";
        sqlDao.execUpdate(sql, worker.getDepositAmt(), worker.getId());
    }

}