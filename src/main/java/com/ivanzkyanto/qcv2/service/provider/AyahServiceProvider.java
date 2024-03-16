package com.ivanzkyanto.qcv2.service.provider;

import com.ivanzkyanto.qcv2.exception.AyahNotFoundException;
import com.ivanzkyanto.qcv2.fetcher.AyahFetcher;
import com.ivanzkyanto.qcv2.model.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@RequiredArgsConstructor
@Slf4j
public class AyahServiceProvider {

    @NonNull
    private AyahFetcher ayahFetcher;

    public AyahDetail random(SurahDetail surah) {
        Random random = new Random();
        int ayahNumber = random.nextInt(surah.getNumberOfAyahs()) + 1;

        return surah.getAyahs().stream()
                .filter(ayah -> ayah.getNumberInSurah().equals(ayahNumber))
                .findFirst()
                .map(ayah -> ayah.toDetail(surah))
                .orElse(null);
    }

//    public AyahDetailWithTranslate random(SurahDetail surah, String ayahTranslate) {
//        Random random = new Random();
//        int ayahNumber = random.nextInt(surah.getNumberOfAyahs()) + 1;
//
//        return surah.getAyahs().stream()
//                .filter(ayah -> ayah.getNumberInSurah().equals(ayahNumber))
//                .findFirst()
//                .map(ayah -> ayah.toDetailWithTranslate(surah, ayahTranslate))
//                .orElse(null);
//    }

    public AyahDetail get(AyahReference reference) throws AyahNotFoundException {
        ApiResponse<AyahDetail> response = ayahFetcher.get(reference);
        return response.getData();
    }

    public AyahDetailWithTranslate get(AyahReference reference, String verseEdition, String translateEdition) throws AyahNotFoundException {
        ApiResponse<AyahDetail[]> response = ayahFetcher.get(reference, verseEdition, translateEdition);

        AyahDetail verse = response.getData()[0];
        AyahDetail translate = response.getData()[1];
        TranslateEdition objectTranslateEdition = translate.getEdition().toTranslateEdition(translate.getText());
        return verse.toDetailWithTranslate(verse.getSurah(), verse.getEdition(), objectTranslateEdition);
    }

}
