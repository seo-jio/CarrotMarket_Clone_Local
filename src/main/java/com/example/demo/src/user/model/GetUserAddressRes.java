package com.example.demo.src.user.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GetUserAddressRes {
    private int addressIdx;
    private String roadNameAddress;
    private String isAuth;
    private String isMain;
    private String isNotified;
}
