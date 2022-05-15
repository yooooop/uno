import java.awt.*;
import java.applet.*;
import javax.swing.*;
import java.util.*;
import java.awt.geom.*;

public class unoback extends Applet {
	Random rn = new Random(); // imports random class
	Image offScreen;
	Graphics offG; //
	Image backuser, backone, backtwo, backthree, background, dirclock, dircounter, select, tutorialimg; //sets the image variables
	Image winuser, winaione, winaitwo, winaithree;
	Image curblue, curred, curyellow, curgreen;
	Image start; //sets the image variables
	AudioClip bgm; //DELETE THIS LINE TO PLAY WITHOUT BGM
	AudioClip play, take, win, aiturn; //sets the audio variables
	int number = rn.nextInt(112); //picks a random number between 0 and 111 which corresponds to the number of cards in the deck
	//zero row for colour(numbered),first row for the number on the cards,second to check if it's taken
	//yellow colour 1; red colour 2; blue colour 3; green colour 4;
	int unocards[][]= new int [112][3];//array for the data that the card has
	//first row: colour   second row: number third row: cardid used for recalling
	Image unopic[] = new Image [112];
	//zero for colour, first for number, second for the position of the cards on the deck, third for x position,
	//fourth for y position, fifth for card's id, sixth to check if it's available
	int userdeck[][] = new int [25][7];
	int ai1deck[][] = new int [40][3];
	int ai2deck[][] = new int [40][3];
	int ai3deck[][] = new int [40][3];
	int player = -1;//variable as an indication of the players
	int availcardsuser[] = new int [20]; //array for the available cards for the user.
	int temp = 1; //temporary number
	int availpos = 0;
	//0 = user, 1 = ai one, 2 = ai two, 3 = ai three, -1 = not taken
	int userx = 200;
	int aioney = 200;
	int aitwox = 600;
	int aithreey = 600;
	boolean startboo = false;
	boolean ran = false;
	boolean available = false;
	boolean played = false;
	boolean colourchoice = true;
	boolean wildplayed = false;
	boolean colourselected = false;
	boolean tutorial = false;
	boolean gameended = false;
	//direction true = clockwise, direction false = counter clockwise
	boolean direction = true;
	//if it's one, it goes clockwise, if it's negative one, it goes counter clockwise
	int dirnum = 1;
	int currentnum; //currentnumber
	int currentcolour; //currentcolour
	//deck's lengths
	int numuser = 0;
	int numaione = 0;
	int numaitwo = 0;
	int numaithree = 0;

