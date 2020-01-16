package br.com.devdojo;

import br.com.devdojo.model.Student;
import br.com.devdojo.repository.StudentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.ConstraintViolationException;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

// @ExtendWith(SpringExtension.class) // antigo @RunWith(SpringRunner.class)
// Desde o Spring 2.1 a anotação acima já está inclusa em outras anotações, sendo elas @DataJpaTest, @WebMvcTest e @SpringBootTest
@DataJpaTest
public class StudentRepositoryTest {
    @Autowired
    private StudentRepository studentRepository;

    @Test
    public void createShouldPersistData() {
        Student student = new Student("Saint Seya", "guerreirodasestrelas@cavaleiro.com");
        this.studentRepository.save(student);

        // agora verificaremos se tudo foi salvo nos conformes
        assertThat(student.getId()).isNotNull();
        assertThat(student.getName()).isEqualTo("Saint Seya");
        assertThat(student.getEmail()).isEqualTo("guerreirodasestrelas@cavaleiro.com");
    }

    @Test
    public void deleteShouldRemoveData() {
        Student student = new Student("Saint Seya", "guerreirodasestrelas@cavaleiro.com");
        this.studentRepository.save(student);
        this.studentRepository.delete(student);

        // TODO: dúvida é por que tanto faz a checagem? Qual seria a forma correta de verificar a existência desse estudante?
        //assertThat(studentRepository.findById(student.getId()).isPresent());
        //assertThat(!studentRepository.findById(student.getId()).isPresent());

        // aqui abaixo pelo visto faz a checagem correta
        assertThat(studentRepository.findById(student.getId())).isEmpty();
    }

    @Test
    public void updateShouldChangeAndPersistData() {
        Student student = new Student("Saint Seya", "guerreirodasestrelas@cavaleiro.com");
        this.studentRepository.save(student);
        student.setName("Saint Seya: Cavaleiro de Ouro");
        student.setEmail("cavaleirodeouro@cavaleiro.com");
        studentRepository.save(student);

        Student studentUpdated = new Student();
        if (this.studentRepository.findById(student.getId()).isPresent()) {
            studentUpdated = this.studentRepository.findById(student.getId()).get();
        }

        assertThat(studentUpdated.getName()).isEqualTo("Saint Seya: Cavaleiro de Ouro");
        assertThat(studentUpdated.getEmail()).isEqualTo("cavaleirodeouro@cavaleiro.com");
    }

    @Test
    public void findByNameIgnoreCaseContainingShouldIgnoreCase() {
        Student student1 = new Student("Saint Seya", "guerreirodasestrelas@cavaleiro.com");
        Student student2 = new Student("sEyA", "cavaleirodebronze@cavaleiro.com");
        studentRepository.save(student1);
        studentRepository.save(student2);

        List<Student> studentList = studentRepository.findByNameIgnoreCaseContaining("seya");
        assertThat(studentList.size()).isEqualTo(2);
    }

    @Test
    public void createWhenNameIsNullShouldThrowConstraintViolationException() {
        // TODO: Isso não está lançando nenhuma exceção. Ainda estou tentando entender o porquê, pois era pra lançar pelo menos algo
        Assertions.assertThrows(ConstraintViolationException.class, () -> studentRepository.save(new Student(null, "email@email.com")));
    }

    @Test
    public void createWhenEmailIsNullShouldThrowConstraintViolationException() {
        Student student = new Student();
        // TODO: Descobrir porque ele não está lançando nenhuma exceção
        Assertions.assertThrows(ConstraintViolationException.class, () -> studentRepository.save(student));
    }
}
