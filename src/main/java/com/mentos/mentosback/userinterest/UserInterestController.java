package com.mentos.mentosback.userinterest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{userId}/interest-categories")
public class UserInterestController {

    private final UserInterestService userInterestService;

    @PostMapping
    public ResponseEntity<UserInterestResDto> registerUserInterests(
            @PathVariable Long userId,
            @RequestBody UserInterestReqDto userInterestReqDto
    ) {
        UserInterestResDto userInterestResDto = userInterestService.registerUserInterests(userId, userInterestReqDto);
        return ResponseEntity.ok(userInterestResDto);
    }

    @GetMapping
    public ResponseEntity<UserInterestResDto> getUserInterests(@PathVariable Long userId) {
        UserInterestResDto userInterestResDto = userInterestService.getUserInterests(userId);
        return ResponseEntity.ok(userInterestResDto);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<String> deleteUserInterest(@PathVariable Long userId, @PathVariable Long categoryId) {
        userInterestService.deleteUserInterest(userId, categoryId);
        return ResponseEntity.ok("관심 카테고리가 삭제되었습니다.");
    }
}
