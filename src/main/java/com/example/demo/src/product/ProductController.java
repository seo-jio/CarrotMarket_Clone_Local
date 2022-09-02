package com.example.demo.src.product;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.product.model.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/app/products")
@RequiredArgsConstructor  //final keyword가 붙은 변수들을 인자로 받는 생성자를 자동으로 만들어준다.
public class ProductController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ProductProvider productProvider;

    private final ProductService productService;

    @ResponseBody
    @PostMapping("")
    public BaseResponse<PostProductRes> createProduct(@RequestBody PostProductReq postProductReq){
        try{
            PostProductRes postProductRes = productService.createProduct(postProductReq);
            return new BaseResponse<>(postProductRes);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @ResponseBody
    @PatchMapping("/change-status")
    public BaseResponse<PatchChangeStatusRes> deleteProduct(@RequestBody PatchChangeStatusReq patchChangeStatusReq){
        try{
            PatchChangeStatusRes patchChangeStatusRes = productService.updateStatus(patchChangeStatusReq);
            return new BaseResponse<>(patchChangeStatusRes);
        }catch(BaseException baseException){
            return new BaseResponse<>(baseException.getStatus());
        }
    }

    @ResponseBody
    @GetMapping("")
    public BaseResponse<List<GetProductRes>> getProducts(){
        try{
            List<GetProductRes> getProductRes = productProvider.getProducts();
            return new BaseResponse<>(getProductRes);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @ResponseBody
    @GetMapping("/by-seller")
    public BaseResponse<List<GetProductRes>> getProductsBySellerIdx(@RequestParam(required = false) Integer sellerIdx){
        try{
            if(sellerIdx == null){
                List<GetProductRes> getProductRes = productProvider.getProducts();
                return new BaseResponse<>(getProductRes);
            }
            List<GetProductRes> getProductRes = productProvider.getProductsBySellerIdx(sellerIdx); //auto unboxing -> .intValue() 불필요
            return new BaseResponse<>(getProductRes);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @ResponseBody
    @GetMapping("/by-location")
    public BaseResponse<List<GetProductRes>> getProductsByLocation(@RequestParam(required = false) Integer location){
        try{
            if(location == null){
                List<GetProductRes> getProductRes = productProvider.getProducts();
                return new BaseResponse<>(getProductRes);
            }
            List<GetProductRes> getProductRes = productProvider.getProductsByLocation(location); //auto unboxing -> .intValue() 불필요
            return new BaseResponse<>(getProductRes);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

}