	//2*(0 ~ 9), 4 wilds each
	public void init() {
		//fills the array row with a value.
		for(int i = 0; i < 7; i++) {
			userdeck[i][6] = 0;
		}
		background = getImage(getCodeBase(), "usedatback.png");
		backuser = getImage(getCodeBase(), "therear360.png");
		backthree = getImage(getCodeBase(), "therear270.png");
		backtwo = getImage(getCodeBase(), "therear180.png");
		backone = getImage(getCodeBase(), "therear90.png");
		start = getImage(getCodeBase(), "start.png");
		dirclock = getImage(getCodeBase(), "directionclock.png");
		dircounter = getImage(getCodeBase(), "directioncounter.png");
	//	click = getImage(getCodeBase(), "click.png");
		select = getImage(getCodeBase(), "Select.png");
		winuser = getImage(getCodeBase(), "winner user.png");
		winaione = getImage(getCodeBase(), "winner ai1.png");
		winaitwo = getImage(getCodeBase(), "winner ai2.png");
		winaithree = getImage(getCodeBase(), "winner ai3.png");
		curblue = getImage(getCodeBase(), "currentblue.png");
		curgreen = getImage(getCodeBase(), "currentgreen.png");
		curyellow = getImage(getCodeBase(), "currentyellow.png");
		curred = getImage(getCodeBase(), "currentred.png");
		tutorialimg = getImage(getCodeBase(), "tutorial.png");
		//makes all the cards as not taken/0
		for(int i = 0; i < unocards.length; i++) {
			unocards[i][2] = player;
		}
		//storing the number and colour of the cards to each colour
		int blue = 0;
		int bluenum = 0;
		for(int i = 0; i < 28; i++) {
			unocards[blue][0] = 3;
			unocards[blue][1] = bluenum;
			blue+=4;
			bluenum++;
			if(bluenum == 14) {
				bluenum = 0;
			}
		}
		int green = 1;
		int greennum = 0;
		for(int i = 0; i < 28; i++) {
			unocards[green][0] = 4;
			unocards[green][1] = greennum;
			green+=4;
			greennum++;
			if(greennum == 14) {
				greennum = 0;
			}
		}
		int red = 2;
		int rednum = 0;
		for(int i = 0; i < 28; i++) {
			unocards[red][0] = 2;
			unocards[red][1] = rednum;
			red+=4;
			rednum++;
			if(rednum == 14) {
				rednum = 0;
			}
		}
		int yellow = 3;
		int yellownum = 0;
		for(int i = 0; i < 28; i++) {
			unocards[yellow][0] = 1; //for colour
			unocards[yellow][1] = yellownum; //for number of the cards
			yellow+=4;
			yellownum++;
			if(yellownum == 14) {
				yellownum = 0;
			}
		}
		//makes the special cards' colours 0 and the number 30/60.
		unocards[52][0] = 0; unocards[53][0] = 0; unocards[54][0] = 0; unocards[55][0] = 0;
		unocards[52][1] = 30; unocards[53][1] = 60; unocards[54][1] = 30; unocards[55][1] = 60;
		unocards[108][0] = 0; unocards[109][0] = 0; unocards[110][0] = 0; unocards[111][0] = 0;
		unocards[108][1] = 30; unocards[109][1] = 60; unocards[110][1] = 30; unocards[111][1] = 60;
		//there are two same cards in the deck
		for (int i = 0; i < unocards.length/2; i++ ) {
            unopic[i] = getImage(getCodeBase(), (i) + ".png");
        }
        for (int i = 56; i < unocards.length; i++ ) {
            unopic[i] = getImage(getCodeBase(), (i-56) + ".png");
        }
		bgm = getAudioClip(getCodeBase(), "background.wav"); //DELETE THIS LINE TO PLAY WITHOUT BGM
		play = getAudioClip(getCodeBase(), "play.wav");
		take = getAudioClip(getCodeBase(), "take.wav");
		win = getAudioClip(getCodeBase(), "winning.wav");
		aiturn = getAudioClip(getCodeBase(), "aiturn.wav");

		//tracking every image to check if there's anything wrong
		MediaTracker tracker = new MediaTracker(this);
		tracker.addImage(backuser, 0); tracker.addImage(backone, 0); tracker.addImage(backtwo, 0); tracker.addImage(backthree, 0); tracker.addImage(background, 0);
		tracker.addImage(start, 0); tracker.addImage(dirclock, 0); tracker.addImage(dircounter, 0); tracker.addImage(select, 0);
		tracker.addImage(winuser, 0); tracker.addImage(winaione, 0); tracker.addImage(winaitwo, 0); tracker.addImage(winaithree, 0);
		tracker.addImage(start, 0); tracker.addImage(tutorialimg, 0);
		tracker.addImage(curblue, 0); tracker.addImage(curgreen, 0); tracker.addImage(curyellow, 0); tracker.addImage(curred, 0);
		for (int i = 0; i < 112; i++) {
			tracker.addImage(unopic[i], 0);
		}
		while(tracker.isErrorAny()){
			if(tracker.isErrorAny()){
				JOptionPane.showMessageDialog(null,"Trouble loading pictures.");
			}
		}
		while(tracker.checkAll(true) != true){ }
		if (tracker.isErrorAny()){
			JOptionPane.showMessageDialog(null, "Trouble loading pictures.");
		}
		offScreen = createImage(900,900);
		offG = offScreen.getGraphics();
		offG.drawImage(start, 0,0, this);

	}

