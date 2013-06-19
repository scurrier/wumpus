<?php
class Wumpus 
{
	private static $currentLine = 0;
	private static $returnLine = array();
	private static $nextLine;

	/**
	 * @param args
	 */
	public static function main() {
		try {
			self::$currentLine = 5;
			$i = '\0';
			$s = array(array(0,0,0,0),
			array(0,2,5,8),		array(0,1,3,10),		array(0,2,4,12),		array(0,3,5,14),		array(0,1,4,6),
			array(0,5,7,15),		array(0,6,8,17),		array(0,1,7,9),		array(0,8,10,18),	array(0,2,9,11),
			array(0,10,12,19),	array(0,3,11,13),	array(0,12,14,20),	array(0,4,13,15),	array(0,6,14,16),
			array(0,15,17,20),	array(0,7,16,18),	array(0,9,17,19),	array(0,11,18,20),	array(0,13,16,19));
			$l = array(0,0,0,0,0,0,0);
			$m = array(0,0,0,0,0,0,0);
			$p = array(0,0,0,0,0,0);
			$aa = 5;
			$ll = $aa;
			$o = 1;
			$f = 0;
			
			$j = 0;
			$k = 0;
			$k1 = 0;
			$j9 = 0;
			while (self::$currentLine <= 1150) {
				self::$nextLine = self::$currentLine + 1;
				switch (self::$currentLine) {
				case 5: break;								 													// 5 rem *** HUNT THE WUMPUS ***
				case 10: break;				 																	// 10 dim p(5)
				case 15: print("INSTRUCTIONS (Y-N) "); break;													// 15 print "INSTRUCTIONS (Y-N)";
				case 20: $i = trim(fgets(STDIN)); break; 														// 20 input $i
				case 25: if ($i == 'N' || $i =='n') self::$nextLine = 35; break;								// 25 if ($i = "N") or ($i = "n") then 35
				case 30: self::gosub(375, 35); break;															// 30 gosub 375
				case 35: self::$nextLine = 80; break;															// 35 goto 80
				case 80: break;																					// 80 rem *** SET UP CAVE (DODECAHEDRAL NODE LIST) ***
																												// 85 dim s(20,3)
																												// 90 for j = 1 to 20
																												// 95 for k = 1 to 3
																												// 100 read s(j,k)
																												// 105 next k
																												// 110 next j
																												// 115 data 2,5,8,1,3,10,2,4,12,3,5,14,1,4,6
																												// 120 data 5,7,15,6,8,17,1,7,9,8,10,18,2,9,11
																												// 125 data 10,12,19,3,11,13,12,14,20,4,13,15,6,14,16
																												// 130 data 15,17,20,7,16,18,9,17,19,11,18,20,13,16,19
																												// 135 def fnA(X)=INT(20*RND(1))+1
																												// 140 def fnB(X)=INT(3*RND(1))+1
																												// 145 def fnC(X)=INT(4*RND(1))+1
				case 150: break;																				// 150 rem *** LOCATE L ARRAY ITEMS ***
				case 155: break;																				// 155 rem *** 1-YOU, 2-WUMPUS, 3&4-PITS, 5&6-BATS ***
				case 160: break;																				// 160 dim l(6)
				case 165: break;																				// 165 dim m(6)
				case 170: $j = 1; break;																		// 170 for j = 1 to 6
				case 175: $l[$j] = self::fnA(); break;															// 175 l(j) = fna(0)
				case 180: $m[$j] = $l[$j]; break;																// 180 m(j) = l(j)
				case 185: ++$j; if ($j <= 6) {self::$nextLine = 175;} break;									// 185 next j
				case 190: break;																				// 190 rem *** CHECK FOR CROSSOVERS (IE l(1)=l(2), ETC) ***
				case 195: $j = 1; break;																		// 195 for j = 1 to 6
				case 200: $k = 1; break;																		// 200 for k = 1 to 6
				case 205: if ($j == $k ) {self::$nextLine = 215;} break;										// 205 if j = k then 215
				case 210: if ($l[$j] == $l[$k]) {self::$nextLine = 170;} break;									// 210 if l(j) = l(k) then 170
				case 215: ++$k; if ($k <= 6) {self::$nextLine = 205;} break;									// 215 next k
				case 220: ++$j; if ($j <= 6) {self::$nextLine = 200;} break;									// 220 next j
				case 225: break;																				// 225 rem *** SET NO. OF ARROWS ***
				case 230: $aa = 5; break;																		// 230 a = 5
				case 235: $ll = $l[1]; break;																	// 235 l = l(1)
				case 240: break;																				// 240 rem *** RUN THE GAME ***
				case 245: print("HUNT THE WUMPUS"); break;														// 245 print "HUNT THE WUMPUS"
				case 250: break;																				// 250 rem *** HAZARD WARNING AND LOCATION ***
				case 255: self::gosub(585, 260); break;															// 255 gosub 585
				case 260: break;																				// 260 rem *** MOVE OR SHOOT ***
				case 265: self::gosub(670, 270); break;															// 265 gosub 670
				case 270: switch($o) {case 1: self::$nextLine = 280; break; case 2: self::$nextLine = 300; break;} break;		// 270 on o goto 280,300
				case 275: break;																				// 275 rem *** SHOOT ***
				case 280: self::gosub(715, 285); break;															// 280 gosub 715
				case 285: if ($f == 0) self::$nextLine = 255; break;											// 285 if f = 0 then 255
				case 290: self::$nextLine = 310; break;															// 290 goto 310
				case 295: break;																				// 295 rem *** MOVE ***
				case 300: self::gosub(975, 305); break;															// 300 gosub 975
				case 305: if ($f == 0) self::$nextLine = 255; break;											// 305 if f = 0 then 255
				case 310: if ($f > 0) self::$nextLine = 335; break;												// 310 if f > 0 then 335
				case 315: break;																				// 315 rem *** LOSE ***
				case 320: print ("HA HA HA - YOU LOSE!"); break;												// 320 print "HA HA HA - YOU LOSE!"
				case 325: self::$nextLine = 340; break;															// 325 goto 340
				case 330: break;																				// 330 rem *** WIN ***
				case 335: print("HEE HEE HEE - THE WUMPUS'LL GET YOU NEXT TIME!!"); break;						// 335 print "HEE HEE HEE - THE WUMPUS'LL GET YOU NEXT TIME!!"
				case 340: $j = 1; break;																		// 340 for j = 1 to 6
				case 345: $l[$j] = $m[$j]; break;																// 345 l(j) = m(j)
				case 350: ++$j; if ($j <= 6) self::$nextLine = 345; break;										// 350 next j
				case 355: print("SAME SETUP (Y-N)"); break;														// 355 print "SAME SETUP (Y-N)";
				case 360: $i = trim(fgets(STDIN)); break;														// 360 input $i
				case 365: if ($i != 'Y' && $i != 'y') self::$nextLine = 170; break;								// 365 if ($i <> "Y") and ($i <> "y") then 170
				case 370: self::$nextLine = 230; break;															// 370 goto 230
				case 375: break;																				// 375 rem *** INSTRUCTIONS ***
				case 380: print("WELCOME TO 'HUNT THE WUMPUS'\n"); break;					 					// 380 print "WELCOME TO 'HUNT THE WUMPUS'"
				case 385: print(																				// 385 print
						"  THE WUMPUS LIVES IN A CAVE OF 20 ROOMS. EACH ROOM\n");	break;						// "  THE WUMPUS LIVES IN A CAVE OF 20 ROOMS. EACH ROOM"
				case 390: print("HAS 3 TUNNELS LEADING TO OTHER ROOMS. (LOOK AT A\n"); break;					// 390 print "HAS 3 TUNNELS LEADING TO OTHER ROOMS. (LOOK AT A"
				case 395: print(																				// 395 print
						"DODECAHEDRON TO SEE HOW THIS WORKS-IF YOU DON'T KNOW\n"); break;						// "DODECAHEDRON TO SEE HOW THIS WORKS-IF YOU DON'T KNOW"
				case 400: print("WHAT A DODECAHEDRON IS, ASK SOMEONE)\n"); break;								// 400 print "WHAT A DODECAHEDRON IS, ASK SOMEONE)"
				case 405: print("\n"); break;																	// 405 print
				case 410: print("     HAZARDS:\n"); break;														// 410 print "     HAZARDS:"
				case 415: print(																				// 415 print
						" BOTTOMLESS PITS - TWO ROOMS HAVE BOTTOMLESS PITS IN THEM\n"); break;					// " BOTTOMLESS PITS - TWO ROOMS HAVE BOTTOMLESS PITS IN THEM"
				case 420: print(																				// 420 print
						"     IF YOU GO THERE, YOU FALL INTO THE PIT (& LOSE!)\n"); break;						// "     IF YOU GO THERE, YOU FALL INTO THE PIT (& LOSE!)"
				case 425: print(																				// 425 print
						" SUPER BATS - TWO OTHER ROOMS HAVE SUPER BATS. IF YOU\n"); break;						// " SUPER BATS - TWO OTHER ROOMS HAVE SUPER BATS. IF YOU"
				case 430: print(																				// 430 print
						"     GO THERE, A BAT GRABS YOU AND TAKES YOU TO SOME OTHER\n"); break;					// "     GO THERE, A BAT GRABS YOU AND TAKES YOU TO SOME OTHER"
				case 435: print("     ROOM AT RANDOM. (WHICH MAY BE TROUBLESOME)\n"); break;					// 435 print "     ROOM AT RANDOM. (WHICH MAY BE TROUBLESOME)"
				case 440: print("HIT RETURN TO CONTINUE"); fgets(STDIN); break;									// 440 input "HIT RETURN TO CONTINUE";a$
				case 445: print("     WUMPUS:\n"); break;														// 445 print "     WUMPUS:"
				case 450: print(																				// 450 print
						" THE WUMPUS IS NOT BOTHERED BY HAZARDS (HE HAS SUCKER\n"); break;						// " THE WUMPUS IS NOT BOTHERED BY HAZARDS (HE HAS SUCKER"
				case 455: print(" FEET AND IS TOO BIG FOR A BAT TO LIFT).  USUALLY\n"); break; 					// 455 print " FEET AND IS TOO BIG FOR A BAT TO LIFT).  USUALLY"
				case 460: print(																				// 460 print
						" HE IS ASLEEP.  TWO THINGS WAKE HIM UP: YOU SHOOTING AN\n"); break;					// " HE IS ASLEEP.  TWO THINGS WAKE HIM UP: YOU SHOOTING AN"
				case 465: print("ARROW OR YOU ENTERING HIS ROOM.\n"); break;									// 465 print "ARROW OR YOU ENTERING HIS ROOM."
				case 470: print(																				// 470 print
						"     IF THE WUMPUS WAKES HE MOVES (P=.75) ONE ROOM\n"); break;							// "     IF THE WUMPUS WAKES HE MOVES (P=.75) ONE ROOM"
				case 475: print(																				// 475 print
						" OR STAYS STILL (P=.25).  AFTER THAT, IF HE IS WHERE YOU\n"); break;					// " OR STAYS STILL (P=.25).  AFTER THAT, IF HE IS WHERE YOU"
				case 480: print(" ARE, HE EATS YOU UP AND YOU LOSE!\n"); break;									// 480 print " ARE, HE EATS YOU UP AND YOU LOSE!"
				case 485: print("\n"); break;																	// 485 print
				case 490: print("     YOU:\n"); break;															// 490 print "     YOU:"
				case 495: print(" EACH TURN YOU MAY MOVE OR SHOOT A CROOKED ARROW\n"); break; 					// 495 print " EACH TURN YOU MAY MOVE OR SHOOT A CROOKED ARROW"
				case 500: print(																				// 500 print
						"   MOVING:  YOU CAN MOVE ONE ROOM (THRU ONE TUNNEL)\n"); break;						// "   MOVING:  YOU CAN MOVE ONE ROOM (THRU ONE TUNNEL)"
				case 505: print(																				// 505 print
						"   ARROWS:  YOU HAVE 5 ARROWS.  YOU LOSE WHEN YOU RUN OUT\n"); break;					// "   ARROWS:  YOU HAVE 5 ARROWS.  YOU LOSE WHEN YOU RUN OUT"
				case 510: print(																				// 510 print
						"   EACH ARROW CAN GO FROM 1 TO 5 ROOMS. YOU AIM BY TELLING\n"); break;					// "   EACH ARROW CAN GO FROM 1 TO 5 ROOMS. YOU AIM BY TELLING"
				case 515: print(																				// 515 print
						"   THE COMPUTER THE ROOM#S YOU WANT THE ARROW TO GO TO.\n"); break;					// "   THE COMPUTER THE ROOM#S YOU WANT THE ARROW TO GO TO."
				case 520: print(																				// 520 print
						"   IF THE ARROW CAN'T GO THAT WAY (IF NO TUNNEL) IT MOVES\n"); break; 					// "   IF THE ARROW CAN'T GO THAT WAY (IF NO TUNNEL) IT MOVES"
				case 525: print("   AT RANDOM TO THE NEXT ROOM.\n"); break;										// 525 print "   AT RANDOM TO THE NEXT ROOM."
				case 530: print("     IF THE ARROW HITS THE WUMPUS, YOU WIN.\n"); break;						// 530 print "     IF THE ARROW HITS THE WUMPUS, YOU WIN."
				case 535: print("     IF THE ARROW HITS YOU, YOU LOSE.\n"); break;								// 535 print "     IF THE ARROW HITS YOU, YOU LOSE."
				case 540: print("HIT RETURN TO CONTINUE\n"); fgets(STDIN); break;								// 540 input "HIT RETURN TO CONTINUE";a$
				case 545: print("    WARNINGS:\n"); break;														// 545 print "    WARNINGS:"
				case 550: print(																				// 550 print
						"     WHEN YOU ARE ONE ROOM AWAY FROM A WUMPUS OR HAZARD,\n"); break;					// "     WHEN YOU ARE ONE ROOM AWAY FROM A WUMPUS OR HAZARD,"
				case 555: print("     THE COMPUTER SAYS:\n"); break;											// 555 print "     THE COMPUTER SAYS:"
				case 560: print(" WUMPUS:  'I SMELL A WUMPUS'\n"); break;										// 560 print " WUMPUS:  'I SMELL A WUMPUS'"
				case 565: print(" BAT   :  'BATS NEARBY'\n"); break;											// 565 print " BAT   :  'BATS NEARBY'"
				case 570: print(" PIT   :  'I FEEL A DRAFT'\n"); break;											// 570 print " PIT   :  'I FEEL A DRAFT'"
				case 575: print("\n"); break;																	// 575 print
				case 580: self::returnFromGosub(); break;														// 580 return
				case 585: break;																				// 585 rem *** PRINT LOCATION & HAZARD WARNINGS ***
				case 590: print("\n"); break;																	// 590 print
				case 595: $j = 2; break;																		// 595 for j = 2 to 6
				case 600: $k = 1; break;																		// 600 for k = 1 to 3
				case 605: if ($s[$l[1]][$k] != $l[$j]) self::$nextLine = 640; break;							// 605 if s(l(1),k) <> l(j) then 640
				case 610: switch($j-1) {																		// 610 on j-1 goto 615,625,625,635,635
							case 1: self::$nextLine = 615; break;
							case 2:
							case 3: self::$nextLine = 625; break;
							case 4:
							case 5: self::$nextLine = 635; break;
							}; break;
				case 615: print("I SMELL A WUMPUS!\n"); break;													// 615 print "I SMELL A WUMPUS!"
				case 620: self::$nextLine = 640; break;															// 620 goto 640
				case 625: print("I FEEL A DRAFT\n"); break;														// 625 print "I FEEL A DRAFT"
				case 630: self::$nextLine = 640; break;															// 630 goto 640
				case 635: print("BATS NEARBY!\n"); break;														// 635 print "BATS NEARBY!"
				case 640: ++$k; if ($k <= 3) self::$nextLine = 605; break;										// 640 next k
				case 645: ++$j; if ($j <= 6) self::$nextLine = 600; break;										// 645 next j
				case 650: print("YOUR ARE IN ROOM "); print($l[1] . "\n"); break;								// 650 print "YOU ARE IN ROOM ";l(1)
				case 655: print("TUNNELS LEAD TO "); print($s[$ll] [1]);										// 655 print "TUNNELS LEAD TO ";s(l,1);" ";s(l,2);" ";s(l,3)
							print(" "); print($s[$ll][2]); 
							print(" "); print($s[$ll][3] . "\n"); break;
				case 660: print("\n"); break;																	// 660 print
				case 665: self::returnFromGosub(); break;														// 665 return
				case 670: break;																				// 670 rem *** CHOOSE OPTION ***
				case 675: print("SHOOT OR MOVE (S-M) "); break;													// 675 print "SHOOT OR MOVE (S-M)";
				case 680: $i = trim(fgets(STDIN)); break;														// 680 input $i
				case 685: if ($i != 'S' && $i != 's') self::$nextLine = 700; break;								// 685 if ($i <> "S") and ($i <> "s") then 700
				case 690: $o = 1; break;																		// 690 o = 1
				case 695: self::returnFromGosub(); break;														// 695 return
				case 700: if ($i != 'M' && $i != 'm') self::$nextLine = 675; break;								// 700 if ($i <> "M") and ($i <> "m") then 675
				case 705: $o = 2; break;																		// 705 o = 2
				case 710: self::returnFromGosub(); break;														// 710 return
				case 715: break;																				// 715 rem *** ARROW ROUTINE ***
				case 720: $f = 0; break;																		// 720 f = 0
				case 725: break;																				// 725 rem *** PATH OF ARROW ***
				case 735: print("NO. OF ROOMS (1-5) "); break;													// 735 print "NO. OF ROOMS (1-5)";
				case 740: $j9 = self::readInt(); break;															// 740 input j9
				case 745: if ($j9 < 1) self::$nextLine = 735; break;											// 745 if j9 < 1 then 735
				case 750: if ($j9 > 5) self::$nextLine = 735; break;											// 750 if j9 > 5 then 735
				case 755: $k = 1; break;																		// 755 for k = 1 to j9
				case 760: print("ROOM # "); break;																// 760 print "ROOM #";
				case 765: $p[$k] = self::readInt(); break;														// 765 input p(k)
				case 770: if ($k <= 2) self::$nextLine = 790; break;											// 770 if k <= 2 then 790
				case 775: if ($p[$k] != $p[$k-2]) self::$nextLine = 790; break;									// 775 if p(k) <> p(k-2) then 790
				case 780: print("ARROWS AREN'T THAT CROOKED - TRY ANOTHER ROOM\n"); break;						// 780 print "ARROWS AREN'T THAT CROOKED - TRY ANOTHER ROOM"
				case 785: self::$nextLine = 760; break;															// 785 goto 760
				case 790: ++$k; if ($k <= $j9) self::$nextLine = 760; break;									// 790 next k
				case 795: break;																				// 795 rem *** SHOOT ARROW ***
				case 800: $ll = $l[1]; break;																	// 800 l = l(1)
				case 805: $k = 1; break;																		// 805 for k = 1 to j9
				case 810: $k1 = 1; break;																		// 810 for k1 = 1 to 3
				case 815: if ($s[$ll][$k1] == $p[$k]) self::$nextLine = 895; break;								// 815 if s(l,k1) = p(k) then 895
				case 820: ++$k1; if ($k1 <= 3) self::$nextLine = 815; break;									// 820 next k1
				case 825: break;																				// 825 rem *** NO TUNNEL FOR ARROW ***
				case 830: $ll = $s[$ll][self::fnB()]; break;													// 830 l = s(l,fnb(1))
				case 835: self::$nextLine = 900; break;															// 835 goto 900
				case 840: ++$k; if ($k <= $j9) self::$nextLine = 810; break;									// 840 next k
				case 845: print("MISSED\n"); break;																// 845 print "MISSED"
				case 850: $ll = $l[1]; break;																	// 850 l = l(1)
				case 855: break;																				// 855 rem *** MOVE WUMPUS ***
				case 860: self::gosub(935, 865); break;															// 860 gosub 935
				case 865: break;																				// 865 rem *** AMMO CHECK ***
				case 870: $aa = $aa - 1; break;																	// 870 a = a-1
				case 875: if ($aa > 0) self::$nextLine = 885; break;											// 875 if a > 0 then 885
				case 880: $f = -1; break;																		// 880 f = -1
				case 885: self::returnFromGosub(); break;														// 885 return
				case 890: break;																				// 890 rem *** SEE IF ARROW IS AT l(1) OR AT l(2)
				case 895: $ll = $p[$k]; break;																	// 895 l = p(k)
				case 900: if ($ll != $l[2]) self::$nextLine = 920; break;										// 900 if l <> l(2) then 920
				case 905: print("AHA! YOU GOT THE WUMPUS!\n"); break;											// 905 print "AHA! YOU GOT THE WUMPUS!"
				case 910: $f = 1; break;																		// 910 f = 1
				case 915: self::returnFromGosub(); break;														// 915 return
				case 920: if ($ll != $l[1]) self::$nextLine = 840; break;										// 920 if l <> l(1) then 840
				case 925: print ("OUCH! ARROW GOT YOU!\n"); break;												// 925 print "OUCH! ARROW GOT YOU!"
				case 930: self::$nextLine = 880; break;															// 930 goto 880
				case 935: break;																				// 935 rem *** MOVE WUMPUS ROUTINE ***
				case 940: $k = self::fnC(); break;																// 940 k = fnc(0)
				case 945: if ($k == 4) self::$nextLine = 955; break;											// 945 if k = 4 then 955
				case 950: $l[2] = $s[$l[2]][$k]; break;															// 950 l(2) = s(l(2),k)
				case 955: if ($l[2] != $ll) self::$nextLine = 970; break;										// 955 if l(2) <> l then 970
				case 960: print("TSK TSK TSK - WUMPUS GOT YOU!\n"); break;										// 960 print "TSK TSK TSK - WUMPUS GOT YOU!"
				case 965: $f = -1; break;																		// 965 f = -1
				case 970: self::returnFromGosub(); break;														// 970 return
				case 975: break;																				// 975 rem *** MOVE ROUTINE ***
				case 980: $f = 0; break;																		// 980 f = 0
				case 985: print("WHERE TO "); break;															// 985 print "WHERE TO";
				case 990: $ll = self::readInt(); break;															// 990 input l
				case 995: if ($ll < 1) self::$nextLine = 985; break;											// 995 if l < 1 then 985
				case 1000: if ($ll > 20) self::$nextLine = 985; break;											// 1000 if l > 20 then 985
				case 1005: $k = 1; break;																		// 1005 for k = 1 to 3
				case 1010: break;																				// 1010 rem *** CHECK IF LEGAL MOVE ***
				case 1015: if ($s[$l[1] ][$k] == $ll) self::$nextLine = 1045; break;							// 1015 if s(l(1),k) = l then 1045
				case 1020: ++$k; if ($k <= 3) self::$nextLine = 1010; break;									// 1020 next k
				case 1025: if ($ll == $l[1]) self::$nextLine = 1045; break;										// 1025 if l = l(1) then 1045
				case 1030: print("NOT POSSIBLE - "); break;														// 1030 print "NOT POSSIBLE -";
				case 1035: self::$nextLine = 985; break;														// 1035 goto 985
				case 1040: break;																				// 1040 rem *** CHECK FOR HAZARDS ***
				case 1045: $l[1] = $ll; break;																	// 1045 l(1) = l
				case 1050: break;																				// 1050 rem *** WUMPUS ***
				case 1055: if ($ll != $l[2]) self::$nextLine = 1090; break;										// 1055 if l <> l(2) then 1090
				case 1060: print("... OOPS! BUMPED A WUMPUS!\n"); break;										// 1060 print "... OOPS! BUMPED A WUMPUS!"
				case 1065: break;																				// 1065 rem *** MOVE WUMPUS ***
				case 1070: self::gosub(940, 1075); break;														// 1070 gosub 940
				case 1075: if ($f == 0) self::$nextLine = 1090; break;											// 1075 if f = 0 then 1090
				case 1080: self::returnFromGosub(); break;														// 1080 return
				case 1085: break;																				// 1085 rem *** PIT ***
				case 1090: if ($ll == $l[3]) self::$nextLine = 1100; break;										// 1090 if l = l(3) then 1100
				case 1095: if ($ll != $l[4]) self::$nextLine = 1120; break;										// 1095 if l <> l(4) then 1120
				case 1100: print("YYYYIIIIEEEE . . . FELL IN PIT\n"); break;									// 1100 print "YYYYIIIIEEEE . . . FELL IN PIT"
				case 1105: $f = -1; break;																		// 1105 f = -1
				case 1110: self::returnFromGosub(); break;														// 1110 return
				case 1115: break;																				// 1115 rem *** BATS ***
				case 1120: if ($ll == $l[5]) self::$nextLine = 1130; break;										// 1120 if l = l(5) then 1130
				case 1125: if ($ll != $l[6]) self::$nextLine = 1145; break;										// 1125 if l <> l(6) then 1145
				case 1130: print("ZAP--SUPER BAT SNATCH! ELSEWHEREVILLE FOR YOU!\n"); break;					// 1130 print "ZAP--SUPER BAT SNATCH! ELSEWHEREVILLE FOR YOU!"
				case 1135: $ll = self::fnA(); break;															// 1135 l = fna(1)
				case 1140: self::$nextLine = 1045; break;														// 1140 goto 1045
				case 1145: self::returnFromGosub(); break;														// 1145 return
				case 1150: break;																				// 1150 end
				}
				self::$currentLine = self::$nextLine;
			}
		} catch (Exception $e) {
			// TODO Auto-generated catch block
			print ($e->getTraceAsString());
		}
	}
	private static function gosub($gosubLine, $lineToReturnTo) {
		self::$nextLine = $gosubLine;
		array_push(self::$returnLine, $lineToReturnTo);
	}
	private static function returnFromGosub() {
		if (empty(self::$returnLine))
			self::$nextLine = 1151;
		else
			self::$nextLine = array_pop(self::$returnLine);
	}
	
	static function fnA() {
		return rand(1,20);
	}

	static function fnB() {
		return rand(1,3);
	}

	static function fnC() {
		return rand(1,4);
	}
	
	private static function readInt() {
		return trim(fgets(STDIN));
	}
}