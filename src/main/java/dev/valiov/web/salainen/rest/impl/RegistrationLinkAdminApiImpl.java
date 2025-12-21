package dev.valiov.web.salainen.rest.impl;

import dev.valiov.web.salainen.dto.RegistrationLinkDTO;
import dev.valiov.web.salainen.rest.RegistrationLinkAdminApi;
import dev.valiov.web.salainen.service.RegistrationLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/registration/link")
@PreAuthorize("hasAnyAuthority('salainen-admin')")
public class RegistrationLinkAdminApiImpl implements RegistrationLinkAdminApi {
    @Autowired
    private RegistrationLinkService registrationLinkService;

    @Override
    @PostMapping
    public ResponseEntity<RegistrationLinkDTO> createRegistrationLink() {
        return registrationLinkService.createRegistrationLink();
    }

    @Override
    @GetMapping("/all")
    public ResponseEntity<List<RegistrationLinkDTO>> listRegistrationLinks() {
        return registrationLinkService.getAllRegistrationLinks();
    }
}
