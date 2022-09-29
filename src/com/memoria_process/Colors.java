package com.memoria_process;

import java.awt.Color;
import java.util.Random;

public class Colors extends Random{
    static Color Indigo500 = new Color(63, 81, 181 );
    static Color Red900 = new Color(183, 28, 28);
    static Color Pink500 = new Color(233, 30, 99);
    static Color Purple600 = new Color(142, 36, 170);
    static Color DeepPurple900 = new Color(49, 27, 146);
    static Color Blue600 = new Color(30, 136, 229);
    static Color Cyan500 = new Color(0, 188, 212);
    static Color Teal500 = new Color(0, 150, 136);
    static Color Green800 = new Color(46, 125, 50);
    static Color LightGreen800 = new Color(85, 139, 47);
    static Color Lime500 = new Color(205, 220, 57);
    static Color Yellow600 = new Color(253, 216, 53);
    static Color Amber600 = new Color(255, 179, 0);
    static Color Orange600 = new Color(251, 140, 0);
    static Color DeepOrange600 = new Color(244, 81, 30);
    static Color Brown700 = new Color(93, 64, 55);
    static Color Grey700 = new Color(97, 97, 97);
    static Color BlueGrey500 = new Color(96, 125, 139);
    static Color Black = new Color(42,42,42);
    static Color White = new Color(255, 255, 255);
    static Color BlueR = new  Color(38,40, 55);
    static Color CyanA700 = new Color(0, 184, 212);
    static Color LightBlueA700 = new Color(0, 145, 234);
    static Color BlueA700 = new Color(41, 98, 255);
    static int i;
    static int maximum = 18;
    static int minimum = 0;
    static Color c[] = {Indigo500, Red900, Pink500, Purple600, DeepPurple900, Blue600, Cyan500, Teal500, Green800, LightGreen800, Lime500, Yellow600, Amber600, Orange600,
    DeepOrange600, Brown700, Grey700, BlueGrey500, Black};

    public static Color randomColor(){
        Color colorSeccion;

        /* * returns random integer between minimum and maximum range */
         i = ((int) (Math.random()*(maximum - minimum))) + minimum;
         colorSeccion = c[i];

        return colorSeccion;
    }
}
