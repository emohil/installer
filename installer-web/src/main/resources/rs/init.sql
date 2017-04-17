INSERT INTO ZL_DICT_GROUP (ID, CREATE_DATE, CODE1, NAME1) VALUES('SERVICE_SORT', now(), 'SERVICE_SORT', '服务类别');
INSERT INTO ZL_DICT_GROUP (ID, CREATE_DATE, CODE1, NAME1) VALUES('ITEM_STATUS', now(), 'ITEM_STATUS', '项目状态');
INSERT INTO ZL_DICT_GROUP (ID, CREATE_DATE, CODE1, NAME1) VALUES('EVALUATE_SCORE', now(), 'EVALUATE_SCORE', '订单评分');
INSERT INTO ZL_DICT_GROUP (ID, CREATE_DATE, CODE1, NAME1) VALUES('UOM', now(), 'UOM', '单位');
INSERT INTO ZL_DICT_GROUP (ID, CREATE_DATE, CODE1, NAME1) VALUES('INDUSTRY', now(), 'INDUSTRY', '行业');
INSERT INTO ZL_DICT_GROUP (ID, CREATE_DATE, CODE1, NAME1) VALUES('INDENT_SOURCE', now(), 'INDENT_SOURCE', '订单来源');
INSERT INTO ZL_DICT_GROUP (ID, CREATE_DATE, CODE1, NAME1) VALUES('VEHICLE', now(), 'VEHICLE', '交通工具');
INSERT INTO ZL_DICT_GROUP (ID, CREATE_DATE, CODE1, NAME1) VALUES('VEHICLE_BELONGS', now(), 'VEHICLE_BELONGS', '交通工具归属');

INSERT INTO ZL_DICT (ID, CREATE_DATE, GROUP_CODE, VALUE1, TEXT1, ORDERS) VALUES('SERVICE_SORT_Q', now(), 'SERVICE_SORT', 'Q', '测量', 0);
INSERT INTO ZL_DICT (ID, CREATE_DATE, GROUP_CODE, VALUE1, TEXT1, ORDERS) VALUES('SERVICE_SORT_C', now(), 'SERVICE_SORT', 'C', '橱柜', 1);
INSERT INTO ZL_DICT (ID, CREATE_DATE, GROUP_CODE, VALUE1, TEXT1, ORDERS) VALUES('SERVICE_SORT_T', now(), 'SERVICE_SORT', 'T', '台面', 2);
INSERT INTO ZL_DICT (ID, CREATE_DATE, GROUP_CODE, VALUE1, TEXT1, ORDERS) VALUES('SERVICE_SORT_W', now(), 'SERVICE_SORT', 'W', '卫浴', 3);
INSERT INTO ZL_DICT (ID, CREATE_DATE, GROUP_CODE, VALUE1, TEXT1, ORDERS) VALUES('SERVICE_SORT_V', now(), 'SERVICE_SORT', 'V', '吊顶', 4);
INSERT INTO ZL_DICT (ID, CREATE_DATE, GROUP_CODE, VALUE1, TEXT1, ORDERS) VALUES('SERVICE_SORT_D', now(), 'SERVICE_SORT', 'D', '地板', 5);
INSERT INTO ZL_DICT (ID, CREATE_DATE, GROUP_CODE, VALUE1, TEXT1, ORDERS) VALUES('SERVICE_SORT_N', now(), 'SERVICE_SORT', 'N', '内门', 6);
INSERT INTO ZL_DICT (ID, CREATE_DATE, GROUP_CODE, VALUE1, TEXT1, ORDERS) VALUES('SERVICE_SORT_J', now(), 'SERVICE_SORT', 'J', '定制家具', 7);
INSERT INTO ZL_DICT (ID, CREATE_DATE, GROUP_CODE, VALUE1, TEXT1, ORDERS) VALUES('SERVICE_SORT_M', now(), 'SERVICE_SORT', 'M', '家电', 8);


