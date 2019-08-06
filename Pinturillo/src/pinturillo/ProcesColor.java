/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pinturillo;

import java.awt.Color;

/**
 *
 * @author julian
 */
public class ProcesColor {

    private Color color;
    private int tipe;

    public ProcesColor(Color color) {
        this.color = color;
    }
    public ProcesColor(){}
    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getTipe() {
        if (Color.WHITE == color) {
            tipe = 0;
        } else if (Color.BLACK == color) {
            tipe = 1;
        } else if (Color.GREEN == color) {
            tipe = 2;
        } else if (Color.RED == color) {
            tipe = 3;
        } else if (Color.BLUE == color) {
            tipe = 4;
        } else if (Color.YELLOW == color) {
            tipe = 5;
        }

        return tipe;
    }

    public void setTipe(int tipe) {
        this.tipe = tipe;
    }

}
