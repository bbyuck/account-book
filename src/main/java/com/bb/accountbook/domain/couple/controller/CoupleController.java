package com.bb.accountbook.domain.couple.controller;

import com.bb.accountbook.domain.couple.service.CoupleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CoupleController {

    private final CoupleService coupleService;
}
