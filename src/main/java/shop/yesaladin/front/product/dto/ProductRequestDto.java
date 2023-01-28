package shop.yesaladin.front.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

/**
 * 상품 등록 요청을 보내기 위한 Dto 입니다.
 *
 * @author 이수정
 * @since 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestDto {

    @NotBlank
    @Length(max = 13)
    private String ISBN;

    // 상품 설명
    @NotBlank
    @Length(max = 255)
    private String title;
    @NotBlank
    private String contents;
    @NotBlank
    private String description;

    // 저자, 출판사
    private List<Long> authors;
    @Positive
    private long publisherId;

    // 정가, 할인율, 포인트 적립율
    @PositiveOrZero
    private long actualPrice;
    @PositiveOrZero
    @Max(100)
    private int discountRate;
    private Boolean isSeparatelyDiscount;
    @PositiveOrZero
    @Max(100)
    private int givenPointRate;
    private Boolean isGivenPoint;

    // 구독 상품 관련
    @Length(max = 9)
    private String ISSN;
    private Boolean isSubscriptionAvailable;

    // 판매 여부
    private Boolean isSale;

    // 수량, 출간일, 노출우선순위
    @PositiveOrZero
    private long quantity;
    @NotBlank
    private String publishedDate;
    @PositiveOrZero
    private int preferentialShowRanking;

    // 썸네일 파일
    @NotBlank
    private String thumbnailFileUrl;
    @NotBlank
    private String thumbnailFileUploadDateTime;

    // e-book 파일
    private String ebookFileUrl;
    private String ebookFileUploadDateTime;

    // codes
    @NotBlank
    private String productTypeCode;
    @NotBlank
    private String productSavingMethodCode;

    // 태그
    private List<Long> tags;

    // 카테고리
//    private List<String> categories;

    // 강제 품절 (수정용)
//    private Boolean isForcedOutOfStock;


    @Override
    public String toString() {
        return "ProductRequestDto{" +
                "ISBN='" + ISBN + '\'' +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", description='" + description + '\'' +
                ", authors=" + authors +
                ", publisherId=" + publisherId +
                ", actualPrice=" + actualPrice +
                ", discountRate=" + discountRate +
                ", isSeparatelyDiscount=" + isSeparatelyDiscount +
                ", givenPointRate=" + givenPointRate +
                ", isGivenPoint=" + isGivenPoint +
                ", ISSN='" + ISSN + '\'' +
                ", isSubscriptionAvailable=" + isSubscriptionAvailable +
                ", isSale=" + isSale +
                ", quantity=" + quantity +
                ", publishedDate='" + publishedDate + '\'' +
                ", preferentialShowRanking=" + preferentialShowRanking +
                ", thumbnailFileUrl='" + thumbnailFileUrl + '\'' +
                ", thumbnailFileUploadDateTime='" + thumbnailFileUploadDateTime + '\'' +
                ", ebookFileUrl='" + ebookFileUrl + '\'' +
                ", ebookFileUploadDateTime='" + ebookFileUploadDateTime + '\'' +
                ", productTypeCode='" + productTypeCode + '\'' +
                ", productSavingMethodCode='" + productSavingMethodCode + '\'' +
                ", tags=" + tags +
                '}';
    }
}
