package br.com.devdojo.javaclient;

import br.com.devdojo.model.PageableResponse;
import br.com.devdojo.model.Student;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

public class JavaClientDAO {
    private RestTemplate restTemplate = new RestTemplateBuilder()
            .rootUri("http://localhost:8080/v1/protected/students")
            .basicAuthentication("seya", "cavaleiro")
            .build();

    private RestTemplate restTemplateAdmin = new RestTemplateBuilder()
            .rootUri("http://localhost:8080/v1/admin/students")
            .basicAuthentication("athena", "deusa")
            .build();

    public Student findById(long id) {
        return restTemplate.getForObject("/{id}", Student.class, id);
    }

    public List<Student> listAll() {
        return listAll("");
    }

    public List<Student> listAll(String param) {
        ResponseEntity<PageableResponse<Student>> exchange = restTemplate.exchange("/" + param, HttpMethod.GET, null, new ParameterizedTypeReference<PageableResponse<Student>>() {
        });
        return exchange.getBody().getContent();
    }

    public Student save(Student student) {
        ResponseEntity<Student> exchange = restTemplateAdmin.exchange("/", HttpMethod.POST, new HttpEntity<>(student, createJsonHeader()), Student.class);
        return exchange.getBody();
    }

    private static HttpHeaders createJsonHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
}