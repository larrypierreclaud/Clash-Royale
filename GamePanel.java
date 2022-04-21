package clashRoyale;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.LinkedList;
import java.util.Random;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements ActionListener {
	
	static final int MAP_WIDTH = 1280;
	static final int CARD_WIDTH = 312;
	static final int SCREEN_WIDTH = MAP_WIDTH +2*CARD_WIDTH;
	static final int SCREEN_HIGHT = 740;
	static final int UNIT_SIZE = 8;  //taille des petits carrés, il faut que SCREEN_WIDTH et SCREEN_HEIgth soient divisibles par ce chiffre. 
	static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HIGHT)/UNIT_SIZE;
	static final int DELAY = 100;  //il faut que 1000 % DELAY = 0;
	
	boolean running = false;
	Timer timer;
	
	
	//variables gestion de l'elixir
	//int rechargeElixir = 0;*/
	
	//variables fond d'écran
	private ImageIcon icoFond;
	private Image imgFond;
	
	//personnages joueur 1
	public Sorcier s1_1 = new Sorcier(80,80);  //les coordonnées sont prises par rapport au terrain
	public Bomber b1 = new Bomber(80,80);   //donc si x=80, il faut 80 sur le terrain
	public Geant g1 = new Geant(80,80);     //donc sa vraie valeur est 80+ CARD_WIDTH
	public Cochon c1 = new Cochon(80,80);
	public Sarbacane sa1 = new Sarbacane(80,80);
	public Valkyrie v1 = new Valkyrie(80,80);
	public Dragon d1 = new Dragon(80,80);
	public Fleches f1 = new Fleches(80,80);
	
	//personnages joueur 2
	public Sorcier s2_1 = new Sorcier(1080,80);
	public Sarbacane sa2 = new Sarbacane(1080,80);
	public Valkyrie v2 = new Valkyrie(1080,80);
	public Dragon d2 = new Dragon(1080,80);
	public Sorcier s2_2 = new Sorcier(980,80);
	public Bomber b2 = new Bomber(1080,80);
	public Geant g2 = new Geant(1080,80);
	public Cochon c2 = new Cochon(1080,80);
	
	
	//tours perso 1
	public Jouable t1_1 = new Tour(false,true,true);  //boolean principale, boolean player, boolean haut
	public Jouable t1_2 = new Tour(false,true,false);
	public Jouable t1_3 = new Tour(true,true,false);
	
	//tours perso 2
	public Jouable t2_1 = new Tour(false,false,true);  //boolean principale, boolean player, boolean haut
	public Jouable t2_2 = new Tour(false,false,false);
	public Jouable t2_3 = new Tour(true,false,false);
	
	//listes joueurs sur le terrain par joueur:
	LinkedList<Jouable> inGame1;
	LinkedList<Jouable> inGame2;
	
	//listes des tours de chaque joueur
	LinkedList<Jouable> towers1; //trois élément : 0.petite tour du haut, 1. petite tour du bas
	LinkedList<Jouable> towers2;     //2. petite tour du bas
	
	//Listes pour l'affichage/selection des cartes
	LinkedList<Jouable> listOrdreCartes1 = new LinkedList<Jouable>();
	LinkedList<Jouable> listOrdreCartes2 = new LinkedList<Jouable>();
	
	//réserves d'élixir
	int elixir1 = 10;
	int elixir2 = 10;
	BarreElixir barre1 = new BarreElixir(10,10, true, 0, (SCREEN_HIGHT-20),20, (CARD_WIDTH)/3);
	BarreElixir barre2 = new BarreElixir(10,10, false, SCREEN_WIDTH - 2*CARD_WIDTH, (SCREEN_HIGHT-20),20, (CARD_WIDTH)/3);  //Mettre de meilleures dimensions
	int recharge = 0;  //on veut recharger toutes les sencondes
	
	//variables pour les fréquences de tir et les vitesses
	int temps = 0;
	
	
	//insbratej
	//Remplissage des LinkedList
	//apparement ça marche que dans le action performed
	
	GamePanel(){
		this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HIGHT)); //nombre total de carrés
		//this.setBackground(Color.white);
		icoFond = new ImageIcon(getClass().getResource("/images/arène1.png"));
		this.imgFond = this.icoFond.getImage();
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		//listes joueurs sur le terrain par joueur:
		inGame1 = new LinkedList<Jouable>();
		inGame2 = new LinkedList<Jouable>();
		
		//listes des tours de chaque joueur
		//perso 1
		towers1 = new LinkedList<Jouable>(); //trois élément : 0.petite tour du haut, 1. petite tour du bas  2. petite tour du bas
		towers1.add(0,t1_1);
		towers1.add(1,t1_2);
		towers1.add(2,t1_3);
		//perso 2
		towers2 = new LinkedList<Jouable>();     
		towers2.add(0,t2_1);
		towers2.add(1,t2_2);
		towers2.add(2,t2_3);
		
		//liste de l'ordre des cartes pour la gestion des cartes
		//perso 1
		listOrdreCartes1.add(s1_1);
		listOrdreCartes1.add(b1);
		listOrdreCartes1.add(g1);
		listOrdreCartes1.add(c1);
		listOrdreCartes1.add(sa1);
		listOrdreCartes1.add(v1);
		listOrdreCartes1.add(d1);
		listOrdreCartes1.add(f1);
		//perso 2
		s2_1.which_player = false;
		s2_2.which_player = false;
		b2.which_player = false;
		g2.which_player = false;
		c2.which_player = false;
		sa2.which_player = false;
		v2.which_player = false;
		d2.which_player = false;
		listOrdreCartes2.add(s2_1);
		listOrdreCartes2.add(s2_2);
		listOrdreCartes2.add(b2);
		listOrdreCartes2.add(g2);
		listOrdreCartes2.add(c2);
		listOrdreCartes2.add(sa2);
		listOrdreCartes2.add(v2);
		listOrdreCartes2.add(d2);
		
		
		startGame();
		//inGame1.add(s1);
	}
	public void startGame() {
		running = true;
		timer = new Timer(DELAY,this);
		timer.start();
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(this.imgFond, 312, 0, 1280, 720, null);
		//s1.draw(g);
		//g.drawImage(img, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, observer)
		draw(g);
	}
	public void draw(Graphics g) {
		if (running) {
			//affichage des persos sur le terrain
			for(Jouable perso : inGame1) {
				perso.draw(g);
			}
			for(Jouable tower : towers2) {
				tower.draw(g);
			}
			for(Jouable perso : inGame2) {
				perso.draw(g);
			}
			for(Jouable tower : towers1) {
				tower.draw(g);
			}
		}
		//vérifie si la partie est terminée
		if(!towers2.get(2).checkLife()) {
			gameOver(g);
		}
		if(!towers1.get(2).checkLife()) {
			gameOver(g);
		}
		//gestion de l'affichage des cartes pour le joueur 1
		listOrdreCartes1.get(0).y_card = 0;
		listOrdreCartes1.get(0).draw_card(g);
		listOrdreCartes1.get(1).y_card = 180;
		listOrdreCartes1.get(1).draw_card(g);
		listOrdreCartes1.get(2).y_card = 2*180;
		listOrdreCartes1.get(2).draw_card(g);
		listOrdreCartes1.get(3).y_card = 3*180;
		listOrdreCartes1.get(3).draw_card(g);
		
		
		//gestion de l'affichage des cartes pour joueur 2
		listOrdreCartes2.get(0).y_card = 0;
		listOrdreCartes2.get(0).x_card = 1904-312;
		listOrdreCartes2.get(0).draw_card(g);
		listOrdreCartes2.get(1).y_card = 180;
		listOrdreCartes2.get(1).x_card = 1904-312;
		listOrdreCartes2.get(1).draw_card(g);
		listOrdreCartes2.get(2).y_card = 2*180;
		listOrdreCartes2.get(2).x_card = 1904-312;
		listOrdreCartes2.get(2).draw_card(g);
		listOrdreCartes2.get(3).y_card = 3*180;
		listOrdreCartes2.get(3).x_card = 1904-312;
		listOrdreCartes2.get(3).draw_card(g);
		
		barre1.drawBarreElixir(g, true);
		barre2.drawBarreElixir(g, false);
		
	}
			
	
	public void gameOver(Graphics g) {
		//Score
		g.setColor(Color.red);
		g.setFont(new Font("Ink Free",Font.BOLD,40));
		FontMetrics metrics = getFontMetrics(g.getFont());
		g.drawString("Victoire",(SCREEN_WIDTH - metrics.stringWidth("Silver is dead. Gold wins"))/2,SCREEN_HIGHT/2);
		System.out.println("victoireeee");
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(running) { //je ne comprends pas cette partie mais c'est celle qui fait que ces méthodes s'appliquent
			//System.out.print(sens_s1 +" mem :  "+ mem +"  ");
	        // à chaque intervalle de temps DELAY
			
			for(Jouable perso: inGame1) {
				perso.radar(inGame2, towers2);
				perso.brain();
				perso.move();
				perso.temps = perso.temps + DELAY;
				if(!perso.checkLife()) {
					perso.meurt(towers2);
				}
			}
			for(Jouable tower : towers2) {
				tower.radar(inGame1,towers1);
				tower.brain();
				tower.temps = tower.temps + DELAY;
				if(!tower.checkLife()) {
					tower.meurt(towers2);
				}
			}
			for(Jouable perso: inGame2) {
				perso.radar(inGame1, towers1);
				perso.brain();
				perso.move();
				perso.temps = perso.temps + DELAY;
				if(!perso.checkLife()) {
					perso.meurt(towers1);
				}
			}
			for(Jouable tower : towers1) {
				tower.radar(inGame2,towers2);
				tower.brain();
				tower.temps = tower.temps + DELAY;
				if(!tower.checkLife()) {
					tower.meurt(towers1);
				}
			}
			
		}
		recharge = recharge + DELAY;   //recharge d'élixir
		if(recharge % 2000 == 0) {
			if(elixir1 < 10) {
				elixir1 = elixir1 + 1;
				barre1.val = elixir1;
			}
			if(elixir2<10) {
				elixir2 = elixir2 +1;
				barre2.val = elixir2;
			}
		}
		temps = temps + DELAY;
		repaint();
		System.out.println(inGame1.toString());
		System.out.println(listOrdreCartes1.toString());
		System.out.println();
		
		
	}
	
	public void spoan1(int ordre) {
		if(elixir1 - listOrdreCartes1.get(ordre).elixir >= 0) {
			elixir1 = elixir1 - listOrdreCartes1.get(ordre).elixir;
			barre1.val = elixir1;
			inGame1.add(listOrdreCartes1.get(ordre));
			listOrdreCartes1.add(listOrdreCartes1.get(ordre));
			listOrdreCartes1.remove(ordre);
		}
		//listOrdreCartes1.get(ordre).y_card = 180*ordre;
	}
	public void spoan2(int ordre) {
		if(elixir2 - listOrdreCartes2.get(ordre).elixir >= 0) {
			elixir2 = elixir2 - listOrdreCartes2.get(ordre).elixir;
			barre2.val = elixir2;
			inGame2.add(listOrdreCartes2.get(ordre));
			listOrdreCartes2.add(listOrdreCartes2.get(ordre));
			listOrdreCartes2.remove(ordre);
		}
	}
	
	
	int ordre1 = 0;
	int ordre2 = 0;
	public class MyKeyAdapter extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			//touches de sélection de ordre 1 (joueur à gauche)
			case KeyEvent.VK_1:
				ordre1 = 0;
				break;
			case KeyEvent.VK_2:
				ordre1 = 1;
				break;
			case KeyEvent.VK_3:
				ordre1 = 2;
				break;
			case KeyEvent.VK_4:
				ordre1 = 3;
				break;
			//touches de spoan_localisation pour le joueur à gauche
			case KeyEvent.VK_A:  //nom des flèches sur le clavier
				listOrdreCartes1.get(ordre1).x = CARD_WIDTH + 1*MAP_WIDTH/6;
				listOrdreCartes1.get(ordre1).y = 1*(SCREEN_HIGHT -120)/3;
				spoan1(ordre1);
				break;
			case KeyEvent.VK_Q:
				listOrdreCartes1.get(ordre1).x = CARD_WIDTH + 1*MAP_WIDTH/6;
				listOrdreCartes1.get(ordre1).y = 2*(SCREEN_HIGHT -120)/3;
				spoan1(ordre1);
				break;
			case KeyEvent.VK_W:
				listOrdreCartes1.get(ordre1).x = CARD_WIDTH + 1*MAP_WIDTH/6;
				listOrdreCartes1.get(ordre1).y = 3*(SCREEN_HIGHT -120)/3;
				spoan1(ordre1);
				break;
			case KeyEvent.VK_Z:
				listOrdreCartes1.get(ordre1).x = CARD_WIDTH + 2*MAP_WIDTH/6;
				listOrdreCartes1.get(ordre1).y = 1*(SCREEN_HIGHT -120)/3;
				spoan1(ordre1);
				break;
			case KeyEvent.VK_S:  //nom des flèches sur le clavier
				listOrdreCartes1.get(ordre1).x = CARD_WIDTH + 2*MAP_WIDTH/6;
				listOrdreCartes1.get(ordre1).y = 2*(SCREEN_HIGHT -120)/3;
				spoan1(ordre1);
				break;
			case KeyEvent.VK_X:
				listOrdreCartes1.get(ordre1).x = CARD_WIDTH + 2*MAP_WIDTH/6;
				listOrdreCartes1.get(ordre1).y = 3*(SCREEN_HIGHT -120)/3;
				spoan1(ordre1);
				break;
			case KeyEvent.VK_E:
				listOrdreCartes1.get(ordre1).x = CARD_WIDTH + 3*MAP_WIDTH/6;
				listOrdreCartes1.get(ordre1).y = 1*(SCREEN_HIGHT -120)/3;
				spoan1(ordre1);
				break;
			case KeyEvent.VK_D:
				listOrdreCartes1.get(ordre1).x = CARD_WIDTH + 3*MAP_WIDTH/6;
				listOrdreCartes1.get(ordre1).y = 2*(SCREEN_HIGHT -120)/3;
				spoan1(ordre1);
				break;
			case KeyEvent.VK_C:
				listOrdreCartes1.get(ordre1).x = CARD_WIDTH + 3*MAP_WIDTH/6;
				listOrdreCartes1.get(ordre1).y = 3*(SCREEN_HIGHT -120)/3;
				spoan1(ordre1);
				break;
			//touches de sélection de ordre2 (joueur à droite)
			case KeyEvent.VK_7:
				ordre2 = 0;
				break;
			case KeyEvent.VK_8:
				ordre2 = 1;
				System.out.println("okayy8");
				System.out.println(ordre2);
				break;
			case KeyEvent.VK_9:
				ordre2 = 2;
				break;
			case KeyEvent.VK_0:
				ordre2 = 3;
				break;
			//touches de spoan localisation pour le joueur à droite
			case KeyEvent.VK_Y:  //nom des flèches sur le clavier
				listOrdreCartes2.get(ordre2).x = CARD_WIDTH + 4*MAP_WIDTH/6;
				listOrdreCartes2.get(ordre2).y = 1*(SCREEN_HIGHT -120)/3;
				spoan2(ordre2);
				break;
			case KeyEvent.VK_G:
				listOrdreCartes2.get(ordre2).x = CARD_WIDTH + 4*MAP_WIDTH/6;
				listOrdreCartes2.get(ordre2).y = 2*(SCREEN_HIGHT -120)/3;
				System.out.println(ordre2);
				spoan2(ordre2);
				break;
			case KeyEvent.VK_V:
				listOrdreCartes2.get(ordre2).x = CARD_WIDTH + 4*MAP_WIDTH/6;
				listOrdreCartes2.get(ordre2).y = 3*(SCREEN_HIGHT -120)/3;
				spoan2(ordre2);
				break;
			case KeyEvent.VK_U:
				listOrdreCartes2.get(ordre2).x = CARD_WIDTH + 5*MAP_WIDTH/6;
				listOrdreCartes2.get(ordre2).y = 1*(SCREEN_HIGHT -120)/3;
				spoan2(ordre2);
				break;
			case KeyEvent.VK_H:  
				listOrdreCartes2.get(ordre2).x = CARD_WIDTH + 5*MAP_WIDTH/6;
				listOrdreCartes2.get(ordre2).y = 2*(SCREEN_HIGHT -120)/3;
				spoan2(ordre2);
				break;
			case KeyEvent.VK_B:
				listOrdreCartes2.get(ordre2).x = CARD_WIDTH + 5*MAP_WIDTH/6;
				listOrdreCartes2.get(ordre2).y = 3*(SCREEN_HIGHT -120)/3;
				spoan2(ordre2);
				break;
			case KeyEvent.VK_I:
				listOrdreCartes2.get(ordre2).x = CARD_WIDTH + 6*MAP_WIDTH/6;
				listOrdreCartes2.get(ordre2).y = 1*(SCREEN_HIGHT -120)/3;
				spoan2(ordre2);
				break;
			case KeyEvent.VK_J:
				listOrdreCartes2.get(ordre2).x = CARD_WIDTH + 6*MAP_WIDTH/6;
				listOrdreCartes2.get(ordre2).y = 2*(SCREEN_HIGHT -120)/3;
				spoan2(ordre2);
				break;
			case KeyEvent.VK_N:
				listOrdreCartes2.get(ordre2).x = CARD_WIDTH + 6*MAP_WIDTH/6;
				listOrdreCartes2.get(ordre2).y = 3*(SCREEN_HIGHT -120)/3;
				spoan2(ordre2);
				break;
			}
			
		}
		
	}
}



