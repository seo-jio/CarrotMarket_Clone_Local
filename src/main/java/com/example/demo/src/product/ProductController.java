package com.example.demo.src.product;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.product.model.*;
import com.example.demo.utils.JwtService;
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

    private final JwtService jwtService;

    @ResponseBody
    @PostMapping("")
    public BaseResponse<PostProductRes> createProduct(@RequestBody PostProductReq postProductReq){
        try{
            int userIdxFindByJwt = jwtService.getUserIdx();
            if (postProductReq.getSellerIdx() != userIdxFindByJwt){
                return new BaseResponse<>(BaseResponseStatus.INVALID_USER_JWT);
            }
            PostProductRes postProductRes = productService.createProduct(postProductReq);
            return new BaseResponse<>(postProductRes);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @ResponseBody
    @PatchMapping("/delete-product")
    public BaseResponse<PatchChangeStatusRes> deleteProduct(@RequestBody PatchChangeStatusReq patchChangeStatusReq){
        try{
            int userIdxFindByJwt = jwtService.getUserIdx();
            if (patchChangeStatusReq.getUserIdx() != userIdxFindByJwt){
                return new BaseResponse<>(BaseResponseStatus.INVALID_USER_JWT);
            }
            PatchChangeStatusRes patchChangeStatusRes = productService.updateStatusN(patchChangeStatusReq);
            return new BaseResponse<>(patchChangeStatusRes);
        }catch(BaseException baseException){
            return new BaseResponse<>(baseException.getStatus());
        }
    }

    @ResponseBody
    @PatchMapping("/sold-product")
    public BaseResponse<PatchChangeStatusRes> soldProduct(@RequestBody PatchChangeStatusReq patchChangeStatusReq){
        try{
            PatchChangeStatusRes patchChangeStatusRes = productService.updateStatusS(patchChangeStatusReq);
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
    @GetMapping("/by-userIdx/{userIdx}")
    public BaseResponse<List<GetProductRes>> getProductsByUserIdx(@PathVariable("userIdx") int userIdx){
        try{
            int userIdxFindByJwt = jwtService.getUserIdx();
            if (userIdx != userIdxFindByJwt){
                return new BaseResponse<>(BaseResponseStatus.INVALID_USER_JWT);
            }
            List<GetProductRes> getProductRes = productProvider.getProductsByUserIdx(userIdx);
            return new BaseResponse<>(getProductRes);
        }catch(BaseException baseException){
            return new BaseResponse<>(baseException.getStatus());
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

    @ResponseBody
    @GetMapping("/by-categoryIdx")
    public BaseResponse<List<GetProductRes>> getProductsByCategory(@RequestParam(required = false) Integer categoryIdx){
        try{
            if(categoryIdx == null){
                List<GetProductRes> getProductRes = productProvider.getProducts();
                return new BaseResponse<>(getProductRes);
            }
            List<GetProductRes> getProductRes = productProvider.getProductsByCategoryIdx(categoryIdx); //auto unboxing -> .intValue() 불필요
            return new BaseResponse<>(getProductRes);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

}
