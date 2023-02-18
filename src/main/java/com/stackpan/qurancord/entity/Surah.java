package com.stackpan.qurancord.entity;

public record Surah(
        int number,
        String arabicName,
        String latinName,
        String latinNameRegex,
        int ayahCount,
        RevelationType revelationType,
        String meaning,
        String description,
        String audioUrl
)
{
}
