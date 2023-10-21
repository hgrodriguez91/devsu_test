package com.test.devsu.srvaccountmanager.dto;

import com.test.devsu.srvaccountmanager.model.Account;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReportResponseDTO {

    List<Account> accountDetails;
}
