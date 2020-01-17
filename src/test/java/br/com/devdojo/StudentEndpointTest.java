package br.com.devdojo;

import br.com.devdojo.model.Student;
import br.com.devdojo.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc // Deve ser colocado caso usarmos o MockMvc
@EnableAutoConfiguration
public class StudentEndpointTest {
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private MockMvc mockMvc; // Outra forma de fazer os testes no lugar do TestRestTemplate.
    @LocalServerPort
    private int port;
    @MockBean // Faz com que o uso aqui não altere os dados reais do banco de dados que estamos usando.
    private StudentRepository studentRepository;

    @TestConfiguration
    static class Config {
        @Bean
        public RestTemplateBuilder restTemplateBuilder() {
            return new RestTemplateBuilder().basicAuthentication("athena", "deusa");
        }
    }

    @Test
    public void listStudentsWhenUsernameAndPasswordAreIncorrectShouldReturnStatusCode401() {
        restTemplate = restTemplate.withBasicAuth("1", "1");
        /*
        Pode ser meio contra-intuitivo, mas esse método acima não altera de fato os valores da autenticação do restTemplate,
        então precisamos passar o valor alterado para o próprio restTemplate pra ele ficar com a alteração de fato.
        */

        ResponseEntity<String> responseEntity = restTemplate.getForEntity("/v1/protected/students/", String.class);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(401);
    }

    @Test
    public void getStudentByIdWhenUsernameAndPasswordAreIncorrectShouldReturnStatusCode401() {
        restTemplate = restTemplate.withBasicAuth("1", "1");
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("/v1/protected/students/1", String.class);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(401);
    }

    @Test
    public void listStudentsWhenUsernameAndPasswordAreCorrectShouldReturnStatusCode200() {
        List<Student> students = asList(
                new Student(1L, "Aragorn", "reidegondor@lotr.com"),
                new Student(2L, "Legolas", "folha@lotr.com")
        );

        // Aqui simula qual deveria ser o retorno para o método que definimos nesse Mock abaixo
        BDDMockito.when(studentRepository.findAll()).thenReturn(students);

        // TODO: Descobrir porque esse teste não está conseguindo autenticar o usuário corretamente
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("/v1/protected/students/", String.class);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    public void getStudentByIdWhenUsernameAndPasswordAreCorrectShouldReturnStatusCode200() {
        Student student = new Student(1L, "Gmili", "gmili@lotr.com");
        BDDMockito.when(studentRepository.findById(student.getId())).thenReturn(Optional.of(student));
        // TODO: Descobrir porque esse teste não está conseguindo autenticar o usuário corretamente
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("/v1/protected/students/{id}", String.class, student.getId());
        //ResponseEntity<String> responseEntity = restTemplate.getForEntity("/v1/protected/students/" + student.getId(), String.class); // Tbm pode ser assim.
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    public void getStudentByIdWhenUsernameAndPasswordAreCorrectAndStudentDoesNotExistShouldReturnStatusCode404() {
        // TODO: Descobrir porque esse teste não está conseguindo autenticar o usuário corretamente
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("/v1/protected/students/{id}", String.class, -1L);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(404);
    }
}
