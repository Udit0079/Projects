/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.report;

import java.util.List;

/**
 *
 * @author sipl
 */
public class Oss4PartGPojo {
    List<Oss4PartGMgmtPojo> oss4PartGMgmtList;
    List<Oss4PartGUnSecAdvPojo> oss4PartGUnSecAdvList;
    List<Oss4PartGExpPojo> oss4PartGExpList;
    List<Oss4PartGSecAdvPojo> oss4PartGSecAdvList;

    public List<Oss4PartGExpPojo> getOss4PartGExpList() {
        return oss4PartGExpList;
    }

    public void setOss4PartGExpList(List<Oss4PartGExpPojo> oss4PartGExpList) {
        this.oss4PartGExpList = oss4PartGExpList;
    }

    public List<Oss4PartGMgmtPojo> getOss4PartGMgmtList() {
        return oss4PartGMgmtList;
    }

    public void setOss4PartGMgmtList(List<Oss4PartGMgmtPojo> oss4PartGMgmtList) {
        this.oss4PartGMgmtList = oss4PartGMgmtList;
    }

    public List<Oss4PartGSecAdvPojo> getOss4PartGSecAdvList() {
        return oss4PartGSecAdvList;
    }

    public void setOss4PartGSecAdvList(List<Oss4PartGSecAdvPojo> oss4PartGSecAdvList) {
        this.oss4PartGSecAdvList = oss4PartGSecAdvList;
    }

    public List<Oss4PartGUnSecAdvPojo> getOss4PartGUnSecAdvList() {
        return oss4PartGUnSecAdvList;
    }

    public void setOss4PartGUnSecAdvList(List<Oss4PartGUnSecAdvPojo> oss4PartGUnSecAdvList) {
        this.oss4PartGUnSecAdvList = oss4PartGUnSecAdvList;
    }
}