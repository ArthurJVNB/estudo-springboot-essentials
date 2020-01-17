package br.com.devdojo.javaclient;

import br.com.devdojo.model.PageableResponse;
import br.com.devdojo.model.Student;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

public class JavaSpringClientTest {
    public static void main(String[] args) {

        JavaClientDAO dao = new JavaClientDAO();

//        Student student = dao.findById(45);
//        System.out.println(student);

//        List<Student> students = dao.listAll("?page=0&sort=id,desc");
//        System.out.println(students);

        Student studentPost = new Student();
//        studentPost.setName("Saori");
//        studentPost.setEmail("saori@athena.com");
//        Student savedStudent = dao.save(studentPost);
//        System.out.println(savedStudent);

        Student studentSaved = dao.save(studentPost);
        System.out.println(studentSaved);

//        studentPost.setId(36L);
//        studentPost.setName("Saori Mitsumasu");
//        dao.update(studentPost);

//        dao.delete(35L);

        // Forma monolítica do código acima (antes de refatorar essa classe)
        /*
        RestTemplate restTemplate = new RestTemplateBuilder()
                .rootUri("http://localhost:8080/v1/protected/students")
                .basicAuthentication("seya", "cavaleiro")
                .build();

        RestTemplate restTemplateAdmin = new RestTemplateBuilder()
                .rootUri("http://localhost:8080/v1/admin/students")
                .basicAuthentication("athena", "deusa")
                .build();

        // Recebendo diretamente o objeto (ele já converte o JSON em objeto do tipo Student, como definido nos parâmetros)
        Student student = restTemplate.getForObject("/{id}", Student.class, 2);
        System.out.println(student);

        // Recebendo um ResponseEntity, que entrega várias coisas como status da requisição e o objeto que definimos
        ResponseEntity<Student> forEntity = restTemplate.getForEntity("/{id}", Student.class, 2);
        System.out.println(forEntity.getBody());

        // Recebendo um array de Student (ele não trabalha diretamente com List)
        // OBS: Só irá funcionar sem o pageable
//        Student[] students = restTemplate.getForObject("/", Student[].class);
//        System.out.println(Arrays.toString(students));

        // Exchange pode ser usado para GET, POST, UPDATE e DELETE
        // Aqui temos a vantagem de podermos customizar como deve ser entregue a resposta, diferentemente da opção acima (getForEntity)
        // Aqui foi personalizado para receber uma lista, ao invés de arrays primitivos
        // OBS: Da forma que está aqui só irá funcionar sem o pageable
//        ResponseEntity<List<Student>> exchange = restTemplate.exchange("/", HttpMethod.GET, null,
//                new ParameterizedTypeReference<List<Student>>() {});
//        System.out.println(exchange.getBody());

        ResponseEntity<PageableResponse<Student>> exchange = restTemplate.exchange("/?sort=id,desc", HttpMethod.GET, null,
                new ParameterizedTypeReference<PageableResponse<Student>>() {
        });
        System.out.println(exchange);

        Student studentPost = new Student();
        studentPost.setName("Saint Seya");
        studentPost.setEmail("seya@saint.com");
        ResponseEntity<Student> exchangePost = restTemplateAdmin.exchange("/", HttpMethod.POST, new HttpEntity<>(studentPost, createJsonHeader()), Student.class);
        System.out.println(exchangePost);

        Student studentPostForObject = restTemplateAdmin.postForObject("/", studentPost, Student.class);
        System.out.println(studentPostForObject);

        ResponseEntity<Student> studentPostForEntity = restTemplateAdmin.postForEntity("/", studentPost, Student.class);
        System.out.println(studentPostForEntity);
        */
    }

    /*
    private static HttpHeaders createJsonHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
    */
}
