package com.stackpan.qurancord.core.worker;

import com.stackpan.qurancord.core.entity.Ayah;
import com.stackpan.qurancord.core.entity.Surah;
import com.stackpan.qurancord.core.util.ImageGenerator;

public class AyahImageWorker extends Thread {

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
