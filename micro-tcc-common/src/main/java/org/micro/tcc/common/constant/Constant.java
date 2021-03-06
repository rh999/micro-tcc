package org.micro.tcc.common.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
*@author jeff.liu
*@desc  静态变量
*@date 2019/8/15
*/
@Component
public  class  Constant {


    public static String transactionMapKey;
    public static String prefix="TCC:";
    public static String ROOT=":ROOT";
    public static String BRANCH=":BRANCH";
    public static String TRY="TRY";
    public static String CONFIRM="CONFIRM";
    public static String CANCEL="CANCEL";
    public final static String GLOBAL_TCCTRANSACTION_ID="globalTccTransactionId";
    public final static String TCCTRANSACTION_STATUS="tccTransactionStatus";

    public final static String TRANSACTION_GROUP="TRANSACTION_GROUP:";

    public final static String TRANSACTION_LOG="TRANSACTION_LOG";

    public static String datacenterId;

    public static String machineId;

    public static String getTransactionMapKey(){
        return prefix+transactionMapKey;
    }

    public final static String DELIMIT="#";

    public static String IDEMPOTENT;

    public static String IDEMPOTENT_TRUE="true";

    public static long DELAY=50;

    public static long PERIOD=10;

    public static String getAppName(){
        return transactionMapKey;
    }

    @Value("${micro.tcc.datacenterId:1}")
    public void setDatacenterId(String datacenterId){
        Constant.datacenterId=datacenterId;
    }

    @Value("${micro.tcc.machineId:1}")
    public void setMachineId(String machineId){
        Constant.machineId=machineId;
    }

    @Value("${spring.application.name}")
    public void setTransactionMapKey(String transactionMapKey) {
        Constant.transactionMapKey = transactionMapKey;
    }

    @Value("${micro.tcc.idempotent:true}")
    public void setIdempotent(String idempotent) {
        Constant.IDEMPOTENT = idempotent;
    }
}
