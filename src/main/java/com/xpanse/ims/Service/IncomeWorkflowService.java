package com.xpanse.ims.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xpanse.ims.DTO.DocumentTransferResponse;
import com.xpanse.ims.Model.DocumentReference;
import com.xpanse.ims.Model.ResponseData;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class IncomeWorkflowService {

    private final RestTemplate restTemplate;

    private final S3Client s3Client;

    // Constructor injection of RestTemplate
    public IncomeWorkflowService(RestTemplate restTemplate, S3Client s3Client) {
        this.restTemplate = restTemplate;
        this.s3Client = s3Client;
    }

    // Method to fetch data based on IMSId and return as a list of ResponseData
    public DocumentTransferResponse fetchIncomeWorkflowData(String imsId) {
        // The URL endpoint with IMSId
        String url = "https://d3ywvgmhi0.execute-api.us-east-1.amazonaws.com/VOIE_POC/income-workflow-api/responses/" + imsId;

        // Prepare headers with the X-Tenant-ID
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Tenant-ID", "TID00001");  // Assuming the value is static

        // Create HttpEntity with headers
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            // Send the request and return the response
            ResponseEntity<List<ResponseData>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    new ParameterizedTypeReference<List<ResponseData>>() {
                    }
            );

            DocumentTransferResponse res = transferPDFs("vendor-pdfs-bucket");

            // Convert response to JSON
//            String jsonRes = convertResponseToJson(res);

            // Return the response body (of type List<ResponseData>)
            return res;
        } catch (HttpClientErrorException e) {
            // Print more detailed error information
            System.out.println("Error Response: " + e.getResponseBodyAsString());
            System.out.println("Error Status Code: " + e.getStatusCode());
            throw e;  // You can throw the exception or handle it gracefully as needed
        }
    }

    // Method to transfer PDFs and return the document transfer response
    public DocumentTransferResponse transferPDFs(String destinationBucket) {
        String sourceBucket = "ims-podium-extractor-test"; // Source bucket name
        // List objects in the source bucket
        ListObjectsV2Request listObjectsRequest = ListObjectsV2Request.builder()
                .bucket(sourceBucket) // Specify the source bucket name
                .build(); // Build the request

        // List objects in the source bucket
        ListObjectsV2Response listObjectsResponse = s3Client.listObjectsV2(listObjectsRequest);
        List<S3Object> s3Objects = listObjectsResponse.contents(); // Extract the list of objects

        // Initialize the list of document references
        List<DocumentReference> documentReferences = new ArrayList<>();
        int documentCount = 0;

        // Iterate over all objects in the source bucket
        for (S3Object s3Object : s3Objects) {
            String key = s3Object.key(); // Get the object key (filename) from S3

            // Check if the object is a PDF (based on file extension)
            if (key.endsWith(".pdf")) {
                System.out.println("Transferring PDF file: " + key);

                // Build the copy request for the PDF file
                CopyObjectRequest copyRequest = CopyObjectRequest.builder()
                        .sourceBucket(sourceBucket) // Specify the source bucket
                        .sourceKey(key) // Specify the key (filename) of the object to copy
                        .destinationBucket(destinationBucket) // Specify the destination bucket
                        .destinationKey(key) // Use the same key (filename) for the destination
                        .build();

                // Perform the copy operation (transfer the PDF)
                CopyObjectResponse copyResponse = s3Client.copyObject(copyRequest);
                System.out.println("Successfully transferred PDF: " + key);

                // Generate the URL for the transferred PDF
                String pdfUrl = generateS3Url(destinationBucket, key);
                System.out.println("URL of transferred PDF: " + pdfUrl);

                // Generate a custom serviceProviderDocId (this could be any business logic)
                String serviceProviderDocId = generateServiceProviderDocId(key);

                // Format the serviceProviderDocLocation (custom URL)
                String serviceProviderDocLocation = pdfUrl;

                // Create a DocumentReference entity
                DocumentReference documentReference = new DocumentReference(serviceProviderDocId, serviceProviderDocLocation);

                // Add to the list of document references
                documentReferences.add(documentReference);

                // Increment the document count
                documentCount++;
            }
        }

        // Create and return the response DTO
        DocumentTransferResponse response = new DocumentTransferResponse(documentCount, documentReferences);
        return response;
    }

    // Helper method to generate a service provider document ID based on the file name (or other logic)
    private String generateServiceProviderDocId(String s3Key) {
        // Example: Use UUID for a unique ID or you can extract a custom ID from the file name
        return UUID.randomUUID().toString(); // Replace with custom logic if necessary
    }

    // Helper method to generate the S3 URL for the document
    private String generateS3Url(String bucketName, String objectKey) {
        // Construct the URL for the PDF document
        return String.format("https://%s.s3.%s.amazonaws.com/%s",
                bucketName, "us-east-1", objectKey); // You can dynamically set the region if necessary
    }

    // Method to convert DocumentTransferResponse to JSON
    public String convertResponseToJson(DocumentTransferResponse res) {
        try {
            // Create ObjectMapper instance
            ObjectMapper objectMapper = new ObjectMapper();

            // Convert DocumentTransferResponse to JSON string
            String jsonResponse = objectMapper.writeValueAsString(res);

            // Return the JSON string
            return jsonResponse;
        } catch (Exception e) {
            e.printStackTrace();  // Handle the exception as needed
            return null;
        }
    }
}
