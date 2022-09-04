package com.example.demo.src.user.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GetBuyProduct {
    private int buyerIdx;
    private String buyerNickname;
    private int sellerIdx;
    private String sellerNickname;
    private int dealIdx;
    private int dealPrice;
    private int productIdx;
    private String title;
}
