package com.example.demo.src.oneShotQuery.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class GetMainPageRes {
    private int productIdx;
    private String title;
    private int price;
    private String roadNameAddress;
    private String productImageUrl;
    private int chattingRoomCount;
    private int likeCount;
    private String isUpdated;
    private String productTime;
}
