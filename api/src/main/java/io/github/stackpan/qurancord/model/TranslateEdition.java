package io.github.stackpan.qurancord.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class TranslateEdition extends Edition {

    private String text;

}
