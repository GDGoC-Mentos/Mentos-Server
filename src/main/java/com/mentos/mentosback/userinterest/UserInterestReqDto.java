package com.mentos.mentosback.userinterest;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record UserInterestReqDto(
        @JsonProperty("user_id") Long userId,
        @JsonProperty("interest_category_ids") List<Long> interestCategoryIds
) {
}