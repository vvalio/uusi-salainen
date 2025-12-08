package dev.valiov.web.salainen.rest.impl;

import dev.valiov.web.salainen.dto.RegistrationLinkDTO;
import dev.valiov.web.salainen.rest.RegistrationLinkUserApi;
import dev.valiov.web.salainen.service.RegistrationLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/registration/link")
public class RegistrationLinkUserApiImpl implements RegistrationLinkUserApi {
    @Autowired
    private RegistrationLinkService registrationLinkService;

    @Override
    @GetMapping("/{slug}")
    public ResponseEntity<RegistrationLinkDTO> getRegistrationLinkBySlug(@PathVariable final String slug) {
        return registrationLinkService.getRegistrationLinkBySlug(slug);
    }

    @Override
    @PostMapping("/{slug}")
    public ResponseEntity<RegistrationLinkDTO> markAsUsedBySlug(String slug) {
        return registrationLinkService.markAsUsedBySlug(slug);
    }
}
