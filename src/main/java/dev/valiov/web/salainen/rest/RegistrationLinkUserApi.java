package dev.valiov.web.salainen.rest;

import dev.valiov.web.salainen.dto.RegistrationLinkDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/registration/link")
public interface RegistrationLinkUserApi {
    @GetMapping("/{slug}")
    @Operation(summary = "Retrieve registration link info", parameters = {
            @Parameter(
                    in = ParameterIn.PATH,
                    name = "slug",
                    description = "Slug of link",
                    required = true
            )
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = RegistrationLinkDTO.class)
                    )
            }),
            @ApiResponse(responseCode = "404", description = "No link found", content = {
                    @Content(mediaType = "application/json")
            })
    })
    ResponseEntity<RegistrationLinkDTO> getRegistrationLinkBySlug(@PathVariable final String slug);

    @PostMapping("/{slug}")
    @Operation(summary = "Mark registration link as used", parameters = {
            @Parameter(
                    in = ParameterIn.PATH,
                    name = "slug",
                    description = "Slug of link",
                    required = true
            )
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = RegistrationLinkDTO.class)
                    )
            }),
            @ApiResponse(responseCode = "404", description = "No link found", content = {
                    @Content(mediaType = "application/json")
            })
    })
    ResponseEntity<RegistrationLinkDTO> markAsUsedBySlug(@PathVariable final String slug);
}
