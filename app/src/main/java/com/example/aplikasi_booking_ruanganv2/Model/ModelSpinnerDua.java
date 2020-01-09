package com.example.aplikasi_booking_ruanganv2.Model;

public class ModelSpinnerDua{
    String id, urai_jam;
    public ModelSpinnerDua() {

    }

    public ModelSpinnerDua(String id, String urai_jam) {
        this.id = id;
        this.urai_jam = urai_jam;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrai_jam() {
        return urai_jam;
    }

    public void setUrai_jam(String urai_jam) {
        this.urai_jam = urai_jam;
    }
}
