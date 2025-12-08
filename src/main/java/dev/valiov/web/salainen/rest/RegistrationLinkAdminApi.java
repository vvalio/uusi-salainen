package dev.valiov.web.salainen.rest;

import dev.valiov.web.salainen.dto.RegistrationLinkDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/registration/link")
@SecurityRequirement(name = "bearerAuth")
public interface RegistrationLinkAdminApi {
    @PostMapping
    @Operation(summary = "Create a new registration link")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = RegistrationLinkDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = {
                    @Content(mediaType = "application/json")
            }),
            @ApiResponse(responseCode = "403", description = "Unauthorized", content = {
                    @Content(mediaType = "application/json")
            })
    })
    ResponseEntity<RegistrationLinkDTO> createRegistrationLink();

    @GetMapping("/all")
    @Operation(summary = "List registration links")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = RegistrationLinkDTO[].class))
            }),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = {
                    @Content(mediaType = "application/json")
            }),
            @ApiResponse(responseCode = "403", description = "Unauthorized", content = {
                    @Content(mediaType = "application/json")
            })
    })
    ResponseEntity<List<RegistrationLinkDTO>> listRegistrationLinks();
}
