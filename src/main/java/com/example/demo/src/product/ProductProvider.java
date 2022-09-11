package com.example.demo.src.product;

import com.example.demo.config.BaseException;
import com.example.demo.src.product.model.GetProductRes;
import com.example.demo.src.user.model.GetUserRes;
import com.fasterxml.jackson.databind.ser.Serializers;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
@RequiredArgsConstructor
public class ProductProvider {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ProductDao productDao;

    public List<GetProductRes> getProducts() throws BaseException{
        try {
            List<GetProductRes> getProductRes = productDao.getProducts();
            return getProductRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetProductRes> getProductsBySellerIdx(int sellerIdx) throws BaseException{
        try{
            List<GetProductRes> getProductRes = productDao.getProductsBySellerIdx(sellerIdx);
            return getProductRes;
        } catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetProductRes> getProductsByLocation(int location) throws BaseException {
        try{
            List<GetProductRes> getProductRes = productDao.getProductsByLocation(location);
            return getProductRes;
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetProductRes> getProductsByCategoryIdx(Integer categoryIdx) throws BaseException {
        try{
            List<GetProductRes> getProductRes = productDao.getProductsByCategoryIdx(categoryIdx);
            return getProductRes;
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetProductRes> getProductsByUserIdx(int userIdx) throws BaseException{
        try{
            List<GetProductRes> getProductRes = productDao.getProductsByUserIdx(userIdx);
            return getProductRes;
        }catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
