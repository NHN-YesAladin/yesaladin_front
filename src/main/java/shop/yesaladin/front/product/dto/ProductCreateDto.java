package shop.yesaladin.front.product.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

/**
 * 상품 등록을 위한 DTO 입니다.
 *
 * @author 이수정
 * @since 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductCreateDto {

    private String isbn;

    // 상품 설명
    private String thumbnailFile;
    private String title;
    private String contents;
    private String description;

    // e-book 파일
    private String ebookFile;

    // 저자, 출판사
    private List<Long> authors;
    private long publisherId;

    // 정가, 할인율, 포인트 적립율
    private long actualPrice;
    private int discountRate;
    private Boolean isSeparatelyDiscount;
    private int givenPointRate;
    private Boolean isGivenPoint;

//    // 구독 상품 관련
//    @Length(max = 9)
//    private String ISSN;
//    private Boolean isSubscriptionAvailable;
//
//    // 판매 여부, 강제품절 여부
//    private Boolean isSale;
////    private Boolean isForcedOutOfStock;
//
//    // 수량, 출간일, 노출우선순위
//    @PositiveOrZero
//    private long quantity;
//    @NotBlank
//    private String publishedDate;
//    @PositiveOrZero
//    private int preferentialShowRanking;
//
//    @NotBlank
//    private String thumbnailFileUploadDateTime;
//
//    // 썸네일 파일
//    @NotBlank
//    private String ebookFileUploadDateTime;
//
//    // codes
//    @NotBlank
//    private String productTypeCode;
//    @NotBlank
//    private String productSavingMethodCode;
//
//    // 태그
//    private List<Long> tags;

    // 카테고리
//    private List<String> categories;

}
