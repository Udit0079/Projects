/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.constant;

public enum PullSmsCode {

    AC_BAL("BAL", "1"),
    AC_STM("STM", "3");
    private String pullKey;
    private String serviceKey;

    private PullSmsCode(String pullKey, String serviceKey) {
        this.pullKey = pullKey;
        this.serviceKey = serviceKey;
    }

    public static String pullKey(String serviceKey) {
        for (PullSmsCode obj : values()) {
            if (obj.serviceKey.equals(serviceKey)) {
                return obj.pullKey;
            }
        }
        return null;
    }

    public static String getServiceKey(String pullKey) {
        for (PullSmsCode obj : values()) {
            if (obj.pullKey.equals(pullKey)) {
                return obj.serviceKey;
            }
        }
        return null;
    }
}
