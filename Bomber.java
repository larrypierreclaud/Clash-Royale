package clashRoyale;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.LinkedList;
import java.util.Random;

public class Bomber extends Jouable {

    int explo = 200;
    LinkedList<Jouable> cible_zone; //liste des joueurs cibl s par les d gats de zone
    public int sens = 1;
    private ImageIcon icoFond;
    private Image imgFond;
    private ImageIcon icoFond2;
    private Image imgFond2;
    public int compt = 0; //pour un affichage correct des bombes

    Bomber(int x1,int y1){
        super(320,320,x1, y1,202,20*UNIT_SIZE,true,true,false, 1800, true);
        this.setPreferredSize(new Dimension(80,40)); //nombre total de carr s
        //this.setBackground(Color.white);
        icoFond = new ImageIcon(getClass().getResource("/images/bomber.png"));
        this.imgFond = this.icoFond.getImage();
        this.setFocusable(true);
        cible_zone = new LinkedList<Jouable>();
        this.elixir = 2;
    }

    public void radar(LinkedList<Jouable> list_perso, LinkedList<Jouable> list_tours) {
        //cette m thode d termine o  est le jouable le plus proche
        double r = 1800;
        //on commence par d terminer la tour la plus proche
        if (y < 360) {
            //si le perso est sur la partie haute du terrain
            if(list_tours.get(0).x != list_tours.get(2).x) {
                //si la petite tour est toujours en vie
                r = Math.sqrt(Math.pow(list_tours.get(0).x-this.x,2)+Math.pow(list_tours.get(0).y -this.y,2));
                cible = list_tours.get(0);
                cible_de_zone( list_perso, list_tours, cible);  //


            }else {
                r = Math.sqrt(Math.pow(list_tours.get(2).x-this.x,2)+Math.pow(list_tours.get(2).y -this.y,2));
                cible = list_tours.get(2);
                cible_de_zone(list_perso, list_tours, cible);
            }
        }else {
            //le perso est sur la partie basse du terrain
            if(list_tours.get(1).x != list_tours.get(2).x) {
                //si la petite tour est toujours en vie
                r = Math.sqrt(Math.pow(list_tours.get(1).x-this.x,2)+Math.pow(list_tours.get(1).y -this.y,2));
                cible = list_tours.get(1);
                cible_de_zone(list_perso, list_tours, cible);

            }else {
                r = Math.sqrt(Math.pow(list_tours.get(2).x-this.x,2)+Math.pow(list_tours.get(2).y -this.y,2));
                cible = list_tours.get(2);
                cible_de_zone(list_perso, list_tours, cible);

            }
        }
        //puis on d termine le joueur le plus proche s'il y en a un
        for(Jouable perso : list_perso) {
            double rp = Math.sqrt(Math.pow(perso.x -this.x,2)+Math.pow(perso.y -this.y,2));
            if(rp<=r) {
                r=rp;
                if(perso.pv > 0) {
                    cible = perso;
                    cible_de_zone(list_perso, list_tours, cible);
                }
            }
        }
    }

    public void cible_de_zone(LinkedList<Jouable> list_perso, LinkedList<Jouable> list_tours, Jouable cible){
        for(Jouable perso : list_perso) {
            double rayon = Math.sqrt(Math.pow(perso.x -cible.x,2)+Math.pow(perso.y -cible.y,2));
            if(rayon<=explo && rayon>0) {
                if(perso.pv > 0) {
                	if(!cible_zone.contains(perso)){
                        cible_zone.add(perso);
                    }
                }
            }
        }
        for(Jouable tour : list_tours) {
            double rayon = Math.sqrt(Math.pow(tour.x -cible.x,2)+Math.pow(tour.y -cible.y,2));
            if(rayon<=explo && rayon>0) {
                if(tour.pv > 0) {
                    if(!cible_zone.contains(tour)) {
                    	cible_zone.add(tour);
                    }
                }
            }
        }
    }


