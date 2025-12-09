package dev.valiov.web.salainen.service;

import dev.valiov.web.salainen.dto.RegistrationLinkDTO;
import dev.valiov.web.salainen.entity.RegistrationLink;
import dev.valiov.web.salainen.repository.RegistrationLinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collector;
import java.util.stream.IntStream;

/**
 * Service layer for registration links.
 */
@Service
public class RegistrationLinkService extends AbstractService {
    private static final char[] slugChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789_".toCharArray();

    @Autowired
    private RegistrationLinkRepository registrationLinkRepository;

    public ResponseEntity<RegistrationLinkDTO> getRegistrationLinkBySlug(final String slug) {
        final var link = registrationLinkRepository.findBySlug(slug);
        if (link.isEmpty()) {
            throw notFound("Link with id %s not found", slug);
        }

        return ok(RegistrationLinkDTO.fromEntity(link.get()));
    }

    public ResponseEntity<List<RegistrationLinkDTO>> getAllRegistrationLinks() {
        final var links = registrationLinkRepository.findAll();
        return ok(links.stream().map(RegistrationLinkDTO::fromEntity).toList());
    }

    public ResponseEntity<RegistrationLinkDTO> markAsUsedBySlug(final String slug) {
        final var link = registrationLinkRepository.findBySlug(slug);
        if (link.isEmpty()) {
            throw notFound("Link with id %s not found", slug);
        }

        final var entity = link.get();
        if (!entity.isUsed()) {
            throw notFound("Link was already used");
        }

        entity.setUsed(true);
        registrationLinkRepository.save(entity);

        return ok(RegistrationLinkDTO.fromEntity(entity));
    }

    public ResponseEntity<RegistrationLinkDTO> createRegistrationLink() {
        final var slug = generateSlug();
        var linkEntity = new RegistrationLink(false, slug);
        linkEntity = registrationLinkRepository.save(linkEntity);

        return ok(RegistrationLinkDTO.fromEntity(linkEntity));
    }

    /**
     * Generates a 50-character slug composed of _, a-z, A-Z and 0-9.
     */
    private String generateSlug() {
        final var rand = new Random();
        return IntStream.range(0, 50)
                .mapToObj(i -> slugChars[rand.nextInt(50)])
                .collect(Collector.of(
                        StringBuilder::new,
                        StringBuilder::append,
                        StringBuilder::append,
                        StringBuilder::toString)
                );
    }
}
