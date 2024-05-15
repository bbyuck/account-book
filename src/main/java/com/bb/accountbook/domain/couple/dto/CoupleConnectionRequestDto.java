package com.bb.accountbook.domain.couple.dto;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoupleConnectionRequestDto {

    @Email
    private String opponentEmail;

    private String nickname;

    private String coupleName;
}
