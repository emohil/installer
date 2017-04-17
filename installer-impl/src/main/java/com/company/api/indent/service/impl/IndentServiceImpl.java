package com.company.api.indent.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.api.account.service.AccountService;
import com.company.api.account.service.EnumAccountType;
import com.company.api.aparty.service.ApartyService;
import com.company.api.dict.service.EnumYesno;
import com.company.api.fs.service.FileIndexService;
import com.company.api.fs.service.FileManagerService;
import com.company.api.fw.service.impl.StringIdBaseServiceImpl;
import com.company.api.fw.service.impl.query.QueryInfo;
import com.company.api.fw.service.impl.query.QueryInfoBuilder;
import com.company.api.fw.util.MessagePush;
import com.company.api.indent.service.EnumEvaluateStatus;
import com.company.api.indent.service.EnumExceptionResultStatus;
import com.company.api.indent.service.EnumExecuteStatus;
import com.company.api.indent.service.EnumIndentExcepStatus;
import com.company.api.indent.service.EnumIndentReleaseStatus;
import com.company.api.indent.service.EnumIndentStatus;
import com.company.api.indent.service.EnumIndentVisitSource;
import com.company.api.indent.service.EnumJpushType;
import com.company.api.indent.service.IndentContactService;
import com.company.api.indent.service.IndentContentService;
import com.company.api.indent.service.IndentExceptionDetailService;
import com.company.api.indent.service.IndentExceptionService;
import com.company.api.indent.service.IndentFreightService;
import com.company.api.indent.service.IndentNodeService;
import com.company.api.indent.service.IndentNodeStepItemService;
import com.company.api.indent.service.IndentNodeStepService;
import com.company.api.indent.service.IndentSctypeSortService;
import com.company.api.indent.service.IndentService;
import com.company.api.item.service.ItemPriceService;
import com.company.api.item.service.ItemService;
import com.company.api.mgr.service.ManagerService;
import com.company.api.scnode.service.EnumScnodeStepType;
import com.company.api.scnode.service.ScnodeService;
import com.company.api.scnode.service.ScnodeStepItemService;
import com.company.api.scnode.service.ScnodeStepService;
import com.company.api.sctype.service.SctypeContentService;
import com.company.api.sctype.service.SctypeNodeService;
import com.company.api.sctype.service.SctypeService;
import com.company.api.sctype.service.SctypeSortService;
import com.company.api.wk.service.WorkerService;
import com.company.po.account.Account;
import com.company.po.admin.Admin;
import com.company.po.aparty.Aparty;
import com.company.po.fs.FileIndex;
import com.company.po.indent.Indent;
import com.company.po.indent.IndentContact;
import com.company.po.indent.IndentContent;
import com.company.po.indent.IndentException;
import com.company.po.indent.IndentExceptionDetail;
import com.company.po.indent.IndentFreight;
import com.company.po.indent.IndentNode;
import com.company.po.item.Item;
import com.company.po.mgr.Manager;
import com.company.po.sctype.Sctype;
import com.company.po.sctype.SctypeContent;
import com.company.po.wk.Worker;
import com.company.sf.indent.IndentSf;
import com.company.dto.Filter;
import com.company.dto.Order;
import com.company.dto.SysDict;
import com.company.repo.fw.dao.SqlDao;
import com.company.repo.indent.dao.IndentDao;
import com.company.util.BooleanUtil;
import com.company.util.DateUtil;
import com.company.util.Dto;
import com.company.util.New;
import com.company.util.NumberUtil;
import com.company.util.ObjectUtil;
import com.company.util.StringUtil;

@Service(IndentService.BEAN_NAME)
public class IndentServiceImpl extends StringIdBaseServiceImpl<Indent> implements IndentService {

    
    private static final String PUSH_TYPE = "pushType";
    
    @Autowired
    private SqlDao sqlDao;

    @Autowired
    private IndentContactService indentContactService;
    
    @Autowired
    private IndentExceptionService indentExceptionService;
    
    @Autowired
    private IndentExceptionDetailService indentExceptionDetailService;

    @Autowired
    private SctypeNodeService sctypeNodeService;

    @Autowired
    private SctypeSortService sctypeSortService;

    @Autowired
    private SctypeContentService sctypeContentService;

    @Autowired
    private ScnodeService scnodeService;

    @Autowired
    private IndentNodeService indentNodeService;
    
    @Autowired
    private IndentSctypeSortService indentSctypeSortService;

    @Autowired
    private ScnodeStepService scnodeStepService;

    @Autowired
    private IndentNodeStepService indentNodeStepService;

    @Autowired
    private SctypeService sctypeService;

    @Autowired
    private ScnodeStepItemService scnodeStepItemService;

    @Autowired
    private IndentNodeStepItemService indentNodeStepItemService;

    @Autowired
    private IndentContentService indentContentService;

    @Autowired
    private ItemPriceService itemPriceService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private ApartyService apartyService;

    @Autowired
    private IndentFreightService indentFreightService;

    @Autowired
    private WorkerService workerService;
    
    @Autowired
    private AccountService accountService;
    
    @Autowired
    private ManagerService managerService;
    
    @Autowired
    private FileIndexService fileIndexService;
    
    @Autowired
    private FileManagerService fileManagerService;

    @Autowired
    public void setBaseDao(IndentDao dao) {
        super.setBaseDao(dao);
    }

    
    @Override
    public Indent findByCode1(String code1) {
        return findOne(Filter.eq("code1", code1));
    }
    
