package library_rest.service;

import library_rest.model.Person;
import library_rest.repository.PersonRepository;
import library_rest.util.UsersIsAlreadyRegisteredException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@Transactional
public class AuthService {
    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(PersonRepository personRepository, PasswordEncoder passwordEncoder) {
        this.personRepository = personRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerNewPerson(Person person){
        Optional<Person> possiblePerson = personRepository.findByUsername(person.getUsername());
        if (possiblePerson.isPresent())
            throw new UsersIsAlreadyRegisteredException("User is already registered in the system");

        person.setRole("ROLE_USER");
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        personRepository.save(person);
    }

}