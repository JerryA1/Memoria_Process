package com.memoria_process;

import java.util.Comparator;

public class ProcessComparator implements Comparator<Proceso> {
    @Override
    public int compare(Proceso o1, Proceso o2) {
        return Integer.valueOf(o1.getTamaño()).compareTo(o2.getTamaño());
    }
}
