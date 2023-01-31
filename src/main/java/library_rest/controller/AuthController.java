package library_rest.controller;

import library_rest.DTO.PersonDTO;
import library_rest.model.Person;
import library_rest.service.AuthService;
import library_rest.util.UserErrorResponse;
import library_rest.util.UsersIsAlreadyRegisteredException;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/login")
    public String login(){
        return "/auth/auth_login";
    }

    @GetMapping("/register")
    public String register(@ModelAttribute("personDTO") PersonDTO personDTO){
        return "/auth/auth_register";
    }

    @PostMapping("/finishRegistration")
    public String finishRegistration(@ModelAttribute("personDTO") @Valid PersonDTO personDTO, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return "/auth/auth_register";
        else {
            authService.registerNewPerson(convertDTOtoPerson(personDTO));
            return "redirect: /auth/login";
        }
    }

    private Person convertDTOtoPerson(PersonDTO personDTO){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(personDTO, Person.class);
    }

    @ExceptionHandler
    private ResponseEntity<UserErrorResponse> exceptionHandler(UsersIsAlreadyRegisteredException e){
        UserErrorResponse response = new UserErrorResponse(e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}