package com.bxk.campusbazaar.api.controller.common;

import com.bxk.campusbazaar.api.service.UserService;
import com.bxk.campusbazaar.pojo.DTO.ProductCommentDTO;
import com.bxk.campusbazaar.pojo.DTO.ProductDTO;
import com.bxk.campusbazaar.pojo.Product;
import com.bxk.campusbazaar.api.service.ProductService;
import com.bxk.campusbazaar.pojo.DTO.SearchDTO;
import com.bxk.campusbazaar.pojo.ProductComment;
import com.bxk.campusbazaar.pojo.ProductImage;
import com.bxk.campusbazaar.tools.Response;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Log4j2
@Component
@RestController
@RequestMapping("/api/product")
@CrossOrigin
public class ProductController {


    private final ProductService productService;
    private final UserService userService;


    @Autowired
    ProductController(ProductService productService,UserService userService) {
        this.productService = productService;
        this.userService = userService;
    }


    /**
     * 测试方法：上架所有商品
     * @return Response<Object>
     */
    @PatchMapping("updateAllProductStatus")
    public Response<Object> updateProductStatus(){
        try {
            productService.updateProductStatus();
        }catch (Exception e){
            return Response.fail();
        }
        return Response.success();
    }

    @GetMapping("/getAllProducts")
    public Response<Object> getAllProducts(){

        List<Product> products = productService.getAllProducts();

        return Response.success(products);
    }

    /**
     * 获取所有上架商品（带图片）
     * @return Response<Object>
     */
    @GetMapping("/getAllProductsWithImg")
    public Response<Object> getAllProductsWithImg() {

        List<Product> products = productService.getProductByLike(null, (byte) 1);

        if (products == null || products.isEmpty()) {
            return Response.success(Collections.emptyList());
        }

        List<ProductDTO> productDtos = productToListDtos(products);

        return Response.success(productDtos);

    }

    /**
     * 将商品列表转换为带图片的商品DTO列表
     * @param products 商品列表
     */
    private List<ProductDTO> productToListDtos(List<Product> products) {

        List<ProductDTO> productDtos = new ArrayList<>();

        for (Product product : products) {
            productDtos.add(productToDtos(product));
        }

        return productDtos;
    }

    /**
     * 将商品转换为带图片的商品DTO
     * @param product 商品列表
     */
    private ProductDTO productToDtos(Product product) {

        ProductDTO productDto = new ProductDTO();

        List<ProductImage> productImages = productService.getProductImgs(product.getId());

        try{
            // 将图片转成byte[]
            for (ProductImage img : productImages) {
                Path imgPath = Paths.get(img.getImageUrl());

                if (!Files.exists(imgPath)) {
                    throw new IOException("图片文件未找到");
                }

                byte[] imageBytes;

                try {
                    imageBytes = Files.readAllBytes(imgPath);
                    img.setImageData(imageBytes);
                } catch (IOException e) {
                    throw new IOException("读取图片文件失败:\n", e);
                }
            }
        } catch (IOException e) {
            log.error(e);
        }

        productDto.product = product;
        productDto.productImages = productImages;



        return productDto;
    }

    @GetMapping("/userSearch")
    public Response<Object> userSearch(@RequestParam(value="keyword", required = false) String keyword,
                                       @RequestParam(value = "sort", required = false) String sort) {
        // 获取基础数据
        List<Product> products = productService.getProductByLike(keyword, (byte)1);

        // 空检查
        if (products == null || products.isEmpty()) {
            return Response.success(Collections.emptyList());
        }

        // 排序处理
        if (sort != null) {
            switch (sort) {
                case "newest":
                    products.sort(Comparator.comparing(Product::getId));
                    break;
                case "sales":
                    products.sort((p1, p2) -> p2.getNob() - p1.getNob());
                    break;
                case "price_asc":
                    products.sort(Comparator.comparingDouble(p ->
                            p.getDiscountPrice() != null ?
                                    p.getDiscountPrice().doubleValue() :
                                    p.getOriginalPrice().doubleValue()));
                    break;
                case "price_desc":
                    products.sort((p1, p2) -> {
                        double price1 = p1.getDiscountPrice() != null ?
                                p1.getDiscountPrice().doubleValue() :
                                p1.getOriginalPrice().doubleValue();
                        double price2 = p2.getDiscountPrice() != null ?
                                p2.getDiscountPrice().doubleValue() :
                                p2.getOriginalPrice().doubleValue();
                        return Double.compare(price2, price1);
                    });
                    break;

            }
        }

        List<ProductDTO> productDtos = productToListDtos(products);
        return Response.success(productDtos);
    }

    /**
     * 根据id获取商品
     */
    @GetMapping("/getProductById")
    public Response<Object> getProductById(@RequestParam("id") int id){
        ProductDTO productDto;

        Product product = productService.getProductById(id);
        productDto = productToDtos(product);


        return Response.success(productDto);
    }

