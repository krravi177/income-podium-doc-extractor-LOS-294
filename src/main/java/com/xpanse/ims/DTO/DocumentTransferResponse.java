package com.xpanse.ims.DTO;

import com.xpanse.ims.Model.DocumentReference;

import java.util.List;

public class DocumentTransferResponse {
    private int documentCount;
    private List<DocumentReference> documentReference;

    // Constructor
    public DocumentTransferResponse(int documentCount, List<DocumentReference> documentReference) {
        this.documentCount = documentCount;
        this.documentReference = documentReference;
    }

    // Getters and Setters
    public int getDocumentCount() {
        return documentCount;
    }

    public void setDocumentCount(int documentCount) {
        this.documentCount = documentCount;
    }

    public List<DocumentReference> getDocumentReference() {
        return documentReference;
    }

    public void setDocumentReference(List<DocumentReference> documentReference) {
        this.documentReference = documentReference;
    }

    @Override
    public String toString() {
        return "DocumentTransferResponse{" +
                "documentCount=" + documentCount +
                ", documentReference=" + documentReference +
                '}';
    }
}
