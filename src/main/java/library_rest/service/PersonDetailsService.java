package library_rest.service;

import library_rest.model.Person;
import library_rest.security.PersonDetails;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@Transactional
public class PersonDetailsService implements UserDetailsService {
    private final PersonService personService;

    @Autowired
    public PersonDetailsService(PersonService personService) {
        this.personService = personService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Person> possiblePerson = personService.findByUsername(username);

        if (possiblePerson.isEmpty())
            throw new UsernameNotFoundException("Username is not found !");

        return new PersonDetails(possiblePerson.get());
    }
}