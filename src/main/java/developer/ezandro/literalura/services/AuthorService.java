package developer.ezandro.literalura.services;

import developer.ezandro.literalura.domain.Author;
import developer.ezandro.literalura.domain.dtos.AuthorDTO;
import developer.ezandro.literalura.repositories.AuthorRepository;
import developer.ezandro.literalura.services.mapper.DTOMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Transactional(readOnly = true)
    public Set<AuthorDTO> findAllAuthors() {
        return this.authorRepository.findAll()
                .stream()
                .map(DTOMapper::toAuthorDTO)
                .collect(Collectors.toSet());
    }

    @Transactional(readOnly = true)
    public List<AuthorDTO> findAuthorsAliveInYear(int year) {
        List<Author> authors = this.authorRepository.findAuthorsAliveInYear(year);
        return authors.stream()
                .map(DTOMapper::toAuthorDTO)
                .toList();
    }
}