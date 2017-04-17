package com.company.api.fw;

public interface DictCodes {

    // protocols
    String SQL_PROTOCOL = "sql://";
    String ENUM_PROTOCOL = "enum://";
    String DICT_PROTOCOL = "dict://";
    String DF_PROTOCOL = "df://";

    /** 行业 */
    String INDUSTRY = "INDUSTRY";
    
    /** 部门 */
    String PART = "PART";

    /** 服务类型 */
    String SERVICE_TYPE = "SERVICE_TYPE";

    /** 服务类别 */
    String SERVICE_SORT = "SERVICE_SORT";

    /** 项目状态 */
    String ITEM_STATUS = "ITEM_STATUS";

    /** 评分 */
    String EVALUATE_SCORE = "EVALUATE_SCORE";

    /** 单位 */
    String UOM = "UOM";

    /** 交通工具 */
    String VEHICLE = "VEHICLE";
    
    /** 交通工具归属 */
    String VEHICLE_BELONGS = "VEHICLE_BELONGS";

}