	public void paint(Graphics g) {
		g.drawImage(offScreen,0,0,this);

	}
	int xx = 0;
	int yy = 0;
	int ii = 0;
	public boolean mouseDown(Event evt, int xx, int yy){

		if(xx > 0 && xx < 900 && yy > 0 && yy < 900 && startboo == false && gameended == false && tutorial == true ) {
			System.out.println("This console will prove that player1, player2, and player3 work");
			System.out.println("10 is reverse, 11 is skip, 12 is plus2, 30 is wild, 60 is plus4");
			bgm.play(); //DELETE THIS LINE TO PLAY WITHOUT BGM
			offG.drawImage(background,0, 0,this);
			offG.drawImage(start, 350, 650, this);
			offG.drawImage(background, 0, 0, this);
			offG.drawImage(dirclock, 150, 150, this);
		//	offG.drawImage(click, 400, 200, this);
			for(int k = 0; k < 7 ; k++) {
				player = 0;
				cardpick(number, player);
				ran = false;
				player = 1;
				cardpick(number, player);
				ran = false;
				player = 2;
				cardpick(number, player);
				ran = false;
				player = 3;
				cardpick(number, player);
				ran = false;
				player = 0;
			}
			offG.drawImage(backuser ,325, 375, this);
			offG.drawImage(unopic[number], 475, 375, this);
			currentnum = unocards[number][1];
			currentcolour = unocards[number][0];
			while(currentnum > 9) {
				number = rn.nextInt(112);
				offG.drawImage(unopic[number], 475, 375, this);
				currentnum = unocards[number][1];
				currentcolour = unocards[number][0];
			}
			current();
			startboo = true;
			cardcheck(currentcolour, currentnum);
			}
			if(wildplayed == true) {
				if(currentnum == 30 || currentnum == 60) { //if a wild card is played, let the user select the colour
					if(xx>0 && xx < 100 && yy > 0 && yy < 100) {
						//blue
						currentcolour = 3;
						colourchoice = true;
						wildplayed = false;
						colourselected = true;
						current();
					}
					if(xx > 800 && xx < 900 && yy > 0 && yy < 100) {
						//red
						currentcolour = 2;
						colourchoice = true;
						wildplayed = false;
						colourselected = true;
						current();
					}
					if(xx > 800 && xx < 900 && yy > 800 && yy < 900) {
						//green
						currentcolour = 4;
						colourchoice = true;
						wildplayed = false;
						colourselected = true;
						current();
					}
					if(xx > 0 && xx < 100 && yy > 800 && yy < 900) {
						//yellow
						currentcolour = 1;
						colourchoice = true;
						wildplayed = false;
						colourselected = true;
						current();
					}
				}
	}
	//		}
			//picks a new card
			if(xx>325 && xx<425 && yy>375 && yy<533 && gameended == false) {
				if(available == false) {
					if(startboo == true) {
						take.play();
						player = 0;
						cardpick(number, player);
						ran = false;
						cardcheck(currentcolour, currentnum);
						play(xx,yy);
					}
				}
			}
				//play method
			play(xx,yy);
				//the play button
		if(colourselected == true && gameended == false) {
			if(xx>400 && xx < 500 && yy > 200 && yy < 300) {
				colourselected = false;
				available = false;
				while(player != 0 && gameended == false) {
					if(player != 0) {
						aiturns();
					}
				}
				cardcheck(currentcolour, currentnum);
			}
		}
		if(xx > 0 && xx < 900 && yy > 0 && yy < 900 && startboo == false && gameended == false && tutorial == false) {
			offG.drawImage(tutorialimg, 0, 0, this);
			tutorial = true;
		}
		repaint();
		return true;
	}
	//picks a random card that's available and gives it to players
	public void cardpick(int number, int player) {
		//choose a random colour between 0 and 111
		number = rn.nextInt(112);
		while (ran == false) {
			//while the card hasn't been played/ran = false
			if (unocards[number][2] == -1) {
				//if the card isn't taken, run this or pick a new number
				for(int i = 0; i < 112; i++){
					//run the for loop to check which random number has been selected
					if(number == i) {
						unocards[i][2] = player;
						//mark that number as taken
						if(player == 0) {
							//run this for user
							offG.drawImage(unopic[i], userx, 730, this);
							userdeck[numuser][3] = userx;
							userdeck[numuser][4] = 730;
							userdeck[numuser][5] = i;
							userx += 30;
							userdeck[numuser][0] = unocards[number][0];
							userdeck[numuser][1] = unocards[number][1];
							numuser++;
							ran = true;
							userdeck[numuser][2] = numuser;
						}else if(player == 1) {
							//run this for aione
							offG.drawImage(backone, 12, aioney, this);
							ai1deck[numaione][2] = i;
							aioney += 30;
							ai1deck[numaione][0] = unocards[number][0];
							ai1deck[numaione][1] = unocards[number][1];
							numaione++;
							ran = true;
						}else if(player == 2) {
							//run this for aitwo
							offG.drawImage(backtwo, aitwox, 12, this);
							ai2deck[numaitwo][2] = i;
							aitwox -= 30;
							ai2deck[numaitwo][0] = unocards[number][0];
							ai2deck[numaitwo][1] = unocards[number][1];
							numaitwo++;
							ran = true;
						}else if(player == 3) {
							//run this for aithree
							offG.drawImage(backthree, 730, aithreey, this);
							ai3deck[numaithree][2] = i;
							aithreey -= 30;
							ai3deck[numaithree][0] = unocards[number][0];
							ai3deck[numaithree][1] = unocards[number][1];
							numaithree++;
							ran = true;
						}
					}
				}
			}else{
				number = rn.nextInt(112);
			}
		}
	}

