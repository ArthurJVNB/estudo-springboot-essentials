package br.com.devdojo.adapter;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class CustomWebMvcConfigurerAdapter implements WebMvcConfigurer {
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        PageableHandlerMethodArgumentResolver resolver = new PageableHandlerMethodArgumentResolver();
        /* Abaixo é o mesmo que definir pela URL qual é o número da página inicial e o tamanho da quantidade de resul-
        tados (?size=5), com a diferença que nesse caso estamos definindo como serão essas configurações caso não seja
        passado nenhuma configuração pela URL. Quando escrevemos algum parâmetrona URL, sobrescrevemos as configurações
        padrões que definimos aqui sem problemas :)
        */
        resolver.setFallbackPageable(PageRequest.of(0, 5));
        resolvers.add(resolver);
    }
}
