package br.com.devdojo.model;

import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class Student extends AbstractEntity {
    @NotEmpty // Para obrigar que esse valor esteja presente na criação desse objeto para o Spring Boot
    private String name;
    @NotEmpty (message = "Precisa colocar o email") // também se pode personalizar a mensagem de erro para o campo
    @Email
    private String email;

    public Student(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public Student() { }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