	int userredraw = 200;
 	int oneredraw = 200;
 	int tworedraw = 600;
 	int threeredraw = 600;

	//play method, runs until the game ends
 	public void play(int xx, int yy) {
  		if(player == 0 && startboo == true && gameended == false) {
  			userredraw = 200;
			oneredraw = 200;
	 	 	tworedraw = 600;
	 	 	threeredraw = 600;
 			cardcheck(currentcolour, currentnum);
			//run this loop for every available card
 			for(int i = 0; i < availpos; i++) {
	 			//if user hasn't played
	 			if(played == false) {
	 				//if the game is started
	 				if(startboo == true) {
	 					//if the available card's x/y is clicked, run this
	 					if(xx > userdeck[availcardsuser[i]][3] && xx < userdeck[availcardsuser[i]][3]+30 && yy> 730 && yy< 887) {
	 						play.play();
	 						System.out.println("user played");
	 						currentnum = userdeck[availcardsuser[i]][1];
	 						currentcolour = userdeck[availcardsuser[i]][0];
	 						unocards[userdeck[availcardsuser[i]][5]][2] = -1;
	 						if(currentcolour == 0) {
	 							wildplayed = true;
	 						}
	 						if(currentnum != 30 && currentnum != 60) {
	 							colourselected = true;
			 				}
			 				//change the variables to the played current num/colour
			 				//and redraw everything
							offG.drawImage(background, 0 , 0, this);
							if(currentnum == 10) {
								temp = dirnum*-1;
							}
							if(temp == -1){
								offG.drawImage(dircounter, 150, 150, this);
							}else if(temp == 1){
								offG.drawImage(dirclock, 150, 150, this);
							}
							offG.drawImage(backuser ,325, 375, this);
							offG.drawImage(unopic[userdeck[availcardsuser[i]][5]], 475, 375, this);
							for(int k = 0; k < numaione; k++){
								offG.drawImage(backone, 12, oneredraw, this);
								oneredraw+=30;
							}
							for(int k = 0; k < numaitwo; k++){
								offG.drawImage(backtwo, tworedraw, 12, this);
								tworedraw-=30;
							}
							for(int k = 0; k < numaithree; k++){
								offG.drawImage(backthree, 730, threeredraw, this);
								threeredraw-=30;
							}
							for(int j = availcardsuser[i]; j < numuser; j++) {
			 					userdeck[j][0] = userdeck[j+1][0];// + 1; //
			 					userdeck[j][1] = userdeck[j+1][1]; //
			 					userdeck[j][2] = userdeck[j+1][2] - 1; //
			 					userdeck[j][3] = userdeck[j+1][3] - 30; //userx
			 					userdeck[j][4] = userdeck[j+1][4]; //usery
			 					userdeck[j][5] = userdeck[j+1][5]; //
			 				}
			 				for(int r = 0; r < 6; r++) {
			 					userdeck[numuser][r] = -1;
			 				}
							for(int k = 0; k < numuser-1; k++) {
								offG.drawImage(unopic[userdeck[k][5]], userredraw, 730, this);
								userredraw+=30;
							}
			 				numuser--;
			 				userx-=30;
			 				played = true;
			 				System.out.println("USER num: " + currentnum);
			 				System.out.println("USER colour: " + currentcolour);
			 				System.out.println("");
			 				cardfunc(currentnum, currentcolour, xx, yy);
			 				current();
			 				win(numuser, numaione, numaitwo, numaithree);
			 				colourpick();
		 				}
	 				}
				}
 			}
  		}
	}

