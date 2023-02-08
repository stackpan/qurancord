package com.stackpan.worker;

import com.stackpan.entity.Ayah;
import com.stackpan.entity.Surah;
import com.stackpan.util.ImageGenerator;

public class AyahImageWorker implements Runnable {

    private final Surah surah;
    private final Ayah ayah;

    public AyahImageWorker(Surah surah, Ayah ayah) {
        this.surah = surah;
        this.ayah = ayah;
    }

    @Override
    public void run() {
        ImageGenerator.generateAyah(surah, ayah);
    }
}
