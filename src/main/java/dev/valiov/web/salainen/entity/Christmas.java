package dev.valiov.web.salainen.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

/**
 * Describes the gift-giving activities of a specific year. This includes the participants, who gifts who and
 * the date the gifters were assigned.
 */
@Entity
@Table(name = "christmas")
@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor
public class Christmas extends Base {
    public Christmas() {
    }

    /**
     * The year of the Christmas, e.g. 2025
     */
    @Column(name = "christmas_year", nullable = false, unique = true)
    private int year;

    /**
     * The day the gifts were assigned.
     */
    @Column(name = "gift_assign_date", nullable = false)
    private LocalDate giftAssignDate;

    /**
     * The day the gifts are given.
     */
    @Column(name = "christmas_date", nullable = false)
    private LocalDate christmasDate;
}
