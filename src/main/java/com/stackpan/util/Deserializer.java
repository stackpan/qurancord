package com.stackpan.util;

import com.google.gson.*;
import com.stackpan.entity.Ayah;
import com.stackpan.entity.RevelationType;
import com.stackpan.entity.Surah;

import java.util.List;

/**
 * Json deserializer for: <a href="https://equran.id/apidev">equran.id</a>
 */
public class Deserializer {

    public static List<JsonElement> parseGetSurah(String jsonString) {
        return JsonParser.parseString(jsonString)
                .getAsJsonArray()
                .asList();
    }

    public static JsonElement parseGetSurahDetail(String jsonString) {
        return JsonParser.parseString(jsonString);
    }

    public static List<JsonElement> parseGetAllAyahBySurah(String jsonString) {
        List<JsonElement> result = null;
        final String desiredKey = "ayat";

        var jsonElement = parseGetSurahDetail(jsonString);
        if (jsonElement.isJsonObject()) {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            if (jsonObject.get(desiredKey).isJsonArray()) result = jsonObject.getAsJsonArray("ayat").asList();
        }

        return result;
    }

    public static Surah parseElementSurah(JsonElement jsonElement) {
        Surah surah = null;

        if (jsonElement.isJsonObject()) {
            JsonObject surahObject = jsonElement.getAsJsonObject();
            surah = SurahBuilder.createBuilder()
                    .setNumber(surahObject.get("nomor").getAsInt())
                    .setArabicName(surahObject.get("nama").getAsString())
                    .setLatinName(surahObject.get("nama_latin").getAsString())
                    .setRevelationType(surahObject.get("tempat_turun").getAsString().equals("mekah") ?
                            RevelationType.MECCAN : RevelationType.MEDINAN)
                    .setMeaning(surahObject.get("arti").getAsString())
                    .setDescription(surahObject.get("deskripsi").getAsString())
                    .setAudioUrl(surahObject.get("audio").getAsString())
                    .build();
        }

        return surah;
    }

    public static Ayah parseElementAyah(JsonElement jsonElement) {
        Ayah ayah = null;

        if (jsonElement.isJsonObject()) {
            JsonObject ayahObject = jsonElement.getAsJsonObject();
            ayah = AyahBuilder.createBuilder()
                    .setId(ayahObject.get("id").getAsInt())
                    .setSurahNumber(ayahObject.get("surah").getAsInt())
                    .setNumber(ayahObject.get("nomor").getAsInt())
                    .setArabicText(ayahObject.get("ar").getAsString())
                    .setLatinText(ayahObject.get("tr").getAsString())
                    .setBahasaTranslate(ayahObject.get("idn").getAsString())
                    .build();
        }

        return ayah;
    }

}
