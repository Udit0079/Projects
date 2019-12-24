package com.cbs.email.service;

/**
 * @author sjain
 *
 */
public enum EmailType {

    SEND_ACCT_STMAT("Account Statement", "acct-stmt.vm");
    private String key;
    private String value;

    EmailType(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    @Override
    public String toString() {
        return value;
    }

    public String getValue() {
        return value;
    }
}
