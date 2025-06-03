package developer.ezandro.literalura.services.mapper;

import developer.ezandro.literalura.domain.Author;
import developer.ezandro.literalura.domain.Book;
import developer.ezandro.literalura.domain.dtos.AuthorDTO;
import developer.ezandro.literalura.domain.dtos.BookDTO;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class DTOMapper {
    private DTOMapper() {
    }

    public static Book toBookEntity(BookDTO bookDTO) {
        Book book = new Book(
                bookDTO.title(),
                extractPrimaryLanguage(bookDTO.languages()),
                bookDTO.download_count()
        );

        Set<Author> authors = toAuthorEntities(bookDTO.authors());
        authors.forEach(book::addAuthor);

        return book;
    }

    public static Set<Author> toAuthorEntities(List<AuthorDTO> authorDTOs) {
        Set<Author> authors = new HashSet<>();
        for (AuthorDTO dto : authorDTOs) {
            authors.add(new Author(
                    dto.name(),
                    dto.birth_year(),
                    dto.death_year()
            ));
        }
        return authors;
    }

    public static BookDTO toBookDTO(Book book) {
        return new BookDTO(
                book.getTitle(),
                book.getAuthors().stream()
                        .map(author -> new AuthorDTO(
                                author.getName(),
                                author.getBirthYear(),
                                author.getDeathYear(),
                                List.of()
                        ))
                        .toList(),
                List.of(book.getLanguage()),
                book.getDownloadCount()
        );
    }

    public static AuthorDTO toAuthorDTO(Author author) {
        List<BookDTO> books = author.getBooks().stream()
                .map(book -> new BookDTO(
                        book.getTitle(),
                        book.getAuthors().stream()
                                .map(a -> new AuthorDTO(a.getName(), a.getBirthYear(), a.getDeathYear(), List.of()))
                                .toList(),
                        List.of(book.getLanguage()),
                        book.getDownloadCount()
                ))
                .toList();

        return new AuthorDTO(
                author.getName(),
                author.getBirthYear(),
                author.getDeathYear(),
                books
        );
    }

    public static String extractPrimaryLanguage(List<String> languages) {
        return (languages != null && !languages.isEmpty()) ? languages.getFirst() : "unknown";
    }
}