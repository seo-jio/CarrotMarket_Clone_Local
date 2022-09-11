package com.example.demo.src.deal;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.deal.model.PostDealReq;
import com.example.demo.src.deal.model.PostDealRes;
import com.example.demo.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/app/deals")
@RequiredArgsConstructor
public class DealController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final DealProvider dealProvider;
    private final DealService dealService;
    private final JwtService jwtService;

    @ResponseBody
    @PostMapping("")
    public BaseResponse<PostDealRes> createDeal(@RequestBody PostDealReq postDealReq){
        try{
            int userIdxFindByJwt = jwtService.getUserIdx();
            if (postDealReq.getBuyerIdx() != userIdxFindByJwt){
                return new BaseResponse<>(BaseResponseStatus.INVALID_USER_JWT);
            }
            PostDealRes postDealRes = dealService.createDeal(postDealReq);
            return new BaseResponse<>(postDealRes);
        }catch(BaseException baseException){
            return new BaseResponse<>(baseException.getStatus());
        }
    }
}
