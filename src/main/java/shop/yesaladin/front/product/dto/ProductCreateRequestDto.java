package shop.yesaladin.front.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import shop.yesaladin.front.file.dto.FileUploadResponseDto;

import java.util.List;

/**
 * 사용자가 요청한 상품 등록 정보를 받아오기 위한 Dto 입니다.
 *
 * @author 이수정
 * @since 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductCreateRequestDto {

    // 상품 ISBN
    private String isbn;

    // 상품 썸네일 파일
    private MultipartFile thumbnailFile;

    // 상품 기본 설명 부분
    private String title;
    private String contents;
    private String description;

    // e-book 파일
    private MultipartFile ebookFile;

    // 저자
    private List<Long> authors;

    // 출판사
    private long publisher;
    private String publishedDate;

    // 상품 유형
    private String productTypeCode;

    // 상품 태그
    private List<Long> tags;

    // 정가
    private long actualPrice;

    // 개별 할인
    private String isSeparatelyDiscount;
    private int discountRate;

    // 포인트 적립
    private String isGivenPoint;
    private int givenPointRate;
    private String productSavingMethodCode;

    // 구독
    private String isSubscriptionAvailable;
    private String issn;

    // 수량
    private long quantity;

    // 노출 우선 순위
    private int preferentialShowRanking;

    // 판매 여부
    private String isSale;

    // 카테고리
    private List<Long> categories;

    @Override
    public String toString() {
        return "ProductCreateRequestDto{" +
                "isbn='" + isbn + '\'' +
                ", thumbnailFile=" + thumbnailFile +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", description='" + description + '\'' +
                ", ebookFile=" + ebookFile +
                ", authors=" + authors +
                ", publisher=" + publisher +
                ", publishedDate='" + publishedDate + '\'' +
                ", productTypeCode='" + productTypeCode + '\'' +
                ", tags=" + tags +
                ", actualPrice=" + actualPrice +
                ", isSeparatelyDiscount='" + isSeparatelyDiscount + '\'' +
                ", discountRate=" + discountRate +
                ", isGivenPoint='" + isGivenPoint + '\'' +
                ", givenPointRate=" + givenPointRate +
                ", productSavingMethodCode='" + productSavingMethodCode + '\'' +
                ", isSubscriptionAvailable='" + isSubscriptionAvailable + '\'' +
                ", issn='" + issn + '\'' +
                ", quantity=" + quantity +
                ", preferentialShowRanking=" + preferentialShowRanking +
                ", isSale='" + isSale + '\'' +
                ", categories=" + categories +
                '}';
    }

    public ProductRequestDto getProductCreateRequestDto(
            FileUploadResponseDto thumbnailFileResponse,
            FileUploadResponseDto ebookFileResponse
    ) {
        return new ProductRequestDto(
                isbn,
                title,
                contents,
                description,
                authors,
                publisher,
                actualPrice,
                discountRate,
                changeStringToBoolean(isSeparatelyDiscount),
                givenPointRate,
                changeStringToBoolean(isGivenPoint),
                issn,
                changeStringToBoolean(isSubscriptionAvailable),
                changeStringToBoolean(isSale),
                quantity,
                publishedDate,
                preferentialShowRanking,
                thumbnailFileResponse.getUrl(),
                thumbnailFileResponse.getFileUploadDateTime(),
                ebookFileResponse.getUrl(),
                ebookFileResponse.getFileUploadDateTime(),
                productTypeCode,
                productSavingMethodCode,
                tags,
                categories
        );
    }

    private boolean changeStringToBoolean(String target) {
        return target != null;
    }
}
