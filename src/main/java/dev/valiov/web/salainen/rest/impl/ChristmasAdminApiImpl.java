package dev.valiov.web.salainen.rest.impl;

import dev.valiov.web.salainen.dto.ChristmasDTO;
import dev.valiov.web.salainen.rest.ChristmasAdminApi;
import dev.valiov.web.salainen.service.ChristmasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/christmas")
@CrossOrigin("http://localhost:5000")
@PreAuthorize("hasAnyAuthority('salainen-admin')")
public class ChristmasAdminApiImpl implements ChristmasAdminApi {
    @Autowired
    private ChristmasService christmasService;

    @Override
    @GetMapping("/all")
    public ResponseEntity<List<ChristmasDTO>> getAllChristmases() {
        return christmasService.getAllChristmases();
    }

    @Override
    @PostMapping
    public ResponseEntity<ChristmasDTO> createChristmas(final ChristmasDTO christmasDTO) {
        return christmasService.createChristmas(christmasDTO);
    }
}
