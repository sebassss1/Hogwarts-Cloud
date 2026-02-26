package com.example.hogwarts.controller;

import com.example.hogwarts.dto.AsignaturaDTO;
import com.example.hogwarts.service.AsignaturaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;

@RestController
@RequestMapping("/postgres/asignaturas")
public class AsignaturaController {

    private final AsignaturaService service;

    public AsignaturaController(AsignaturaService service) {
        this.service = service;
    }

    @Operation(summary = "Obtener todas las asignaturas", description = "Devuelve una lista con todas las asignaturas registradas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de asignaturas obtenida correctamente")
    })
    @GetMapping
    public ResponseEntity<List<AsignaturaDTO>> getAll() {
        return ResponseEntity.ok(service.getAllDTO());
    }

    @Operation(summary = "Obtener asignatura por ID", description = "Busca y devuelve una asignatura dado su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Asignatura encontrada"),
            @ApiResponse(responseCode = "404", description = "Asignatura no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<AsignaturaDTO> getById(@PathVariable Long id) {
        return service.getByIdDTO(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}