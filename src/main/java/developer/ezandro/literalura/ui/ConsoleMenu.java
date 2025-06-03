package developer.ezandro.literalura.ui;

import developer.ezandro.literalura.domain.Book;
import developer.ezandro.literalura.domain.dto.ApiResponseDTO;
import developer.ezandro.literalura.domain.dto.AuthorDTO;
import developer.ezandro.literalura.domain.dto.BookDTO;
import developer.ezandro.literalura.exceptions.BookNotFoundException;
import developer.ezandro.literalura.services.AuthorService;
import developer.ezandro.literalura.services.BookService;
import developer.ezandro.literalura.services.LiterAluraApiClient;
import developer.ezandro.literalura.services.LiterAluraDataDeserializer;
import developer.ezandro.literalura.services.mapper.DTOMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Pattern;

@Component
public class ConsoleMenu {
    private static final Scanner SCANNER = new Scanner(System.in);
    private final LiterAluraApiClient literAluraApiClient;
    private final LiterAluraDataDeserializer literAluraDataDeserializer;
    private final BookService bookService;
    private final AuthorService authorService;

    public ConsoleMenu(LiterAluraApiClient literAluraApiClient,
                       LiterAluraDataDeserializer literAluraDataDeserializer,
                       BookService bookService,
                       AuthorService authorService) {
        this.literAluraApiClient = literAluraApiClient;
        this.literAluraDataDeserializer = literAluraDataDeserializer;
        this.bookService = bookService;
        this.authorService = authorService;
    }

    private void exitProgram() {
        System.out.println("""
                
                === Program terminated ===
                Thank you for using LiterAlura.
                """);
    }

    public void inputOption() {
        boolean running = true;

        while (running) {
            System.out.print("""
                    
                    ╔══════════════════════════════╗
                    ║          LiterAlura          ║
                    ╚══════════════════════════════╝
                    
                     1 - Search book by title
                     2 - List registered books
                     3 - List registered authors
                     4 - List authors alive in a given year
                     5 - List books by language
                     0 - Exit
                    
                    Choose an option:\s""");

            String input = SCANNER.nextLine().trim();

            if (input.isEmpty()) {
                System.out.printf("%nERROR: 'OPTION' field must not be blank.%n");
            } else {
                try {
                    int option = Integer.parseInt(input);

                    if (option == 0) {
                        this.exitProgram();
                        running = false;
                    } else if (option >= 1 && option <= 5) {
                        this.handleOption(option);
                    } else {
                        System.out.printf("%nERROR: 'OPTION' field must be between 1 and 5.%n");
                    }
                } catch (NumberFormatException _) {
                    System.out.printf("%nERROR: 'OPTION' field must be a number (1-5 or 0).%n");
                }
            }
        }
    }

    private void handleOption(int option) {
        switch (option) {
            case 1 ->
                    this.handleSearchBookByTitle();
            case 2 ->
                    this.handleListRegisteredBooks();

            case 3 ->
                    this.handleListRegisteredAuthors();

            case 4 ->
                    this.handleListAuthorsAliveInAGivenYear();

            case 5 ->
                    this.handleListBooksByLanguage();
            default ->
                    throw new IllegalStateException("Unexpected value: " + option);
        }
    }

    private void handleSearchBookByTitle() {
        String book;
        boolean isValidInput;

        do {
            System.out.print("Enter the book title you want to search for: ");
            book = SCANNER.nextLine().trim();
            isValidInput = true;

            if (book.isEmpty()) {
                System.out.println("ERROR: Book title cannot be empty.");
                isValidInput = false;
            } else if (book.length() < 3) {
                System.out.println("ERROR: Title must have at least 3 characters.");
                isValidInput = false;
            } else if (book.matches("^[\\W\\d_]+$")) {
                System.out.println("ERROR: Title must contain letters.");
                isValidInput = false;
            }
        } while (!isValidInput);

        try {
            String json = this.literAluraApiClient.getData(book);
            ApiResponseDTO response = this.literAluraDataDeserializer.getData(json, ApiResponseDTO.class);

            if (response.results().isEmpty()) {
                System.out.println("No books found for the title: \"" + book + "\"");
                return;
            }

            BookDTO bookDTO = response.results().getFirst();
            Book savedBook = this.bookService.saveBookIfNotExists(bookDTO);
            System.out.println(DTOMapper.toBookDTO(savedBook));

        } catch (IOException e) {
            System.out.println("Error reading data: " + e.getMessage());
        }
    }

