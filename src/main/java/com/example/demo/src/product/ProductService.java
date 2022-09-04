package com.example.demo.src.product;

import com.example.demo.config.BaseException;
import com.example.demo.src.product.model.PatchChangeStatusReq;
import com.example.demo.src.product.model.PatchChangeStatusRes;
import com.example.demo.src.product.model.PostProductReq;
import com.example.demo.src.product.model.PostProductRes;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
@RequiredArgsConstructor
public class ProductService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ProductDao productDao;

    public PostProductRes createProduct(PostProductReq postProductReq) throws BaseException{
        try{
            int productIdx = productDao.createProduct(postProductReq);
            return new PostProductRes(productIdx);
        }catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public PatchChangeStatusRes updateStatusN(PatchChangeStatusReq patchChangeStatusReq) throws BaseException{
        try{
            int productIdx = productDao.updateStatusN(patchChangeStatusReq);
            return new PatchChangeStatusRes(productIdx);
        }catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public PatchChangeStatusRes updateStatusS(PatchChangeStatusReq patchChangeStatusReq) throws BaseException{
        try{
            int productIdx = productDao.updateStatusS(patchChangeStatusReq);
            return new PatchChangeStatusRes(productIdx);
        }catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
