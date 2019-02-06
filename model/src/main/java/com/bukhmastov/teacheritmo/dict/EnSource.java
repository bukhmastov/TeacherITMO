package com.bukhmastov.teacheritmo.dict;

public enum EnSource {
    EXTERNAL(0),
    SYSTEM(1),
    ;

    EnSource(int id) {
        this.id = id;
    }

    private int id;

    public int getId() {
        return id;
    }

    public static EnSource byId(int id) {
        for (EnSource source : values()) {
            if (id == source.getId()) {
                return source;
            }
        }
        return null;
    }
}
