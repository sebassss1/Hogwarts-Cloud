package com.example.hogwarts.controller;

import com.example.hogwarts.dto.EstudianteCreateDTO;
import com.example.hogwarts.dto.EstudianteDTO;
import com.example.hogwarts.dto.EstudianteUpdateDTO;
import com.example.hogwarts.service.EstudianteService;
import jakarta.validation.Valid;
import org.hibernate.annotations.SoftDelete;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;

@RestController
@RequestMapping("/postgres/estudiantes")
public class EstudianteController {

    private final EstudianteService service;

    public EstudianteController(EstudianteService service) {
        this.service = service;
    }

    @Operation(summary = "Obtener todos los estudiantes", description = "Devuelve una lista con todos los estudiantes registrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de estudiantes obtenida correctamente")
    })
    @GetMapping
    public ResponseEntity<List<EstudianteDTO>> getAll() {
        return ResponseEntity.ok(service.getAllDTO());
    }

    @Operation(summary = "Obtener estudiante por ID", description = "Busca y devuelve un estudiante dado su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estudiante encontrado"),
            @ApiResponse(responseCode = "404", description = "Estudiante no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EstudianteDTO> getById(@PathVariable Long id) {
        return service.getByIdDTO(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear un estudiante", description = "Registra un nuevo estudiante en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Estudiante creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos del estudiante no válidos")
    })
    @PostMapping
    public ResponseEntity<EstudianteDTO> create(@Valid @RequestBody EstudianteCreateDTO dto) {
        EstudianteDTO created = service.createFromDTO(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Operation(summary = "Actualizar un estudiante", description = "Actualiza los datos de un estudiante existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estudiante actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos no válidos"),
            @ApiResponse(responseCode = "404", description = "Estudiante no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<EstudianteDTO> update(@PathVariable Long id, @Valid @RequestBody EstudianteUpdateDTO dto) {
        return service.updateFromDTO(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar un estudiante", description = "Realiza un borrado lógico de un estudiante dado su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Estudiante eliminado correctamente")
    })
    @SoftDelete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        // boolean deleted = service.delete(id);
        // return deleted ? ResponseEntity.noContent().build() :
        // ResponseEntity.notFound().build();
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}