package com.ivanzkyanto.qcv2.model;

import lombok.*;

@Data
@AllArgsConstructor
public class AyahReference implements AyahReferenceMaker {

    private Integer surahNumber;

    @NonNull
    private Integer ayahNumber;

    @Override
    public String make() {
        StringBuilder stringBuilder = new StringBuilder();

        if (surahNumber != null) {
            stringBuilder.append(surahNumber).append(":");
        }

        stringBuilder.append(ayahNumber);

        return stringBuilder.toString();
    }

}
