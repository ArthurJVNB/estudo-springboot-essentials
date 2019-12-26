package br.com.devdojo.endpoint;

import br.com.devdojo.error.CustomErrorType;
import br.com.devdojo.model.Student;
import br.com.devdojo.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.Arrays.asList;

@RestController
@RequestMapping("students")
public class StudentEndpoint {
    private final DateUtil dateUtil;

    @Autowired
    public StudentEndpoint(DateUtil dateUtil) {
        this.dateUtil = dateUtil;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> listAll() {
        return new ResponseEntity<>(Student.students, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public ResponseEntity<?> getStudentId(@PathVariable("id") int id) {
        // --- temporário
        Student student = new Student();
        student.setId(id);
        int index = Student.students.indexOf(student);
        // --- temporário

        if (index == -1)
            return new ResponseEntity<>(new CustomErrorType("Student not found"), HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(Student.students.get(index), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> save(@RequestBody Student student) {
        Student.students.add(student);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }
}
