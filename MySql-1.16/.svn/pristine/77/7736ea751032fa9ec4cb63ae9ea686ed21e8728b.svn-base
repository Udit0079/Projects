/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.constant;

public enum DdsMultiGl {

    DAILY_DEPOSIT_SCHEME("0", "DDS"),
    ADVANCES("1", "ADN"),
    RECURRING_DEPOSIT("2", "RD"),
    SAVING_DEPOSIT("3", "SAV"),
    CURRENT_DEPOSIT("4", "CUR");
    private String glCode;
    private String glText;

    public String getGlCode() {
        return glCode;
    }

    public String getGlText() {
        return glText;
    }

    private DdsMultiGl(String glCode, String glText) {
        this.glCode = glCode;
        this.glText = glText;
    }

    public String getGlCode(String glText) {
        for (DdsMultiGl instance : values()) {
            if (instance.glText.equals(glText)) {
                return instance.glCode;
            }
        }
        return null;
    }

    public String getGlText(String glCode) {
        for (DdsMultiGl instance : values()) {
            if (instance.glCode.equals(glCode)) {
                return instance.glText;
            }
        }
        return null;
    }
}
