package com.example.hogwarts.controller;

import com.example.hogwarts.dto.ProfesorDTO;
import com.example.hogwarts.service.ProfesorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;

@RestController
@RequestMapping("/postgres/profesores")
public class ProfesorController {

    private final ProfesorService service;

    public ProfesorController(ProfesorService service) {
        this.service = service;
    }

    @Operation(summary = "Obtener todos los profesores", description = "Devuelve una lista con todos los profesores registrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de profesores obtenida correctamente")
    })
    @GetMapping
    public ResponseEntity<List<ProfesorDTO>> getAll() {
        return ResponseEntity.ok(service.getAllDTO());
    }

    @Operation(summary = "Obtener profesor por ID", description = "Busca y devuelve un profesor dado su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profesor encontrado"),
            @ApiResponse(responseCode = "404", description = "Profesor no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProfesorDTO> getById(@PathVariable Long id) {
        return service.getByIdDTO(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}