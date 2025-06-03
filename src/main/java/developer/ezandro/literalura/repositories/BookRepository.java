package developer.ezandro.literalura.repositories;

import developer.ezandro.literalura.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByTitle(String title);

    @Query(value = "SELECT b FROM Book b WHERE language = :languageCode")
    List<Book> findBooksByLanguageCode(@Param(value = "languageCode") String languageCode);
}