	//method to check if the user's deck has any available cards
	public void cardcheck(int currentcolour, int currentnum) {
		for(int i = 0; i < availpos; i++) {
			availcardsuser[i] = 0;
		}
		//make the available cards blank
		for(int i = 0; i < numuser; i++) {
			userdeck[i][6] = 0;
		}
 		availpos = 0;
		//if there is an available card, make the available true so user can't take anymore cards
		for(int i = 0; i < numuser; i++) {
			if(userdeck[i][0] == currentcolour || userdeck[i][0] == 0 || userdeck[i][1] == currentnum) {
				available = true;
				availcardsuser[availpos] = userdeck[i][2];
				userdeck[i][6] = 1;
				availpos++;
			}
		}
	}

	boolean oneavail = false;
	boolean twoavail = false;
	boolean threeavail = false;
	//method for ai turns
	public void aiturns() {
		if(played == true && gameended == false) {
			aiturn.play();
			if(player == 1) {
			System.out.println("1st ai played");
			userredraw = 200;
			oneredraw = 200;
	 	 	tworedraw = 600;
	 	 	threeredraw = 600;
	 	 	while(oneavail == false) {
				for(int i = 0; i < numaione; i++) {
					//if there is an available card, play that card
					if(oneavail == false) {
						if(ai1deck[i][0] == currentcolour || ai1deck[i][0] == 0 || ai1deck[i][1] == currentnum) {
							oneavail = true;
							currentnum = ai1deck[i][1];
			 				currentcolour = ai1deck[i][0];
			 				unocards[ai1deck[i][2]][2] = -1;
							offG.drawImage(background, 0 , 0, this);
							//redraw
							if(currentnum == 10) {
								temp = dirnum*-1;
							}
							if(temp == -1){
								offG.drawImage(dircounter, 150, 150, this);
							}else if(temp == 1){
								offG.drawImage(dirclock, 150, 150, this);
							}
							offG.drawImage(backuser ,325, 375, this);
							offG.drawImage(unopic[ai1deck[i][2]], 475, 375, this);
							for(int k = 0; k < numaione-1; k++){
								offG.drawImage(backone, 12, oneredraw, this);
								oneredraw+=30;
							}
							for(int k = 0; k < numaitwo; k++){
								offG.drawImage(backtwo, tworedraw, 12, this);
								tworedraw-=30;
							}
							for(int k = 0; k < numaithree; k++){
								offG.drawImage(backthree, 730, threeredraw, this);
								threeredraw-=30;
							}
							for(int k = 0; k < numuser; k++) {
								offG.drawImage(unopic[userdeck[k][5]], userredraw, 730, this);
								userredraw+=30;
							}
							for(int j = i; j < numaione; j++) {
	 							ai1deck[j][0] = ai1deck[j+1][0];
	 							ai1deck[j][1] = ai1deck[j+1][1]; 
	 							ai1deck[j][2] = ai1deck[j+1][2]; 
	 						}
	 						ai1deck[numaione][0] = -1;
	 						ai1deck[numaione][1] = -1; 
	 						ai1deck[numaione][2] = -1;
							i = numaione - 1;
						}
					}
				}
					//if there are no available cards, pick a card
				if(oneavail == false) {
					player = 1;
					cardpick(number, player);
					ran = false;
				}
			}
			numaione--;
			aioney -= 30;
	 	 	oneavail = false;
	 	 	System.out.println("ONE num: " + currentnum);
	 	 	cardfunc(currentnum, currentcolour, xx, yy);
	 	 	if(currentcolour == 0) {
				int rancol = rn.nextInt(4) + 1;
				currentcolour = rancol;
			}
			current();
			win(numuser, numaione, numaitwo, numaithree);
	 	 	System.out.println("ONE colour: " + currentcolour);
	 	 	colourpick();
	 	 	if(dirnum == -1 && player == 0) {
	 	 		played = false;
	 	 	}
			System.out.println("");
			//same logic as ai one
			}else if(player == 2) {
				userredraw = 200;
				oneredraw = 200;
	 		 	tworedraw = 600;
	 		 	threeredraw = 600;
				System.out.println("2nd ai played");
				while(twoavail == false) {
					for(int i = 0; i < numaitwo; i++) {
							if(twoavail == false) {
								if(ai2deck[i][0] == currentcolour || ai2deck[i][0] == 0 || ai2deck[i][1] == currentnum) {
									twoavail = true;
									currentnum = ai2deck[i][1];
					 				currentcolour = ai2deck[i][0];
					 				unocards[ai2deck[i][2]][2] = -1;
									offG.drawImage(background, 0 , 0, this);
									if(currentnum == 10) {
										temp = dirnum*-1;
									}
									if(temp == -1){
										offG.drawImage(dircounter, 150, 150, this);
									}else if(temp == 1){
										offG.drawImage(dirclock, 150, 150, this);
									}
									offG.drawImage(backuser ,325, 375, this);
									offG.drawImage(unopic[ai2deck[i][2]], 475, 375, this);
									for(int k = 0; k < numaione; k++){
										offG.drawImage(backone, 12, oneredraw, this);
										oneredraw+=30;
									}
									for(int k = 0; k < numaitwo-1; k++){
										offG.drawImage(backtwo, tworedraw, 12, this);
										tworedraw-=30;
									}
									for(int k = 0; k < numaithree; k++){
										offG.drawImage(backthree, 730, threeredraw, this);
										threeredraw-=30;
									}
									for(int k = 0; k < numuser; k++) {
										offG.drawImage(unopic[userdeck[k][5]], userredraw, 730, this);
										userredraw+=30;
									}

									for(int j = i; j < numaitwo; j++) {
	 									ai2deck[j][0] = ai2deck[j+1][0];
	 									ai2deck[j][1] = ai2deck[j+1][1]; 
	 									ai2deck[j][2] = ai2deck[j+1][2]; 
	 								}
	 								ai1deck[numaitwo][0] = -1;
	 								ai1deck[numaitwo][1] = -1; 
	 								ai1deck[numaitwo][2] = -1;
	 								i = numaitwo - 1;
								}
							}

						}
						if(twoavail == false) {
							player = 2;
							cardpick(number, player);
							ran = false;
						}
				}
				numaitwo--;
				aitwox+=30;
				twoavail = false;
		 	 	System.out.println("TWO num: " + currentnum);
		 		cardfunc(currentnum, currentcolour, xx, yy);
		 		if(currentcolour == 0) {
				int rancol = rn.nextInt(4) + 1;
				currentcolour = rancol;
				}
				current();
				win(numuser, numaione, numaitwo, numaithree);
		 	 	colourpick();
		 	 	if(currentnum == 11) {
				played = false;
				}
		 	 	System.out.println("TWO colour: " + currentcolour);
				System.out.println("");
		 	 	//same logic as ai one
			}else if(player == 3) {
				userredraw = 200;
				oneredraw = 200;
	 	 		tworedraw = 600;
	 	 		threeredraw = 600;
				System.out.println("3rd ai played");
				while(threeavail == false) {
					for(int i = 0; i < numaithree; i++) {
						if(threeavail == false) {
							if(ai3deck[i][0] == currentcolour || ai3deck[i][0] == 0 || ai3deck[i][1] == currentnum) {
								threeavail = true;
								currentnum = ai3deck[i][1];
				 				currentcolour = ai3deck[i][0];
				 				unocards[ai3deck[i][2]][2] = -1;
								offG.drawImage(background, 0 , 0, this);
								if(currentnum == 10) {
									temp = dirnum*-1;
								}
								if(temp == -1){
									offG.drawImage(dircounter, 150, 150, this);
								}else if(temp == 1){
									offG.drawImage(dirclock, 150, 150, this);
								}
								offG.drawImage(backuser ,325, 375, this);
								offG.drawImage(unopic[ai3deck[i][2]], 475, 375, this);
								for(int k = 0; k < numaione; k++){
									offG.drawImage(backone, 12, oneredraw, this);
									oneredraw+=30;
								}
								for(int k = 0; k < numaitwo; k++){
									offG.drawImage(backtwo, tworedraw, 12, this);
									tworedraw-=30;
								}
								for(int k = 0; k < numaithree-1; k++){
									offG.drawImage(backthree, 730, threeredraw, this);
									threeredraw-=30;
								}
								for(int k = 0; k < numuser; k++) {
									offG.drawImage(unopic[userdeck[k][5]], userredraw, 730, this);
									userredraw+=30;
								}
								for(int j = i; j < numaithree; j++) {
	 								ai3deck[j][0] = ai3deck[j+1][0];
	 								ai3deck[j][1] = ai3deck[j+1][1]; 
	 								ai3deck[j][2] = ai3deck[j+1][2]; 
	 							}
	 							ai3deck[numaithree][0] = -1;
	 							ai3deck[numaithree][1] = -1; 
	 							ai3deck[numaithree][2] = -1;
								i = numaithree - 1;
							}
						}
				}
				if(threeavail == false){
					player = 3;
					cardpick(number, player);
					ran = false;
				}
			}
			numaithree--;
			aithreey+=30;
			threeavail = false;
			System.out.println("THREE num: " + currentnum);
			cardfunc(currentnum, currentcolour, xx, yy);
			if(currentcolour == 0) {
				int rancol = rn.nextInt(4) + 1;
				currentcolour = rancol;
			}
			current();
			win(numuser, numaione, numaitwo, numaithree);
			System.out.println("THREE colour: " + currentcolour);
			colourpick();
			if(dirnum == 1 && player == 0) {
				played = false;
			}
			System.out.println("");
			}
		}
	}
	
