package developer.ezandro.literalura.domain.dtos;

import java.util.List;
import java.util.stream.Collectors;

public record AuthorDTO(
        String name,
        Integer birth_year,
        Integer death_year,
        List<BookDTO> bookDTOS
) {
    public String prettyPrint() {
        String booksList = bookDTOS().stream()
                .map(book -> "  - " + book.title())
                .collect(Collectors.joining("\n"));

        return String.format("""
                        Author: %s
                        Birth Year: %d
                        Death Year: %d
                        Books:
                        %s
                        
                        ----------------------------------------
                        """,
                name(),
                birth_year(),
                death_year(),
                booksList);
    }
}