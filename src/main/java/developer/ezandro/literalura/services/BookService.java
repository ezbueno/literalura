package developer.ezandro.literalura.services;

import developer.ezandro.literalura.domain.Author;
import developer.ezandro.literalura.domain.Book;
import developer.ezandro.literalura.domain.dtos.AuthorDTO;
import developer.ezandro.literalura.domain.dtos.BookDTO;
import developer.ezandro.literalura.exceptions.BookNotFoundException;
import developer.ezandro.literalura.repositories.AuthorRepository;
import developer.ezandro.literalura.repositories.BookRepository;
import developer.ezandro.literalura.services.mapper.DTOMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookService(BookRepository bookRepository,
                       AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Transactional
    public Book saveBookIfNotExists(BookDTO dto) {
        Optional<Book> existingBook = this.bookRepository.findByTitle(dto.title());

        return existingBook.orElseGet(() -> {
            Book book = DTOMapper.toBookEntity(dto);
            book.getAuthors().clear();

            Set<Author> authorsToAdd = new HashSet<>();
            for (AuthorDTO authorDTO : dto.authors()) {
                Author author = this.authorRepository
                        .findByNameAndBirthYearAndDeathYear(
                                authorDTO.name(),
                                authorDTO.birth_year(),
                                authorDTO.death_year())
                        .orElseGet(() -> {
                            Author newAuthor = new Author(
                                    authorDTO.name(),
                                    authorDTO.birth_year(),
                                    authorDTO.death_year());
                            return this.authorRepository.save(newAuthor);
                        });
                authorsToAdd.add(author);
            }

            for (Author author : authorsToAdd) {
                book.addAuthor(author);
            }

            return this.bookRepository.save(book);
        });
    }

    @Transactional(readOnly = true)
    public Set<BookDTO> findAllBooks() {
        return this.bookRepository.findAll()
                .stream()
                .map(DTOMapper::toBookDTO)
                .collect(Collectors.toSet());
    }

    @Transactional(readOnly = true)
    public List<BookDTO> findAllBooksByLanguageCode(String languageCode) {
        List<Book> books = this.bookRepository.findBooksByLanguageCode(languageCode);
        
        if (books.isEmpty()) {
            throw new BookNotFoundException("No books found for language: " + languageCode.toUpperCase());
        }

        return books.stream()
                .map(DTOMapper::toBookDTO)
                .toList();
    }
}