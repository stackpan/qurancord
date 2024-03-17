package com.ivanzkyanto.qcv2.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class AyahDetailWithTranslate extends AyahDetail {

    private TranslateEdition translate;

}
