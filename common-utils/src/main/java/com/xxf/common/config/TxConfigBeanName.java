package com.xxf.common.config;

import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import java.util.Properties;

/**
 * 作用：扫描工程中类名以Service或ServiceImpl结尾的，
 * 方法名以add/save/update/batch/delete/import/change/insert开头的增加事务
 */
//@Component
//@Configuration
public class TxConfigBeanName {
    @Autowired
    private DataSourceTransactionManager transactionManager;

    // 创建事务通知
//    @Bean(name = "txAdvice")
    public TransactionInterceptor getAdvisor() throws Exception {
        Properties properties = new Properties();
        properties.setProperty("get*", "PROPAGATION_NOT_SUPPORTED");
        properties.setProperty("select*", "PROPAGATION_NOT_SUPPORTED");
        properties.setProperty("query*", "PROPAGATION_NOT_SUPPORTED");
        properties.setProperty("add*", "PROPAGATION_REQUIRED");
        properties.setProperty("save*", "PROPAGATION_REQUIRED");
        properties.setProperty("update*", "PROPAGATION_REQUIRED");
        properties.setProperty("batch*", "PROPAGATION_REQUIRED");
        properties.setProperty("delete*", "PROPAGATION_REQUIRED");
        properties.setProperty("import*", "PROPAGATION_REQUIRED");
        properties.setProperty("change*", "PROPAGATION_REQUIRED");
        properties.setProperty("insert*", "PROPAGATION_REQUIRED");
        TransactionInterceptor tsi = new TransactionInterceptor(transactionManager, properties);
        return tsi;
    }

    //    @Bean
    public BeanNameAutoProxyCreator txProxy() {
        BeanNameAutoProxyCreator creator = new BeanNameAutoProxyCreator();
        creator.setInterceptorNames("txAdvice");
        creator.setBeanNames("*Service", "*ServiceImpl");
        creator.setProxyTargetClass(true);
        return creator;
    }
}
