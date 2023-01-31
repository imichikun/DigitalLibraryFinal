package library_rest.controller;

import library_rest.model.Reader;
import library_rest.service.ReaderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
//@RequestMapping("/readers")
public class ReaderController {
    private final ReaderService readerService;

    @Autowired
    public ReaderController(ReaderService readerService) {
        this.readerService = readerService;
    }

    @GetMapping("/")
    public String getAllReaders(Model model){
        model.addAttribute("allReaders", readerService.getAll());
        return "/readers/all_readers";
    }

    @GetMapping("/{id}")
    public String getReaderById(@PathVariable("id") int id, Model model){
        model.addAttribute("reader", readerService.getOne(id));
//        model.addAttribute("allReaders", readerService.getAll());
        return "/readers/one_reader";
    }

    @GetMapping("/new")
    public String saveReader(Model model){
        model.addAttribute("reader", new Reader());
        return "/readers/save_update_reader";
    }

    @PostMapping("/save")
    public String saveReader(@ModelAttribute("reader") @Valid Reader reader, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return "/readers/save_update_reader";

        readerService.saveReader(reader);
        return "redirect:/";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteReader(@PathVariable("id") int id){
        readerService.deleteReader(id);
        return "redirect:/";
    }

    @PutMapping("/update/{id}")
    public String updateReaderFinish(@PathVariable("id") int id, Model model){
        model.addAttribute("reader", readerService.updateReader(id));
        return "/readers/save_update_reader";
    }

}