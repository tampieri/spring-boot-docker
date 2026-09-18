package br.com.sasati.spring_boot_docker.repository;

import br.com.sasati.spring_boot_docker.entity.Prompt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromptRepository extends JpaRepository<Prompt, Long> {
    // Métodos de consulta customizados podem ser adicionados aqui, se necessário.
    // Exemplo: List<Prompt> findByCategoria(String categoria);
}
