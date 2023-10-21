package com.test.devsu.srvclientmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReportDTO {

    private ClientDTO cliente;
    private List<AccountDTO> accountDetails;
}