    @Override
    public List<Indent> list(IndentSf sf, int start, int limit, List<Order> orders) {
        if ("1".equals(sf.getIsAPI())) {
            QueryInfo queryInfo = prepareQuery1(sf).order(orders).build();

            List<Indent> _list = sqlDao.list(queryInfo.getSql(), start, limit, Indent.class, queryInfo.getParArr());

            return _list;
        } else if ("2".equals(sf.getIsAPI())) {
            QueryInfo queryInfo = prepareQuery2(sf).order(orders).build();

            List<Indent> _list = sqlDao.list(queryInfo.getSql(), start, limit, Indent.class, queryInfo.getParArr());

            return _list;
        } else {

            QueryInfo queryInfo = prepareQuery(sf).order(orders).build();

            List<Indent> _list = sqlDao.list(queryInfo.getSql(), start, limit, Indent.class, queryInfo.getParArr());

            // 把查出的contactList freightList 放到对应的 Indent
            for (Indent indent : _list) {
                String id = indent.getId();
                Sctype sctype = sctypeService.find(indent.getServeType());
                indent.setServeTypeDisp(sctype.getName1());
                indent.setContact(indentContactService.findOne(Filter.eq("indentId", id)));
                indent.setIndentFreight(indentFreightService.findOne(Filter.eq("indentId", id)));
                indent.setAparty(apartyService.findOne(Filter.eq("id", indent.getApartyId())));
            }
            return _list;

        }
    }

    @Override
    public int count(IndentSf sf) {
        if ("1".equals(sf.getIsAPI())) {
            QueryInfo queryInfo = prepareQuery1(sf).build();
            return sqlDao.count(queryInfo.getCountSql(), queryInfo.getParArr());
        } else if ("2".equals(sf.getIsAPI())) {
            QueryInfo queryInfo = prepareQuery2(sf).build();
            return sqlDao.count(queryInfo.getCountSql(), queryInfo.getParArr());
        } else {
            QueryInfo queryInfo = prepareQuery(sf).build();
            return sqlDao.count(queryInfo.getCountSql(), queryInfo.getParArr());
        }
    }

    
    @Override
    public void saveIndent(Indent indent) {
        String code1 = prepareIndentCode1(indent);
        indent.setCode1(code1);
        indent.setCrtDate(new Date());
        indent.setApSettlement(EnumYesno.NO.name());
        indent.setArSettlement(EnumYesno.NO.name());
        indent.setStatus(EnumIndentStatus.NORMAL.name());
        indent.setEvaluateStatus(EnumEvaluateStatus.UNEVALUATED.name());
        indent.setExecuteStatus(EnumExecuteStatus.BEFORE.name());
        if (StringUtil.isBlank(indent.getReleaseStatus())) {
            indent.setReleaseStatus(EnumIndentReleaseStatus.UNRELEASED.name());
        }
        indent.setProgStatus("");
        indent.setExcepStatus("");
        indent.setActualPay(new BigDecimal(0));
        indent.setManagerAward(new BigDecimal(0));
        indent.setWorkerAward(new BigDecimal(0));
        indent.setProfit(new BigDecimal(0));

        save(indent);
        IndentContact indentContact = indent.getContact();
        indentContact.setRegionProv(indent.getRegionProv());
        indentContact.setRegionCity(indent.getRegionCity());
        indentContact.setRegionDist(indent.getRegionDist());
        indentContact.setIndentId(indent.getId());
        indentContactService.save(indentContact);

        IndentFreight indentFreight = indent.getIndentFreight();
        if (indentFreight != null) {
            indentFreight.setIndentId(indent.getId());
            indentFreightService.save(indentFreight);
        }
        indentContentService.saveIndentContent(indent);
        indentNodeService.saveIndentNodes(indent);
    }

    private String prepareIndentCode1(Indent indent) {
        
        String date = DateUtil.format(new Date(), "yyMM");
        Dto indentPriceDto = indent.getIndentPriceDto();
        Dto sortName = new Dto();
        Iterator<String> iterator = indentPriceDto.keySet().iterator();
        List<String> keyList = New.list();
        while (iterator.hasNext()) {
            String key = (String) iterator.next();
            if (key.endsWith("_code1")) {
                String replace = key.replace("_code1", "_checked");
                if (BooleanUtil.getBoolean(indentPriceDto.get(replace))) {
                    keyList.add(key);
                }
            }
        }
        for (String string : keyList) {
            String typeCode = string.substring(string.indexOf("_") + 1, string.lastIndexOf("_"));
            SctypeContent sctypeContent = sctypeContentService.find(typeCode);
            String code = sctypeContent.getSctypeSortId();
            sortName.put(code, code);
        }
        String pre = "";
        if (sortName.size() == 1) {
            Iterator<String> iterator2 = sortName.keySet().iterator();
            while (iterator2.hasNext()) {
                pre = (String) sortName.get((String) iterator2.next());
            }
        } else {
            // 多个服务类别 B 包 加
            pre = indent.getServeType() + "B";
        }
        Aparty aparty = apartyService.find(indent.getApartyId());
        String preCode1 = pre + "-" + aparty.getCode1() + indent.getRegionCity() + "-" + date;
        // 查找最大code1
        String sql = "SELECT MAX(CODE1) as CODE1 FROM ZL_INDENT WHERE CODE1 LIKE ?";
        String str = sqlDao.unique(sql, Indent.class, preCode1 + "%").getCode1();
        if (!StringUtil.isNotBlank(str)) {
            
            str = preCode1;
        }
        int count = NumberUtil.toInt(str.replace(preCode1, ""), 0);
        String format = NumberUtil.format(count + 1, "0000");

        String code1 = preCode1 + format;
        return code1;
    }



