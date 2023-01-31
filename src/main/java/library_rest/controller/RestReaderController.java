package library_rest.controller;

import library_rest.DTO.ReaderDTO;
import library_rest.model.Reader;
import library_rest.service.ReaderService;
import library_rest.util.ReaderNotCreatedException;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rest")
public class RestReaderController {
    private final ReaderService readerService;
    private final ModelMapper modelMapper;

    @Autowired
    public RestReaderController(ReaderService readerService, ModelMapper modelMapper) {
        this.readerService = readerService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/")
    public List<ReaderDTO> getAllReadersDTO(){
        List<Reader> readers = readerService.getAll();
        return readers.stream().map(this::convertModeltoDTO).toList();
    }

    @GetMapping("/{id}")
    public ReaderDTO getOneReaderDTO(@PathVariable("id") int id){
        return convertModeltoDTO(readerService.getOne(id));
    }

    @PostMapping("/save")
    public ResponseEntity<HttpStatus> restSaveReader(@RequestBody @Valid ReaderDTO readerDTO, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();
            String errMessage = errors.stream().map(err -> err.getField() + " - " + err.getDefaultMessage() + ";")
                    .collect(Collectors.joining());

            throw new ReaderNotCreatedException(errMessage);
        }

        readerService.saveReader(convertDTOtoModel(readerDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    private ReaderDTO convertModeltoDTO(Reader reader){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(reader, ReaderDTO.class);
    }

    private Reader convertDTOtoModel(ReaderDTO readerDTO){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(readerDTO, Reader.class);
    }
}