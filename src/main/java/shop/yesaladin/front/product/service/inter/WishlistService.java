package shop.yesaladin.front.product.service.inter;

import java.util.List;
import shop.yesaladin.front.product.dto.WishlistResponseDto;

public interface WishlistService {

    List<WishlistResponseDto> findWishlistByLoginId(String loginId);

    void deleteWishlist(Long id);
}
