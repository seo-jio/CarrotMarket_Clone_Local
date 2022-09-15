package com.example.demo.src.user;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.user.model.PostUserOAuthRes;
import com.example.demo.src.user.model.PostUserRes;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/oauth")
public class OAuthController {

    @Autowired
    private OAuthService oAuthService;

    /**
     * 카카오 callback
     * [GET] /oauth/kakao/callback
     */
    @ResponseBody
    @GetMapping("/kakao")
    public BaseResponse<PostUserOAuthRes> kakaoCallback(@RequestParam String code) {
        try{
            System.out.println("code = " + code);
            String accessToken = oAuthService.getKakaoAccessToken(code); //코드로 부터 access token 추출
            System.out.println("accessToken = " + accessToken);
            PostUserOAuthRes postUserOAuthRes = oAuthService.createKakaoUserReal(accessToken);  //access token에서 사용자 정보 추출

//            oAuthService.createKakaoUser(accessToken);  //access token에서 사용자 정보 추출
//            PostUserOAuthRes postUserOAuthRes = new PostUserOAuthRes(1, "hello");

            return new BaseResponse<>(postUserOAuthRes);
        }catch(BaseException baseException){
            return new BaseResponse<>(baseException.getStatus());
        }
    }
}
