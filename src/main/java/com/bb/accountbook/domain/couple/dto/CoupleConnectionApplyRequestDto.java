package com.bb.accountbook.domain.couple.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoupleConnectionApplyRequestDto {

    @NotBlank
    private Long userCoupleId;

    @NotBlank
    private String nickname;
}
