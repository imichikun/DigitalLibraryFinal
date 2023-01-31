package library_rest.controller;

import library_rest.model.Book;
import library_rest.model.Reader;
import library_rest.service.BookService;
import library_rest.service.ReaderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;
    private final ReaderService readerService;

    @Autowired
    public BookController(BookService bookService, ReaderService readerService) {
        this.bookService = bookService;
        this.readerService = readerService;
    }

    @GetMapping("/")
    public String getAllBooks(Model model){
        model.addAttribute("allBooks", bookService.getAllBooks());
        return "/books/all_books";
    }

    @GetMapping("/{id}")
    public String getOneBook(@PathVariable("id") int id, Model model, @ModelAttribute("reader") Reader reader){
        model.addAttribute("book", bookService.getOneBook(id));

        Optional<Reader> possibleReader = bookService.getReaderOfBook(id);
        if (possibleReader.isEmpty())
            model.addAttribute("allReaders", readerService.getAll());
        else
            model.addAttribute("readerOfBook", possibleReader.get());

        return "/books/one_book";
    }

    @GetMapping("/newBook")
    public String newBook(@ModelAttribute("book") Book book){
        return "/books/save_update_book";
    }

    @PostMapping("/saveBook")
    public String saveBook(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return "/books/save_update_book";

        bookService.saveBook(book);
        return "redirect:/books/";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteBook(@PathVariable("id") int id){
        bookService.deleteBook(id);
        return "redirect:/books/";
    }

    @PutMapping("/update/{id}")
    public String updateBook(@PathVariable("id") int id, Model model){
        model.addAttribute("book", bookService.updateBook(id));
        return "/books/save_update_book";
    }

    @GetMapping("/remove/{id}")
    public String removeBook(@PathVariable("id") int id){
        bookService.removeBook(id);
        return "redirect:/books/" + id;
    }

    @PostMapping("/set/{id}")
    public String setBook(@PathVariable("id") int id, @ModelAttribute("reader") Reader reader){
        bookService.setBook(id, reader);
        return "redirect:/books/" + id;
    }

    @GetMapping("/findBook")
    public String findBook(){
        return "/books/find_book";
    }

    @PostMapping("/showSoughtBook")
    public String findBook(Model model, @RequestParam("query") String query){
        model.addAttribute("soughtBooks", bookService.findBook(query));
        return "/books/find_book";
    }
}