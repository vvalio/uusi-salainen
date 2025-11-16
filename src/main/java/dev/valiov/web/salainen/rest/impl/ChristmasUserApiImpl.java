package dev.valiov.web.salainen.rest.impl;

import dev.valiov.web.salainen.dto.ChristmasDTO;
import dev.valiov.web.salainen.rest.ChristmasUserApi;
import dev.valiov.web.salainen.service.ChristmasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/christmas")
public class ChristmasUserApiImpl implements ChristmasUserApi {
    @Autowired
    private ChristmasService christmasService;

    @Override
    @GetMapping("/current")
    public ResponseEntity<ChristmasDTO> getCurrentChristmas() {
        return christmasService.getCurrentChristmas();
    }

    @Override
    @GetMapping
    public ResponseEntity<ChristmasDTO> getChristmas(final Integer year, final Long id) {
        return christmasService.getChristmas(year, id);
    }
}
