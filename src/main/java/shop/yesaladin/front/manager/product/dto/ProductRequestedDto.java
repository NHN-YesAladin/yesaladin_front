package shop.yesaladin.front.manager.product.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import shop.yesaladin.front.manager.file.dto.FileUploadResponseDto;

/**
 * 사용자가 요청한 상품 등록 정보를 받아오기 위한 DTO 입니다.
 *
 * @author 이수정
 * @since 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestedDto {

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

//    private List<String> categories;


    @Override
    public String toString() {
        return "ProductRequestedDto{" +
                "isbn='" + isbn + '\'' +
                ", thumbnailFile=" + thumbnailFile.getOriginalFilename() +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", description='" + description + '\'' +
                ", ebookFile=" + ebookFile.getOriginalFilename() +
                ", authors=" + authors +
                ", publisher=" + publisher +
                ", publishedDate='" + publishedDate + '\'' +
                ", productTypeCode='" + productTypeCode + '\'' +
                ", tags=" + tags +
                ", actualPrice=" + actualPrice +
                ", isSeparatelyDiscount=" + isSeparatelyDiscount +
                ", discountRate=" + discountRate +
                ", isGivenPoint=" + isGivenPoint +
                ", givenPointRate=" + givenPointRate +
                ", productSavingMethodCode='" + productSavingMethodCode + '\'' +
                ", isSubscriptionAvailable=" + isSubscriptionAvailable +
                ", issn='" + issn + '\'' +
                ", quantity=" + quantity +
                ", preferentialShowRanking=" + preferentialShowRanking +
                ", isSale=" + isSale +
                '}';
    }

    public ProductCreateRequestDto getProductCreateRequestDto(
            FileUploadResponseDto thumbnailFileResponse,
            FileUploadResponseDto ebookFileResponse
    ) {
        return new ProductCreateRequestDto(
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
                tags
        );
    }

    private boolean changeStringToBoolean(String target) {
        return target != null ? true : false;
    }
}
