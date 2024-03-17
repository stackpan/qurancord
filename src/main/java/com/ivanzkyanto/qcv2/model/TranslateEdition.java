package com.ivanzkyanto.qcv2.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class TranslateEdition extends Edition {

    private String text;

}
