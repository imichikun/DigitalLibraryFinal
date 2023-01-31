package library_rest.DTO;

import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

public class ReaderDTO {

    @NotEmpty(message = "Reader's name cannot be empty")
    private String name;

    @Range(min = 14, max = 65, message = "Reader's age should be in the span from 15 to 65")
    private int age;

    public ReaderDTO(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public ReaderDTO() {
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }
}