package com.cloudstreamkafkafunctional.programming.consumeraccountenrichment.service;

import com.cloudstreamkafkafunctional.programming.sharedLib.model.AccountInfo;
import com.cloudstreamkafkafunctional.programming.sharedLib.model.TransactionReportData;

public interface AccountService {
  AccountInfo enrichAccountInfo(TransactionReportData transactionReportData);
}
