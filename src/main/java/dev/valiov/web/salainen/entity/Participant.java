package dev.valiov.web.salainen.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "participant")
@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor
public class Participant extends Base {
    public Participant() {
    }

    @Id
    @GeneratedValue
    private Long id;

    /**
     * For the library doing the gifting.
     */
    @Column(name = "person_id", nullable = false)
    private String personId;

    @Column(name = "family_id", nullable = false)
    private String familyId;

    @Column(nullable = false)
    private String name;
}