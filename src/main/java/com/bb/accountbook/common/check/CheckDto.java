package com.bb.accountbook.common.check;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CheckDto {
    private String url;
    private String accessToken;
    private String refreshToken;
}