    public void brain() {
        //A PRIORI ELLE FONCTIONNE
        //cette m thode d finit dans quel sens le bomber doit bouger
        //sens = 1 : droite, sens =2 : gauche, sens = 3 : bas, sens = 4 : haut
        //d'abord il avance sur l'axe x, puis sur l'axe y, puis il se stoppe quand on est   la bonne port e
        double r = Math.sqrt(Math.pow(cible.x-this.x, 2)+Math.pow(cible.y-this.y, 2));
        System.out.println("le r vaut: "+ r);
        if(this.pas == 1){
            this.pas = 2;
        }else if(this.pas ==2){
            this.pas = 1;
        }
        if(r<=this.portée) {
            //le bomber est capable de tirer, donc il ne bouge plus
        	if(this.temps % this.freq ==0) {
        		this.pas = -1*(compt%2);
                compt++;
            }
            
            this.sens = 0;
            this.tir(cible);
            System.out.println(cible_zone.toString());
            for(Jouable cible2 : cible_zone) {
                this.tir(cible2);
            }


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
            this.x = this.x + 3*UNIT_SIZE/2;
            //this.sens =2;
        }else if(sens==2){
            this.x = this.x - 3*UNIT_SIZE/2;
            //this.sens =3;
        }else if(sens==3) {
            this.y = this.y + 3*UNIT_SIZE/2;
            //this.sens=4;
        }else if(sens==4) {
            this.y = this.y -3*UNIT_SIZE/2;
            //this.sens = 1;
        }
        System.out.println("je suis cens  avancer");
        System.out.println("le sens vaut: "+sens);
    }

    public void draw(Graphics g) {
        //dimensions de l'image : 880*1037
        if(this.pv >0) {
            if(which_player==true){
                if(this.pas == 0){ // attack image of player 1
                    icoFond = new ImageIcon(getClass().getResource("/images/bomberattack1.1.png"));
                    this.imgFond = this.icoFond.getImage();
                    g.drawImage(this.imgFond, x, y, 132, 157, null);
                }else if(this.pas == -1){ // walking image 1 of player 1
                    icoFond = new ImageIcon(getClass().getResource("/images/bomberattack1.2.png"));
                    this.imgFond = this.icoFond.getImage();
                    g.drawImage(this.imgFond, x, y, 132, 157, null);
                }else if(this.pas == 1){ // walking image 1 of player 1
                    icoFond = new ImageIcon(getClass().getResource("/images/bomber1.1.png"));
                    this.imgFond = this.icoFond.getImage();
                    g.drawImage(this.imgFond, x, y, 132, 157, null);
                }else{ //walking image 2 of player 1
                    icoFond = new ImageIcon(getClass().getResource("/images/bomber1.2.png"));
                    this.imgFond = this.icoFond.getImage();
                    g.drawImage(this.imgFond, x, y, 132, 157, null);
                }
            }else{
                if(this.pas == 0){ // attack image of player 2
                    icoFond = new ImageIcon(getClass().getResource("/images/bomberattack2.1.png"));
                    this.imgFond = this.icoFond.getImage();
                    g.drawImage(this.imgFond, x, y, 132, 157, null);
                }else if(this.pas == -1){ // walking image 1 of player 1
                    icoFond = new ImageIcon(getClass().getResource("/images/bomberattack2.2.png"));
                    this.imgFond = this.icoFond.getImage();
                    g.drawImage(this.imgFond, x, y, 132, 157, null);
                }else if(this.pas == 1){ // walking image 1 of player 2
                    icoFond = new ImageIcon(getClass().getResource("/images/bomber2.1.png"));
                    this.imgFond = this.icoFond.getImage();
                    g.drawImage(this.imgFond, x, y, 132, 157, null);
                }else{ //walking image 2 of player 2
                    icoFond = new ImageIcon(getClass().getResource("/images/bomber2.2.png"));
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
        return "je suis un bomber, mes coordon es : " + this.x +", " +this.y;
    }

    public void draw_card(Graphics g) {
        icoFond2 = new ImageIcon(getClass().getResource("/images/bomber_card.png"));
        this.imgFond2 = this.icoFond2.getImage();
        g.drawImage(this.imgFond2, x_card, y_card, 312, 180, null);
    }



}
