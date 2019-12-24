/**
 *
 * @author Navneet Goyal
 */
package com.cbs.dto;

public class UserGrid implements Comparable {

    private short levelid;
    private String fromdate, todate;
    private String userid, username, status, brncode, orgbrncode, deputeorxfer;

    public String getBrncode() {
        return brncode;
    }

    public void setBrncode(String brncode) {
        this.brncode = brncode;
    }

    public String getDeputeorxfer() {
        return deputeorxfer;
    }

    public void setDeputeorxfer(String deputeorxfer) {
        this.deputeorxfer = deputeorxfer;
    }

    public String getOrgbrncode() {
        return orgbrncode;
    }

    public void setOrgbrncode(String orgbrncode) {
        this.orgbrncode = orgbrncode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public short getLevelid() {
        return levelid;
    }

    public void setLevelid(short levelid) {
        this.levelid = levelid;
    }

    public String getFromdate() {
        return fromdate;
    }

    public void setFromdate(String fromdate) {
        this.fromdate = fromdate;
    }

    public String getTodate() {
        return todate;
    }

    public void setTodate(String todate) {
        this.todate = todate;
    }

    public int compareTo(Object o) {
        return this.userid.compareTo(((UserGrid) o).userid);
    }
}

