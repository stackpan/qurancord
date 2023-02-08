package com.stackpan;

import java.net.URL;

public class App {

    public static final String CACHE_RESOURCE_PATH = System.getProperty("user.dir") + "/.res";

    public URL getResource(String file) {
        return this.getClass().getResource("/" + file);
    }

}
