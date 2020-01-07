package br.com.devdojo.endpoint;

import br.com.devdojo.error.CustomErrorType;
import br.com.devdojo.error.ResourceNotFoundException;
import br.com.devdojo.model.Student;
import br.com.devdojo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("v1")
public class StudentEndpoint {
    private final StudentRepository studentDAO;

    @Autowired
    public StudentEndpoint(StudentRepository studentDAO) {
        this.studentDAO = studentDAO;
    }

    //@RequestMapping(method = RequestMethod.GET)
    //@GetMapping // mesma coisa que o de cima
    @GetMapping(path = "protected/students")
    public ResponseEntity<?> listAll(Pageable pageable) {
        return new ResponseEntity<>(studentDAO.findAll(pageable), HttpStatus.OK);
    }

    //@RequestMapping(method = RequestMethod.GET, path = "/{id}")
    //@GetMapping(path = "/{id}") // mesma coisa que o de cima
    @GetMapping(path = "protected/students/{id}")
    public ResponseEntity<?> getStudentId(@PathVariable("id") Long id, @AuthenticationPrincipal UserDetails userDetails) {
        System.out.println(userDetails);
        verifyStudentExists(id);
        Student student = studentDAO.findById(id).get();
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    //@GetMapping(path = "/findByName/{name}")
    @GetMapping(path = "protected/students/findByName/{name}")
    public ResponseEntity<?> findStudentsByName(@PathVariable String name) {
        return new ResponseEntity<>(studentDAO.findByNameIgnoreCaseContaining(name), HttpStatus.OK);
    }

    //@RequestMapping(method = RequestMethod.POST)
    //@PostMapping // mesma coisa que o de cima
    @PostMapping(path = "admin/students")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> save(@Valid @RequestBody Student student) {
        return new ResponseEntity<>(studentDAO.save(student), HttpStatus.CREATED);
    }

    //@RequestMapping(method = RequestMethod.DELETE)
    //@DeleteMapping // mesma coisa que o de cima
    //@DeleteMapping(path = "/{id}")
    @DeleteMapping(path = "admin/students/{id}")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        verifyStudentExists(id);
        studentDAO.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //@RequestMapping(method = RequestMethod.PUT)
    //@PutMapping // mesma coisa que o de cima
    @PutMapping(path = "admin/students")
    public ResponseEntity<?> update(@RequestBody Student student) {
        verifyStudentExists(student.getId());
        studentDAO.save(student);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void verifyStudentExists(Long id) {
        if (!studentDAO.findById(id).isPresent())
            throw new ResourceNotFoundException("Student not found by ID: " + id);
    }
}
