package br.com.sasati.spring_boot_docker;

import br.com.sasati.spring_boot_docker.entity.Prompt;
import br.com.sasati.spring_boot_docker.repository.PromptRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest
class PromptRepositoryIntegrationTest {

    @Container
    static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:16-alpine")
                    .withDatabaseName("prompts_db_test")
                    .withUsername("test")
                    .withPassword("test");

    @DynamicPropertySource
    static void configureDatasource(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private PromptRepository promptRepository;

    @Test
    void devePersistirEBuscarPromptNoPostgresReal() {
        Prompt prompt = new Prompt();
        prompt.setNomePrompt("Teste de integração com Testcontainers");
        prompt.setId(null); // Garante que o Hibernate trate como nova entidade
        prompt.setCategoria("teste categoria");
        prompt.setObjetivo("objeto");
        prompt.setModelo("string");
        prompt.setStatus("string");
        prompt.setAtualizacao(LocalDateTime.now());


        Prompt salvo = promptRepository.save(prompt);

        assertThat(promptRepository.findById(salvo.getId())).isPresent();
    }
}
