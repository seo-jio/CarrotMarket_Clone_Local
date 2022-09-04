package com.example.demo.src.oneShotQuery;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.oneShotQuery.model.GetLifePageRes;
import com.example.demo.src.oneShotQuery.model.GetMainPageRes;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/app/one-shot")
@RequiredArgsConstructor
public class OneShotController {

    final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final OneShotProvider oneShotProvider;

    @ResponseBody
    @GetMapping("/main-page/{userIdx}")
    public BaseResponse<List<GetMainPageRes>> mainPage(@PathVariable int userIdx){
        try{
            List<GetMainPageRes> getMainPageRes = oneShotProvider.getMainPage(userIdx);
            return new BaseResponse<>(getMainPageRes);
        }catch(BaseException baseException){
            return new BaseResponse<>(baseException.getStatus());
        }
    }

    @ResponseBody
    @GetMapping("/life-page/{userIdx}")
    public BaseResponse<List<GetLifePageRes>> lifePage(@PathVariable int userIdx){
        try{
            List<GetLifePageRes> getLifePageRes = oneShotProvider.getLifePage(userIdx);
            return new BaseResponse<>(getLifePageRes);
        }catch(BaseException baseException){
            return new BaseResponse<>(baseException.getStatus());
        }
    }
}
