package com.sjcapstone.tandanji.domain.classification.repository;

import com.sjcapstone.tandanji.domain.classification.entity.ClassificationResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassificationResultRepository extends JpaRepository<ClassificationResult, Long> {
}