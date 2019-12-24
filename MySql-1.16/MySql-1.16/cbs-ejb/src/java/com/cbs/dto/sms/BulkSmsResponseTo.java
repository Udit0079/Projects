/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.sms;

import java.io.Serializable;

public class BulkSmsResponseTo implements Serializable {

    private String id;
    private String status;
    private String error;
    private String vendorResId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getVendorResId() {
        return vendorResId;
    }

    public void setVendorResId(String vendorResId) {
        this.vendorResId = vendorResId;
    }
}
