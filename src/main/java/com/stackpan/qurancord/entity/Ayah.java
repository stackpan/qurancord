package com.stackpan.qurancord.entity;

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
