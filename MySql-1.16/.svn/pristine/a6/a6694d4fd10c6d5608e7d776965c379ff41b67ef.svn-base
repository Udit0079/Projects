/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cbs.constant;

/**
 *
 * @author root
 */
public enum RoleName {
     // TODO Remove Id From Enums
    ROLE_MANAGER("MANAGER", 1), ROLE_ASST_MANAGER("ASSISTENT MANAGER", 2), ROLE_OFFICER("OFFICER", 3),
    ROLE_CLERK("CLERK", 4), ROLE_DBA("DBA", 5), ROLE_SYS_ADMIN("SYSTEM ADMIN", 6),
    ROLE_SUPER_USER("SUPER USER", 7),ROLE_AUDITOR("AUDITOR", 99);
    private int roleId;
    private String value;

    private RoleName(String value, int roleId) {
        this.value = value;
        this.roleId = roleId;
    }

    public String getValue() {
        return this.value;
    }

    public int getRoleId() {
        return roleId;
    }

    public static String getRole(int roleId) {
        for (RoleName role : values()) {
            if (role.roleId == roleId) {
                return role.value;
            }
        }
        return null;
    }
    
    public static Integer getRoleId(String value) {
        for (RoleName role : values()) {
            if (role.value.equals(value)) {
                return role.roleId;
            }
        }
        return null;
    }
}
