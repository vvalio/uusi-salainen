package dev.valiov.web.salainen.rest;

import dev.valiov.web.salainen.dto.ChristmasDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Interface definition for the user-part of Christmas API.
 */
@SecurityRequirement(name = "bearerAuth")
public interface ChristmasUserApi {
    @GetMapping("/current")
    @Operation(summary = "Retrieve the currently active Christmas")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ChristmasDTO.class)
                    )
            }),
            @ApiResponse(responseCode = "404", description = "No Christmas is currently active", content = {
                    @Content(mediaType = "application/json")
            })
    })
    ResponseEntity<ChristmasDTO> getCurrentChristmas();

    @GetMapping
    @Operation(summary = "Retrieve Christmas", parameters = {
            @Parameter(
                    in = ParameterIn.QUERY,
                    name = "year",
                    description = "The year",
                    required = false
            ),
            @Parameter(
                    in = ParameterIn.QUERY,
                    name = "id",
                    description = "ID",
                    required = false
            )
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ChristmasDTO.class)
                    )
            }),
            @ApiResponse(responseCode = "404", description = "No Christmas found", content = {
                    @Content(mediaType = "application/json")
            }),
            @ApiResponse(responseCode = "403", description = "Unauthorized", content = {
                    @Content(mediaType = "application/json")
            }),
    })
    ResponseEntity<ChristmasDTO> getChristmas(@RequestParam(value = "year", required = false) final Integer year, @RequestParam(value = "id", required = false) final Long id);
}
