package io.github.stackpan.qurancord.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class AyahDetailWithTranslate extends AyahDetail {

    private TranslateEdition translate;

}