    private QueryInfoBuilder prepareQuery(IndentSf sf) {
        if (sf == null) {
            sf = new IndentSf();
        }
        if (sf.getIndentFreight() == null) {
            sf.setIndentFreight(new IndentFreight());
        }

        if (sf.getContact() == null) {
            sf.setContact(new IndentContact());
        }
        if (sf.getAparty() == null) {
            sf.setAparty(new Aparty());
        }

        if (sf.getItem() == null) {
            sf.setItem(new Item());
        }

        List<Object> sqlPars = New.list();

        StringBuilder sql = new StringBuilder()//
                .append("select indent.ID")//
                .append(", indent.CODE1")//
                .append(", newIndent.CODE1 as 'newCode1'")//
                .append(", indent.APARTY_ID")//
                .append(", indent.SERVE_TYPE")//
                .append(", indent.EXECUTE_STATUS")//
                .append(", indent.EVALUATE_STATUS")
                .append(", indent.RELEASE_STATUS")
                .append(", indent.ITEM_ID")//
                .append(", indent.WORKER_ID")//
                .append(", indent.SERVICE_DATE")//
                .append(", indent.STATUS")//
                .append(", indent.EXCEP_STATUS")//
                .append(", wka.NAME1 as 'WORKER_NAME1'")//
                .append(" from ZL_INDENT indent"); //

        sql.append(" inner join ZL_ITEM item on item.ID = indent.ITEM_ID");//
        sql.append(" inner join ZL_APARTY aparty on aparty.ID = item.APARTY_ID"); //
        sql.append(" left join ZL_INDENT_CONTACT contact on contact.INDENT_ID = indent.ID"); //
        sql.append(" left join ZL_WORKER wk on wk.ID = indent.WORKER_ID");//
        sql.append(" left join ZL_ACCOUNT wka on wka.WORKER_ID = wk.ID");
        sql.append(" left join ZL_INDENT newIndent on newIndent.ORIGINAL_CODE1 = indent.CODE1");

        if (StringUtil.isNotEmpty(sf.getManagerName1())) {
            sql.append(" left join ZL_MANAGER mgr on mgr.ID = indent.MANAGER_ID"); //
            sql.append(" left join ZL_ACCOUNT mgra on mgra.MANAGER_ID = mgr.ID"); //
        }
        sql.append(" where 1=1");

        if (StringUtil.isNotEmpty(sf.getIndentType())) {
            sql//
            .append(" and indent.ID in (")//
                    .append("  select INDENT_ID from ZL_INDENT_SORT where CODE1=?")//
                    .append(")");
            sqlPars.add(sf.getIndentType());
        }

        QueryInfoBuilder builder = QueryInfoBuilder.ins(sql.toString()).sqlPars(sqlPars) //
                .andContains("item.NAME1", sf.getItem().getName1())//项目名称
                .andContains("wka.NAME1", sf.getWorkerName1())//工匠名称
                .andContains("mgra.NAME1", sf.getManagerName1())//经理人名称
                .andContains("aparty.NAME1", sf.getAparty().getName1())//甲方名称
                .andContains("contact.NAME1", sf.getContact().getName1())//业主名字
                .andContains("contact.MOBILE", sf.getContact().getMobile())//业主手机
                .andContains("contact.SUP_NAME1", sf.getContact().getSupName1())//监理名字
                .andContains("contact.SUP_MOBILE", sf.getContact().getSupMobile())//监理手机
                .andEq("indent.ITEM_ID", sf.getItemId())//项目列表用   根据项目ID统计订单量
                .andEq("indent.REGION_PROV", sf.getRegionProv()) // 搜索省
                .andEq("indent.REGION_CITY", sf.getRegionCity()) // 搜索市
                .andEq("indent.REGION_DIST", sf.getRegionDist()) // 搜索地区
                .andEq("indent.EXECUTE_STATUS", sf.getExecuteStatus())// 执行状态
                .andIn("indent.EXECUTE_STATUS", sf.getExecuteStatusList())//多个执行状态
                .andEq("indent.EVALUATE_STATUS", sf.getEvaluateStatus())// 评价状态
                .andEq("indent.SERVE_TYPE", sf.getServeType()) //订单服务类型
                .andEq("indent.STATUS", sf.getStatus())// 订单状态
                .andEq("indent.SOURCE", sf.getSource())// 上门次数
                .andEq("indent.PROG_STATUS", sf.getProgress())// 操作进度
                .andEq("indent.EXCEP_STATUS", sf.getExcepStatus())// 异常状态
                .andGe("indent.CRT_DATE", sf.getComitDateBegin())//订单提交日期 起
                .andLe("indent.CRT_DATE", sf.getComitDateEnd())//订单提交日期 止
                .andGe("item.BEGIN_DATE", sf.getGotDateBegin())//项目周期 起
                .andLe("item.OVER_DATE", sf.getGotDateEnd());//项目周期 止
        return builder;
    }

