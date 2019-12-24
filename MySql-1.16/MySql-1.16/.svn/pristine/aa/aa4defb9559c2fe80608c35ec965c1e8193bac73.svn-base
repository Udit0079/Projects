/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.constant;

public enum MmsEnum {

    CREATE("CREATE", "com.cbs.pojo.mms.pain009.Document"),
    AMEND("AMEND", "com.cbs.pojo.mms.pain009.Document"),
    CANCEL("CANCEL", "com.cbs.pojo.mms.pain009.Document");
    private String msg;
    private String className;

    private MmsEnum(String msg, String className) {
        this.msg = msg;
        this.className = className;
    }

    public String getMsg() {
        return msg;
    }

    public String getClassName() {
        return className;
    }

    public static String getMsgName(String className) {
        for (MmsEnum instance : values()) {
            if (instance.className.equals(className)) {
                return instance.msg;
            }
        }
        return null;
    }

    public static String getClassName(String msg) {
        for (MmsEnum instance : values()) {
            if (instance.msg.equals(msg)) {
                return instance.className;
            }
        }
        return null;
    }
}
