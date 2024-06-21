package com.bb.accountbook.domain.couple.dto;

import com.bb.accountbook.common.model.status.UserCoupleStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CoupleConnectionInfoResponseDto {
    private Long userCoupleId;

    private UserCoupleStatus opponentUserCoupleStatus;
    private String opponentEmail;
    private String opponentNickname;
    private String coupleName;
}
