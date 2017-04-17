package com.company.api.fw;

public interface Constants {

    /**
     * 保留小数位数
     */
    int SCALE = 2;

    String ITEM_SEPARATOR = ",";

    String USER_BEAN = "USER_BEAN";

    String WEIXIN_DATA = "WEIXIN_DATA";

    String LOGIN_KEY = "LOGIN_KEY";

    String MULTI_VALUE_SEP = "|";

    String[] IMGTYPES = { "jpg", "jpeg", "bmp", "gif", "png" };
    int IMGSIZE = 5 * 1024 * 1024;// 5M
}
