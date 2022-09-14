package com.example.demo.src.deal.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class PostDealReq {
    private int buyerIdx;
    private int productIdx;
    private Integer dealPrice;
}
