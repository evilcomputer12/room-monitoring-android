package com.example.airq;

public class lista {
    private String id;
    private String temp;
    private String hum;
    private String co2;
    private String pm25;
    private String vreme;

    public lista(){}
    public lista(String id, String temp, String hum, String co2, String pm25, String vreme){
        this.id = id;
        this.temp = temp;
        this.hum = hum;
        this.co2 = co2;
        this.pm25 = pm25;
        this.vreme = vreme;
    }
    public String getId() {
        return id;
    }

    public void setId(String title) {
        this.id = id;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getHum() {
        return hum;
    }

    public void setHum(String hum) {
        this.hum = hum;
    }

    public String getCo2() {
        return co2;
    }

    public void setCo2(String co2) {
        this.co2 = co2;
    }

    public String getPm25() {
        return pm25;
    }

    public void setPm25(String pm25) {
        this.pm25 = pm25;
    }

    public String getVreme() {
        return vreme;
    }

    public void setVreme(String vreme) {
        this.vreme = vreme;
    }

}
