package br.com.sasati.spring_boot_docker.service;

import br.com.sasati.spring_boot_docker.entity.Prompt;
import br.com.sasati.spring_boot_docker.repository.PromptRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PromptService {

    private final PromptRepository promptRepository;

    // Injeção por construtor (boa prática)
    public PromptService(PromptRepository promptRepository) {
        this.promptRepository = promptRepository;
    }

    @Transactional(readOnly = true)
    public List<Prompt> listarTodos() {
        return promptRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Prompt> buscarPorId(Long id) {
        return promptRepository.findById(id);
    }

    @Transactional
    public Prompt criar(Prompt prompt) {
        prompt.setId(null); // Garante que o Hibernate trate como nova entidade
        prompt.setAtualizacao(LocalDateTime.now());
        return promptRepository.save(prompt);
    }

    @Transactional
    public Prompt atualizar(Long id, Prompt promptAtualizado) {
        return promptRepository.findById(id)
                .map(promptExistente -> {
                    promptExistente.setNomePrompt(promptAtualizado.getNomePrompt());
                    promptExistente.setCategoria(promptAtualizado.getCategoria());
                    promptExistente.setObjetivo(promptAtualizado.getObjetivo());
                    promptExistente.setModelo(promptAtualizado.getModelo());
                    promptExistente.setStatus(promptAtualizado.getStatus());
                    promptExistente.setAtualizacao(LocalDateTime.now());
                    return promptRepository.save(promptExistente);
                })
                .orElseThrow(() -> new RuntimeException("Prompt não encontrado com id: " + id));
    }

    @Transactional
    public void deletar(Long id) {
        if (!promptRepository.existsById(id)) {
            throw new RuntimeException("Prompt não encontrado com id: " + id);
        }
        promptRepository.deleteById(id);
    }
}
