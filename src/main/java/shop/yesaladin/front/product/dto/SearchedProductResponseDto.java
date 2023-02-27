package shop.yesaladin.front.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

/**
 * 상품 검색 결과 ResponseDto
 *
 * @author 김선홍
 * @since 1.0
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SearchedProductResponseDto {
    private Long id;
    private String isbn;
    private String title;
    private Long quantity;
    private Long sellingPrice;
    private int rate;
    private Boolean isForcedOutOfStock;
    private String thumbnailFile;
    private String publisher;
    private LocalDate publishedDate;
    private Boolean isEbook;
    private Boolean isSubscriptionAvailable;
    private List<String> authors;
    private List<String> tags;

    public String[] getTagsArray() {
        return tags.get(0).split(",");
    }

    public String[] getAuthorsArray() {
        return authors.get(0).split(",");
    }
}
