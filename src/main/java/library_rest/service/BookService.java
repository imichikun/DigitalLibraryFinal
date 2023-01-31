package library_rest.service;

import library_rest.model.Book;
import library_rest.model.Reader;
import library_rest.repository.BookRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BookService {
    private final BookRepository bookRepository;
    private final ReaderService readerService;

    @Autowired
    public BookService(BookRepository bookRepository, ReaderService readerService) {
        this.bookRepository = bookRepository;
        this.readerService = readerService;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getOneBook(int id) {
        Optional<Book> possibleBook = bookRepository.findById(id);
        return possibleBook.orElse(null);
    }

    public void saveBook(Book book) {
        bookRepository.save(book);
    }

    public void deleteBook(int id) {
        bookRepository.delete(getOneBook(id));
    }

    public Book updateBook(int id) {
        return getOneBook(id);
    }

    public Optional<Reader> getReaderOfBook(int id) {
        Book soughtBook = getOneBook(id);                   // читателя у книги может быть и не быть, поэтому .ofNullable,
        return Optional.ofNullable(soughtBook.getReader()); // если его нет, то вернётся пустой Optional-объект
    }

    public void removeBook(int id) {
        Book returnedBook = getOneBook(id);
        returnedBook.setReader(null);
        returnedBook.setTakenDate(null);
    }

    public void setBook(int id, Reader reader) {
        Book bookToBeSet = getOneBook(id);

        bookToBeSet.setTakenDate(LocalDateTime.now());
        bookToBeSet.setReader(reader);
    }

    public List<Book> findBook(String query) {
        return bookRepository.findByTitleStartingWith(query);
    }
}