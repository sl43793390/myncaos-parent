package com.example.entity;

import java.util.Date;

public class Permission {
    private String permissionId;

    private String resourceName;

    private String action;

    private Double version;

    private Date timestamp;

    private String idParent;

    private Integer nbrLevel;

    private Long nbrOrder;

    public String getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(String permissionId) {
        this.permissionId = permissionId == null ? null : permissionId.trim();
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName == null ? null : resourceName.trim();
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action == null ? null : action.trim();
    }

    public Double getVersion() {
        return version;
    }

    public void setVersion(Double version) {
        this.version = version;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getIdParent() {
        return idParent;
    }

    public void setIdParent(String idParent) {
        this.idParent = idParent == null ? null : idParent.trim();
    }

    public Integer getNbrLevel() {
        return nbrLevel;
    }

    public void setNbrLevel(Integer nbrLevel) {
        this.nbrLevel = nbrLevel;
    }

    public Long getNbrOrder() {
        return nbrOrder;
    }

    public void setNbrOrder(Long nbrOrder) {
        this.nbrOrder = nbrOrder;
    }
}