package com.bingdou.core.model.live;

/**
 * Created by gaoshan on 16-11-25.
 */
public enum RecordType {

    CNC(1,"cnc"),UPYUN(2,"upyun"),CC(3,"cc");

    private int index;
    private String name;

    RecordType(int index, String name) {
        this.index = index;
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static RecordType getByIndex(int index) {
        for (RecordType recordType : RecordType.values()) {
            if (index == recordType.getIndex())
                return recordType;
        }
        return null;
    }

    public static RecordType getByName(String name) {
        for (RecordType recordType : RecordType.values()) {
            if (name.equals(recordType.getName()))
                return recordType;
        }
        return null;
    }
}
