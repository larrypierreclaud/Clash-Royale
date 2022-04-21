package clashRoyale;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.LinkedList;
import java.util.Random;

public class Valkyrie extends Jouable {

    int explo = 250;  //ici c'est plus le rayon de la valkyrie et pas de l'explosion.
    LinkedList<Jouable> cible_zone; //liste des joueurs ciblés par les dégats de zone
    public int sens = 1;
    private ImageIcon icoFond;
    private Image imgFond;
    private ImageIcon icoFond2;
    private Image imgFond2;

    Valkyrie(int x1,int y1){
        super(2300,2300,x1, y1,215,5*UNIT_SIZE,false,true, false, 1500, true);
        this.setPreferredSize(new Dimension(80,40)); //nombre total de carrés
        //this.setBackground(Color.white);
        icoFond = new ImageIcon(getClass().getResource("/images/valkyrie.png"));
        this.imgFond = this.icoFond.getImage();
        this.setFocusable(true);
        cible_zone = new LinkedList<Jouable>();
        this.elixir = 4;
    }

    public void radar(LinkedList<Jouable> list_perso, LinkedList<Jouable> list_tours) {
        //cette méthode détermine où est le jouable le plus proche
        double r = 1800;
        //on commence par déterminer la tour la plus proche
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
        //puis on détermine le joueur le plus proche s'il y en a un
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
        //ici la différence c'est que les dégats de zone se font avec la valkyrie pour origine et pas la cible de base
        //mais du coup le paramètre "cible" ne sert plus à rien (essayer de voir pour l'enlever)
        for(Jouable perso : list_perso) {
            double rayon = Math.sqrt(Math.pow(perso.x -this.x,2)+Math.pow(perso.y -this.y,2));
            if(rayon<=explo && rayon>0) {
                if(perso.pv > 0) {
                    cible_zone.add(perso);
                }
            }
        }
        for(Jouable tour : list_tours) {
            double rayon = Math.sqrt(Math.pow(tour.x -this.x,2)+Math.pow(tour.y -this.y,2));
            if(rayon<=explo && rayon>0) {
                if(tour.pv > 0) {
                    cible_zone.add(tour);
                }
            }
        }
    }


    public void brain() {
        //A PRIORI ELLE FONCTIONNE
        //cette méthode définit dans quel sens le bomber doit bouger
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
            //le bomber est capable de tirer, donc il ne bouge plus
            this.sens = 0;
            this.pas = 0;
            this.tir(cible);
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
            this.x = this.x + UNIT_SIZE;
            //this.sens =2;
        }else if(sens==2){
            this.x = this.x - UNIT_SIZE;
            //this.sens =3;
        }else if(sens==3) {
            this.y = this.y + UNIT_SIZE;
            //this.sens=4;
        }else if(sens==4) {
            this.y = this.y - UNIT_SIZE;
            //this.sens = 1;
        }
        System.out.println("je suis censé avancer");
        System.out.println("le sens vaut: "+sens);
    }

    public void draw(Graphics g) {
        //dimensions de l'image : 880*1037
        if(this.pv >0) {
            if(which_player==true){
                if(this.pas == 0){ // attack image of player 1
                    icoFond = new ImageIcon(getClass().getResource("/images/valkyrieattack1.png"));
                    this.imgFond = this.icoFond.getImage();
                    g.drawImage(this.imgFond, x, y, 132, 157, null);
                }else if(this.pas == 1){ // walking image 1 of player 1
                    icoFond = new ImageIcon(getClass().getResource("/images/valkyrie1.1.png"));
                    this.imgFond = this.icoFond.getImage();
                    g.drawImage(this.imgFond, x, y, 132, 157, null);
                }else{ //walking image 2 of player 1
                    icoFond = new ImageIcon(getClass().getResource("/images/valkyrie1.2.png"));
                    this.imgFond = this.icoFond.getImage();
                    g.drawImage(this.imgFond, x, y, 132, 157, null);
                }
            }else{
                if(this.pas == 0){ // attack image of player 2
                    icoFond = new ImageIcon(getClass().getResource("/images/valkyrieattack2.png"));
                    this.imgFond = this.icoFond.getImage();
                    g.drawImage(this.imgFond, x, y, 132, 157, null);
                }else if(this.pas == 1){ // walking image 1 of player 2
                    icoFond = new ImageIcon(getClass().getResource("/images/valkyrie2.1.png"));
                    this.imgFond = this.icoFond.getImage();
                    g.drawImage(this.imgFond, x, y, 132, 157, null);
                }else{ //walking image 2 of player 2
                    icoFond = new ImageIcon(getClass().getResource("/images/valkyrie2.2.png"));
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
        return "je suis une valkyrie, mes coordonées : " + this.x +", " +this.y;
    }

    public void draw_card(Graphics g) {
        icoFond2 = new ImageIcon(getClass().getResource("/images/valkyrie_card.png"));
        this.imgFond2 = this.icoFond2.getImage();
        g.drawImage(this.imgFond2, x_card, y_card, 312, 180, null);
    }


}