	//method for each card's functions
	public void cardfunc(int currentnum, int currentcolour, int xx, int yy) {
		int temporary = 0;
		int temporary2 = 0;
		//if reverse card is played, switch the direction of the play
		if (currentnum == 10) {
			dirnum = dirnum * -1;
		//if plus 2 is played, add 2 to the other player's cards
		}else if(currentnum == 12) {
			temporary = player;
			player += dirnum;
			if(player > 3) {
				player = 0;
			}else if(player < 0) {
				player = 3;
			}
				for(int i = 0; i < 2; i++) {
					cardpick(number, player);
					ran = false;
				}
			player = temporary;
		}else if(currentnum == 60) {
			//if plus 4 is played, add four to the next player
			colourchoice = false;
			if(player == 0) {
				for(int i = 0; i < 4; i++) {
					if(dirnum == -1) {
						player = 3;
						cardpick(number, player);
						ran = false;
					}else if(dirnum == 1) {
						player = 1;
						cardpick(number, player);
						ran = false;
					}
				}
				player = 0;
			}else{
				temporary2 = player;
					player = player + dirnum;
					if(player > 3) {
						player = 0;
					}else if(player < 0) {
						player = 3;
					}
					for(int i = 0; i < 4; i++) {
						cardpick(number, player);
						ran = false;
					}
				player = temporary2;
			}
			colourchoice = true;
		}
	}