    private QueryInfoBuilder prepareQuery1(IndentSf sf) {
        if (sf == null) {
            sf = new IndentSf();
        }
        List<Object> sqlPars = New.list();
        StringBuilder sql = new StringBuilder("SELECT D.* FROM ZL_INDENT D WHERE EXISTS (SELECT 1 FROM ")//
                .append(" (SELECT S.INDENT_ID, COUNT(S.ID) AS COUNT1 FROM ZL_WORKER W")//
                .append(" INNER JOIN ZL_INDENT_SORT S WHERE W.ID = ? AND");
        sqlPars.add(sf.getWorkerId());
        sql.append(" W.SKILL_TYPE LIKE CONCAT('%',S.CODE1,'|%') GROUP BY S.INDENT_ID ) A")//
                .append(", (SELECT INDENT_ID, COUNT(*) AS COUNT2 FROM ZL_INDENT_SORT GROUP BY INDENT_ID ) B")//
                .append(", (SELECT S.INDENT_ID, COUNT(S.ID) AS COUNT3 FROM ZL_WORKER W INNER JOIN ZL_INDENT_SORT S WHERE")//
                .append(" W.ID = ? AND");
        sqlPars.add(sf.getWorkerId());
        sql.append(" W.SERVICE_CATEGORY LIKE CONCAT('%|',S.SERVE_TYPE,'%') GROUP BY S.INDENT_ID ) C WHERE")//
                .append(" A.INDENT_ID = C.INDENT_ID AND A.COUNT1 = B.COUNT2 AND A.COUNT1 = C.COUNT3 AND A.INDENT_ID = D.ID)")
                
                .append(" AND D.REGION_PROV = ? AND D.REGION_CITY = ? AND  D.REGION_DIST IN (");
        sqlPars.add(sf.getRegionProv());
        sqlPars.add(sf.getRegionCity());// 订单查看的服务地区
        String regionDist = sf.getRegionDist();
        String[] strings = regionDist.split("\\|");
        for (int i = 0; i < strings.length-1; i++) {
            sql.append(" ? ,");
            sqlPars.add(strings[i]);
        }
        sql.append("? )");
        sqlPars.add(strings[strings.length-1]);
        if (StringUtil.isNotBlank(sf.getWorkerId())) {
            sql.append(" AND IF(D.WORKER_ID <>'',D.WORKER_ID=?,1=1)");
            sqlPars.add(sf.getWorkerId());
        }
        if (StringUtil.isNotBlank(sf.getManagerId())) {
            sql.append(" AND IF(D.MANAGER_ID <>'',D.MANAGER_ID=?,1=1)");
            sqlPars.add(sf.getManagerId());
        }
        QueryInfoBuilder builder = QueryInfoBuilder.ins(sql.toString()).sqlPars(sqlPars)//
                .andContains("D.SERVE_TYPE", sf.getServeType())//服务类型
                .andEq("D.RELEASE_STATUS", EnumIndentReleaseStatus.RELEASED.name())//发布状态
                .andEq("D.EXECUTE_STATUS", EnumExecuteStatus.BEFORE.name())//执行状态
                .orderDesc("D.CREATE_DATE").orderDesc("D.CODE1");
        return builder;
    }

    private QueryInfoBuilder prepareQuery2(IndentSf sf) {
        if (sf == null) {
            sf = new IndentSf();
        }
        List<Object> sqlPars = New.list();
        StringBuilder sql = new StringBuilder("SELECT A.* from ZL_INDENT A,ZL_INDENT_CONTACT B WHERE")//
                .append(" A.ID = B.INDENT_ID");
        if (StringUtil.isNotBlank(sf.getWorkerId())) {
            sql.append(" AND A.WORKER_ID = ?");
            sqlPars.add(sf.getWorkerId());
        } else if (StringUtil.isNotBlank(sf.getManagerId())) {
            sql.append(" AND A.MANAGER_ID = ?");
            sqlPars.add(sf.getManagerId());
        }
        sql.append(" AND (B.NAME1 LIKE ? OR B.DETAIL_ADDR1 LIKE ?)");
        sqlPars.add("%" + sf.getSearchName1() + "%");
        sqlPars.add("%" + sf.getSearchAddr1() + "%");
        QueryInfoBuilder builder = QueryInfoBuilder.ins(sql.toString()).sqlPars(sqlPars)//
                .orderDesc("A.CREATE_DATE");
        return builder;
    }

    @Override
    public Indent find(String id) {
        Indent indent = super.find(id);
        IndentContact indentContact = indentContactService.findOne(Filter.eq("indentId", indent.getId()));
        indent.setContact(indentContact);
        IndentFreight indentFreight = indentFreightService.findOne(Filter.eq("indentId", indent.getId()));
        indent.setIndentFreight(indentFreight);
        List<IndentContent> indentContents = indentContentService.findList(Filter.eq("indentId", indent.getId()));
        Sctype sctype = sctypeService.find(indent.getServeType());
        indent.setServeTypeDisp(sctype.getName1());
        // 回显数据拼接
        Dto data = new Dto();
        for (IndentContent indentContent : indentContents) {
            data.put("indentPrice_" + indentContent.getCode1() + "_checked", true);
            data.put("indentPrice_" + indentContent.getCode1() + "_counts", indentContent.getCounts());
            data.put("indentPrice_" + indentContent.getCode1() + "_code1", indentContent.getCode1());
        }
        if (EnumYesno.YES.name().equals(indent.getIndentAssign())) {
            indent.setIndentAssignSelected(true);
            Account managerAccount = accountService.findOne(Filter.eq("managerId", indent.getManagerId()));
            Account workerAccount = accountService.findOne(Filter.eq("workerId", indent.getWorkerId()));
            indent.setManagerName1(managerAccount.getName1());
            indent.setWorkerName1(workerAccount.getName1());
        }
        
        indent.setIndentPriceDto(data);
        return indent;
    }

