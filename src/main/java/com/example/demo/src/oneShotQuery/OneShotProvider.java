package com.example.demo.src.oneShotQuery;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.oneShotQuery.model.GetLifePageRes;
import com.example.demo.src.oneShotQuery.model.GetMainPageRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
@RequiredArgsConstructor
public class OneShotProvider {
    private final OneShotDao oneShotDao;


    public List<GetMainPageRes> getMainPage(int userIdx) throws BaseException {
        try{
            List<GetMainPageRes> getMainPageRes = oneShotDao.getMainPage(userIdx);
            return getMainPageRes;
        }catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetLifePageRes> getLifePage(int userIdx) throws BaseException{
        try{
            List<GetLifePageRes> getLifePageRes = oneShotDao.getLifePage(userIdx);
            return getLifePageRes;
        }catch(Exception exception){
            System.out.println(exception.getMessage());
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
