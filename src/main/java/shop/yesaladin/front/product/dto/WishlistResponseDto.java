package shop.yesaladin.front.product.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class WishlistResponseDto {
    private long id;
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

    String registerDate;

    public static WishlistResponseDto getWishlistResponseDto(ProductDetailResponseDto dto) {
        return WishlistResponseDto.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .actualPrice(dto.getActualPrice())
                .isbn(dto.getIsbn())
                .authors(dto.getAuthors())
                .discountRate(dto.getDiscountRate())
                .pointPrice(dto.getPointPrice())
                .sellingPrice(dto.getSellingPrice())
                .publisher(dto.getPublisher())
                .thumbnailFileUrl(dto.getThumbnailFileUrl())
                .isSubscriptionAvailable(dto.isSubscriptionAvailable())
                .registerDate(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .build();
    }
}