    @Override
    public boolean scrambleIndent(Account account, String id) {

        Worker worker = workerService.find(account.getWorkerId());
        Indent indent = super.find(id);
        String sql1 = "update ZL_INDENT set SERVICE_DATE = ?,PLAN_DATE = ?, WORKER_ID = ? , MANAGER_ID = ?, EXECUTE_STATUS = ? where ID = ? and EXECUTE_STATUS = ?";
        int i = sqlDao.execUpdate(sql1, new Date(), DateUtil.addHours(new Date(), indent.getDurationTime()), worker.getId(), worker.getManagerId(), EnumExecuteStatus.CENTRE.name(), id, EnumExecuteStatus.BEFORE.name());
        if (i != 1) {
            return false;
        }
        
        String sql2 = "update ZL_WORKER set TOTAL_INDENT = ? where ID = ?";
        sqlDao.execUpdate(sql2, worker.getTotalIndent() + 1, worker.getId());
        
        Manager manager = managerService.find(worker.getManagerId());
        String sql3 = "update ZL_MANAGER set TOTAL_INDENT = ? where ID = ?";
        sqlDao.execUpdate(sql3, manager.getTotalIndent() + 1, manager.getId());
        return true;
    }

    @Override
    public void changeIndentException(String indentId) {
        // 订单异常 订单状态 异常
        String sql = "update ZL_INDENT set STATUS = ? where ID = ? ";
        sqlDao.execUpdate(sql, EnumIndentStatus.EXCEPTION.name(), indentId);
    }

    @Override
    public Map<EnumExecuteStatus, Integer> executeStatus2Count() {

        Map<EnumExecuteStatus, Integer> ret = New.hashMap();

        String sql = "select EXECUTE_STATUS, count(*) STATUS_COUNT from ZL_INDENT group by EXECUTE_STATUS";

        List<Map<String, Object>> list = sqlDao.queryForList(sql);
        for (Map<String, Object> map : list) {
            String executeStatus = ObjectUtil.toString(map.get("EXECUTE_STATUS"));
            Integer statusCount = NumberUtil.toInt(map.get("STATUS_COUNT"), 0);

            ret.put(EnumExecuteStatus.valueOf(executeStatus), statusCount);
        }

        return ret;
    }

    @Override
    public Dto indentCancle(String id, String workerId) {
        // 申请取消 订单状态 异常 异常状态 取消
        Dto ret = new Dto();
        ret.put(TAG_SUCCESS, false);
        Indent indent = find(id);
        if (DateUtil.addHours(indent.getServiceDate(), 2).before(new Date())) {
            ret.put(TAG_MSG, "您抢单时间已过2小时，不能取消订单！");
            return ret;
        }
        if (indentNodeService.count(Filter.eq("indentId", id), Filter.ne("nodeExecuteStatus", EnumExecuteStatus.BEFORE.name())) > 0) {
            ret.put(TAG_MSG, "您已开始操作订单，不能取消！");
            return ret;
        }
        String serveType = indent.getServeType();
        Sctype sctype = sctypeService.find(serveType);
        
        Worker worker = workerService.find(workerId);
        List<IndentContent> indentContentList = indentContentService.findList(Filter.eq("indentId", indent.getId()));
        String sortName = completeSortName(indentContentList);
        Account workerAccount = accountService.find(worker.getAccountId());
        {//推送给工匠取消订单信息
            if (EnumAccountType.WORKER.name().equals(workerAccount.getLastLoginType())) {
                Map<String, String> extras = new HashMap<String, String>();
                extras.put("id", id);
                extras.put("accountType", EnumAccountType.WORKER.name());
                extras.put(PUSH_TYPE, EnumJpushType.INDENT_DETAIL.name());
                List<String> workerRegistrationId = new ArrayList<String>();
                if (StringUtil.isNotBlank(workerAccount.getRegistrationId())) {
                    workerRegistrationId.add(workerAccount.getRegistrationId());
                }
                if (workerRegistrationId.size() > 0) {
                    MessagePush push = new MessagePush("订单取消成功("+sctype.getName1()+sortName+")", "众联工匠", extras);
                    push.pushMsgToRegistrationIds(workerRegistrationId);
                }
            }
        }

        {//推送给经理人取消订单信息
            Manager manager = managerService.find(worker.getManagerId());
            Account managerAccount = accountService.find(manager.getAccountId());
            if (EnumAccountType.MANAGER.name().equals(managerAccount.getLastLoginType())) {
                Map<String, String> extras = new HashMap<String, String>();
                extras.put("id", id);
                extras.put("accountType", EnumAccountType.MANAGER.name());
                extras.put(PUSH_TYPE, EnumJpushType.INDENT_DETAIL.name());
                List<String> managerRegistrationId = new ArrayList<String>();
                if (StringUtil.isNotBlank(managerAccount.getRegistrationId())) {
                    managerRegistrationId.add(managerAccount.getRegistrationId());
                }
                if (managerRegistrationId.size() > 0) {
                    MessagePush push = new MessagePush(workerAccount.getName1()+"订单取消成功("+sctype.getName1()+sortName+")", "众联工匠", extras);
                    push.pushMsgToRegistrationIds(managerRegistrationId);
                }
            }
        }
        //订单取消   当前的订单  成为已取消  他下面的节点都要成为新的订单的节点  包括报价
        String sql1 = "update ZL_INDENT set EXECUTE_STATUS = ?, EXCEP_STATUS = ?, STATUS = ? where ID = ?";
        sqlDao.execUpdate(sql1, EnumExecuteStatus.AFTER.name(), SysDict.EMPTY.getValue(),EnumIndentStatus.CANCEL.name(), id);
        //去掉 物流的货运单号
        String sql2 = "update ZL_INDENT_FREIGHT set CODE1 = ? where INDENT_ID = ?";
        sqlDao.execUpdate(sql2, null, id);
        
        Indent newIndent = new Indent();
        IndentContact indentContact = new IndentContact();
        IndentFreight indentFreight = new IndentFreight();
        
        BeanUtils.copyProperties(indent, newIndent);
        newIndent.setId(null);
        newIndent.setWorkerId("");
        newIndent.setManagerId("");
        newIndent.setServiceDate(null);
        if (indent.getContact() != null) {
            BeanUtils.copyProperties(indent.getContact(), indentContact);
            indentContact.setId(null);
            newIndent.setContact(indentContact);
        }
        if (indent.getIndentFreight() != null) {
            BeanUtils.copyProperties(indent.getIndentFreight(), indentFreight);
            indentFreight.setId(null);
            newIndent.setIndentFreight(indentFreight);
        }
        saveIndent(newIndent);
        ret.put(TAG_MSG, "取消成功！");
        ret.put(TAG_SUCCESS, true);
        return ret;
    }
    
    
    private String completeSortName (List<IndentContent> indentContentList) {
        String sortDisp = "";
        Dto  sortIds = new Dto();
        for (IndentContent indentContent : indentContentList) {
            String indentPriceCode1 = indentContent.getCode1();
                SctypeContent content = sctypeContentService.find(indentPriceCode1);
                String sortId = content.getSctypeSortId();
            if (!sortIds.containsKey(sortId)) {
                sortIds.put(sortId, sortId);
                sortDisp += content.getDesc1();
            }
        }
        return sortDisp;
    }

