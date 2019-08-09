package org.micro.tcc.common.core;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class TccTransactionContext implements Serializable {


    private static final long serialVersionUID = -5483706013898190522L;
    private TransactionGid xid;

    private int status;

    private Map<String, String> attachments = new ConcurrentHashMap<String, String>();

    public TccTransactionContext() {

    }

    public TccTransactionContext(TransactionGid xid, int status) {
        this.xid = xid;
        this.status = status;
    }

    public void setXid(TransactionGid xid) {
        this.xid = xid;
    }

    public TransactionGid getXid() {
        return xid;
    }

    public void setAttachments(Map<String, String> attachments) {
        if (attachments != null && !attachments.isEmpty()) {
            this.attachments.putAll(attachments);
        }
    }

    public Map<String, String> getAttachments() {
        return attachments;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }


}