package br.com.devdojo.endpoint;

import br.com.devdojo.error.CustomErrorType;
import br.com.devdojo.model.Student;
import br.com.devdojo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("students")
public class StudentEndpoint {
    private final StudentRepository studentDAO;

    @Autowired
    public StudentEndpoint(StudentRepository studentDAO) {
        this.studentDAO = studentDAO;
    }

//    @RequestMapping(method = RequestMethod.GET)
    @GetMapping // mesma coisa que o de cima
    public ResponseEntity<?> listAll() {
        return new ResponseEntity<>(studentDAO.findAll(), HttpStatus.OK);
    }

//    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    @GetMapping(path = "/{id}") // mesma coisa que o de cima
    public ResponseEntity<?> getStudentId(@PathVariable("id") Long id) {
        if (studentDAO.findById(id).isPresent()) {
            Student student = studentDAO.findById(id).get();
            return new ResponseEntity<>(student, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new CustomErrorType("Student not found"), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = "/findByName/{name}")
    public ResponseEntity<?> findStudentsByName(@PathVariable String name) {
        return new ResponseEntity<>(studentDAO.findByNameIgnoreCaseContaining(name), HttpStatus.OK);
    }

//    @RequestMapping(method = RequestMethod.POST)
    @PostMapping // mesma coisa que o de cima
    public ResponseEntity<?> save(@RequestBody Student student) {
        return new ResponseEntity<>(studentDAO.save(student), HttpStatus.CREATED);
    }

//    @RequestMapping(method = RequestMethod.DELETE)
//    @DeleteMapping // mesma coisa que o de cima
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        studentDAO.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

//    @RequestMapping(method = RequestMethod.PUT)
    @PutMapping // mesma coisa que o de cima
    public ResponseEntity<?> update(@RequestBody Student student) {
        studentDAO.save(student);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