    @Override
    public Dto deleteIndent(String id) {
        Dto ret = new Dto();
        Indent indent = super.find(id);
        if (!EnumExecuteStatus.BEFORE.name().equals(indent.getExecuteStatus())) {
            ret.put(TAG_SUCCESS, false);
            ret.put(TAG_MSG, "订单已执行, 无法删除！");
        } else {
            indentContactService.deleteByIndentId(id);
            indentContentService.deleteByIndentId(id);
            indentFreightService.deleteByIndentId(id);
            indentSctypeSortService.deleteByIndentId(id);
            indentNodeService.deleteByIndentId(id);
            super.delete(id);
            ret.put(TAG_MSG, "订单删除成功！");
            ret.put(TAG_SUCCESS, true);
        }
        return ret;
    }

    @Override
    public int countByItemIdAndStatus(String itemId, String status) {
        String sql = "select count(*) from ZL_INDENT where ITEM_ID=? and EXECUTE_STATUS<>?";
        return sqlDao.count(sql, itemId, status);
    }

    @Override
    public void nodeProgress(IndentNode indentNode) {
        // 每次开始新的节点 维护订单 的PROG_STATUS
        String sql = "update ZL_INDENT set PROG_STATUS = ? where  ID = ? ";
        sqlDao.execUpdate(sql, indentNode.getCode1(), indentNode.getIndentId());
    }

    @Override
    public void changeIndentNormal(IndentException indentException) {
        List<IndentExceptionDetail> indentExceptionDetailList = indentExceptionDetailService.findList(Arrays.asList(Filter.eq("result", EnumExceptionResultStatus.SUSPEND.name()),
                Filter.eq("exceptionId", indentException.getId())), Arrays.asList(Order.asc("operateDate")));
        IndentExceptionDetail indentExceptionDetail = indentExceptionDetailService.findOne(Filter.eq("result", EnumExceptionResultStatus.CONTINUE.name()), Filter.eq("exceptionId", indentException.getId()));
        Long times = null;
        if (indentExceptionDetailList != null && indentExceptionDetailList.size() > 0) {
            Calendar calendar1 = DateUtil.toCalendar(indentExceptionDetail.getOperateDate());
            Calendar calendar2 = DateUtil.toCalendar(indentExceptionDetailList.get(0).getOperateDate());
            times = calendar1.getTimeInMillis() - calendar2.getTimeInMillis();
        }
        Indent indent = super.find(indentException.getIndentId());
        String sql = "update ZL_INDENT set STATUS = ?, EXCEP_STATUS= ?, PLAN_DATE = ? where ID= ?";
        Date newPlanDate = DateUtil.addMilliseconds(indent.getPlanDate(), NumberUtil.toInt(times, 0));
        sqlDao.execUpdate(sql, EnumIndentStatus.NORMAL.name(),SysDict.EMPTY.getValue(), newPlanDate, indentException.getIndentId());
    }

    @Override
    public void changeIndentPause(IndentException indentException) {
        String sql = "update ZL_INDENT set STATUS = ?, EXCEP_STATUS= ? where ID= ?";
        sqlDao.execUpdate(sql, EnumIndentStatus.EXCEPTION.name(), EnumIndentExcepStatus.PAUSE.name(), indentException.getIndentId());
    }

    @Override
    public void pushToAllWorker(Indent indent) {
        Map<String, String> extras = new HashMap<String, String>();
        extras.put("id", indent.getId());
        extras.put(PUSH_TYPE, EnumJpushType.INDENT.name());
        extras.put("accountType", EnumAccountType.WORKER.name());
        List<Worker> workerList = workerService.findRegistrationIds(indent);
        List<String> registrationIds = new ArrayList<String>();
        for (Worker worker : workerList) {
            if (StringUtil.isNotBlank(worker.getRegistrationId())) {
                registrationIds.add(worker.getRegistrationId());
            }
        }
        if (registrationIds.size() > 0) {
            MessagePush messagePush = new MessagePush("新的订单来啦", "众联工匠", extras);
            messagePush.pushMsgToRegistrationIds(registrationIds);
        }
        updateReleaseDate(indent.getId());
    }


