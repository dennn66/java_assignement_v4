package com.dennn66.gwt.client;

import com.google.gwt.storage.client.Storage;

public class Utils {
    public static String getToken() {
        return Storage.getLocalStorageIfSupported().getItem("jwt");
    }

    public static void saveToken(String token) {
        Storage.getLocalStorageIfSupported().setItem("jwt", "Bearer " +  token);
    }
    public static void removeToken() {
        Storage.getLocalStorageIfSupported().removeItem("jwt");
    }
}
