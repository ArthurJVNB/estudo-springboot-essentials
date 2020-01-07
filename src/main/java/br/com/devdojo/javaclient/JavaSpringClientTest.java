package br.com.devdojo.javaclient;

import br.com.devdojo.model.Student;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

public class JavaSpringClientTest {
    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplateBuilder()
                .rootUri("http://localhost:8080/v1/protected/students")
                .basicAuthentication("seya", "cavaleiro")
                .build();

        // Recebendo diretamente o objeto (ele já converte o JSON em objeto do tipo Student, como definido nos parâmetros)
        Student student = restTemplate.getForObject("/{id}", Student.class, 2);
        System.out.println(student);

        // Recebendo um ResponseEntity, que entrega várias coisas como status da requisição e o objeto que definimos
        ResponseEntity<Student> forEntity = restTemplate.getForEntity("/{id}", Student.class, 2);
        System.out.println(forEntity.getBody());

        // Recebendo um array de Student (ele não trabalha diretamente com List)
        Student[] students = restTemplate.getForObject("/", Student[].class);
        System.out.println(Arrays.toString(students));

        // Exchange pode ser usado para GET, POST, UPDATE e DELETE
        // Aqui temos a vantagem de podermos customizar como deve ser entregue a resposta, diferentemente da opção acima (getForEntity)
        // Aqui foi personalizado para receber uma lista, ao invés de arrays primitivos
        ResponseEntity<List<Student>> exchange = restTemplate.exchange("/", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Student>>() {});
        System.out.println(exchange.getBody());
    }
}
