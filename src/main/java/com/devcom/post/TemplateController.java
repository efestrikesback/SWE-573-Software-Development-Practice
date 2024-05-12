package com.devcom.post;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/templates")
@RequiredArgsConstructor
public class TemplateController {
    private final TemplateService templateService;

    @PostMapping("/create")
    public ResponseEntity<Template> createTemplate(@RequestBody Template template) {
        return ResponseEntity.ok(templateService.createTemplate(template));
    }

    @PostMapping("/addField/{templateId}")
    public ResponseEntity<TemplateField> addFieldToTemplate(@PathVariable Long templateId, @RequestBody TemplateField field) {
        return ResponseEntity.ok(templateService.addFieldToTemplate(templateId, field));
    }
}