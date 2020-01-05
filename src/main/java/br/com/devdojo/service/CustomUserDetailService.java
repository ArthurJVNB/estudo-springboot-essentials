package br.com.devdojo.service;

import br.com.devdojo.model.User;
import br.com.devdojo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CustomUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = Optional.ofNullable(userRepository.findByUsername(username))
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        /*
        Acima é o mesmo que escrever:

        User user = userRepository.findByUsername(username);
        if (user == null)
            throw new UsernameNotFoundException("User not found");
        */

        /*
        A lista de autoridades já viria com o "user" se tivéssemos colocado as devidas relações no BD, mas já que
        não colocamos, a gente precisou criar a lista com o ".createAuthorityList".
        De toda forma precisamos montar uma lista de "GrantedAuthority" (só não sei no momento como faríamos para
        pegar as "authorities" que o user teria).
        */
        List<GrantedAuthority> authorityListAdmin = AuthorityUtils.createAuthorityList("ROLE_USER", "ROLE_ADMIN");
        List<GrantedAuthority> authorityListUser = AuthorityUtils.createAuthorityList("ROLE_USER");

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.isAdmin() ? authorityListAdmin : authorityListUser);
    }
}
