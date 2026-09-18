package br.com.sasati.spring_boot_docker.controller;

import br.com.sasati.spring_boot_docker.entity.Prompt;
import br.com.sasati.spring_boot_docker.service.PromptService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prompts")
@Tag(name = "Prompts", description = "API para gerenciamento de prompts")
public class PromptController {

    private final PromptService promptService;

    public PromptController(PromptService promptService) {
        this.promptService = promptService;
    }

    // CREATE
    @PostMapping
    @Operation(summary = "Cria um novo prompt", description = "Adiciona um novo prompt ao banco de dados.")
    @ApiResponse(responseCode = "201", description = "Prompt criado com sucesso")
    public ResponseEntity<Prompt> criar(@RequestBody Prompt prompt) {
        Prompt novoPrompt = promptService.criar(prompt);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoPrompt);
    }

    // READ (todos)
    @GetMapping
    @Operation(summary = "Lista os prompts", description = "Lista todos os prompts ao banco de dados.")
    @ApiResponse(responseCode = "201", description = "Lista retornada com sucesso")
    public ResponseEntity<List<Prompt>> listarTodos() {
        return ResponseEntity.ok(promptService.listarTodos());
    }

    // READ (por ID)
    @GetMapping("/{id}")
    @Operation(summary = "Busca um prompt", description = "Busca o prompt com id informado.")
    @ApiResponse(responseCode = "201", description = "Busca do prompt retornada com sucesso")
    public ResponseEntity<Prompt> buscarPorId(@PathVariable Long id) {
        return promptService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // UPDATE
    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um prompt", description = "Atualiza o prompt com id informado.")
    @ApiResponse(responseCode = "201", description = "Atualiza realizada com sucesso.")
    public ResponseEntity<Prompt> atualizar(@PathVariable Long id, @RequestBody Prompt prompt) {
        try {
            Prompt promptAtualizado = promptService.atualizar(id, prompt);
            return ResponseEntity.ok(promptAtualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE
    @DeleteMapping("/{id}")
    @Operation(summary = "Exclui um prompt", description = "Exclui o prompt com id informado.")
    @ApiResponse(responseCode = "201", description = "Exclusão realizada com sucesso.")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        try {
            promptService.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
