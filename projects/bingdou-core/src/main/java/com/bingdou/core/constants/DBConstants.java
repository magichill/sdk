package com.bingdou.core.constants;

import com.bingdou.core.utils.SpringUtils;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;


public class DBConstants {

    private DBConstants() {
    }

    public static final String USER_DB_NAME;
    public static final String PAY_DB_NAME;

    static {
        SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) SpringUtils.getBean("sqlSessionFactory");
        Configuration configuration = sqlSessionFactory.getConfiguration();
        USER_DB_NAME = configuration.getVariables().getProperty("userDB");
        PAY_DB_NAME = configuration.getVariables().getProperty("payDB");
    }

}
