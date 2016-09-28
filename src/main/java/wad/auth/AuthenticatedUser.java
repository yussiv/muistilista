
package wad.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import wad.domain.Person;
import wad.repository.PersonRepository;

@Service
public class AuthenticatedUser {
    
    @Autowired
    private PersonRepository personRepo;

    public Person get() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return personRepo.findByUsername(auth.getName());
    }
}
