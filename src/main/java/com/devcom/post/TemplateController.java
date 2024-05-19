package com.devcom.post;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/templates")
@RequiredArgsConstructor
public class TemplateController {
    private final TemplateService templateService;

    private final TemplateFieldRepository templateFieldRepository;

    @GetMapping("/{templateId}/fields")
    public List<TemplateField> getFields(@PathVariable Long templateId) {
        return templateService.getFields(templateId);
    }

}