    /**
     * 获取商品评论
     */
    @GetMapping("/getCommentsByProductId")
    public Response<Object> getCommentsByProductId(@RequestParam("productId") int productId){

        List<ProductCommentDTO> productComments = productCommentsToDtos(productService.getCommentsByProductId((long) productId));


        return Response.success(productComments);
    }

    /**
     * ProductComments 转换为 ProductCommentDtos
     */
    private List<ProductCommentDTO> productCommentsToDtos(List<ProductComment> productComments) {
        List<ProductCommentDTO> productCommentDtos = new ArrayList<>();

        for (ProductComment productComment : productComments) {
            ProductCommentDTO dto = productCommentToDto(productComment);
            productCommentDtos.add(dto);
        }

        return productCommentDtos;
    }

    /**
     * ProductComment 转换为 ProductCommentDto
     */
    private ProductCommentDTO productCommentToDto(ProductComment productComment) {
        ProductCommentDTO dto = new ProductCommentDTO();

        dto.setId(productComment.getId());
        dto.setProductId(productComment.getProductId());
        dto.setUserId(productComment.getUserId());
        dto.setStarRating(productComment.getStarRating());
        dto.setCreatedAt(productComment.getCreatedAt());
        dto.setContent(productComment.getContent());

        dto.setUserName(userService.getUserNameById(productComment.getUserId()));

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dto.setCreatedAtStr(sdf.format(productComment.getCreatedAt()));


        return dto;
    }

    /**
     * 添加商品评论
     * @param productCommentDTO 商品评论
     * @return Response<Object>
     */
    @PostMapping("/addComment")
    public Response<Object> addComment(@RequestBody ProductCommentDTO productCommentDTO){

        ProductComment productComment = new ProductComment();

        productComment.setId(productCommentDTO.getId());
        productComment.setProductId(productCommentDTO.getProductId());
        productComment.setUserId(productCommentDTO.getUserId());
        if (productCommentDTO.getStarRating() == null) {
            productComment.setStarRating((byte) 3);
        } else {
            productComment.setStarRating(productCommentDTO.getStarRating());
        }
        productComment.setContent(productCommentDTO.getContent());
        productComment.setCreatedAt(productCommentDTO.getCreatedAt());

        try {
            productService.addComment(productComment);
        }
        catch (Exception e){
            return Response.fail(e.getCause().getMessage());
        }

        return Response.success();
    }

    /**
     * 后台查询商品
     * @param searchData 搜索关键字和状态
     * @return 商品列表
     */
    @PostMapping("/searchProducts")
    public List<Product> searchProducts(@RequestBody SearchDTO searchData){
        List<Product> products;

        String name = null;
        Byte status = null;

        if (searchData.keyword!=null){
            name = searchData.keyword.trim();
        }

        if (searchData.status!=null){
            status = searchData.status;
        }

        products = productService.getProductByLike(name, status);

        return products;
    }


    /**
     * 获取商品图片
     * @param id 商品id
     * @return 商品图片二进制的数组
     */
    @GetMapping("/getProductImages")
    public Response<Object> getProductImages(@RequestParam int id){
        ArrayList<byte[]> images = new ArrayList<>();

        List<ProductImage> productImages = productService.getProductImgs((long) id);
        for (ProductImage img : productImages) {
            Path imgPath = Path.of(img.getImageUrl());

            if (!imgPath.toFile().exists()) {
                return Response.fail("图片文件未找到");
            }

            // 读取图片转换成byte[]
            byte[] imageBytes;

            try {
                imageBytes = Files.readAllBytes(imgPath);
                images.add(imageBytes);
            } catch (IOException e) {
                log.error("读取许可证文件失败", e);
                return Response.fail("读取许可证文件失败");
            }

        }

        return Response.success(images);
    }

    /**
     * 购买人数++
     */
    @PatchMapping("/updateProductNob")
    public Response<Object> updateProductNob(@RequestParam int id){
        try {
            productService.updateProductNob(id);
        }catch (Exception e){
            return Response.fail();
        }
        return Response.success();
    }

    /**
     * 更新商品状态
     * @param id 商品id
     * @param status 商品状态
     * @return Response<Object>
     */
    @PatchMapping("updateProductStatus")
    public Response<Object> updateProductStatus(@RequestParam int id, @RequestParam int status){
        if (!Arrays.asList(0,1,2).contains(status)){
            return Response.fail("商品状态只能为0,1或2");
        }
        try {
            productService.updateProductStatus(id,status);
        }catch (Exception e){
            return Response.fail();
        }
        return Response.success();
    }


    /**
     * 添加商品
     * 商品状态和购买人数数据库默认0，不覆盖赋值
     */
    @PostMapping("/addProduct")
    public Response<Object> addProduct(@RequestBody Product product){

        log.info(product.toString());

        try {
            productService.addProduct(product);
        }catch (Exception e){
            return Response.fail(e.getCause().getMessage());
        }
        return Response.success("添加商品成功");
    }
}
