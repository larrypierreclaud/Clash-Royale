package clashRoyale;

import javax.swing.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.*;

public class Projectile {
    int xBdf;
    int yBdf;
    int vitesse;
    int degats;
    Jouable perso;
    boolean BouleDeFeu;
    int UNIT_SIZE = 6;
    int sens;
    private ImageIcon icoFond;
    private Image imgFond;
    private ImageIcon icoFond2;
    private Image imgFond2;
    public boolean fleche;


    public Projectile(int xBdf, int yBdf, int vitesse, boolean BouleDeFeu){
        this.xBdf=xBdf;
        this.yBdf=yBdf;
        this.vitesse=vitesse;
        this.BouleDeFeu=BouleDeFeu;
    }

    public void drawBdf(Graphics g){

        if(this.BouleDeFeu){
            //on dessine la boule de feu à partir du coeur
            g.setColor(Color.white);
            //centre
            g.fillRect(this.xBdf, this.yBdf, UNIT_SIZE, UNIT_SIZE);
            g.fillRect(this.xBdf +1*UNIT_SIZE, this.yBdf - 1*UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);
            // premier contour
            g.setColor(Color.orange);
            for (int i = -2; i < 0; i++ ) {
                g.fillRect(this.xBdf +i*UNIT_SIZE, this.yBdf, UNIT_SIZE, UNIT_SIZE);
            }
            for (int i = -1; i < 1; i++ ) {
                g.fillRect(this.xBdf +i*UNIT_SIZE, this.yBdf + 1*UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);
            }
            for (int i = -1; i < 1; i++ ) {
                g.fillRect(this.xBdf +i*UNIT_SIZE, this.yBdf - 1*UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);
            }
            for (int j = -3; j < -1; j++) {
                g.fillRect(this.xBdf +1*UNIT_SIZE, this.yBdf + j*UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);
            }
            for (int j = -2; j < 0; j++) {
                g.fillRect(this.xBdf +2*UNIT_SIZE, this.yBdf + j*UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);
            }
            g.fillRect(this.xBdf + 1*UNIT_SIZE, this.yBdf, UNIT_SIZE, UNIT_SIZE);
            // contour rouge
            g.setColor(Color.red);
            for (int i = -2; i < 2; i++ ) {
                g.fillRect(this.xBdf +i*UNIT_SIZE, this.yBdf + 2*UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);
            }
            for (int i = -3; i < 2; i = i+ 4 ) {
                g.fillRect(this.xBdf +i*UNIT_SIZE, this.yBdf + 1*UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);
                g.fillRect(this.xBdf +(i+1)*UNIT_SIZE, this.yBdf + 1*UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);
            }
            for (int i = 2; i < 4; i++ ) {
                g.fillRect(this.xBdf +i*UNIT_SIZE, this.yBdf, UNIT_SIZE, UNIT_SIZE);
            }
            g.fillRect(this.xBdf -3*UNIT_SIZE, this.yBdf, UNIT_SIZE, UNIT_SIZE);
            for (int i = -4; i < -2; i++ ) {
                g.fillRect(this.xBdf +i*UNIT_SIZE, this.yBdf -1*UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);
            }
            for (int j = -3; j < 0; j++) {
                g.fillRect(this.xBdf +3*UNIT_SIZE, this.yBdf + j*UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);
            }
            for (int i = -2; i < 1; i++ ) {
                g.fillRect(this.xBdf +i*UNIT_SIZE, this.yBdf -2*UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);
            }
            for (int j = -5; j < -2; j++) {
                g.fillRect(this.xBdf, this.yBdf + j*UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);
            }
            for (int i = 1; i < 3; i++ ) {
                g.fillRect(this.xBdf +i*UNIT_SIZE, this.yBdf -4*UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);
            }
            g.fillRect(this.xBdf +2*UNIT_SIZE, this.yBdf -3*UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);
            g.fillRect(this.xBdf -1*UNIT_SIZE, this.yBdf -5*UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);
            g.fillRect(this.xBdf -2*UNIT_SIZE, this.yBdf -4*UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);
            g.fillRect(this.xBdf -3*UNIT_SIZE, this.yBdf -5*UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);
            g.fillRect(this.xBdf -4*UNIT_SIZE, this.yBdf -3*UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);
        }
        else if(this.BouleDeFeu== false){
            if(fleche){
                icoFond = new ImageIcon(getClass().getResource("/images/fleche_droite.png"));
                this.imgFond = this.icoFond.getImage();
                g.drawImage(this.imgFond, xBdf, yBdf, 132, 157, null);
            }
            else if(!fleche){
                icoFond = new ImageIcon(getClass().getResource("/images/fleche_gauche.png"));
                this.imgFond = this.icoFond.getImage();
                g.drawImage(this.imgFond, xBdf, yBdf, 132, 157, null);
            }
        }
    }

    public void Brain(Jouable cible, Jouable perso){  //perso : le sorcier, celui qui tire, cible : vers qui va la boule de feu
        if(cible.x- perso.x<0){
            sens=1;
            this.xBdf=this.xBdf-vitesse;
            if(this.xBdf < cible.x) {
            	this.xBdf = cible.x;
            }
            fleche = false;
        }else if(cible.x- perso.x>0){
            sens=2;
            this.xBdf=this.xBdf+vitesse;
            if(this.xBdf > cible.x) {
            	this.xBdf = cible.x;
            }
            fleche = true;
        }

        if(cible.y- perso.y<0){
            sens=4;
            this.yBdf=this.yBdf-vitesse;
            if(this.yBdf < cible.y) {
            	this.yBdf = cible.y;
            }
        }else if(cible.y- perso.y>0){
            sens=3;
            this.yBdf=this.yBdf+vitesse;
            if(this.yBdf > cible.y) {
            	this.yBdf = cible.y;
            }
        }
        if(this.xBdf >= cible.x - 3*UNIT_SIZE && this.xBdf <= cible.x + 3*UNIT_SIZE && this.yBdf >= cible.y - 3*UNIT_SIZE && this.yBdf <= cible.y + 3*UNIT_SIZE) {
        	if(perso.recadre_proj) {
        		this.xBdf = perso.x+65;
            	this.yBdf = perso.y+65;
        	}else {
        		this.xBdf = perso.x;
        		this.yBdf = perso.y;
        	}
        }
        

    }


}


