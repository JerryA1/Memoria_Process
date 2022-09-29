package com.memoria_process;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.TableCellRenderer;
import java.awt.Color;
import java.awt.Component;
import java.util.LinkedList;
import java.util.List;

public class Methods {
    static int contP = 0; // Contador de procesos
    static String[] rowfieldsTable; // Rows que se añadiran a la tabla principal(tableInfo)
    static int indice = 0; // Indice contador de rows en tabla principal(tableInfo)
    static int siseP; // Tamaño del proceso
    static int indicador = 0; // Indicador de entrada a memoria

    static int contPS = 0; // Contador de procesos finalizados
    static int procesoExit; // Id del proceso a terminar
    static String status; // Status del proceso: En memoria, En fila o Finalizado
    static Color c;
    static Color vacio = Colors.White;
    static int memory = ControllerIndex.memorySize;

    public static List<Proceso> listaFIla = new LinkedList<Proceso>();
    static panelContentMemory pcm = new panelContentMemory();
    static IndexGUI index = new IndexGUI();

    public static void Reserve(){
        siseP = Integer.parseInt(ControllerIndex.txtSizeP.getText());
        contP++;
        // Se añade el proceso al tabla solo como existente
        c = Colors.randomColor();
        rowfieldsTable = new String[]{Integer.toString(contP), Integer.toString(siseP), getActualTime(), "", "", String.valueOf(c)};
        ControllerIndex.modelData.addRow(rowfieldsTable);
        // Si el indicador = 0, no hay fila
        if (indicador == 0) {
            int hueco = checkList(ControllerIndex.listaControl, siseP);
            boolean res = (hueco != -1);
            if (res){
                int direcc = ControllerIndex.listaControl.get(hueco).getDireccion();
                Proceso process = new Proceso(1, direcc, siseP, contP, c);
                int espacioRestante = ControllerIndex.listaControl.get(hueco).getTamaño() - siseP;
                // Se inserta el proceso en lugar del hueco
                ControllerIndex.listaControl.set(hueco, process);
                // Si el proceso es más pequeño que el hueco, inserta un hueco
                if (espacioRestante > 0){
                    Proceso bloqueRestante = new Proceso(0, direcc + siseP, espacioRestante, 0, vacio);
                    ControllerIndex.listaControl.add(hueco + 1, bloqueRestante);
                }
                /* escribeMemoria(direcc, tamanyo, 1); */
                ControllerIndex.tableInfo.setValueAt("En memoria", indice, 4);
                ControllerIndex.TxtATerminal.append(" -> Proceso " + contP + " en memoria\n");
                memory -= siseP;
                paintProcess();
                imprimelista();
            }
            else {
                ControllerIndex.tableInfo.setValueAt("En fila", indice, 4);
                ControllerIndex.TxtATerminal.append(" -> Proceso " + contP + " en fila\n");
                indicador = 1;
                procesoAFila(siseP, contP, c);
                imprimelistaFila();
            }
        }
        else{
            ControllerIndex.tableInfo.setValueAt("En fila", indice, 4);
            ControllerIndex.TxtATerminal.append(" -> Proceso " + contP + " en fila\n");
            procesoAFila(siseP, contP, c);
            imprimelistaFila();
        }
        ControllerIndex.TxtATerminal.append(" -> Memoria " + memory + "\n");
        indice++;
    }

