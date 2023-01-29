package shop.yesaladin.front.product.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SearchedProductDto {
    private Long id;
    private String title;
    private Long quantity;
    private int discountRate;
    private long sellingPrice;
    private Boolean isForcedOutOfStack;
    private String thumbnailFileUrl;
    private String publisher;
    private String publishedDate;
    private List<SearchedProductCategory> categories;
    private List<String> authors;
    private List<String> tags;
    public String getAuthorLine() {
        StringBuilder sb = new StringBuilder();
        for(String author: authors)
            sb.append(author).append(" ");
        return sb.toString();
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SearchedProductCategory {
        private Long id;
        private Long parent;
        private String name;
        private Boolean isShown;
        private Boolean disable;
    }
}
