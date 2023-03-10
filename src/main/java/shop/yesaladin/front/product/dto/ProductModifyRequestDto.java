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
public class ProductModifyRequestDto {

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

    // 카테고리
    private List<Long> categories;

    @Override
    public String toString() {
        return "ProductModifyRequestDto{" +
                "thumbnailFile=" + thumbnailFile +
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
                ", categories=" + categories +
                '}';
    }

    public ProductUpdateDto getProductUpdateDto(
            FileUploadResponseDto thumbnailFileResponse,
            FileUploadResponseDto ebookFileResponse
    ) {
        return ProductUpdateDto.builder()
                .title(title)
                .contents(contents)
                .description(description)
                .authors(authors)
                .publisherId(publisher)
                .actualPrice(actualPrice)
                .discountRate(discountRate)
                .isSeparatelyDiscount(changeStringToBoolean(isSeparatelyDiscount))
                .givenPointRate(givenPointRate)
                .isGivenPoint(changeStringToBoolean(isGivenPoint))
                .issn(issn)
                .isSubscriptionAvailable(changeStringToBoolean(isSubscriptionAvailable))
                .quantity(quantity)
                .publishedDate(publishedDate)
                .preferentialShowRanking(preferentialShowRanking)
                .thumbnailFileUrl(thumbnailFileResponse.getUrl())
                .thumbnailFileUploadDateTime(thumbnailFileResponse.getFileUploadDateTime())
                .ebookFileUrl(ebookFileResponse.getUrl())
                .ebookFileUploadDateTime(ebookFileResponse.getFileUploadDateTime())
                .productTypeCode(productTypeCode)
                .productSavingMethodCode(productSavingMethodCode)
                .tags(tags)
                .categories(categories)
                .build();
    }

    private boolean changeStringToBoolean(String target) {
        return target != null;
    }
}
