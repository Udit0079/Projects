/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.constant;

/**
 *
 * @author root
 */
public enum NeftEnum {

    F27("F27", "com.cbs.pojo.neftrtgs.F27"),
    N02("N02", "com.cbs.pojo.neftrtgs.N02"),
    N06("N06", "com.cbs.pojo.neftrtgs.N06"),
    N07("N07", "com.cbs.pojo.neftrtgs.N07"),
    R09("R09", "com.cbs.pojo.neftrtgs.R09"),
    R41("R41", "com.cbs.pojo.neftrtgs.R41"),
    R42("R42", "com.cbs.pojo.neftrtgs.R42"),
    R90("R90", "com.cbs.pojo.neftrtgs.R90");
    private String msg;
    private String className;

    private NeftEnum(String msg, String className) {
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
        for (NeftEnum instance : values()) {
            if (instance.className.equals(className)) {
                return instance.msg;
            }
        }
        return null;
    }

    public static String getClassName(String msg) {
        for (NeftEnum instance : values()) {
            if (instance.msg.equals(msg)) {
                return instance.className;
            }
        }
        return null;
    }
}
