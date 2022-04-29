package clashRoyale;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.LinkedList;
import java.util.Random;

import javax.swing.JPanel;

public abstract class Jouable extends JPanel{
    public int pv;
    public int pvInit;
    public int x;
    public int y;
    public int dégats;
    public int portée;
    public boolean tir_aerien;
    public boolean tir_sol;
    public boolean aerien;
    public int freq;
    public int vitesse;
    public boolean which_player;  //true pour celui avec les commandes à gauche
    public boolean alive;
    static final int MAP_WIDTH = 1280;  //variables géométriques
    static final int CARD_WIDTH = 312;  //pour être en accord avec GamePanel
    static final int UNIT_SIZE = 8;
    Jouable cible;
    int x_card;
    int y_card=1500;  //IL Y A UNE ERREUR ICI, SORCIER IMMOBILE EST CACHE
    int elixir;
    int temps = 0;
    int pas = 1;
    public boolean recadre_proj;

    public Jouable(int vie, int VieInit, int x1, int y1, int damage, int p, boolean tir_air, boolean tir_sol, boolean en_air, int fréquence, boolean play) {
        int coefx = (int) x1/8;
        int coefy = (int) y1/8;
        int x2 = 8*coefx;
        int y2 = 8*coefy;
        pv = vie;
        pvInit = VieInit;
        x = x2 + CARD_WIDTH;
        y = y2;
        dégats = damage;
        portée = p;
        tir_aerien = tir_air;
        this.tir_sol = tir_sol;
        aerien = en_air;
        freq = fréquence;
        which_player = play;
        this.alive = true;
    }

    public void touché(Jouable j1) {
        this.pv = this.pv - j1.dégats;
        this.checkLife();
    }

    public void tir(Jouable j1) {
        if(this.alive) {
            if(this.tir_sol && !j1.aerien && this.temps % this.freq ==0) {
                j1.pv = j1.pv - this.dégats;
                j1.checkLife();
            }else if(this.tir_aerien && j1.aerien && this.temps % this.freq ==0){
                j1.pv = j1.pv - this.dégats;
                j1.checkLife();
            }
        }
    }

    public abstract void radar(LinkedList<Jouable> list_perso, LinkedList<Jouable> list_tours);
    //cette méthode indique où est le jouable le plus proche

    public abstract void brain();
    //cette méthode indique dans quel sens le perso doit bouger

    public boolean checkLife() {
        if(this.pv > 0) {
            return true;
        }else {
            return false;
        }
    }

    public void draw(Graphics g) {
        //elle sera en inversé pour la tour :
        //la tour est déjà dessinée sur la map donc il vaut dessiner sa trace quand elle meurt
    }

    public boolean checkLimits() {
        if(x <1280 && y <720) {
            return true;
        }else {
            return false;
        }
    }
    public abstract void meurt(LinkedList<Jouable> list_tours);

    public abstract void move();

    public abstract String toString();

    public abstract void draw_card(Graphics g);
}
