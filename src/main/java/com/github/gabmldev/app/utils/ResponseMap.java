package com.github.gabmldev.app.utils;

import java.util.HashMap;
import java.util.Map;

public class ResponseMap {
    private final Map<String, Object> map = new HashMap<>();

    private ResponseMap() {}

    // --- Builder estático ---
    public static ResponseMap builder() {
        return new ResponseMap();
    }

    public ResponseMap put(String key, Object value) {
        this.map.put(key, value);
        return this;
    }

    public Map<String, Object> build() {
        return this.map;
    }
}
