package clashRoyale;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.LinkedList;
import java.util.Random;

public class Fleches extends Jouable {
    /* pas de defense, que de l'attaque
    pas de personnage qui se déplace
    définir position où jouer
    définir opposants dans la zone ie rayon de fleches et leur enlever
     */
    //int UNIT_SIZE = 9;
    int explo = 600;
    LinkedList<Jouable> cible_zone; //liste des joueurs ciblés par les dégats de zone
    public int sens = 0;
    private ImageIcon icoFond;
    private Image imgFond;
    private ImageIcon icoFond2;
    private Image imgFond2;
    int trig = 1;

    Fleches(int x1,int y1){
        super(1,1,x1, y1,100,500,true,true, true, 1000, true);
        this.setPreferredSize(new Dimension(80,40)); //nombre total de carrés
        //this.setBackground(Color.white);
        //icoFond = new ImageIcon(getClass().getResource(""));
        //this.imgFond = this.icoFond.getImage();
        this.setFocusable(true);
        this.elixir = 3;
    }

    public void cible_de_zone(LinkedList<Jouable> list_perso, LinkedList<Jouable> list_tours, Jouable cible){
        for(Jouable perso : list_perso) {
            double rayon = Math.sqrt(Math.pow(perso.x -this.x,2)+Math.pow(perso.y -this.y,2));
            if(rayon<=explo) {
                if(perso.pv > 0) {
                	if(perso.pv > 0) {
                        cible_zone.add(perso);
                    }
                }
            }
        }
        /*for(Jouable tour : list_tours) {
            double rayon = Math.sqrt(Math.pow(tour.x -cible.x,2)+Math.pow(tour.y -cible.y,2));
            if(rayon<=explo && rayon>0) {
                if(tour.pv > 0) {
                    cible_zone.add(tour);
                }
            }
        }*/
    }

    public void brain(){
        //dans quel sens doit bouger mais les fleches ne bougent pas...
        //sens = 1 : droite, sens =2 : gauche, sens = 3 : bas, sens = 4 : haut
        //d'abord il avance sur l'axe x, puis sur l'axe y, puis il se stoppe quand on est à la bonne portée
        if(cible_zone != null) {
        	for(Jouable cible2 : cible_zone) {
                this.tir(cible2);
            }
        }
        if(this.temps == 500) {
        	this.trig = 0;
        }
        this.pv=0;
    }

    @Override
    public void radar(LinkedList<Jouable> list_perso, LinkedList<Jouable> list_tours) {
    		cible_de_zone(list_perso,list_tours,this);
    		System.out.println("okay I'm radar for arrows");
    }


    public void draw(Graphics g) {
        //dimensions de l'image : 880*1037
    
        if(this.trig  == 1) {
        	icoFond = new ImageIcon(getClass().getResource("/images/fleches.png"));
            this.imgFond = this.icoFond.getImage();
            g.drawImage(this.imgFond, x, y, 132, 157, null);
        }

    }

    public void draw_card(Graphics g) {
        icoFond2 = new ImageIcon(getClass().getResource("/images/fleches_card.png"));
        this.imgFond2 = this.icoFond2.getImage();
        g.drawImage(this.imgFond2, x_card, y_card, 312, 180, null);
    }

    public void meurt(LinkedList<Jouable> list_tours) {
        this.x = list_tours.get(2).x;
        this.y = list_tours.get(2).y;
        this.alive = false;
    }

    @Override
    public void move() {
    	System.out.println("okay je move les flèches");
    }

    public String toString() {
        return "je suis les fleches, mes coordonnées : " + this.x +", " +this.y;
    }

}

