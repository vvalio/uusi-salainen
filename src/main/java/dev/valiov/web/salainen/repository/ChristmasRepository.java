package dev.valiov.web.salainen.repository;

import dev.valiov.web.salainen.entity.Christmas;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ChristmasRepository extends JpaRepository<Christmas, Long> {
    /**
     * Returns the Christmas for the specified year.
     *
     * @param year the year
     */
    @Query("SELECT c FROM Christmas c WHERE c.year = :year")
    Optional<Christmas> findByYear(@Param("year") final int year);

    boolean existsByYear(final int year);
}
