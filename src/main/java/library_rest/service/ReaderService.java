package library_rest.service;

import library_rest.model.Reader;
import library_rest.repository.ReaderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ReaderService {
    private ReaderRepository readerRepository;

    @Autowired
    public ReaderService(ReaderRepository readerRepository) {
        this.readerRepository = readerRepository;
    }

    public List<Reader> getAll() {
        return readerRepository.findAll();
    }

    public Reader getOne(int id) {
        Optional<Reader> possibleReader = readerRepository.findById(id);
        return possibleReader.orElse(null);
    }

    public void saveReader(Reader reader) {
        if (reader.getCreatedAt() == null)
            reader.setCreatedAt(LocalDateTime.now());
        readerRepository.save(reader);
    }

    public void deleteReader(int id) {
        readerRepository.delete(getOne(id));
    }

    public Reader updateReader(int id) {
        Reader updatedReader = getOne(id);
        updatedReader.setUpdatedAt(LocalDateTime.now());
        return updatedReader;
    }
}