INSERT INTO ZL_DICT (ID, CREATE_DATE, GROUP_CODE, VALUE1, TEXT1, ORDERS) VALUES('ITEM_STATUS_NORMAL', now(), 'ITEM_STATUS', 'NORMAL', '正常', 1);
INSERT INTO ZL_DICT (ID, CREATE_DATE, GROUP_CODE, VALUE1, TEXT1, ORDERS) VALUES('ITEM_STATUS_STOP', now(), 'ITEM_STATUS', 'STOP', '停用', 2);

INSERT INTO ZL_DICT (ID, CREATE_DATE, GROUP_CODE, VALUE1, TEXT1, ORDERS) VALUES('INDENTSTATUS_NORMAL', now(), 'INDENTSTATUS', 'NORMAL', '正常', 1);
INSERT INTO ZL_DICT (ID, CREATE_DATE, GROUP_CODE, VALUE1, TEXT1, ORDERS) VALUES('INDENTSTATUS_UNNORMAL', now(), 'INDENTSTATUS', 'UNNORMAL', '异常', 2);

INSERT INTO ZL_DICT (ID, CREATE_DATE, GROUP_CODE, VALUE1, TEXT1, ORDERS) VALUES('CLEARINGSTATUS_NONE', now(), 'CLEARINGSTATUS', 'NONE', '未结算', 1);
INSERT INTO ZL_DICT (ID, CREATE_DATE, GROUP_CODE, VALUE1, TEXT1, ORDERS) VALUES('CLEARINGSTATUS_YES', now(), 'CLEARINGSTATUS', 'YES', '已结算', 2);

INSERT INTO ZL_DICT (ID, CREATE_DATE, GROUP_CODE, VALUE1, TEXT1, ORDERS) VALUES('EVALUATE_SCORE_ONE', now(), 'EVALUATE_SCORE', 'ONE', '一星', 5);
INSERT INTO ZL_DICT (ID, CREATE_DATE, GROUP_CODE, VALUE1, TEXT1, ORDERS) VALUES('EVALUATE_SCORE_TWO', now(), 'EVALUATE_SCORE', 'TWO', '二星', 4);
INSERT INTO ZL_DICT (ID, CREATE_DATE, GROUP_CODE, VALUE1, TEXT1, ORDERS) VALUES('EVALUATE_SCORE_THREE', now(), 'EVALUATE_SCORE', 'THREE', '三星',3);
INSERT INTO ZL_DICT (ID, CREATE_DATE, GROUP_CODE, VALUE1, TEXT1, ORDERS) VALUES('EVALUATE_SCORE_FOUR', now(), 'EVALUATE_SCORE', 'FOUR', '四星', 2);
INSERT INTO ZL_DICT (ID, CREATE_DATE, GROUP_CODE, VALUE1, TEXT1, ORDERS) VALUES('EVALUATE_SCORE_FIVE', now(), 'EVALUATE_SCORE', 'FIVE', '五星', 1);

