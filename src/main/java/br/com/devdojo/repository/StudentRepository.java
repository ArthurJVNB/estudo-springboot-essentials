package br.com.devdojo.repository;

import br.com.devdojo.model.Student;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/*
A interface PagingAndSortingRepository herda de CrudRepository, com o extra de ser paginável, ou seja, podemos limitar
a quantidade de elementos trazidos de volta após um pedido. Por exemplo, por padrão ele devolve até no máximo 20 elemen-
tos por página. Para definir outros padrões, precisamos criar uma classe que implemente a interface WebMvcConfigurer.
Essa interface não obriga a gente a implementar todos os métodos nela apresentados, basta sobrescrever aqueles que inte-
ressar para a gente.
A paginação é bem interessante para que economizemos banda de internet ao limitar a quantidade de resultados entregues e
agilizamos o processo de resposta do servidor com o cliente.

Podemos acrescentar alguns parâmetros ao URL pra ajustar como serão entregues os resultados. Abaixo estão alguns:
?page=0             ->      Aqui é configurado qual a página de resultado que será devolvida
?size=10            ->      Aqui definimos a quantidade máxima de resultados devolvidos
?page=0&size=10
      ou            ->      Aqui estamos usando as duas configurações juntas. Tanto faz a ordem escrita
?size=10&page=0
?sort=name,asc      ->      Aqui definimos que queremos uma ordenação crescente levando em conta a propriedade "name"
?sort=name,desc     ->      O mesmo que o de cima, mas agora queremos de forma descendente
?sort=name,asc&sort=email,desc      ->      Aqui juntamos duas regras de ordenação. A ordenação vai seguindo a ordem
                                            dada, nesse caso primeiro a propriedade "name" e depois "email"
*/
public interface StudentRepository extends PagingAndSortingRepository<Student, Long> {
    List<Student> findByNameIgnoreCaseContaining(String name);
}
