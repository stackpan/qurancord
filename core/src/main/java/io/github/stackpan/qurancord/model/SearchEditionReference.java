package io.github.stackpan.qurancord.model;

import lombok.*;

@Data
@AllArgsConstructor
public class SearchEditionReference implements SearchEditionReferenceMaker {

    @NonNull
    private String language;

    private String edition;

    @Override
    public String make() {
        StringBuilder stringBuilder = new StringBuilder(language);

        if (edition != null) {
            stringBuilder.append(".").append(edition);
        }

        return stringBuilder.toString();
    }
}