INSERT INTO ZL_DICT (ID, CREATE_DATE, GROUP_CODE, VALUE1, TEXT1, ORDERS) VALUES('UOM_METER', now(), 'UOM', 'METER', '米', 1);
INSERT INTO ZL_DICT (ID, CREATE_DATE, GROUP_CODE, VALUE1, TEXT1, ORDERS) VALUES('UOM_SQUARE', now(), 'UOM', 'SQUARE', '平米', 2);
INSERT INTO ZL_DICT (ID, CREATE_DATE, GROUP_CODE, VALUE1, TEXT1, ORDERS) VALUES('UOM_HALL', now(), 'UOM', 'HALL', '樘', 3);
INSERT INTO ZL_DICT (ID, CREATE_DATE, GROUP_CODE, VALUE1, TEXT1, ORDERS) VALUES('UOM_ROOM', now(), 'UOM', 'ROOM', '间', 4);
INSERT INTO ZL_DICT (ID, CREATE_DATE, GROUP_CODE, VALUE1, TEXT1, ORDERS) VALUES('UOM_PIECE', now(), 'UOM', 'PIECE', '件', 5);
INSERT INTO ZL_DICT (ID, CREATE_DATE, GROUP_CODE, VALUE1, TEXT1, ORDERS) VALUES('UOM_YANMI', now(), 'UOM', 'YANMI', '延米', 6);
INSERT INTO ZL_DICT (ID, CREATE_DATE, GROUP_CODE, VALUE1, TEXT1, ORDERS) VALUES('UOM_SET', now(), 'UOM', 'SET', '套', 7);
INSERT INTO ZL_DICT (ID, CREATE_DATE, GROUP_CODE, VALUE1, TEXT1, ORDERS) VALUES('UOM_EA', now(), 'UOM', 'EA', '个', 8);
INSERT INTO ZL_DICT (ID, CREATE_DATE, GROUP_CODE, VALUE1, TEXT1, ORDERS) VALUES('UOM_BLOCK', now(), 'UOM', 'BLOCK', '块', 9);
INSERT INTO ZL_DICT (ID, CREATE_DATE, GROUP_CODE, VALUE1, TEXT1, ORDERS) VALUES('UOM_TIMES', now(), 'UOM', 'TIMES', '次', 10);
INSERT INTO ZL_DICT (ID, CREATE_DATE, GROUP_CODE, VALUE1, TEXT1, ORDERS) VALUES('UOM_SHEET', now(), 'UOM', 'SHEET', '张', 11);
INSERT INTO ZL_DICT (ID, CREATE_DATE, GROUP_CODE, VALUE1, TEXT1, ORDERS) VALUES('UOM_SERIES', now(), 'UOM', 'SERIES', '组', 12);
INSERT INTO ZL_DICT (ID, CREATE_DATE, GROUP_CODE, VALUE1, TEXT1, ORDERS) VALUES('UOM_LEAF', now(), 'UOM', 'LEAF', '扇', 13);
INSERT INTO ZL_DICT (ID, CREATE_DATE, GROUP_CODE, VALUE1, TEXT1, ORDERS) VALUES('UOM_POSITION', now(), 'UOM', 'POSITION', '人位', 14);
INSERT INTO ZL_DICT (ID, CREATE_DATE, GROUP_CODE, VALUE1, TEXT1, ORDERS) VALUES('UOM_SILL', now(), 'UOM', 'SILL', '台', 15);

INSERT INTO ZL_DICT (ID, CREATE_DATE, GROUP_CODE, VALUE1, TEXT1, ORDERS) VALUES('INDUSTRY_SERVICE', now(), 'INDUSTRY', 'SERVICE', '服务业', 1);
INSERT INTO ZL_DICT (ID, CREATE_DATE, GROUP_CODE, VALUE1, TEXT1, ORDERS) VALUES('INDUSTRY_INTERNET', now(), 'INDUSTRY', 'INTERNET', '通信与互联网', 2);
INSERT INTO ZL_DICT (ID, CREATE_DATE, GROUP_CODE, VALUE1, TEXT1, ORDERS) VALUES('INDUSTRY_FINANCE', now(), 'INDUSTRY', 'FINANCE', '金融/保险/投资', 3);
INSERT INTO ZL_DICT (ID, CREATE_DATE, GROUP_CODE, VALUE1, TEXT1, ORDERS) VALUES('INDUSTRY_OTHER', now(), 'INDUSTRY', 'OTHER', '其他行业', 4);

INSERT INTO ZL_DICT (ID, CREATE_DATE, GROUP_CODE, VALUE1, TEXT1, ORDERS) VALUES('INDENT_SOURCE_001', now(), 'INDENT_SOURCE', '001', '博洛尼', 1);
INSERT INTO ZL_DICT (ID, CREATE_DATE, GROUP_CODE, VALUE1, TEXT1, ORDERS) VALUES('INDENT_SOURCE_002', now(), 'INDENT_SOURCE', '002', '科宝', 2);
INSERT INTO ZL_DICT (ID, CREATE_DATE, GROUP_CODE, VALUE1, TEXT1, ORDERS) VALUES('INDENT_SOURCE_003', now(), 'INDENT_SOURCE', '003', '海尔', 3);
INSERT INTO ZL_DICT (ID, CREATE_DATE, GROUP_CODE, VALUE1, TEXT1, ORDERS) VALUES('INDENT_SOURCE_004', now(), 'INDENT_SOURCE', '004', '欧派', 3);