    private void updateReleaseDate(String id) {
        String sql = "update ZL_INDENT set RELEASE_DATE = ?, RELEASE_STATUS = ? where ID = ?";
        sqlDao.execUpdate(sql, new Date(), EnumIndentReleaseStatus.RELEASED.name(), id);
    }


    @Override
    public void firstOperItem(IndentNode indentNode) {
        String sql = "update ZL_INDENT set PROG_STATUS = ? where ID = ?";
        sqlDao.execUpdate(sql, indentNode.getCode1(), indentNode.getIndentId());
    }
    
    @Override
    public int monthIndent(String workerId) {
        String sql = "select count(*) from ZL_INDENT where date_format(CREATE_DATE,'%Y-%m')=date_format(now(),'%Y-%m') and WORKER_ID=?";
        return sqlDao.count(sql, workerId);
    }

    @Override
    public void overIndent(IndentException indentException) {
        String sql = "update ZL_INDENT set STATUS = ?, EXCEP_STATUS= ?, EXECUTE_STATUS = ? where ID = ?";
        sqlDao.execUpdate(sql, EnumIndentStatus.OVER.name(), SysDict.EMPTY.getValue(), EnumExecuteStatus.AFTER.name(), indentException.getIndentId());
    }

    @Override
    public int exceptionIndentCount() {
        String sql = "select count(*) from ZL_INDENT where STATUS=?";
        return sqlDao.count(sql, new Object[] {EnumIndentStatus.EXCEPTION.name()});
    }

    @Override
    public List<Indent> findByCarModel(String workerId, String carModel) {
        String sql = "select indent.* from ZL_INDENT indent left join ZL_INDENT_FREIGHT freight on indent.ID = freight.INDENT_ID where indent.WORKER_ID = ? and indent.SERVE_TYPE = ? and freight.CAR_MODEL = ? order by indent.SERVICE_DATE";
        List<Indent> listAll = sqlDao.listAll(sql, Indent.class, workerId, "T", carModel);
        return listAll;
    }
    
    @Override
    public int finishIndentNum(String workerId) {
        String sql = "select count(*) from ZL_INDENT where WORKER_ID=?";
        return sqlDao.count(sql, workerId);
    }
    
    @Override
    public int onTimeFinishNum(String workerId,String executeStatus) {
        String sql = "select count(*) from ZL_INDENT where WORKER_ID=? and EXECUTE_STATUS=? and date_format(FINISH_DATE,'%Y%m%d') < date_format(PLAN_DATE,'%Y%m%d')";
        return sqlDao.count(sql, workerId, executeStatus);
    }


    @Override
    public List<Indent> findOverTimeIndent() {
        String sql = "SELECT ID, CODE1, WORKER_ID from ZL_INDENT WHERE 1=1 AND TIMESTAMPDIFF(HOUR,NOW(),PLAN_DATE) < 1 AND EXECUTE_STATUS<>? AND `STATUS`=?";
        List<Indent> list = sqlDao.listAll(sql, Indent.class,EnumExecuteStatus.AFTER.name(), EnumIndentStatus.NORMAL.name());
        return list;
    }


    @Override
    public Indent createAnotherIndent(String indentId, Admin admin, Indent data) {
        Indent indent = find(indentId);
        IndentException indentException = indentExceptionService.findOne(Filter.eq("indentId", indentId),//
                Filter.eq("result", EnumExceptionResultStatus.OVER.name()));
        String comment = indentException.getIdea();
        Indent newIndent = new Indent();
        IndentContact indentContact = new IndentContact();
        IndentFreight indentFreight = new IndentFreight();
        
        BeanUtils.copyProperties(indent, newIndent);
        newIndent.setId(null);
        newIndent.setCrtDate(new Date());
        newIndent.setCode1(prepareIndentCode1(newIndent));
        newIndent.setComment(comment);
        newIndent.setWorkerId(indent.getWorkerId());
        newIndent.setManagerId(indent.getManagerId());
        newIndent.setExecuteStatus(EnumExecuteStatus.BEFORE.name());
        newIndent.setStatus(EnumIndentStatus.NORMAL.name());
        newIndent.setProgStatus(SysDict.EMPTY.getValue());
        newIndent.setReleaseStatus(EnumIndentReleaseStatus.RELEASED.name());
        newIndent.setReleaseDate(new Date());
        newIndent.setSource(EnumIndentVisitSource.SECOND.name());
        newIndent.setIndentAssign(EnumYesno.YES.name());
        newIndent.setOriginalCode1(indent.getCode1());
        newIndent.setServiceDate(null);
        newIndent.setPlanDate(null);
        newIndent.setAdminId(admin.getId());
        newIndent.setAdminName1(admin.getName1());
        newIndent.setIndentPriceDto(data.getIndentPriceDto());
        if (indent.getContact() != null) {
            BeanUtils.copyProperties(indent.getContact(), indentContact);
            indentContact.setId(null);
            newIndent.setContact(indentContact);
        }
        //保存新的indent
        super.save(newIndent);
        indentContact.setRegionProv(newIndent.getRegionProv());
        indentContact.setRegionCity(newIndent.getRegionCity());
        indentContact.setRegionDist(newIndent.getRegionDist());
        indentContact.setIndentId(newIndent.getId());
        indentContactService.save(indentContact);

        if (data.getIndentFreight() != null) {
            indentFreight = data.getIndentFreight();
            indentFreight.setId(null);
            indentFreight.setIndentId(newIndent.getId());
            indentFreightService.save(indentFreight);
        }
        indentContentService.saveIndentContent(newIndent);
        indentNodeService.saveIndentNodes(newIndent);
        List<FileIndex> findByBelongToAndExts = fileManagerService.findByBelongToAndExts(indentId, "INDENT", "DWG");
        for (FileIndex fileIndex : findByBelongToAndExts) {
            FileIndex newFileIndex = new FileIndex();
            BeanUtils.copyProperties(fileIndex, newFileIndex);
            newFileIndex.setId(null);
            newFileIndex.setBelongTo(newIndent.getId());
            fileIndexService.save(newFileIndex);
        }
        pushExtraIndent(newIndent);
        return newIndent;
    }

