package com.xpanse.ims.Model;

import java.util.List;

public class ResponseData {

    private String imsId;
    private String tenantRefId;
    private String imsTranDate;
    private String imsStatus;
    private List<ResourceDetail> resourceDetails;

    // Getters and Setters

    public String getImsId() {
        return imsId;
    }

    public void setImsId(String imsId) {
        this.imsId = imsId;
    }

    public String getTenantRefId() {
        return tenantRefId;
    }

    public void setTenantRefId(String tenantRefId) {
        this.tenantRefId = tenantRefId;
    }

    public String getImsTranDate() {
        return imsTranDate;
    }

    public void setImsTranDate(String imsTranDate) {
        this.imsTranDate = imsTranDate;
    }

    public String getImsStatus() {
        return imsStatus;
    }

    public void setImsStatus(String imsStatus) {
        this.imsStatus = imsStatus;
    }

    public List<ResourceDetail> getResourceDetails() {
        return resourceDetails;
    }

    public void setResourceDetails(List<ResourceDetail> resourceDetails) {
        this.resourceDetails = resourceDetails;
    }

    // Nested class for ResourceDetail
    public static class ResourceDetail {
        private String storageType;
        private String storageAddress;
        private String resourceType;

        // Getters and Setters
        public String getStorageType() {
            return storageType;
        }

        public void setStorageType(String storageType) {
            this.storageType = storageType;
        }

        public String getStorageAddress() {
            return storageAddress;
        }

        public void setStorageAddress(String storageAddress) {
            this.storageAddress = storageAddress;
        }

        public String getResourceType() {
            return resourceType;
        }

        public void setResourceType(String resourceType) {
            this.resourceType = resourceType;
        }
    }
}
