package com.sjcapstone.tandanji.domain.classification.service;

import com.sjcapstone.tandanji.domain.classification.entity.Food;
import com.sjcapstone.tandanji.domain.classification.repository.FoodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service // 이 클래스가 서비스 계층임을 선언합니다.
@Transactional(readOnly = true) // 기본적으로 읽기 전용으로 설정하여 성능을 높입니다.
@RequiredArgsConstructor // final이 붙은 필드(레포지토리)를 자동으로 연결해줍니다.
public class FoodService {

    private final FoodRepository foodRepository; // 레포지토리를 불러옵니다.

    /**
     * 음식 정보 저장 (예측 결과 저장)
     */
    @Transactional // 저장할 때는 쓰기 권한이 필요하므로 따로 붙여줍니다.
    public Long saveFood(String name) {
        Food food = new Food(name); // 새로운 음식 객체 생성
        foodRepository.save(food); // 레포지토리를 통해 DB에 저장
        return food.getId();
    }

    /**
     * 전체 음식 목록 조회
     */
    public List<Food> findAllFoods() {
        return foodRepository.findAll();
    }
}