package shop.yesaladin.front.product.dto;

import lombok.*;
import shop.yesaladin.front.category.dto.CategoryResponseDto;
import shop.yesaladin.front.publish.dto.PublisherResponseDto;
import shop.yesaladin.front.tag.dto.TagResponseDto;
import shop.yesaladin.front.writing.dto.AuthorsResponseDto;

import java.util.List;

/**
 * 상품 수정 View에 들어갈 정보를 받아오기 위한 Dto 입니다.
 *
 * @author 이수정
 * @since 1.0
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductModifyDto {

    // 상품 ISBN
    private String isbn;

    // 상품 썸네일 파일
    private String thumbnailFile;

    // 상품 기본 설명 부분
    private String title;
    private String contents;
    private String description;

    // e-book 파일 url
    private String ebookFileUrl;

    // 저자
    private List<AuthorsResponseDto> authors;

    // 출판사
    private PublisherResponseDto publisher;
    private String publishedDate;

    // 상품 유형
    private String productTypeCode;

    // 상품 태그
    private List<TagResponseDto> tags;

    // 정가
    private long actualPrice;

    // 개별 할인
    private Boolean isSeparatelyDiscount;
    private int discountRate;

    // 포인트 적립
    private Boolean isGivenPoint;
    private int givenPointRate;
    private String productSavingMethodCode;

    // 구독
    private Boolean isSubscriptionAvailable;
    private String issn;

    // 수량
    private long quantity;

    // 노출 우선 순위
    private int preferentialShowRanking;

    // 카테고리
    private List<CategoryResponseDto> categories;
}
