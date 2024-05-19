package com.devcom.post;

import com.devcom.community.Community;
import com.devcom.community.CommunityRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TemplateService {

    private final TemplateRepository templateRepository;
    private final TemplateFieldRepository templateFieldRepository;
    private final CommunityRepository communityRepository;

    @Transactional
    public Template createTemplate(Long communityId, Template template) {
        Community community = communityRepository.findById(communityId)
                .orElseThrow(() -> new RuntimeException("Community not found"));
        template.setCommunity(community);
        return templateRepository.save(template);
    }

    @Transactional
    public TemplateField addFieldToTemplate(Long templateId, TemplateField field) {
        Template template = templateRepository.findById(templateId)
                .orElseThrow(() -> new RuntimeException("Template not found"));
        field.setTemplate(template);
        return templateFieldRepository.save(field);
    }
    public List<Template> getTemplates(Long communityId) {
        return templateRepository.findByCommunityCommunityId(communityId);
    }


}
