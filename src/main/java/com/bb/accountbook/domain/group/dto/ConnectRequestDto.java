package com.bb.accountbook.domain.group.dto;

import com.bb.accountbook.common.model.codes.MemberCode;
import jakarta.annotation.Nonnull;
import lombok.Data;

@Data
public class ConnectRequestDto {

    @Nonnull
    private String nickname;

    @Nonnull
    private MemberCode memberCode;

    @Nonnull
    private String opponentEmail;

}
