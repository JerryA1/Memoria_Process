package com.memoria_process;

import java.awt.Color;

public class Proceso {
    int estado;    // Estado (0 = libre,1 = ocupado)
    int direccion; // Dirección inicial del bloque
    int tamaño;    // Tamaño (En ticks)
    int pid;       // Id del proceso
    Color colorP;  // Color estatico del proceso

    public Proceso(int estado, int direccion, int tamaño, int pid, Color colorP) {
        this.estado = estado;
        this.direccion = direccion;
        this.tamaño = tamaño;
        this.pid = pid;
        this.colorP = colorP;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getDireccion() {
        return direccion;
    }

    public void setDireccion(int direccion) {
        this.direccion = direccion;
    }

    public int getTamaño() {
        return tamaño;
    }

    public void setTamaño(int tamaño) {
        this.tamaño = tamaño;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public Color getColorP() {
        return colorP;
    }

    public void setColorP(Color colorP) {
        this.colorP = colorP;
    }
}
