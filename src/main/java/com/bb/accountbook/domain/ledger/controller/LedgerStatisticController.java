package com.bb.accountbook.domain.ledger.controller;

import com.bb.accountbook.domain.ledger.service.LedgerPresentationService;
import com.bb.accountbook.domain.ledger.service.LedgerStatisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LedgerStatisticController {

    private final LedgerStatisticService ledgerStatisticService;
    private final LedgerPresentationService ledgerPresentationService;


}
