package com.xpanse.ims.Model;

public class DocumentReference {
    private String serviceProviderDocId;
    private String serviceProviderDocLocation;

    // Constructor
    public DocumentReference(String serviceProviderDocId, String serviceProviderDocLocation) {
        this.serviceProviderDocId = serviceProviderDocId;
        this.serviceProviderDocLocation = serviceProviderDocLocation;
    }

    // Getters and Setters
    public String getServiceProviderDocId() {
        return serviceProviderDocId;
    }

    public void setServiceProviderDocId(String serviceProviderDocId) {
        this.serviceProviderDocId = serviceProviderDocId;
    }

    public String getServiceProviderDocLocation() {
        return serviceProviderDocLocation;
    }

    public void setServiceProviderDocLocation(String serviceProviderDocLocation) {
        this.serviceProviderDocLocation = serviceProviderDocLocation;
    }

    @Override
    public String toString() {
        return "DocumentReference{" +
                "serviceProviderDocId='" + serviceProviderDocId + '\'' +
                ", serviceProviderDocLocation='" + serviceProviderDocLocation + '\'' +
                '}';
    }
}
