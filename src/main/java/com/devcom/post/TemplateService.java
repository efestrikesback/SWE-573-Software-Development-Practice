package com.devcom.post;

import com.devcom.community.Community;
import com.devcom.community.CommunityRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TemplateService {

    private final TemplateRepository templateRepository;
    private final TemplateFieldRepository templateFieldRepository;

    private final CommunityRepository  communityRepository;

    //TODo ADD IF EXISTS
    public Template createTemplate(Template template) {
        Community community = communityRepository.findById(template.getCommunity().getCommunityId())
                .orElseThrow(() -> new RuntimeException("Community not found"));
        Template createdTemplate = new Template();
        createdTemplate.setName(template.getName());
        createdTemplate.setDescription(template.getDescription());
        createdTemplate.setCommunity(community);  // Setting community
        return templateRepository.save(createdTemplate);
    }


    @Transactional
    public TemplateField addFieldToTemplate(Long templateId, TemplateField field) {
        Template template = templateRepository.findById(templateId).orElseThrow(() -> new IllegalArgumentException("Template not found"));
        field.setTemplate(template);
        return templateFieldRepository.save(field);
    }
}
