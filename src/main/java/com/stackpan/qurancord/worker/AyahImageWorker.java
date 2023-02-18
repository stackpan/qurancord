package com.stackpan.qurancord.worker;

import com.stackpan.qurancord.entity.Ayah;
import com.stackpan.qurancord.entity.Surah;
import com.stackpan.qurancord.util.ImageGenerator;

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
