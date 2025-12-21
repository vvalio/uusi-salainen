package dev.valiov.web.salainen.dto;

import java.time.Instant;

/**
 * Base DTO containing fields from the base entity class.
 * Note: use java record-style method names instead of JavaBeans, and no setters.
 */
interface BaseDTO {
    /**
     * The time at which this object was created.
     */
    Instant createdAt();

    /**
     * The time at which this object was modified.
     */
    Instant modifiedAt();
}
