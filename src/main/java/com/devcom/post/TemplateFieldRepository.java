package com.devcom.post;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TemplateFieldRepository extends JpaRepository<TemplateField, Long> {
    List<TemplateField> findByTemplateId(Long templateId);
}