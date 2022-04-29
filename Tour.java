package clashRoyale;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.LinkedList;

import javax.swing.ImageIcon;

public class Tour extends Jouable {
	int[] basic_coords = new int[2]; //permet de garder en mémoire la position de la tour avant qu'elle meurt
	private ImageIcon icoFond;
	private Image imgFond;
	public Projectile Bdf = new Projectile(100,100,3*UNIT_SIZE/2,false); //int xBdf, int yBdf, int vitesse, boolean BouleDeFeu
	int sens=1;
	
	Tour(boolean principale, boolean player, boolean haut){
		super(3000,3000,10,10,50,150,true,true,false,500, true);  //int vie, int VieInit, int x1, int y1, int damage, int p, boolean tir_air, boolean tir_sol, boolean en_air, int fréquence, boolean play
		if(principale) {
			this.pv = 4000;
			this.pvInit = 4000;
			if(player) {  //true pour celui avec les commandes à gauche
				x = UNIT_SIZE*20+CARD_WIDTH;
				y = UNIT_SIZE*40;
				basic_coords[0] = x;
				basic_coords[1] = y;
			}else {
				x = 130*UNIT_SIZE+CARD_WIDTH;
				y = 40*UNIT_SIZE;
				basic_coords[0] = x;
				basic_coords[1] = y;
			}
		}else {
			if(player) {
				if(haut) {
					x=30*UNIT_SIZE+CARD_WIDTH;
					y=13*UNIT_SIZE;
					basic_coords[0] = x;
					basic_coords[1] = y;
				}else {
					x=30*UNIT_SIZE+CARD_WIDTH;  //PARFAIT
					y=60*UNIT_SIZE;
					basic_coords[0] = x;
					basic_coords[1] = y;
				}
			}else {
				if(haut) {
					x=120*UNIT_SIZE+CARD_WIDTH;
					y=13*UNIT_SIZE;
					basic_coords[0] = x;
					basic_coords[1] = y;
				}else {
					x=120*UNIT_SIZE+CARD_WIDTH;
					y=60*UNIT_SIZE;
					basic_coords[0] = x;
					basic_coords[1] = y;
				}
			}
		}
		Bdf.xBdf = this.x;
		Bdf.yBdf = this.y;
		recadre_proj = false;
	}
	
	public void move() {
		//constitue juste un déplacement des coordonnées vers la tour principale quand la tour est morte
		this.x = 1200;
		this.y = 350;
	}
	
	public void draw(Graphics g) {
		//barres de vie
		g.setColor(Color.black);
        g.fillRect( this.x,  this.y-10 , 127, 10);
        if(this.which_player==true){
            g.setColor(Color.blue);
        } else{ g.setColor(Color.red);}
        g.fillRect( this.x+1 ,  this.y-9 , (125*this.pv)/this.pvInit, 8);
      //en inversé car on représente que les restes de la tour
		if(this.pv <= 0) {
			//g.fillRect(basic_coords[0], basic_coords[1], 80, 80);
			icoFond = new ImageIcon(getClass().getResource("/images/tourcotedetruite.png"));
            this.imgFond = this.icoFond.getImage();
            g.drawImage(this.imgFond, basic_coords[0], basic_coords[1], 120, 120, null);
		}
		//boules de feu
		if(this.sens ==0) {
			if(cible.pv > 0) {
				Bdf.drawBdf(g);
			}
		}
		
	}
	
	public void radar(LinkedList<Jouable> list_perso, LinkedList<Jouable> list_tours) {
		
		double r=2000;
		for(Jouable perso : list_perso) {
			double rp = Math.sqrt(Math.pow(perso.x -this.x,2)+Math.pow(perso.y -this.y,2));
			if(rp<=r) {
				r=rp;
				cible = perso;
			}
		}
	}
	public void brain() {
		if(!(cible == null)) {
			double r = Math.sqrt(Math.pow(cible.x-this.x, 2)+Math.pow(cible.y-this.y, 2));
	        System.out.println("le r vaut: "+ r);
	        if(r<=this.portée) {
	            //la tour est capable de tirer
	            this.tir(cible);
	            Bdf.Brain(cible,this);
	            this.sens = 0;
	        }else {
	        	this.sens = 1;
	        }
		}
	}
	
	public String toString() {
		return "je suis une tour, mes coordonées : " + this.x +", " +this.y;
	}
	public void meurt(LinkedList<Jouable> list_tours) {
		//ses coordonnées passent sur la tour principale
		this.x = list_tours.get(2).x;
		this.y = list_tours.get(2).y;
	}
	
	public void draw_card(Graphics g) {
		
	}
	
}
