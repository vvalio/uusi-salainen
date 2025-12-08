package dev.valiov.web.salainen.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * A link object sent to each person for registration; can only be used once.
 */
@Entity
@Table(name = "registration_link")
@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor
public class RegistrationLink extends Base {
    public RegistrationLink() {
    }

    /**
     * Has the link been used? Only true when registration through the link has been completed.
     */
    @Column(name = "used", nullable = false)
    private boolean used;

    /**
     * The slug for the link, which is a string of 50 random characters that identify the link.
     */
    @Column(name = "slug", nullable = false, unique = true)
    private String slug;
}
