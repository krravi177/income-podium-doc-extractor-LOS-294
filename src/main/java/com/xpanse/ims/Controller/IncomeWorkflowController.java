package com.xpanse.ims.Controller;

import com.xpanse.ims.DTO.DocumentTransferResponse;
import com.xpanse.ims.Service.IncomeWorkflowService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.databind.ObjectMapper;  // Import ObjectMapper


@RestController
public class IncomeWorkflowController {

    private final IncomeWorkflowService incomeWorkflowService;

    // Constructor injection of the service
    public IncomeWorkflowController(IncomeWorkflowService incomeWorkflowService) {
        this.incomeWorkflowService = incomeWorkflowService;
    }

    @GetMapping("/fetch-income-workflow")
    public DocumentTransferResponse fetchIncomeWorkflow(@RequestParam String imsId) {
        // Fetch data for the given IMSId and return the result
        DocumentTransferResponse response = incomeWorkflowService.fetchIncomeWorkflowData(imsId);

        ObjectMapper objectMapper = new ObjectMapper();

        return response;
    }
}
