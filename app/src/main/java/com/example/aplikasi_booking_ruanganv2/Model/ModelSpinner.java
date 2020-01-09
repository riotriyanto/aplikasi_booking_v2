package com.example.aplikasi_booking_ruanganv2.Model;


public class ModelSpinner {
    String id, urairuangan;

    public ModelSpinner(String id, String urairuangan) {
        this.id = id;
        this.urairuangan = urairuangan;
    }

    public ModelSpinner() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrairuangan() {
        return urairuangan;
    }

    public void setUrairuangan(String urairuangan) {
        this.urairuangan = urairuangan;
    }
}