	//method to prevent colour switching bugs
	public void colourpick() {
		if(colourchoice == true) {
							if(player > 3){
								player = 0;
							}else if(player < 0){
								player = 3;
							}
		 					if(currentnum != 11) {
			 					player += dirnum;
		 					}
			 				if(player == 0 && currentnum == 11) {
			 					player = 2;
			 				}else if(player == 2 && currentnum == 11) {
			 					player = 0;
			 				}else if(player == 3 && currentnum == 11) {
								player = 1;
							}else if(player == 1 && currentnum == 11) {
								player = 3;
							}else if(player > 3){
								player = 0;
							}else if(player < 0){
								player = 3;
							}
		 				}
	}

	public void current() {
		if (currentcolour == 1) {
			offG.drawImage(curyellow, 400, 600, this);
		}else if(currentcolour == 2) {
			offG.drawImage(curred, 400, 600, this);
		}else if(currentcolour == 3) {
			offG.drawImage(curblue, 400, 600, this);
		}else if(currentcolour == 4) {
			offG.drawImage(curgreen, 400, 600, this);
		}
	}

	public void win(int numuser, int numaione, int numaitwo, int numaithree) {
		if(numuser == 0 && startboo == true) {
			offG.drawImage(winuser, 0, 0, this);
			gameended = true;
			win.play();
		}else if(numaione == 0 && startboo == true) {
			offG.drawImage(winaione, 0, 0, this);
			gameended = true;
			win.play();
		}else if(numaitwo == 0 && startboo == true) {
			offG.drawImage(winaitwo, 0, 0, this);
			gameended = true;
			win.play();
		}else if(numaithree == 0 && startboo == true) {
			offG.drawImage(winaithree, 0, 0, this);
			gameended = true;
			win.play();
		}
	}
	//unable to play after game is over
}


