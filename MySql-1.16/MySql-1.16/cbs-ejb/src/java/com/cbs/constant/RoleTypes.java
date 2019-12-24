/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.constant;

/**
 *
 * @author Dhirendra Singh
 */
public enum RoleTypes {

    // TODO Remove Id From Enums
    ROLE_MANAGER("ROLE_MANAGER", 1), ROLE_ASST_MANAGER("ROLE_ASST_MANAGER", 2), ROLE_OFFICER("ROLE_OFFICER", 3),
    ROLE_CLERK("ROLE_CLERK", 4), ROLE_DBA("ROLE_DBA", 5), ROLE_SYS_ADMIN("ROLE_SYS_ADMIN", 6),
    ROLE_SUPER_USER("ROLE_SUPER_USER", 7),ROLE_ASST_DBA("ROLE_ASST_DBA", 8),ROLE_AUDITOR("ROLE_AUDITOR", 99);
    private int roleId;
    private String value;

    private RoleTypes(String value, int roleId) {
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
        for (RoleTypes role : values()) {
            if (role.roleId == roleId) {
                return role.value;
            }
        }
        return null;
    }
    
    public static Integer getRoleId(String value) {
        for (RoleTypes role : values()) {
            if (role.value.equals(value)) {
                return role.roleId;
            }
        }
        return null;
    }
}
