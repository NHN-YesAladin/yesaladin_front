package shop.yesaladin.front.product.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 전체 조회 응답을 받기 위한 DTO 입니다.
 *
 * @author 이수정
 * @since 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductsResponseDto {
    private long id;

    private String title;
    private List<String> authors;
    private String publisher;
    private String publishedDate;
    private long sellingPrice;
    private int discountRate;

    private Boolean isOutOfStock;
    private Boolean isShown;
    private Boolean isDeleted;

    private String authorLine;
    private String thumbnailFileUrl;
    private List<String> tags;

    public void makeAuthorLine() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < this.authors.size(); i++) {
            stringBuilder.append(this.authors.get(i));
            if (i != this.authors.size() -1)
                stringBuilder.append(" | ");
        }
        this.authorLine = stringBuilder.toString();
    }
}
