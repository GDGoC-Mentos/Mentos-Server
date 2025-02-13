package com.mentos.mentosback.category;

import com.mentos.mentosback.common.apiPayload.code.status.ErrorStatus;
import com.mentos.mentosback.common.apiPayload.exception.GeneralException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Category findById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.ID_NOT_FOUND));
    }
}
