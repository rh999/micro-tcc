package org.micro.tcc.tc.component;

import feign.RequestInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpRequestInterceptor;
import org.micro.tcc.common.constant.Constant;
import org.micro.tcc.common.core.Transaction;
import org.micro.tcc.tc.http.HttpClientRequestInterceptor;
import org.micro.tcc.tc.http.RestTemplateInterceptor;
import org.micro.tcc.tc.recover.RecoverScheduledJob;
import org.micro.tcc.tc.recover.RecoverScheduledZookeeperJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

/**
 * @author jeff.liu
 *
 *  date 2019/8/6 15:01
 */
// @Configuration
@ComponentScan(basePackages = {"org.micro.tcc.**"})
@Slf4j
public class MicroTccSpringConfig {


    @Bean
    public CoordinatorWatcher coordinatorWatcher() throws Exception {
        System.setProperty("jute.maxbuffer", String.valueOf(4096 * 1024 * 3));
        CoordinatorWatcher coordinatorWatcher=new CoordinatorWatcher();
        return coordinatorWatcher;
    }

    @Bean
    public RecoverScheduledJob recoverScheduledJob(){
        RecoverScheduledJob recoverScheduledJob=new RecoverScheduledJob();
        return recoverScheduledJob;
    }

    @Bean
    @ConditionalOnClass(CoordinatorWatcher.class)
    public RecoverScheduledZookeeperJob recoverScheduledZookeeperJob(){
        RecoverScheduledZookeeperJob recoverScheduledJob=new RecoverScheduledZookeeperJob();
        return recoverScheduledJob;
    }

    @Bean
    @ConditionalOnClass(HttpRequestInterceptor.class)
    public HttpRequestInterceptor httpClientRequestInterceptor(){
        return  new HttpClientRequestInterceptor();
    }

    @Bean
    @ConditionalOnClass(ClientHttpRequestInterceptor.class)
    public ClientHttpRequestInterceptor clientHttpRequestInterceptor(){
        return  new RestTemplateInterceptor();
    }

    /*@Bean
    //@ConditionalOnClass
    @ConditionalOnBean(ClientHttpRequestInterceptor.class)
    public RestTemplate restTemplate(ClientHttpRequestInterceptor clientHttpRequestInterceptor){
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setInterceptors(Collections.singletonList(clientHttpRequestInterceptor));
        return restTemplate;
    }*/

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            log.debug("TCC:Feign interceptor ");
            Transaction transaction = TransactionManager.getInstance().getCurrentTransaction();
            if(null==transaction){
                return;
            }
            String gid = transaction.getTransactionXid().getGlobalTccTransactionId();
            requestTemplate.header(Constant.GLOBAL_TCCTRANSACTION_ID, gid);
            requestTemplate.header(Constant.TCCTRANSACTION_STATUS, String.valueOf(transaction.getStatus().value()));
        };
    }


}