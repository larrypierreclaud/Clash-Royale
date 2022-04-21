package clashRoyale;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;
import javax.swing.JPanel;

public class BarreElixir {
    // parametres: placement sur ecran, vitesse de rechargement (modulo), hcnagre nom variables ( int pv)
    int max;
    int xA;
    int yA;
    int hauteur;
    int largeur;
    boolean sym;
    int val;
    int UNIT_SIZE = 6;

    BarreElixir(int Max, int valeur, boolean sens, int x, int y, int h, int l ) {
        this.hauteur = h;
        this.largeur = l;
        this.max = Max;
        this.xA = x;
        this.sym = sens;
        this.yA = y;
        this.val = valeur;
    }

    public void drawBarreElixir (Graphics g, boolean symetrie) {
        if (symetrie) {
            g.setColor(Color.black);
            g.fillRect( this.xA,  this.yA , 1*UNIT_SIZE, (this.hauteur+1)*UNIT_SIZE );
            g.fillRect( this.xA + 1*UNIT_SIZE,  this.yA, this.largeur*UNIT_SIZE, 1*UNIT_SIZE);
            g.fillRect( this.xA + 1*UNIT_SIZE,  this.yA + this.hauteur*UNIT_SIZE, this.largeur*UNIT_SIZE, 1*UNIT_SIZE);
            g.fillRect( this.xA + (this.largeur+1)*UNIT_SIZE ,  this.yA, 1*UNIT_SIZE, (this.hauteur+1)*UNIT_SIZE);
            if (this.val != 0) {
                g.setColor(Color.MAGENTA);
                g.fillRect( this.xA + 1*UNIT_SIZE ,  this.yA +1*UNIT_SIZE , this.val*this.largeur*UNIT_SIZE/this.max, (this.hauteur-1)*UNIT_SIZE);
            }

        } else {
            g.setColor(Color.black);
            g.fillRect( this.xA,  this.yA , 1*UNIT_SIZE, (this.hauteur+1)*UNIT_SIZE );
            g.fillRect( this.xA + 1*UNIT_SIZE,  this.yA, this.largeur*UNIT_SIZE, 1*UNIT_SIZE);
            g.fillRect( this.xA + 1*UNIT_SIZE,  this.yA + this.hauteur*UNIT_SIZE, this.largeur*UNIT_SIZE, 1*UNIT_SIZE);
            g.fillRect( this.xA + (this.largeur+1)*UNIT_SIZE ,  this.yA, 1*UNIT_SIZE, (this.hauteur + 1)*UNIT_SIZE);
            if (this.val != 0) {
                g.setColor(Color.MAGENTA);
                g.fillRect( this.xA + this.largeur*UNIT_SIZE - this.val*this.largeur*UNIT_SIZE/this.max + 1*UNIT_SIZE ,  this.yA +1*UNIT_SIZE , this.val*this.largeur*UNIT_SIZE/this.max, (this.hauteur-1)*UNIT_SIZE);
            }
        }
    }

}

