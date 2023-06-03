package com.group1.projectmanagementapi.status.models.dto.request;

import com.group1.projectmanagementapi.status.models.Status;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StatusRequest {

    @NotEmpty(message = "Status is required")
    private String status;

    public Status convertToEntity() {
        return Status.builder()
                .status(this.status)
                .build();
    }
}
