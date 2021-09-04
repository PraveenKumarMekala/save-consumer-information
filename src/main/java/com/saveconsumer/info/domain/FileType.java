package com.saveconsumer.info.domain;

public enum FileType {
    CSV("CSV"),
    XML("XML");

    public final String label;

    private FileType(String label) {
        this.label = label;
    }

}
