package com.ivanzkyanto.qcv2.util;

import com.ivanzkyanto.qcv2.fetcher.AyahFetcher;
import com.ivanzkyanto.qcv2.model.ApiResponse;
import com.ivanzkyanto.qcv2.model.AyahReference;
import com.ivanzkyanto.qcv2.model.Edition;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class AyahImageGeneratorTest {

    @Autowired
    private AyahFetcher ayahFetcher;

    @Autowired
    private RestTemplate restTemplate;

    @Test
    void generate() throws IOException {
        var verseEdition = "quran-simple-enhanced";
        var translateEdition = "id.indonesian";

        var response = ayahFetcher.get(new AyahReference(2, 255), verseEdition, translateEdition);
        var ayahs = Stream.of(response.getData())
                .collect(Collectors.toMap(
                        ayahDetail -> ayahDetail.getEdition().getIdentifier(),
                        ayahDetail -> ayahDetail
                ));

        var verse = ayahs.get(verseEdition).getText();
        var translate = ayahs.get(translateEdition).getText();
        var surahName = ayahs.get(translateEdition).getSurah().getEnglishName();
        var surahNumber = ayahs.get(verseEdition).getSurah().getNumber();
        var ayahNumber = ayahs.get(verseEdition).getNumberInSurah();

        var image = AyahImageRendererKt.render(verse, translate, surahName, ayahNumber);

        var fileName = "ayah:ed:%s_tl:%s_ref:%d:%d".formatted(verseEdition, translateEdition, surahNumber, ayahNumber);
        var file = new File("storage/" + fileName + ".png");
        ImageIO.write(image, "png", file);

        assertTrue(file.exists());
    }

    @Test
    void generateManyEditions() {
        var type = new ParameterizedTypeReference<ApiResponse<Edition[]>>() {
        };
        var getQuranEditionsResponse = restTemplate.exchange("/v1/edition?type=quran", HttpMethod.GET, null, type);
        var verseEditions = Stream.of(Objects.requireNonNull(getQuranEditionsResponse.getBody()).getData())
                .map(Edition::getIdentifier).toList();
        var translatedEdition = "id.indonesian";

        List<String> editionsRequest = new ArrayList<>(verseEditions);
        editionsRequest.add(translatedEdition);

        var response = ayahFetcher.get(new AyahReference(2, 255), editionsRequest.toArray(new String[0]));
        var splitAyahDetails = Stream.of(response.getData()).collect(Collectors.partitioningBy(ayahDetail -> !ayahDetail.getEdition().getIdentifier().equals("id.indonesian")));
        var verseEditionAyahDetails = splitAyahDetails.get(true);
        var translateEditionAyahDetail = splitAyahDetails.get(false).get(0);

        verseEditionAyahDetails.forEach(ayahDetail -> {
            var verse = ayahDetail.getText();
            var translate = translateEditionAyahDetail.getText();
            var surahName = translateEditionAyahDetail.getSurah().getEnglishName();
            var surahNumber = translateEditionAyahDetail.getNumber();
            var ayahNumber = translateEditionAyahDetail.getNumberInSurah();

            var image = AyahImageRendererKt.render(verse, translate, surahName, ayahNumber);

            var verseEditionIdentifier = ayahDetail.getEdition().getIdentifier();
            var translateEditionIdentifier = translateEditionAyahDetail.getEdition().getIdentifier();
            var fileName = "ayah:ed:%s_tl:%s_ref:%d:%d".formatted(verseEditionIdentifier, translateEditionIdentifier, surahNumber, ayahNumber);
            var file = new File("storage/" + fileName + ".png");
            try {
                ImageIO.write(image, "png", file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            assertTrue(file.exists());
        });
    }
}
