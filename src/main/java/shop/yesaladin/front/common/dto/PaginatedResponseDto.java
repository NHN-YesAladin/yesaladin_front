package shop.yesaladin.front.common.dto;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class PaginatedResponseDto<T> {

    private long totalPage;
    private long currentPage;
    private long totalDataCount;
    private List<T> dataList;

}
