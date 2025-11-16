package dev.valiov.web.salainen.service;

import dev.valiov.web.salainen.dto.ChristmasDTO;
import dev.valiov.web.salainen.entity.Christmas;
import dev.valiov.web.salainen.repository.ChristmasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Service
public class ChristmasService extends AbstractService {
    private static final ZoneId ZONE_UTC = ZoneId.of("UTC");

    @Autowired
    private ChristmasRepository christmasRepository;

    /**
     * Returns the currently active Christmas, usually the latest one. Note that this only checks the year; no gifts
     * have necessarily been given.
     * If none are found, this method throws a {@link org.springframework.web.server.ResponseStatusException}
     * API Type: User
     *
     * @return the currently active Christmas
     */
    public ResponseEntity<ChristmasDTO> getCurrentChristmas() {
        final var christmas = christmasRepository.findByYear(LocalDate.now().getYear());
        if (christmas.isEmpty()) {
            throw notFound("No active Christmas found");
        }

        final var christmasDto = ChristmasDTO.fromEntity(christmas.get());
        return ok(christmasDto);
    }

    /**
     * Returns the Christmas for the specified year/id.
     *
     * @param year the year to retrieve the Christmas for
     * @param id the id of the Christmas
     * @return the Christmas with that year/id, or nothing if not found
     */
    public ResponseEntity<ChristmasDTO> getChristmas(final Integer year, final Long id) {
        Optional<Christmas> christmas;
        if (id != null && year != null) {
            throw badRequest("Cannot provide both year and id");
        } else if (id != null) {
            christmas = christmasRepository.findById(id);
        } else if (year != null) {
            christmas = christmasRepository.findByYear(year);
        } else {
            throw badRequest("Must provide one of: year, id");
        }

        if (christmas.isEmpty()) {
            throw notFound("No Christmas found");
        }

        return ok(ChristmasDTO.fromEntity(christmas.get()));
    }

    /**
     * Returns all Christmases.
     * API Type: User
     */
    public ResponseEntity<List<ChristmasDTO>> getAllChristmases() {
        return ok(christmasRepository.findAll().stream().map(ChristmasDTO::fromEntity).toList());
    }


    /**
     * Creates a new Christmas.
     * API Type: Admin
     *
     * @param christmasDTO the Christmas to create
     * @return the created database object
     */
    public ResponseEntity<ChristmasDTO> createChristmas(final ChristmasDTO christmasDTO) {
        if (christmasRepository.existsByYear(christmasDTO.year())) {
            throw conflict("Christmas already exists for year %d", christmasDTO.year());
        }

        String error;
        if ((error = validateNewChristmas(christmasDTO)) != null) {
            throw badRequest(error);
        }

        final var christmas = christmasDTO.createEntity();
        return ok(ChristmasDTO.fromEntity(christmasRepository.save(christmas)));
    }

    /**
     * Updates the specified Christmas.
     * API Type: Admin
     *
     * @param id     the id of the Christmas to update
     * @param update the Christmas update data
     * @return the updated database object
     */
    public ResponseEntity<ChristmasDTO> updateChristmas(final Long id, final ChristmasDTO update) {
        final var christmas = christmasRepository.findById(id);
        if (christmas.isEmpty()) {
            throw notFound("No Christmas found with id %d", id);
        }

        final var updated = update.updateEntity(christmas.get());
        final var dbEntity = christmasRepository.save(updated);

        return ok(ChristmasDTO.fromEntity(dbEntity));
    }

    // Private helper methods

    /**
     * Returns an error string if the Christmas is invalid, {@code null} otherwise.
     *
     * @param christmasDTO Christmas to check
     */
    private String validateNewChristmas(final ChristmasDTO christmasDTO) {
        final var now = LocalDate.now(ZONE_UTC);
        final var giftDate = christmasDTO.giftAssignDate();
        final var christmasDate = christmasDTO.christmasDate();

        if (giftDate.isEqual(christmasDate) || giftDate.isAfter(christmasDate)) {
            return "Gifts must be assigned before Christmas";
        } else if (christmasDTO.year() == now.getYear()) {
            if (giftDate.isEqual(now) || giftDate.isBefore(now)) {
                return "Gifts must be assigned in the future";
            } else if (christmasDate.isEqual(now) || christmasDate.isBefore(now)) {
                return "Christmas must be in the future";
            }
        } else if (christmasDate.getYear() != christmasDTO.year()) {
            return "Christmas date must be in the year specified";
        }

        return null;
    }
}
