package io.github.stackpan.qurancord.model;

import lombok.Data;

@Data
public class Edition {

    private String identifier;

    private String language;

    private String name;

    private String englishName;

    private String format;

    private String type;

    private String direction;

    public TranslateEdition toTranslateEdition(String translate) {
        TranslateEdition edition = new TranslateEdition();
        edition.setIdentifier(this.identifier);
        edition.setLanguage(this.language);
        edition.setName(this.name);
        edition.setEnglishName(this.englishName);
        edition.setFormat(this.format);
        edition.setType(this.type);
        edition.setDirection(this.direction);

        edition.setText(translate);

        return edition;
    }

}