    private void handleListRegisteredBooks() {
        Set<BookDTO> bookDTOS = this.bookService.findAllBooks();
        if (bookDTOS.isEmpty()) {
            System.out.println("No books registered yet.");
            return;
        }

        System.out.println("\nREGISTERED BOOKS:");
        bookDTOS.forEach(System.out::println);
    }

    private void handleListRegisteredAuthors() {
        Set<AuthorDTO> authorDTOS = this.authorService.findAllAuthors();
        if (authorDTOS.isEmpty()) {
            System.out.println("No authors registered yet.");
            return;
        }

        System.out.println("\nREGISTERED AUTHORS:");
        for (AuthorDTO author : authorDTOS) {
            System.out.println(author.prettyPrint());
        }
    }

    private void handleListAuthorsAliveInAGivenYear() {
        String input;
        boolean isValid;

        do {
            isValid = true;
            System.out.print("Enter the year (4 digits): ");
            input = SCANNER.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println("ERROR: 'YEAR' field cannot be blank.");
                isValid = false;
                continue;
            }

            if (!Pattern.matches("^\\d{4}$", input)) {
                System.out.println("ERROR: Year must be exactly 4 digits.");
                isValid = false;
            }
        } while (!isValid);

        int year = Integer.parseInt(input);

        List<AuthorDTO> authorsAlive = this.authorService.findAuthorsAliveInYear(year);
        if (authorsAlive.isEmpty()) {
            System.out.println("\nNo authors alive in year " + year);
        } else {
            System.out.println("\nAuthors alive in year " + year + ":");
            authorsAlive.forEach(author -> System.out.println(author.prettyPrint()));
        }
    }

    private void handleListBooksByLanguage() {
        List<String> validLanguageCodes = List.of("es", "en", "fr", "pt");

        while (true) {
            String input = this.getLanguageInput(validLanguageCodes);
            if (input.equals("menu")) {
                return;
            }

            this.processLanguageSearch(input);

            if (!this.shouldSearchAgain()) {
                return;
            }
        }
    }

    private String getLanguageInput(List<String> validCodes) {
        while (true) {
            System.out.println("""
                    
                    Enter the language code for the search:
                    ES - Spanish | EN - English
                    FR - French | PT - Portuguese
                    (or type 'menu' to return)""");

            String input = SCANNER.nextLine().trim().toLowerCase();

            if (input.equals("menu") || this.isValidLanguageCode(input, validCodes)) {
                return input;
            }
        }
    }

    private boolean isValidLanguageCode(String input, List<String> validCodes) {
        if (input.isEmpty()) {
            System.out.println("ERROR: Language code cannot be blank.");
            return false;
        }
        if (input.length() != 2) {
            System.out.println("ERROR: Language code must be exactly 2 characters.");
            return false;
        }
        if (!input.matches("[a-z]{2}")) {
            System.out.println("ERROR: Language code must contain only letters.");
            return false;
        }
        if (!validCodes.contains(input)) {
            System.out.println("ERROR: " + input.toUpperCase() + " not supported");
            return false;
        }
        return true;
    }

    private void processLanguageSearch(String code) {
        try {
            List<BookDTO> books = this.bookService.findAllBooksByLanguageCode(code);
            System.out.println("\nBooks in " + code.toUpperCase() + ":");
            books.forEach(System.out::println);
        } catch (BookNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private boolean shouldSearchAgain() {
        while (true) {
            System.out.print("\nSearch again? (yes/no): ");
            String choice = SCANNER.nextLine().trim().toLowerCase();

            if (choice.startsWith("y")) {
                return true;
            }

            if (choice.startsWith("n")) {
                return false;
            }

            System.out.println("ERROR: Enter 'yes' or 'no'");
        }
    }
}