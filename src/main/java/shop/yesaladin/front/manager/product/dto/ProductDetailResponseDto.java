package shop.yesaladin.front.manager.product.dto;


import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * shop에 상품 싱세 정보를 요청하기 위한 DTO 입니다.
 *
 * @author 이수정
 * @since 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetailResponseDto {

    private long id;

    private String ebookFileUrl;
    private String title;
    private List<String> authors;
    private String publisher;

    private String thumbnailFileUrl;

    private long actualPrice;
    private long sellingPrice;
    private int discountRate;
    private long pointPrice;
    private int pointRate;

    private String publishedDate;
    private String isbn;
    private boolean isSubscriptionAvailable;
    private String issn;

    private String contents;

    private String description;

    @Override
    public String toString() {
        return "ProductDetailResponseDto{" +
                "id=" + id +
                ", ebookFileUrl='" + ebookFileUrl + '\'' +
                ", title='" + title + '\'' +
                ", authors=" + authors +
                ", publisher='" + publisher + '\'' +
                ", thumbnailFileUrl='" + thumbnailFileUrl + '\'' +
                ", actualPrice=" + actualPrice +
                ", sellingPrice=" + sellingPrice +
                ", pointPrice=" + pointPrice +
                ", pointRate=" + pointRate +
                ", publishedDate='" + publishedDate + '\'' +
                ", isbn='" + isbn + '\'' +
                ", isSubscriptionAvailable=" + isSubscriptionAvailable +
                ", issn='" + issn + '\'' +
                ", contents='" + contents + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
