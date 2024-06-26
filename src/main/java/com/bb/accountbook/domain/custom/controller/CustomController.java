package com.bb.accountbook.domain.custom.controller;

import com.bb.accountbook.common.model.ApiResponse;
import com.bb.accountbook.domain.custom.dto.CustomColorDto;
import com.bb.accountbook.domain.custom.dto.CustomCreateRequestDto;
import com.bb.accountbook.domain.custom.dto.CustomCreateResponseDto;
import com.bb.accountbook.domain.custom.dto.CustomDto;
import com.bb.accountbook.domain.custom.service.CustomService;
import com.bb.accountbook.security.SecurityContextProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static com.bb.accountbook.common.model.codes.SuccessCode.SUC_CUS_000;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CustomController {

    private final CustomService customService;

    private final SecurityContextProvider securityContextProvider;

    @PostMapping("/api/v1/custom")
    public ApiResponse<CustomCreateResponseDto> createCustom(@RequestBody CustomCreateRequestDto requestDto) {
        Long customId = customService.saveCustom(securityContextProvider.getCurrentEmail(), requestDto.getCustomCode(), requestDto.getCustomValue());
        return new ApiResponse<>(new CustomCreateResponseDto(customId), SUC_CUS_000);
    }

    @GetMapping("/api/v1/custom")
    public ApiResponse<List<CustomDto>> findCustoms() {
        return new ApiResponse<>(
                customService.findOwnCustoms(securityContextProvider.getCurrentEmail())
                        .stream()
                        .map(custom -> new CustomDto(custom.getId(), custom.getCode(), custom.getValue()))
                        .collect(Collectors.toList())
        );
    }

    @GetMapping("/api/v1/custom/color")
    public ApiResponse<CustomColorDto> findCustomColor() {
        return new ApiResponse<>(new CustomColorDto(customService.getCustomColor(securityContextProvider.getCurrentEmail())));
    }
}
