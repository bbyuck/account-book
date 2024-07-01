package com.bb.accountbook.domain.icon.controller;

import com.bb.accountbook.common.model.ApiResponse;
import com.bb.accountbook.domain.icon.dto.*;
import com.bb.accountbook.domain.icon.service.IconService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class IconController {

    private final IconService iconService;

    @GetMapping("/api/v1/icons")
    public ApiResponse<IconFindResponseDto> findIcons() {
        return new ApiResponse<>(new IconFindResponseDto(iconService.findAllIcons().stream().map(IconDto::new).collect(Collectors.toList())));
    }

    @PostMapping("/api/v1/icon")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ApiResponse<IconInsertResponseDto> insertIcons(@RequestBody MultiIconInsertRequestDto requestDto) {
        return new ApiResponse<>(new IconInsertResponseDto(iconService.insertIcons(requestDto)));
    }
}
