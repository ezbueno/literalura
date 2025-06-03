package developer.ezandro.literalura.repositories;

import developer.ezandro.literalura.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    @Query(value = "SELECT a FROM Author a WHERE a.birthYear <= :year AND (a.deathYear IS NULL OR a.deathYear >= :year)")
    List<Author> findAuthorsAliveInYear(@Param("year") int year);

    Optional<Author> findByNameAndBirthYearAndDeathYear(String name, Integer birthYear, Integer deathYear);
}