package com.example.hogwarts.controller;

import com.example.hogwarts.dto.MascotaCreateStandaloneDTO;
import com.example.hogwarts.dto.MascotaDTO;
import com.example.hogwarts.service.MascotaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;

@RestController
@RequestMapping("/postgres/mascotas")
public class MascotaController {

    private final MascotaService service;

    public MascotaController(MascotaService service) {
        this.service = service;
    }

    @Operation(summary = "Obtener todas las mascotas", description = "Devuelve una lista con todas las mascotas registradas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de mascotas obtenida correctamente")
    })
    @GetMapping
    public ResponseEntity<List<MascotaDTO>> getAll() {
        return ResponseEntity.ok(service.getAllDTO());
    }

    @Operation(summary = "Obtener mascota por ID", description = "Busca y devuelve una mascota dado su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Mascota encontrada"),
            @ApiResponse(responseCode = "404", description = "Mascota no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<MascotaDTO> getById(@PathVariable Long id) {
        return service.getByIdDTO(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear una mascota", description = "Registra una nueva mascota en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Mascota creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos de la mascota no válidos")
    })
    @PostMapping
    public ResponseEntity<MascotaDTO> create(@Valid @RequestBody MascotaCreateStandaloneDTO dto) {
        MascotaDTO created = service.createFromDTO(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
}