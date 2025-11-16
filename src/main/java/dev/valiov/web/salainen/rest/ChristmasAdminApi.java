package dev.valiov.web.salainen.rest;

import dev.valiov.web.salainen.dto.ChristmasDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;

@SecurityRequirement(name = "bearerAuth")
public interface ChristmasAdminApi {
    @GetMapping("/all")
    @Operation(summary = "Retrieve all active Christmases")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ChristmasDTO[].class)
                    )
            }),
    })
    ResponseEntity<List<ChristmasDTO>> getAllChristmases();

    @PostMapping
    @Operation(summary = "Create a new Christmas for this year, if not already created")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ChristmasDTO.class)
                    )
            }),
            @ApiResponse(responseCode = "409", description = "Already exists", content = {
                    @Content(mediaType = "application/json")
            })
    })
    ResponseEntity<ChristmasDTO> createChristmas(@RequestBody final ChristmasDTO christmasDTO);
}
