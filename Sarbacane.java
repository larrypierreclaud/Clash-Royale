package clashRoyale;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.LinkedList;
import java.util.Random;

public class Sarbacane extends Jouable {

    public int sens = 1;
    private ImageIcon icoFond;
    private Image imgFond;
    private ImageIcon icoFond2;
    private Image imgFond2;

    Sarbacane(int x1,int y1){
        super(220,220,x1, y1,110,40*UNIT_SIZE,true,true, false, 700, true);
        this.setPreferredSize(new Dimension(80,40)); //nombre total de carrés
        //this.setBackground(Color.white);
        this.setFocusable(true);
        this.elixir = 3;
    }

    public void radar(LinkedList<Jouable> list_perso, LinkedList<Jouable> list_tours) {
        //cette méthode détermine où est le jouable le plus proche
        double r = 2000;
        //on commence par déterminer la tour la plus proche
        if (y < 360) {
            //si le perso est sur la partie haute du terrain
            if(list_tours.get(0).x != list_tours.get(2).x) {
                //si la petite tour est toujours en vie
                r = Math.sqrt(Math.pow(list_tours.get(0).x-this.x,2)+Math.pow(list_tours.get(0).y -this.y,2));
                cible = list_tours.get(0);
            }else {
                r = Math.sqrt(Math.pow(list_tours.get(2).x-this.x,2)+Math.pow(list_tours.get(2).y -this.y,2));
                cible = list_tours.get(2);
            }
        }else {
            //le perso est sur la partie basse du terrain
            if(list_tours.get(1).x != list_tours.get(2).x) {
                //si la petite tour est toujours en vie
                r = Math.sqrt(Math.pow(list_tours.get(1).x-this.x,2)+Math.pow(list_tours.get(1).y -this.y,2));
                cible = list_tours.get(1);
            }else {
                r = Math.sqrt(Math.pow(list_tours.get(2).x-this.x,2)+Math.pow(list_tours.get(2).y -this.y,2));
                cible = list_tours.get(2);
            }
        }
        //puis on détermine le joueur le plus proche si il y en a un
        for(Jouable perso : list_perso) {
            double rp = Math.sqrt(Math.pow(perso.x -this.x,2)+Math.pow(perso.y -this.y,2));
            if(rp<=r) {
                r=rp;
                if(perso.pv > 0) {
                    cible = perso;
                }
            }
        }
    }


    public void brain() {
        //A PRIORI ELLE FONCTIONNE
        //cette méthode définit dans quel sens le sorcier doit bouger
        //sens = 1 : droite, sens =2 : gauche, sens = 3 : bas, sens = 4 : haut
        //d'abord il avance sur l'axe x, puis sur l'axe y, puis il se stoppe quand on est à la bonne portée
        double r = Math.sqrt(Math.pow(cible.x-this.x, 2)+Math.pow(cible.y-this.y, 2));
        System.out.println("le r vaut: "+ r);
        if(this.pas == 1){
            this.pas = 2;
        }else{
            this.pas = 1;
        }
        if(r<=this.portée) {
            //le sorcier est capable de tirer, donc il ne bouge plus
            this.sens = 0;
            this.pas = 0;
            this.tir(cible);
        }else if(this.x >= cible.x - 3*UNIT_SIZE && this.x <= cible.x + 3*UNIT_SIZE) {
        	if(cible.y - this.y >0) {
                this.sens = 3;
            }else {
                this.sens = 4;
            }
        }else {
        	if(cible.x - this.x > 0) {
                this.sens = 1;
            }else {
                this.sens = 2;
            }
        }
    }


    public void move() {
        if(sens==1) {
            this.x = this.x + 2*UNIT_SIZE;
            //this.sens =2;
        }else if(sens==2){
            this.x = this.x - 2*UNIT_SIZE;
            //this.sens =3;
        }else if(sens==3) {
            this.y = this.y + 2*UNIT_SIZE;
            //this.sens=4;
        }else if(sens==4) {
            this.y = this.y -2*UNIT_SIZE;
            //this.sens = 1;
        }
        System.out.println("je suis censé avancer");
        System.out.println("le sens vaut: "+sens);
    }

    public void draw(Graphics g) {
        //dimensions de l'image : 880*1037
        if(this.pv >0) {
            if(which_player==true){
                if(this.pas == 1){ // walking image 1 of player 1
                    icoFond = new ImageIcon(getClass().getResource("/images/sarbacane1.1.png"));
                    this.imgFond = this.icoFond.getImage();
                    g.drawImage(this.imgFond, x, y, 132, 157, null);
                }else{ //walking image 2 of player 1
                    icoFond = new ImageIcon(getClass().getResource("/images/sarbacane1.2.png"));
                    this.imgFond = this.icoFond.getImage();
                    g.drawImage(this.imgFond, x, y, 132, 157, null);
                }
            }else{
                if(this.pas == 1){ // walking image 1 of player 2
                    icoFond = new ImageIcon(getClass().getResource("/images/sarbacane2.1.png"));
                    this.imgFond = this.icoFond.getImage();
                    g.drawImage(this.imgFond, x, y, 132, 157, null);
                }else{ //walking image 2 of player 2
                    icoFond = new ImageIcon(getClass().getResource("/images/sarbacane2.2.png"));
                    this.imgFond = this.icoFond.getImage();
                    g.drawImage(this.imgFond, x, y, 132, 157, null);
                }
            }
            //gestion de la barre de vie
            g.setColor(Color.black);
            g.fillRect( this.x,  this.y-10 , 127, 10);
            if(this.which_player==true){
                g.setColor(Color.blue);
            } else{ g.setColor(Color.red);}
            g.fillRect( this.x+1 ,  this.y-9 , (125*this.pv)/this.pvInit, 8);

        }
    }

    public void meurt(LinkedList<Jouable> list_tours) {
        this.x = list_tours.get(2).x;
        this.y = list_tours.get(2).y;
        this.alive = false;
    }

    public String toString() {
        return "je suis un sorcier, mes coordonées : " + this.x +", " +this.y;
    }

    public void draw_card(Graphics g) {
        icoFond2 = new ImageIcon(getClass().getResource("/images/sarbacane_card.png"));
        this.imgFond2 = this.icoFond2.getImage();
        g.drawImage(this.imgFond2, x_card, y_card, 312, 180, null);
    }

    public void cible_de_zone(LinkedList<Jouable> list_perso, LinkedList<Jouable> list_tours,Jouable cible){

    }


}


