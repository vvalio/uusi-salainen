package dev.valiov.web.salainen.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import dev.valiov.web.salainen.entity.RegistrationLink;

import java.time.Instant;

public record RegistrationLinkDTO(
        Long id,
        Boolean used,
        String slug,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
        Instant createdAt,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
        Instant modifiedAt
) implements BaseDTO {
    public static RegistrationLinkDTO fromEntity(RegistrationLink entity) {
        return new RegistrationLinkDTO(
                entity.getId(),
                entity.isUsed(),
                entity.getSlug(),
                entity.getCreatedAt(),
                entity.getModifiedAt()
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
