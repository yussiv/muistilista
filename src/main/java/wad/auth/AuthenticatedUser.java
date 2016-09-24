
package wad.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wad.domain.Person;
import wad.repository.PersonRepository;

@Service
public class AuthenticatedUser {
    
    @Autowired
    private PersonRepository personRepo;

    public Person get() {
        return personRepo.findOne(1L);
    }
}
