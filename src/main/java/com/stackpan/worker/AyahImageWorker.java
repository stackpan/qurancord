package com.stackpan.worker;

import com.stackpan.entity.Ayah;
import com.stackpan.entity.Surah;
import com.stackpan.util.ImageGenerator;

public class AyahImageWorker extends Thread {

    private final Surah surah;
    private final Ayah ayah;

    private String resultPath;

    public AyahImageWorker(Surah surah, Ayah ayah) {
        this.surah = surah;
        this.ayah = ayah;
    }

    @Override
    public void run() {
        resultPath = ImageGenerator.generateAyah(surah, ayah);
    }

    public String getResultPath() {
        return resultPath;
    }
}
