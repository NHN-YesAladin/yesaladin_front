package shop.yesaladin.front.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.util.List;

/**
 * 상품 등록 요청을 보내기 위한 Dto 입니다.
 *
 * @author 이수정
 * @since 1.0
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestDto {

    @NotBlank
    @Length(min = 13, max = 13)
    private String isbn;

    // 상품 설명
    @NotBlank
    @Length(min = 1, max = 255)
    private String title;
    @NotBlank
    @Length(min = 1, max = 21844)
    private String contents;
    @NotBlank
    @Length(min = 1, max = 21844)
    private String description;

    // 저자, 출판사
    private List<Long> authors;
    @Positive
    private long publisherId;

    // 정가, 할인율, 포인트 적립율
    @PositiveOrZero
    @Max(2000000)
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
    @Length(max = 8)
    private String issn;
    private Boolean isSubscriptionAvailable;

    // 판매 여부
    private Boolean isSale;

    // 수량, 출간일, 노출우선순위
    @PositiveOrZero
    @Max(2000000)
    private long quantity;
    @NotBlank
    private String publishedDate;

    @Min(-100)
    @Max(100)
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
    private List<Long> categories;


    @Override
    public String toString() {
        return "ProductRequestDto{" +
                "isbn='" + isbn + '\'' +
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
                ", issn='" + issn + '\'' +
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
