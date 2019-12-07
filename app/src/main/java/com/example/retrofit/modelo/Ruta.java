package com.example.retrofit.modelo;

public class Ruta {
    private String inicio;
    private String Destino;
    private int duration;

    public Ruta(String inicio, String destino, int duration) {
        this.inicio = inicio;
        Destino = destino;
        this.duration = duration;
    }

    public String getInicio() {
        return inicio;
    }

    public void setInicio(String inicio) {
        this.inicio = inicio;
    }

    public String getDestino() {
        return Destino;
    }

    public void setDestino(String destino) {
        Destino = destino;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
