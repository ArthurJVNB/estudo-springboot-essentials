package br.com.devdojo;

import br.com.devdojo.model.Student;
import br.com.devdojo.repository.StudentRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(SpringExtension.class) // antigo @RunWith(SpringRunner.class)
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
        assertThat(studentRepository.findById(student.getId()).isPresent());
        assertThat(!studentRepository.findById(student.getId()).isPresent());
    }
}
