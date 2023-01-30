package shop.yesaladin.front.file.dto;

import lombok.*;

/**
 * 업로드할 파일 정보를 받아와 저장한 Dto 입니다.
 *
 * @author 이수정
 * @since 1.0
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class FormDataDto {

    private String title;
    private String content;
}
