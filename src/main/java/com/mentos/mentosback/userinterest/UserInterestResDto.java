package com.mentos.mentosback.userinterest;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

import com.mentos.mentosback.category.CategoryDto;

@Getter
@AllArgsConstructor
public class UserInterestResDto {
    private Long userId;
    private List<CategoryDto> interestCategories;

    // List<UserInterest> 를 UserInterestResDto 로 변환한다.
    public static UserInterestResDto from(Long userId, List<UserInterest> userInterests) {
        List<CategoryDto> categories = userInterests.stream()
                .map(ui -> new CategoryDto(ui.getCategory().getId(),ui.getCategory().getName()))
                .collect(Collectors.toList()); // 변환된 CategoryDto 객체들을 리스트로 수집

        return new UserInterestResDto(userId, categories);
    }
}
