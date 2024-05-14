package com.bb.accountbook.domain.group.dto;

import com.bb.accountbook.common.model.codes.MemberCode;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ConnectRequestDto {

    @NotBlank
    private String nickname;

    @NotBlank
    private MemberCode memberCode;

    @Email
    private String opponentEmail;

}
