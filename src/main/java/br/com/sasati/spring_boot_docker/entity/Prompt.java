package br.com.sasati.spring_boot_docker.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "prompts")
public class Prompt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único do prompt", example = "1")
    private Long id;

    @Column(name = "nome_prompt", nullable = false)
    @Schema(description = "Nome descritivo do prompt", example = "Resumo de Artigo Científico")
    private String nomePrompt;

    @Column(nullable = false)
    private String categoria;

    @Column(columnDefinition = "TEXT")
    private String objetivo;

    @Column(nullable = false)
    private String modelo;

    @Column(nullable = false)
    private String status;

    @Column(name = "atualizacao")
    private LocalDateTime atualizacao;

    // Construtor padrão (obrigatório para JPA)
    public Prompt() {}

    // Construtor completo (opcional, útil para testes)
    public Prompt(String nomePrompt, String categoria, String objetivo,
                  String modelo, String status, LocalDateTime atualizacao) {
        this.nomePrompt = nomePrompt;
        this.categoria = categoria;
        this.objetivo = objetivo;
        this.modelo = modelo;
        this.status = status;
        this.atualizacao = atualizacao;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNomePrompt() { return nomePrompt; }
    public void setNomePrompt(String nomePrompt) { this.nomePrompt = nomePrompt; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public String getObjetivo() { return objetivo; }
    public void setObjetivo(String objetivo) { this.objetivo = objetivo; }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getAtualizacao() { return atualizacao; }
    public void setAtualizacao(LocalDateTime atualizacao) { this.atualizacao = atualizacao; }
}
