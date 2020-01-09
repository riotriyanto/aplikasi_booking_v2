package com.example.aplikasi_booking_ruanganv2.Model;

public class ModelData {
    String ruangan, id, jam, tgl;

    public ModelData(){

    }

    public ModelData(String ruangan, String id, String jam, String tgl) {
        this.ruangan = ruangan;
        this.id = id;
        this.jam = jam;
        this.tgl = tgl;
    }

    public String getRuangan() {
        return ruangan;
    }

    public void setRuangan(String ruangan) {
        this.ruangan = ruangan;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJam() {
        return jam;
    }

    public void setJam(String jam) {
        this.jam = jam;
    }

    public String getTgl() {
        return tgl;
    }

    public void setTgl(String tgl) {
        this.tgl = tgl;
    }
}