    @Override
    public void pushIndent(String indentId) {
        Indent indent = find(indentId);
        if (!StringUtil.isNotBlank(indent.getWorkerId())) {
            pushToAllWorker(indent);
        } else {//推动到指定的用户群  eg:某个工匠 某个经理人旗下的工匠
            Account account = accountService.findOne(Filter.eq("workerId", indent.getWorkerId()));
            {
                List<String> registrationIds = new ArrayList<String>();
                Map<String, String> extras = new HashMap<String, String>();
                extras.put("id", indentId);
                extras.put("accountType", EnumAccountType.WORKER.name());
                extras.put(PUSH_TYPE, EnumJpushType.INDENT.name());
                if (StringUtil.isNotBlank(account.getRegistrationId())) {
                    registrationIds.add(account.getRegistrationId());
                }
                if (registrationIds.size() > 0) {
                    MessagePush push = new MessagePush("新的订单来啦", "众联工匠", extras);
                    push.pushMsgToRegistrationIds(registrationIds);
                }
            }
        }
        updateReleaseDate(indent.getId());
    }


    @Override
    public List<Indent> findOverTimeEvaluate(Date date) {
        
        String sql = "SELECT ID from ZL_INDENT WHERE 1=1 AND FINISH_DATE < ? AND PROG_STATUS=?";
        List<Indent> list = sqlDao.listAll(sql, Indent.class, date, EnumScnodeStepType.INVITEEVAL.name());
        return list;
    }


    @Override
    public void pushExtraIndent(Indent indent) {
        {//推送订单到原工匠
            Map<String, String> extras = new HashMap<String, String>();
            extras.put("id", indent.getId());
            extras.put(PUSH_TYPE, EnumJpushType.INDENT.name());
            extras.put("accountType", EnumAccountType.WORKER.name());
            Worker worker = workerService.findByWorkerId(indent.getWorkerId());
            List<String> registrationIds = new ArrayList<String>();
            if (StringUtil.isNotBlank(worker.getAccount().getRegistrationId())) {
                registrationIds.add(worker.getAccount().getRegistrationId());
            }
            if (registrationIds.size() > 0) {
                MessagePush messagePush = new MessagePush("一个新的二次上门单", "众联工匠", extras);
                messagePush.pushMsgToRegistrationIds(registrationIds);
            }
            updateReleaseDate(indent.getId());
        }
    }


    @Override
    public List<Indent> unScrambleOverTimeIndent(Date date) {
        String sql = "SELECT * from ZL_INDENT WHERE INDENT_ASSIGN = ? AND RELEASE_STATUS = ? AND RELEASE_DATE < ?";
        return sqlDao.listAll(sql, Indent.class, EnumYesno.YES.name(), EnumIndentReleaseStatus.RELEASED.name(), date);
    }


    @Override
    public void clearWorkerIdAndPush(Indent indent) {
        String sql = "UPDATE ZL_INDENT SET WORKER_ID = ? WHERE ID = ?";
        sqlDao.execUpdate(sql, SysDict.EMPTY.getValue(), indent.getId());
        {//推送订单到经理人旗下的工匠
            Map<String, String> extras = new HashMap<String, String>();
            extras.put("id", indent.getId());
            extras.put(PUSH_TYPE, EnumJpushType.INDENT.name());
            extras.put("accountType", EnumAccountType.WORKER.name());
            List<Worker> workerList = workerService.findRegistrationIds(indent);
            List<String> registrationIds = new ArrayList<String>();
            for (Worker worker : workerList) {
                if (StringUtil.isNotBlank(worker.getRegistrationId())) {
                    registrationIds.add(worker.getRegistrationId());
                }
            }
            if (registrationIds.size() > 0) {
                MessagePush messagePush = new MessagePush("一个新的二次上门单", "众联工匠", extras);
                messagePush.pushMsgToRegistrationIds(registrationIds);
            }
        }
    }


    @Override
    public void clearManagerIdAndPush(Indent indent) {
        String sql = "UPDATE ZL_INDENT SET MANAGER_ID = ?, INDENT_ASSIGN = ? WHERE ID = ?";
        sqlDao.execUpdate(sql, SysDict.EMPTY.getValue(), EnumYesno.NO.name(), indent.getId());
        {//推送到满足条件的工匠
            Map<String, String> extras = new HashMap<String, String>();
            extras.put("id", indent.getId());
            extras.put(PUSH_TYPE, EnumJpushType.INDENT.name());
            extras.put("accountType", EnumAccountType.WORKER.name());
            List<Worker> workerList = workerService.findRegistrationIds(indent);
            List<String> registrationIds = new ArrayList<String>();
            for (Worker worker : workerList) {
                if (StringUtil.isNotBlank(worker.getRegistrationId())) {
                    registrationIds.add(worker.getRegistrationId());
                }
            }
            if (registrationIds.size() > 0) {
                MessagePush messagePush = new MessagePush("一个新的二次上门单", "众联工匠", extras);
                messagePush.pushMsgToRegistrationIds(registrationIds);
            }
        }
    }

}