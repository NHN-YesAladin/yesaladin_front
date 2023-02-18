package shop.yesaladin.front.common.dto;

import lombok.*;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class PaginatedResponseDto<T> {

    private long totalPage;
    private long currentPage;
    private long totalDataCount;
    private List<T> dataList;

}
