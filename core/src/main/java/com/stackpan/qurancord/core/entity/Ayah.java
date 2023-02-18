package com.stackpan.qurancord.core.entity;

public record Ayah(
        int id,
        int surahNumber,
        int number,
        String arabicText,
        String latinText,
        String bahasaTranslate
)
{

}
