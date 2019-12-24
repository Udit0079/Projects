package com.cbs.entity;

import java.sql.Date;
import java.util.Set;

public abstract class BaseEntity {

    private Boolean isDelete;
    private String modifiedBy;
    private Date modifiedDate;

    public Set<BaseEntity> fetchAllChildRecordSets() {
        // TODO Implement this method if you dont want to deleted objects from database.
        // See@ DataAccessObject
        return null;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    @Override
    public String toString() {
        return "BaseEntity [isDelete=" + isDelete + ", modifiedBy=" + modifiedBy + ", modifiedDate=" + modifiedDate + "]";
    }
}