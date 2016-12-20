package com.bingdou.cdn.response;

import java.util.List;

/**
 * Created by gaoshan on 16/12/13.
 */
public class ViewerCountResponse {

    private int count = 0;
    private int retcode = 0;
    private String rettime = "";
    private List<DataValue> dataValue;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getRetcode() {
        return retcode;
    }

    public void setRetcode(int retcode) {
        this.retcode = retcode;
    }

    public String getRettime() {
        return rettime;
    }

    public void setRettime(String rettime) {
        this.rettime = rettime;
    }

    public List<DataValue> getDataValue() {
        return dataValue;
    }

    public void setDataValue(List<DataValue> dataValue) {
        this.dataValue = dataValue;
    }

    public class DataValue {
        private String prog;
        private String time;
        private int value;

        public String getProg() {
            return prog;
        }

        public void setProg(String prog) {
            this.prog = prog;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }
}
