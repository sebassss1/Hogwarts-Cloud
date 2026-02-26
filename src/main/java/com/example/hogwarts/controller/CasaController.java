package com.example.hogwarts.controller;

import com.example.hogwarts.dto.CasaDTO;
import com.example.hogwarts.service.CasaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;

@RestController
@RequestMapping("/postgres/casas")
public class CasaController {

    private final CasaService service;

    public CasaController(CasaService service) {
        this.service = service;
    }

    @Operation(summary = "Obtener todas las casas", description = "Devuelve una lista con todas las casas de Hogwarts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de casas obtenida correctamente")
    })
    @GetMapping
    public ResponseEntity<List<CasaDTO>> getAll() {
        return ResponseEntity.ok(service.getAllDTO());
    }

    @Operation(summary = "Obtener casa por ID", description = "Busca y devuelve una casa dado su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Casa encontrada"),
            @ApiResponse(responseCode = "404", description = "Casa no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CasaDTO> getById(@PathVariable Long id) {
        return service.getByIdDTO(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}