    public static void Libere(){
        // Se checa que los procesos que hayan salido sean menores o iguales a los que fueron creados
        if (contPS >= contP){
            JOptionPane.showMessageDialog(null, "No hay procesos en espera");
        } else{
            procesoExit = Integer.parseInt(ControllerIndex.txtNumberP.getText());
            if (procesoExit <= contP) {
                status = (String) ControllerIndex.tableInfo.getValueAt(procesoExit-1, 4);
                String add = (String) ControllerIndex.tableInfo.getValueAt(procesoExit - 1, 1);
                if (status.equals("En memoria")){
                    // Se busca el índice del proceso en la lista de control
                    int indice = 0;
                    //pid = procesoExit;
                    for (Proceso bloque : ControllerIndex.listaControl){
                        indice ++;
                        if (bloque.getPid() == procesoExit){
                            break;
                        }
                    }
                    indice --;
                    boolean found = (indice != -1);
                    // Si lo ha encontrado, lo borra de la lista de control
                    if (found){
                        Proceso blockDelete = ControllerIndex.listaControl.get(indice);
                        ControllerIndex.listaControl.get(indice).setEstado(0);
                        ControllerIndex.listaControl.get(indice).setPid(0);
                        ControllerIndex.listaControl.get(indice).setColorP(vacio);
                        /*escribeMemoria(bloqueABorrar[1], bloqueABorrar[2], 0);*/
                        fusiona(indice);
                        fusiona(indice - 1);
                    }
                    imprimelista();

                    ControllerIndex.tableInfo.setValueAt("Finalizado", procesoExit - 1, 4);
                    ControllerIndex.tableInfo.setValueAt(getActualTime(), procesoExit - 1, 3);
                    ControllerIndex.TxtATerminal.append(" -> Proceso " + procesoExit + " ha finalizdo con éxito\n");
                    memory += Integer.parseInt(add);
                    ControllerIndex.TxtATerminal.append(" -> Memoria " + memory + "\n");
                    paintProcess();
                    contPS++;
                } else{
                    JOptionPane.showMessageDialog(null, "El proceso no se puede finalizar o ya ha sido finalizado");
                }
            } else{
                JOptionPane.showMessageDialog(null, "Cuidado, este proceso no existe");
            }
        }

    }

    public static void LibereFiFo() {
        if (listaFIla.size() != 0) {
            int pid = listaFIla.get(0).getPid();
            int sisePFila = listaFIla.get(0).getTamaño();
            int hueco = checkList(ControllerIndex.listaControl, sisePFila);
            Color libereF = listaFIla.get(0).getColorP();
            creaProceso(ControllerIndex.listaControl, hueco, sisePFila, pid, libereF);
            imprimelistaFila();
        }
        else {
            indicador = 0;
            JOptionPane.showMessageDialog(null, "No quedan procesos en fila");
        }
        ControllerIndex.TxtATerminal.append(" -> Memoria " + memory + "\n");
    }

    public static void LibereMenor(){
        if (listaFIla.size() != 0){
            // Se ordenan los procesos en listaFila de menor a mayor tamaño, si dos procesos
            //tienen el mismo tamaño se emplea politica FIFO para ordenamiento
            Collections.sort(listaFIla, new ProcessComparator());
            int pid = listaFIla.get(0).getPid();
            int sizePFila = listaFIla.get(0).getTamaño();
            int hueco = checkList(ControllerIndex.listaControl, sizePFila);
            Color libereM = listaFIla.get(0).getColorP();
            creaProceso(ControllerIndex.listaControl, hueco, sizePFila, pid, libereM);
            imprimelistaFila();
        } else {
            indicador = 0;
            JOptionPane.showMessageDialog(null, "No quedan procesos en fila");
        }
        ControllerIndex.TxtATerminal.append(" -> Memoria " + memory + "\n");
    }

    public static String getActualTime() {
        Date dtFechaActual = new Date();
        DateFormat dfLocal = new SimpleDateFormat("HH:mm:ss");

        return dfLocal.format(dtFechaActual);
    }
/*
    static void pintarCol(){
        RenderColor col = new RenderColor(0);
        ControllerIndex.contentMemory.getColumnModel().getColumn(0).setCellRenderer(col);
        ControllerIndex.contentMemory.repaint();
    }*/

    /**
     * Implementación del método checkList.
     *
     * @param listaControl
     *            - Lista de control.
     * @param tamaño
     *            - Tamaño del hueco requerido.
     * @return Devuelve el índice del lugar en el que se encuentra el primer
     *         bloque libre con el que satisfacer la petición, o -1 en el caso
     *         de no haber ninguno disponible.
     */
    public static int checkList(List<Proceso> listaControl, int tamaño){
        int res = 0;
        for (Proceso bloque : listaControl){
            if (bloque.getEstado() == 0 && bloque.getTamaño() >= tamaño){
                break;
            }
            res++;
        }
        if (res == listaControl.size()){
            res = -1;
        }
        return res;
    }

