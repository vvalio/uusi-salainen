package dev.valiov.web.salainen.repository;

import dev.valiov.web.salainen.entity.RegistrationLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RegistrationLinkRepository extends JpaRepository<RegistrationLink, Long> {
    /**
     * Finds a registration link by the slug
     *
     * @param slug the slug
     */
    @Query("SELECT l FROM RegistrationLink l WHERE l.slug = :slug")
    Optional<RegistrationLink> findBySlug(@Param("slug") final String slug);

}
