package com.sjcapstone.tandanji.domain.classification.repository;

import com.sjcapstone.tandanji.domain.classification.entity.ClassificationRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassificationRequestRepository extends JpaRepository<ClassificationRequest, Long> {
    // 기본적인 CRUD 기능이 자동으로 포함됩니다.
}