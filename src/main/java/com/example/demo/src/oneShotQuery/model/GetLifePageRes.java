package com.example.demo.src.oneShotQuery.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class GetLifePageRes {
    private int postIdx;
    private int writerIdx;
    private String nickname;
    private String roadNameAddress;
    private String title;
    private String content;
    private String postTypeName;
    private String postImageUrl;
    private int commentCount;
    private int sympathyCount;
    private int curiousCount;
    private String postTime;
}
