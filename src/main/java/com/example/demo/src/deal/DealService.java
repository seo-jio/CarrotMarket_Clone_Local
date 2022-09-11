package com.example.demo.src.deal;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.deal.model.PostDealReq;
import com.example.demo.src.deal.model.PostDealRes;
import com.example.demo.src.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
public class DealService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final DealDao dealDao;
    private final ProductService productService;

    public PostDealRes createDeal(PostDealReq postDealReq) throws BaseException{
        try{
            int dealIdx = dealDao.createDeal(postDealReq);
            return new PostDealRes(dealIdx);
        }catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