    /**
     * Fusiona un bloque de memoria de la lista de control con su vecino
     * posterior, si ambos son bloques libres.
     *
     * @param indice
     *            - Posición en la lista de control del primer bloque de la
     *            fusión, siendo el segundo indice + 1.
     * @return Devuelve <b>true</b> en el caso de que ambos bloques tengan
     *         índices válidos, y estén vacíos, <b>false</b> en caso contrario.
     */
    private static boolean fusiona(int indice){
        boolean fusionable = false;
        if (indice >= 0 && (indice + 1) < ControllerIndex.listaControl.size()){
            fusionable = ControllerIndex.listaControl.get(indice).getEstado() == 0
                    && ControllerIndex.listaControl.get(indice + 1).getEstado() == 0;
        }
        if (fusionable) {
            int tamaño = ControllerIndex.listaControl.remove(indice + 1).getTamaño();
            int tamaño2 = ControllerIndex.listaControl.get(indice).getTamaño();
            ControllerIndex.listaControl.get(indice).setTamaño(tamaño + tamaño2);
        }
        return fusionable;
    }

    public static void imprimelista(){
        System.out.println("-------- Procesos en memoria --------");
        for (Proceso bloque : ControllerIndex.listaControl){
            System.out.println(bloque.getEstado()+", "+bloque.getDireccion()+", "+bloque.getTamaño()+", "+bloque.getPid());
        }
    }

    public static void imprimelistaFila(){
        System.out.println("-------- Procesos en fila --------");
        for (Proceso bloque : listaFIla){
            System.out.println(bloque.getEstado()+", "+bloque.getDireccion()+", "+bloque.getTamaño()+", "+bloque.getPid());
        }
    }

    public static void procesoAFila(int siseP, int contP, Color c){
        Proceso pAFila = new Proceso(0, 0, siseP, contP, c);
        listaFIla.add(pAFila);
    }

    /**
     * Asigna bloque de memoria a un nuevo proceso.
     * @param listaControl
     *         - Lista que lleva el control de los procesos en memoria.
     * @param hueco
     *         - Identificador numérico del bloque vacio en memoria.
     * @param sizePFila
     *         - Tamaño que necesita el proceso de la fila.
     * @param pid
     *         - Identificador numérico del proceso que se va a crear. El que
     *           la invoque tiene la responsabiidad de evitar que se repita.
     */
    public static void creaProceso(List<Proceso> listaControl, int hueco, int sizePFila, int pid, Color c){
        boolean res = (hueco != -1);
        if (res) {
            int direcc = listaControl.get(hueco).getDireccion();
            Proceso process = new Proceso(1, direcc, sizePFila, pid, c);
            int espacioRestante = listaControl.get(hueco).getTamaño() - sizePFila;
            // Se inserta el proceso en lugar del hueco
            listaControl.set(hueco, process);
            // Si el proceso es más pequeño que el hueco, inserta un hueco
            if (espacioRestante > 0) {
                Proceso bloqueRestante = new Proceso(0, direcc + sizePFila, espacioRestante, 0, vacio);
                listaControl.add(hueco + 1, bloqueRestante);
            }
            listaFIla.remove(0);
            ControllerIndex.tableInfo.setValueAt("En memoria", pid - 1, 4);
            ControllerIndex.TxtATerminal.append(" -> Proceso " + pid + " salio de fila, entro a memoria\n");
            // Se indica la memoria libre, inlcuyendo huecos vacios
            memory -= sizePFila;
        }
        else {
            ControllerIndex.TxtATerminal.append(" -> Proceso " + pid + " No encontro espacio en memoria\n");
        }
        // Si no hay mas procesos en fila se deja el indicador en 0(libre)
        if (listaFIla.size() == 0) indicador = 0;

    }

    public static void paintProcess(){
        pcm.repaint();
        pcm.validate();
        index.validate();
    }
}
