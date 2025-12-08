package dev.valiov.web.salainen.dto;

import dev.valiov.web.salainen.entity.RegistrationLink;

public record RegistrationLinkDTO(
        Long id,
        Boolean used,
        String slug
) {
    public static RegistrationLinkDTO fromEntity(RegistrationLink entity) {
        return new RegistrationLinkDTO(
                entity.getId(),
                entity.isUsed(),
                entity.getSlug()
        );
    }

    public RegistrationLink createEntity() {
        return RegistrationLink.builder()
                .used(used)
                .slug(slug)
                .build();
    }

    public RegistrationLink updateEntity(RegistrationLink entity) {
        final var builder = entity.toBuilder();
        if (used != null) {
            entity.setUsed(used);
        }

        if (slug != null) {
            entity.setSlug(slug);
        }

        return builder.build();
    }
}
