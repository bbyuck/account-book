package com.bb.accountbook.domain.couple.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ConnectRequestDto {

    @NotBlank
    private String nickname;

    @Email
    private String opponentEmail;

    private String coupleName;

}
