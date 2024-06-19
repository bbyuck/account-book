package com.bb.accountbook.domain.couple.dto;

import com.bb.accountbook.common.model.status.CoupleStatus;
import com.bb.accountbook.common.model.status.UserCoupleStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CoupleStatusFindResponseDto {

    private CoupleStatus coupleStatus;
    private UserCoupleStatus userCoupleStatus;
}
