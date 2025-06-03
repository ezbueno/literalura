package developer.ezandro.literalura.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.lang.NonNull;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BookDTO(
        String title,
        List<AuthorDTO> authors,
        List<String> languages,
        Double download_count) {


    @NonNull
    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("#,###");
        df.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.GERMANY));

        return String.format("""
                        ╔═══════════════════════════════
                        ║            BOOK
                        ╠═══════════════════════════════
                        ║  Title: %s
                        ║  Author(s): %s
                        ║  Language(s): %s
                        ║  Downloads: %s
                        ╚═══════════════════════════════
                        """,
                title(),
                authors().stream().map(AuthorDTO::name).collect(Collectors.joining(", ")),
                String.join(", ", languages()),
                df.format(download_count)
        );
    }
}