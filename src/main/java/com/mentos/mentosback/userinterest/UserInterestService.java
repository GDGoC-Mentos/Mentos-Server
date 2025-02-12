package com.mentos.mentosback.userinterest;

import com.mentos.mentosback.category.Category;
import com.mentos.mentosback.category.CategoryRepository;
import com.mentos.mentosback.user.User;
import com.mentos.mentosback.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserInterestService {

    private final UserInterestRepository userInterestRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;


    @Transactional
    public UserInterestResDto registerUserInterests(Long userId, UserInterestReqDto reqDto) {
        // 목표: userInterests db 에 user_id, category_id 를 저장한다.

        // 1. user 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다." + userId));

        // 2. 기존 관심 카테고리 삭제
        userInterestRepository.deleteByUser(user);

        // 3. 새로운 관심 카테고리 저장
        List<UserInterest> userInterests = reqDto.interestCategoryIds().stream()
                .map(categoryId -> {
                    Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new IllegalArgumentException("카테고리를 찾을 수 없습니다.")); // Category 조회
                    return UserInterest.createUserInterest(user, category);   // UserInterest 생성
                })
                .collect(Collectors.toList());

        userInterestRepository.saveAll(userInterests);

        return UserInterestResDto.from(user.getId(), userInterests);
    }

    public UserInterestResDto getUserInterests(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다." + userId));

        List<UserInterest> userInterests = userInterestRepository.findByUser(user);

        return UserInterestResDto.from(user.getId(), userInterests);
    }
}
