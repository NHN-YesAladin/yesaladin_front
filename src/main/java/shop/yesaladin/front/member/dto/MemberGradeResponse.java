package shop.yesaladin.front.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberGradeResponse {

    private Integer id;
    private String name;
    private Long baseOrderAmount;
    private Long baseGivenPoint;
}
