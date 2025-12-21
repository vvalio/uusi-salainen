package dev.valiov.web.salainen.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import dev.valiov.web.salainen.entity.Christmas;

import java.time.Instant;
import java.time.LocalDate;

/**
 * JSON object for {@link dev.valiov.web.salainen.entity.Christmas}.
 */
public record ChristmasDTO(
        Long id, // response/outgoing only, ignored on requests
        Integer year,
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate giftAssignDate,
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate christmasDate,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
        Instant createdAt,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
        Instant modifiedAt
) implements BaseDTO {
    public static ChristmasDTO fromEntity(Christmas entity) {
        return new ChristmasDTO(
                entity.getId(),
                entity.getYear(),
                entity.getGiftAssignDate(),
                entity.getChristmasDate(),
                entity.getCreatedAt(),
                entity.getModifiedAt()
        );
    }

    // For entities, we don't set the createdAt and modifiedAt fields as Hibernate sets them for us.

    public Christmas createEntity() {
        return Christmas.builder()
                .year(year)
                .giftAssignDate(giftAssignDate)
                .christmasDate(christmasDate)
                .build();
    }

    public Christmas updateEntity(Christmas entity) {
        final var builder = entity.toBuilder();
        if (year != null) {
            entity.setYear(year);
        }

        if (giftAssignDate != null) {
            entity.setGiftAssignDate(giftAssignDate);
        }

        if (christmasDate != null) {
            entity.setChristmasDate(christmasDate);
        }

        return builder.build();
    }

    @Override
    public Instant createdAt() {
        return createdAt;
    }

    @Override
    public Instant modifiedAt() {
        return modifiedAt;
    }
}
