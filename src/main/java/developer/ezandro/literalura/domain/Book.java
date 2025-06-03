package developer.ezandro.literalura.domain;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String language;
    private Double downloadCount;
    @ManyToMany(cascade = {CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(
            name = "books_authors",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private final Set<Author> authors = new HashSet<>();

    protected Book() {
    }

    public Book(String title, String language, Double downloadCount) {
        this.title = title;
        this.language = language;
        this.downloadCount = downloadCount;
    }

    public String getTitle() {
        return this.title;
    }

    public Long getId() {
        return this.id;
    }

    public String getLanguage() {
        return this.language;
    }

    public Double getDownloadCount() {
        return this.downloadCount;
    }

    public Set<Author> getAuthors() {
        return this.authors;
    }

    public void addAuthor(Author author) {
        if (author != null) {
            this.authors.add(author);
            author.getBooks().add(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;
        Book book = (Book) o;
        return Objects.equals(id, book.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}