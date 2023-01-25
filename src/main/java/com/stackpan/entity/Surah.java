package com.stackpan.entity;



public record Surah(
        int number,
        String arabicName,
        String latinName,
        int ayahCount,
        RevelationType revelationType,
        String meaning,
        String description,
        String audioUrl
)
{
}
