import java.util.ArrayDeque
import java.util.Deque
import java.util.Random

class Wumpus {
    private var currentLine: Int = 0
    private val returnLine: Deque<Int> = ArrayDeque()
	private var nextLine: Int = 0
    var random = Random()
	var console = Console()
	var earlyExitHack: Int = 1150

	fun main(args: Array<String>) {
		try {
			currentLine = 5
			var iS = '\u0000'
			val s = arrayOf(arrayOf(0,0,0,0),
			arrayOf(0,2,5,8), arrayOf(0,1,3,10), arrayOf(0,2,4,12), arrayOf(0,3,5,14), arrayOf(0,1,4,6),
			arrayOf(0,5,7,15), arrayOf(0,6,8,17), arrayOf(0,1,7,9), arrayOf(0,8,10,18), arrayOf(0,2,9,11),
			arrayOf(0,10,12,19), arrayOf(0,3,11,13), arrayOf(0,12,14,20), arrayOf(0,4,13,15), arrayOf(0,6,14,16),
			arrayOf(0,15,17,20), arrayOf(0,7,16,18), arrayOf(0,9,17,19), arrayOf(0,11,18,20), arrayOf(0,13,16,19))
			val l = Array(7) {0}
            val m = Array(7) {0}
			val p = Array(6) {0}
			var aa = 5
			var ll = aa
			var o = 1
			var f = 0

			var j = 0
			var k = 0
			var k1 = 0
			var j9 = 0
			while (currentLine <= 1150 && currentLine != earlyExitHack) {
				nextLine = currentLine + 1
				when (currentLine) {
				5 -> {}										 													// 5 rem *** HUNT THE WUMPUS ***
				10 -> {}					 																	// 10 dim p(5)
				15 -> console.print("INSTRUCTIONS (Y-N) ")																// 15 print "INSTRUCTIONS (Y-N)";
				20 -> { iS = console.readln() }	 								// 20 input i$
				25 -> if (iS == 'N' || iS =='n') nextLine = 35													// 25 if (i$ = "N") or (i$ = "n") then 35
				30 -> gosub(375, 35)														// 30 gosub 375
				35 -> nextLine = 80																				// 35 goto 80
				80 -> {}																						// 80 rem *** SET UP CAVE (DODECAHEDRAL NODE LIST) ***
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
				150 -> {}																						// 150 rem *** LOCATE L ARRAY ITEMS ***
				155 -> {}																						// 155 rem *** 1-YOU, 2-WUMPUS, 3&4-PITS, 5&6-BATS ***
				160 -> {}																						// 160 dim l(6)
				165 -> {}																						// 165 dim m(6)
				170 -> j = 1																					// 170 for j = 1 to 6
				175 -> l[j] = fnA()																				// 175 l(j) = fna(0)
				180 -> m[j] = l[j]																				// 180 m(j) = l(j)
				185 -> { ++j; if (j <= 6) nextLine = 175 }														// 185 next j
				190 -> {}																						// 190 rem *** CHECK FOR CROSSOVERS (IE l(1)=l(2), ETC) ***
				195 -> j = 1																					// 195 for j = 1 to 6
				200 -> k = 1																					// 200 for k = 1 to 6
				205 -> if (j == k ) nextLine = 215																// 205 if j = k then 215
				210 -> if (l[j] == l[k]) nextLine = 170															// 210 if l(j) = l(k) then 170
				215 -> { ++k; if (k <= 6) nextLine = 205 }														// 215 next k
				220 -> { ++j; if (j <= 6) nextLine = 200 }														// 220 next j
				225 -> {}																						// 225 rem *** SET NO. OF ARROWS ***
				230 -> aa = 5																					// 230 a = 5
				235 -> ll = l[1]																				// 235 l = l(1)
				240 -> {}																						// 240 rem *** RUN THE GAME ***
				245 -> console.println("HUNT THE WUMPUS")														// 245 print "HUNT THE WUMPUS"
				250 -> {}																						// 250 rem *** HAZARD WARNING AND LOCATION ***
				255 -> gosub(585, 260)													// 255 gosub 585
				260 -> {}																						// 260 rem *** MOVE OR SHOOT ***
				265 -> gosub(670, 270)													// 265 gosub 670
				270 -> when(o) {1 -> nextLine = 280; 2 -> nextLine = 300}										// 270 on o goto 280,300
				275 -> {}																						// 275 rem *** SHOOT ***
				280 -> gosub(715, 285)													// 280 gosub 715
				285 -> if (f == 0) nextLine = 255																// 285 if f = 0 then 255
				290 -> nextLine = 310																			// 290 goto 310
				295 -> {}																						// 295 rem *** MOVE ***
				300 -> gosub(975, 305)													// 300 gosub 975
				305 -> if (f == 0) nextLine = 255																// 305 if f = 0 then 255
				310 -> if (f > 0) nextLine = 335																// 310 if f > 0 then 335
				315 -> {}																						// 315 rem *** LOSE ***
				320 -> console.println("HA HA HA - YOU LOSE!")															// 320 print "HA HA HA - YOU LOSE!"
				325 -> nextLine = 340																			// 325 goto 340
				330 -> {}																						// 330 rem *** WIN ***
				335 -> console.println("HEE HEE HEE - THE WUMPUS'LL GET YOU NEXT TIME!!")								// 335 print "HEE HEE HEE - THE WUMPUS'LL GET YOU NEXT TIME!!"
				340 -> j = 1																					// 340 for j = 1 to 6
				345 -> l[j] = m[j]																				// 345 l(j) = m(j)
				350 -> { ++j; if (j <= 6) nextLine = 345 }														// 350 next j
				355 -> console.print("SAME SETUP (Y-N)")																// 355 print "SAME SETUP (Y-N)";
				360 -> { iS = console.readln() }									// 360 input i$
				365 -> if (iS != 'Y' && iS != 'y') nextLine = 170												// 365 if (i$ <> "Y") and (i$ <> "y") then 170
				370 -> nextLine = 230																			// 370 goto 230
				375 -> {}																						// 375 rem *** INSTRUCTIONS ***
				380 -> console.println("WELCOME TO 'HUNT THE WUMPUS'")											 		// 380 print "WELCOME TO 'HUNT THE WUMPUS'"
				385 -> console.println(																					// 385 print
						"  THE WUMPUS LIVES IN A CAVE OF 20 ROOMS. EACH ROOM")									// "  THE WUMPUS LIVES IN A CAVE OF 20 ROOMS. EACH ROOM"
				390 -> console.println("HAS 3 TUNNELS LEADING TO OTHER ROOMS. (LOOK AT A")								// 390 print "HAS 3 TUNNELS LEADING TO OTHER ROOMS. (LOOK AT A"
				395 -> console.println(																					// 395 print
						"DODECAHEDRON TO SEE HOW THIS WORKS-IF YOU DON'T KNOW")									// "DODECAHEDRON TO SEE HOW THIS WORKS-IF YOU DON'T KNOW"
				400 -> console.println("WHAT A DODECAHEDRON IS, ASK SOMEONE)")											// 400 print "WHAT A DODECAHEDRON IS, ASK SOMEONE)"
				405 -> console.println("")																				// 405 print
				410 -> console.println("     HAZARDS:")																	// 410 print "     HAZARDS:"
				415 -> console.println(																					// 415 print
						" BOTTOMLESS PITS - TWO ROOMS HAVE BOTTOMLESS PITS IN THEM")							// " BOTTOMLESS PITS - TWO ROOMS HAVE BOTTOMLESS PITS IN THEM"
				420 -> console.println(																					// 420 print
						"     IF YOU GO THERE, YOU FALL INTO THE PIT (& LOSE!)")								// "     IF YOU GO THERE, YOU FALL INTO THE PIT (& LOSE!)"
				425 -> console.println(																					// 425 print
						" SUPER BATS - TWO OTHER ROOMS HAVE SUPER BATS. IF YOU")								// " SUPER BATS - TWO OTHER ROOMS HAVE SUPER BATS. IF YOU"
				430 -> console.println(																					// 430 print
						"     GO THERE, A BAT GRABS YOU AND TAKES YOU TO SOME OTHER")							// "     GO THERE, A BAT GRABS YOU AND TAKES YOU TO SOME OTHER"
				435 -> console.println("     ROOM AT RANDOM. (WHICH MAY BE TROUBLESOME)")								// 435 print "     ROOM AT RANDOM. (WHICH MAY BE TROUBLESOME)"
				440 -> { console.input("HIT RETURN TO CONTINUE") }												// 440 input "HIT RETURN TO CONTINUE";a$
				445 -> console.println("     WUMPUS:")																	// 445 print "     WUMPUS:"
				450 -> console.println(																					// 450 print
						" THE WUMPUS IS NOT BOTHERED BY HAZARDS (HE HAS SUCKER")								// " THE WUMPUS IS NOT BOTHERED BY HAZARDS (HE HAS SUCKER"
				455 -> console.println(" FEET AND IS TOO BIG FOR A BAT TO LIFT).  USUALLY")						 		// 455 print " FEET AND IS TOO BIG FOR A BAT TO LIFT).  USUALLY"
				460 -> console.println(																					// 460 print
						" HE IS ASLEEP.  TWO THINGS WAKE HIM UP: YOU SHOOTING AN")								// " HE IS ASLEEP.  TWO THINGS WAKE HIM UP: YOU SHOOTING AN"
				465 -> console.println("ARROW OR YOU ENTERING HIS ROOM.")												// 465 print "ARROW OR YOU ENTERING HIS ROOM."
				470 -> console.println(																					// 470 print
						"     IF THE WUMPUS WAKES HE MOVES (P=.75) ONE ROOM")									// "     IF THE WUMPUS WAKES HE MOVES (P=.75) ONE ROOM"
				475 -> console.println(																					// 475 print
						" OR STAYS STILL (P=.25).  AFTER THAT, IF HE IS WHERE YOU")								// " OR STAYS STILL (P=.25).  AFTER THAT, IF HE IS WHERE YOU"
				480 -> console.println(" ARE, HE EATS YOU UP AND YOU LOSE!")											// 480 print " ARE, HE EATS YOU UP AND YOU LOSE!"
				485 -> console.println("")																				// 485 print
				490 -> console.println("     YOU:")																		// 490 print "     YOU:"
				495 -> console.println(" EACH TURN YOU MAY MOVE OR SHOOT A CROOKED ARROW")						 		// 495 print " EACH TURN YOU MAY MOVE OR SHOOT A CROOKED ARROW"
				500 -> console.println(																					// 500 print
						"   MOVING:  YOU CAN MOVE ONE ROOM (THRU ONE TUNNEL)")									// "   MOVING:  YOU CAN MOVE ONE ROOM (THRU ONE TUNNEL)"
				505 -> console.println(																					// 505 print
						"   ARROWS:  YOU HAVE 5 ARROWS.  YOU LOSE WHEN YOU RUN OUT")							// "   ARROWS:  YOU HAVE 5 ARROWS.  YOU LOSE WHEN YOU RUN OUT"
				510 -> console.println(																					// 510 print
						"   EACH ARROW CAN GO FROM 1 TO 5 ROOMS. YOU AIM BY TELLING")							// "   EACH ARROW CAN GO FROM 1 TO 5 ROOMS. YOU AIM BY TELLING"
				515 -> console.println(																					// 515 print
						"   THE COMPUTER THE ROOM#S YOU WANT THE ARROW TO GO TO.")								// "   THE COMPUTER THE ROOM#S YOU WANT THE ARROW TO GO TO."
				520 -> console.println(																					// 520 print
						"   IF THE ARROW CAN'T GO THAT WAY (IF NO TUNNEL) IT MOVES")		 					// "   IF THE ARROW CAN'T GO THAT WAY (IF NO TUNNEL) IT MOVES"
				525 -> console.println("   AT RANDOM TO THE NEXT ROOM.")												// 525 print "   AT RANDOM TO THE NEXT ROOM."
				530 -> console.println("     IF THE ARROW HITS THE WUMPUS, YOU WIN.")									// 530 print "     IF THE ARROW HITS THE WUMPUS, YOU WIN."
				535 -> console.println("     IF THE ARROW HITS YOU, YOU LOSE.")											// 535 print "     IF THE ARROW HITS YOU, YOU LOSE."
				540 -> { console.input("HIT RETURN TO CONTINUE") }												// 540 input "HIT RETURN TO CONTINUE";a$
				545 -> console.println("    WARNINGS:")																	// 545 print "    WARNINGS:"
				550 -> console.println(																					// 550 print
						"     WHEN YOU ARE ONE ROOM AWAY FROM A WUMPUS OR HAZARD,")								// "     WHEN YOU ARE ONE ROOM AWAY FROM A WUMPUS OR HAZARD,"
				555 -> console.println("     THE COMPUTER SAYS:")														// 555 print "     THE COMPUTER SAYS:"
				560 -> console.println(" WUMPUS:  'I SMELL A WUMPUS'")													// 560 print " WUMPUS:  'I SMELL A WUMPUS'"
				565 -> console.println(" BAT   :  'BATS NEARBY'")														// 565 print " BAT   :  'BATS NEARBY'"
				570 -> console.println(" PIT   :  'I FEEL A DRAFT'")													// 570 print " PIT   :  'I FEEL A DRAFT'"
				575 -> console.println("")																				// 575 print
				580 -> returnFromGosub()																		// 580 return
				585 -> {}																						// 585 rem *** PRINT LOCATION & HAZARD WARNINGS ***
				590 -> console.println("")																				// 590 print
				595 -> j = 2																					// 595 for j = 2 to 6
				600 -> k = 1																					// 600 for k = 1 to 3
				605 -> if (s[l[1]][k] != l[j]) nextLine = 640													// 605 if s(l(1),k) <> l(j) then 640
				610 -> when(j-1) {																				// 610 on j-1 goto 615,625,625,635,635
							1 -> nextLine = 615
							2,3 -> nextLine = 625
							4, 5 -> nextLine = 635
						}
				615 -> console.println("I SMELL A WUMPUS!")																// 615 print "I SMELL A WUMPUS!"
				620 -> nextLine = 640																			// 620 goto 640
				625 -> console.println("I FEEL A DRAFT")																// 625 print "I FEEL A DRAFT"
				630 -> nextLine = 640																			// 630 goto 640
				635 -> console.println("BATS NEARBY!")																	// 635 print "BATS NEARBY!"
				640 -> { ++k; if (k <= 3) nextLine = 605 }														// 640 next k
				645 -> { ++j; if (j <= 6) nextLine = 600 }														// 645 next j
				650 -> { console.print("YOUR ARE IN ROOM "); console.println(l[1]) }											// 650 print "YOU ARE IN ROOM ";l(1)
				655 -> { console.print("TUNNELS LEAD TO ")				                                                // 655 print "TUNNELS LEAD TO ";s(l,1);" ";s(l,2);" ";s(l,3)
					console.print(s[ll][1])
					console.print(" "); console.print(s[ll][2])
					console.print(" "); console.println(s[ll][3])
				}
				660 -> console.println("")																		// 660 print
				665 -> returnFromGosub()																		// 665 return
				670 -> {}																						// 670 rem *** CHOOSE OPTION ***
				675 -> console.print("SHOOT OR MOVE (S-M) ")															// 675 print "SHOOT OR MOVE (S-M)";
				680 -> { iS = console.readln() 								}									// 680 input i$
				685 -> if (iS != 'S' && iS != 's') nextLine = 700												// 685 if (i$ <> "S") and (i$ <> "s") then 700
				690 -> o = 1																					// 690 o = 1
				695 -> returnFromGosub()																		// 695 return
				700 -> if (iS != 'M' && iS != 'm') nextLine = 675												// 700 if (i$ <> "M") and (i$ <> "m") then 675
				705 -> o = 2																					// 705 o = 2
				710 -> returnFromGosub()																		// 710 return
				715 -> {}																						// 715 rem *** ARROW ROUTINE ***
				720 -> f = 0																					// 720 f = 0
				725 -> {}																						// 725 rem *** PATH OF ARROW ***
				735 -> console.print("NO. OF ROOMS (1-5) ")																// 735 print "NO. OF ROOMS (1-5)";
				740 -> j9 = console.readInt()																			// 740 input j9
				745 -> if (j9 < 1) nextLine = 735																// 745 if j9 < 1 then 735
				750 -> if (j9 > 5) nextLine = 735																// 750 if j9 > 5 then 735
				755 -> k = 1																					// 755 for k = 1 to j9
				760 -> console.print("ROOM # ")																			// 760 print "ROOM #";
				765 -> p[k] = console.readInt()																			// 765 input p(k)
				770 -> if (k <= 2) nextLine = 790																// 770 if k <= 2 then 790
				775 -> if (p[k] != p[k-2]) nextLine = 790														// 775 if p(k) <> p(k-2) then 790
				780 -> console.println("ARROWS AREN'T THAT CROOKED - TRY ANOTHER ROOM")									// 780 print "ARROWS AREN'T THAT CROOKED - TRY ANOTHER ROOM"
				785 -> nextLine = 760																			// 785 goto 760
				790 -> { ++k; if (k <= j9) nextLine = 760 }														// 790 next k
				795 -> {}																						// 795 rem *** SHOOT ARROW ***
				800 -> ll = l[1]																				// 800 l = l(1)
				805 -> k = 1																					// 805 for k = 1 to j9
				810 -> k1 = 1																					// 810 for k1 = 1 to 3
				815 -> if (s[ll][k1] == p[k]) nextLine = 895													// 815 if s(l,k1) = p(k) then 895
				820 -> { ++k1; if (k1 <= 3) nextLine = 815 }													// 820 next k1
				825 -> {}																						// 825 rem *** NO TUNNEL FOR ARROW ***
				830 -> ll = s[ll][fnB()]																		// 830 l = s(l,fnb(1))
				835 -> nextLine = 900																			// 835 goto 900
				840 -> { ++k; if (k <= j9) nextLine = 810 }														// 840 next k
				845 -> console.println("MISSED")																		// 845 print "MISSED"
				850 -> ll = l[1]																				// 850 l = l(1)
				855 -> {}																						// 855 rem *** MOVE WUMPUS ***
				860 -> gosub(935, 865)													// 860 gosub 935
				865 -> {}																						// 865 rem *** AMMO CHECK ***
				870 -> aa -= 1																					// 870 a = a-1
				875 -> if (aa > 0) nextLine = 885																// 875 if a > 0 then 885
				880 -> f = -1																					// 880 f = -1
				885 -> returnFromGosub()																		// 885 return
				890 -> {}																						// 890 rem *** SEE IF ARROW IS AT l(1) OR AT l(2)
				895 -> ll = p[k]																				// 895 l = p(k)
				900 -> if (ll != l[2]) nextLine = 920															// 900 if l <> l(2) then 920
				905 -> console.println("AHA! YOU GOT THE WUMPUS!")														// 905 print "AHA! YOU GOT THE WUMPUS!"
				910 -> f = 1																					// 910 f = 1
				915 -> returnFromGosub()																		// 915 return
				920 -> if (ll != l[1]) nextLine = 840															// 920 if l <> l(1) then 840
				925 -> console.println ("OUCH! ARROW GOT YOU!")															// 925 print "OUCH! ARROW GOT YOU!"
				930 -> nextLine = 880																			// 930 goto 880
				935 -> {}																						// 935 rem *** MOVE WUMPUS ROUTINE ***
				940 -> k = fnC()																				// 940 k = fnc(0)
				945 -> if (k == 4) nextLine = 955																// 945 if k = 4 then 955
				950 -> l[2] = s[l[2]][k]																		// 950 l(2) = s(l(2),k)
				955 -> if (l[2] != ll) nextLine = 970															// 955 if l(2) <> l then 970
				960 -> console.println("TSK TSK TSK - WUMPUS GOT YOU!")													// 960 print "TSK TSK TSK - WUMPUS GOT YOU!"
				965 -> f = -1																					// 965 f = -1
				970 -> returnFromGosub()																		// 970 return
				975 -> {}																						// 975 rem *** MOVE ROUTINE ***
				980 -> f = 0																					// 980 f = 0
				985 -> console.print("WHERE TO ")																		// 985 print "WHERE TO";
				990 -> ll = console.readInt()																			// 990 input l
				995 -> if (ll < 1) nextLine = 985																// 995 if l < 1 then 985
				1000 -> if (ll > 20) nextLine = 985																// 1000 if l > 20 then 985
				1005 -> k = 1																					// 1005 for k = 1 to 3
				1010 -> {}																						// 1010 rem *** CHECK IF LEGAL MOVE ***
				1015 -> if (s[l[1]][k] == ll) nextLine = 1045													// 1015 if s(l(1),k) = l then 1045
				1020 -> { ++k; if (k <= 3) nextLine = 1010 }													// 1020 next k
				1025 -> if (ll == l[1]) nextLine = 1045															// 1025 if l = l(1) then 1045
				1030 -> console.print("NOT POSSIBLE - ")																// 1030 print "NOT POSSIBLE -";
				1035 -> nextLine = 985																			// 1035 goto 985
				1040 -> {}																						// 1040 rem *** CHECK FOR HAZARDS ***
				1045 -> l[1] = ll																				// 1045 l(1) = l
				1050 -> {}																						// 1050 rem *** WUMPUS ***
				1055 -> if (ll != l[2]) nextLine = 1090															// 1055 if l <> l(2) then 1090
				1060 -> console.println("... OOPS! BUMPED A WUMPUS!")													// 1060 print "... OOPS! BUMPED A WUMPUS!"
				1065 -> {}																						// 1065 rem *** MOVE WUMPUS ***
				1070 -> gosub(940, 1075)													// 1070 gosub 940
				1075 -> if (f == 0) nextLine = 1090																// 1075 if f = 0 then 1090
				1080 -> returnFromGosub()																		// 1080 return
				1085 -> {}																						// 1085 rem *** PIT ***
				1090 -> if (ll == l[3]) nextLine = 1100															// 1090 if l = l(3) then 1100
				1095 -> if (ll != l[4]) nextLine = 1120															// 1095 if l <> l(4) then 1120
				1100 -> console.println("YYYYIIIIEEEE . . . FELL IN PIT")												// 1100 print "YYYYIIIIEEEE . . . FELL IN PIT"
				1105 -> f = -1																					// 1105 f = -1
				1110 -> returnFromGosub()																		// 1110 return
				1115 -> {}																						// 1115 rem *** BATS ***
				1120 -> if (ll == l[5]) nextLine = 1130															// 1120 if l = l(5) then 1130
				1125 -> if (ll != l[6]) nextLine = 1145															// 1125 if l <> l(6) then 1145
				1130 -> console.println("ZAP--SUPER BAT SNATCH! ELSEWHEREVILLE FOR YOU!")								// 1130 print "ZAP--SUPER BAT SNATCH! ELSEWHEREVILLE FOR YOU!"
				1135 -> ll = fnA()																				// 1135 l = fna(1)
				1140 -> nextLine = 1045																			// 1140 goto 1045
				1145 -> returnFromGosub()																		// 1145 return
				1150 -> {}																						// 1150 end
				}
				currentLine = nextLine
			}
		} catch (e: Throwable) {
			e.printStackTrace()
		}
	}
	private fun gosub(gosubLine: Int, lineToReturnTo: Int) {
		nextLine = gosubLine
		returnLine.addLast(lineToReturnTo)
	}
	private fun returnFromGosub() {
		nextLine = if (returnLine.isEmpty())
			1151
		else
			returnLine.pollLast()
	}
	fun fnA(): Int {
		return random.nextInt(20) + 1
	}
	fun fnB(): Int {
		return random.nextInt(3) + 1
	}
	fun fnC(): Int {
		return random.nextInt(4) + 1
	}
}