INSERT INTO ZL_DICT (ID, CREATE_DATE, GROUP_CODE, VALUE1, TEXT1, ORDERS) VALUES('VEHICLE_MINIVAN', now(), 'VEHICLE', 'MINIVAN', '小面', 1);
INSERT INTO ZL_DICT (ID, CREATE_DATE, GROUP_CODE, VALUE1, TEXT1, ORDERS) VALUES('VEHICLE_JINBEI', now(), 'VEHICLE', 'JINBEI', '金杯', 2);
INSERT INTO ZL_DICT (ID, CREATE_DATE, GROUP_CODE, VALUE1, TEXT1, ORDERS) VALUES('VEHICLE_VAN', now(), 'VEHICLE', 'VAN', '箱货', 3);

INSERT INTO ZL_DICT (ID, CREATE_DATE, GROUP_CODE, VALUE1, TEXT1, ORDERS) VALUES('VEHICLE_BELONGS_OWN', now(), 'VEHICLE_BELONGS', 'OWN', '自有', 1);
INSERT INTO ZL_DICT (ID, CREATE_DATE, GROUP_CODE, VALUE1, TEXT1, ORDERS) VALUES('VEHICLE_BELONGS_RENT', now(), 'VEHICLE_BELONGS', 'RENT', '租赁', 2);
INSERT INTO ZL_DICT (ID, CREATE_DATE, GROUP_CODE, VALUE1, TEXT1, ORDERS) VALUES('VEHICLE_BELONGS_NONE', now(), 'VEHICLE_BELONGS', 'NONE', '无', 3);

INSERT INTO ZL_APARTY(CREATE_DATE, ID, CODE1, NAME1, APARTY_STATUS) VALUES(now(), '001','001','博洛尼','ENABLED');
INSERT INTO ZL_APARTY(CREATE_DATE, ID, CODE1, NAME1, APARTY_STATUS) VALUES(now(),'002','002','科宝','ENABLED');
INSERT INTO ZL_APARTY(CREATE_DATE, ID, CODE1, NAME1, APARTY_STATUS) VALUES(now(),'003','003','海尔','ENABLED');
INSERT INTO ZL_APARTY(CREATE_DATE, ID, CODE1, NAME1, APARTY_STATUS) VALUES(now(),'004','004','欧派','ENABLED');

INSERT INTO ZL_SCHEDULE (ID, NAME1, QUALIFIED_NAME, STATUS, CRON_EXPRESSION) VALUES ('RankJob0', '定期更新排名', 'com.company.api.schedule.job.RankJob', 'ENABLED', '0 59 23 * * ?');
INSERT INTO ZL_SCHEDULE (ID, NAME1, QUALIFIED_NAME, STATUS, CRON_EXPRESSION) VALUES ('IndentOvertimeJob0', '订单到期提醒', 'com.company.api.schedule.job.IndentOvertimeJob', 'ENABLED', '0 0 0/1 * * ?');
INSERT INTO ZL_SCHEDULE (ID, NAME1, QUALIFIED_NAME, STATUS, CRON_EXPRESSION) VALUES ('IndentOvertimeEvaluateJob', '评价到期自动评价', 'com.company.api.schedule.job.IndentOvertimeEvaluateJob', 'ENABLED', '0 0 0/1 * * ?');
INSERT INTO ZL_SCHEDULE (ID, NAME1, QUALIFIED_NAME, STATUS, CRON_EXPRESSION) VALUES ('WorkerApplyOverTimeJob', '工匠申请超时处理', 'com.company.api.schedule.job.WorkerApplyOverTimeJob', 'ENABLED', '0 0 0/1 * * ?');
INSERT INTO ZL_SCHEDULE (ID, NAME1, QUALIFIED_NAME, STATUS, CRON_EXPRESSION) VALUES ('ScrambleIndentOvertimeJob', '订单超时未抢处理', 'com.company.api.schedule.job.ScrambleIndentOvertimeJob', 'ENABLED', '0 0 0/1 * * ?');