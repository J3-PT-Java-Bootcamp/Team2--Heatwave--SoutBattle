package ScreenManager;

import ScreenManager.TextObjects.TextObject;

import static ScreenManager.ColorFactory.BLANK_SPACE;

public class PrinterConstants {
    public final static int LIMIT_X=120,LIMIT_Y=20,TAB_INDENT=5; //Screen sizes in characters
    public final static String CENTER_CARET = BLANK_SPACE.repeat(LIMIT_X / 2);
    public final static String GAME_NAME="S.OUT.Battle";
    public final static TextObject HEADER=new TextObject("=".repeat(LIMIT_X)+"\n"
            +"-".repeat(LIMIT_X-GAME_NAME.length())+GAME_NAME+"\n"+"=".repeat(LIMIT_X),
            TextObject.Scroll.NO, LIMIT_X,LIMIT_Y/2);

    public final static TextObject GAME_LOGO= new TextObject("""
                                                                            ,,
    .M""\"bgd                      mm       `7MM""\"Yp,          mm     mm   `7MM
  ,MI    "Y                      MM         MM    Yb          MM     MM     MM
           `MMb.      ,pW"Wq.`7MM  `7MM mmMMmm       MM    dP  ,6"Yb.mmMMmm mmMMmm   MM  .gP"Ya
              `YMMNq. 6W'   `Wb MM    MM   MM         MM""\"bg. 8)   MM  MM     MM     MM ,M'   Yb
            .     `MM 8M     M8 MM    MM   MM         MM    `Y  ,pm9MM  MM     MM     MM 8M""\"""\"
            Mb     dM YA.   ,A9 MM    MM   MM    d8b  MM    ,9 8M   MM  MM     MM     MM YM.    ,
            P"Ybmmd"   `Ybmd9'  `Mbod"YML. `Mbmo Y8P.JMMmmmd9  `Moo9^Yo.`Mbmo  `Mbmo.JMML.`Mbmmd'""",
            TextObject.Scroll.BLOCK, LIMIT_X, LIMIT_Y);
    public final static TextObject TEAM_LOGO= new TextObject( """
                __  __           __ _       __
               / / / /__  ____ _/ /| |     / /___ __   _____
              / /_/ / _ \\/ __ `/ __/ | /| / / __ `/ | / / _ \\
             / __  /  __/ /_/ / /_ | |/ |/ / /_/ /| |/ /  __/
            /_/ /_/\\___/\\__,_/\\__/ |__/|__/\\__,_/ |___/\\___/ 		 _  __   __  _ _/_ _
            														/_///_'_\\/_'/ // _\\  ...
            													   /
            														""", TextObject.Scroll.BLOCK,LIMIT_X,LIMIT_Y);
    public final static TextObject EMPTY_LINE=new TextObject(BLANK_SPACE.repeat(LIMIT_X-1),
            TextObject.Scroll.NO, LIMIT_X,1);
    public final static TextObject GAME_OVER= new ScreenManager.TextObjects.TextObject("""
              .g8""\"bgd       db      `7MMM.     ,MMF'`7MM""\"YMM        .g8""8q.`7MMF'   `7MF'`7MM""\"YMM  `7MM""\"Mq.
             .dP'     `M      ;MM:       MMMb    dPMM    MM    `7      .dP'    `YM.`MA     ,V    MM    `7    MM   `MM.
            dM'       `     ,V^MM.      M YM   ,M MM    MM   d        dM'      `MM VM:   ,V     MM   d      MM   ,M9 
            MM             ,M  `MM      M  Mb  M' MM    MMmmMM        MM        MM  MM.  M'     MMmmMM      MMmmdM9  
            MM.    `7MMF'  AbmmmqMA     M  YM.P'  MM    MM   Y  ,     MM.      ,MP  `MM A'      MM   Y  ,   MM  YM.  
             `Mb.     MM   A'     VML    M  `YM'   MM    MM     ,M     `Mb.    ,dP'   :MM;       MM     ,M   MM   `Mb.
               `"bmmmdPY .AMA.    .AMMA..JML. `'  .JMML..JMMmmmmMMM       `"bmmd"'      VF      .JMMmmmmMMM .JMML. .JMM.""",TextObject.Scroll.BLOCK,LIMIT_X,LIMIT_Y).alignTextCenter().alignTextMiddle();
    public final static TextObject FIGHT_TITLE= new ScreenManager.TextObjects.TextObject("""
   ________________________________________________
________|          __      __         _____             |_______
 \\       |         |__  |  |  _   |__|   |     |         |      /
 \\      |         |    |  |___|  |  |   |     X         |     /
 /      |_______________________________________________|     \\
/__________)                                        (__________\\""",TextObject.Scroll.BLOCK,LIMIT_X,LIMIT_Y).alignTextCenter();
    public final static TextObject YOU_WIN= new ScreenManager.TextObjects.TextObject("""
Y88b   d88P  .d88888b.  888     888      888       888 8888888 888b    888 
 Y88b d88P  d88P" "Y88b 888     888      888   o   888   888   8888b   888 
  Y88o88P   888     888 888     888      888  d8b  888   888   88888b  888 
   Y888P    888     888 888     888      888 d888b 888   888   888Y88b 888 
    888     888     888 888     888      888d88888b888   888   888 Y88b888 
    888     888     888 888     888      88888P Y88888   888   888  Y88888 
    888     Y88b. .d88P Y88b. .d88P      8888P   Y8888   888   888   Y8888 
    888      "Y88888P"   "Y88888P"       888P     Y888 8888888 888    Y888""",TextObject.Scroll.BLOCK,LIMIT_X,LIMIT_Y).alignTextCenter().alignTextMiddle();
    public final static TextObject CANDLES= new ScreenManager.TextObjects.TextObject("""
                          )                    `
                         /(l                   /)
                        (  \\                  / (
                          ) * )                ( , )
                          \\#/                  \\#'
                         .-"#'-.             .-"#"=,
                     (  |"-.='|            '|"-,-"|
                                  )\\ |     |  ,        /(|     | /(         ,
                          (       /  )|     | (\\       (  \\     | ) )       ((
                           )\\     (   (|     | ) )      ) , )    |/ (        ) \\
                          /  )     ) . )     |/  (     ( # (     ( , )      /   )
                         ( * (      \\#/|     (`# )      `#/|     |`#/      (  '(
                        \\#/     .-"#'-.   .-"#'-,   .-"#'-.   .-=#"-;     `#/
                        .-"#'-.   |"=,-"|   |"-.-"|)  1"-.-"|   |"-.-"|   ,-"#"-.
                        |"-.-"|   |  !  |   |     |   |     |   |     !   |"-.-"|
                        |     |   |     |._,|     |   |     |._,|     a   |     |
                        |     |   |     |   |     |   |     |   |     p   |     |
                        |     |   |     |   |     |   |     |   |     x   |     |
                        '-._,-'   '-._,-'   '-._,-'   '-._,-'   '-._,-"   '-._,-'""",TextObject.Scroll.BLOCK,LIMIT_X,LIMIT_Y).alignTextCenter();

    public final static TextObject CASTLE= new ScreenManager.TextObjects.TextObject("""
                    |>>>
                  |
                    |>>>      _  _|_  _         |>>>
                |        |;| |;| |;|         |
                  _  _|_  _    \\\\.    .  /    _  _|_  _
                 |;|_|;|_|;|    \\\\:. ,  /    |;|_|;|_|;|
                  \\\\..      /    ||;   . |    \\\\.    .  /
                  \\\\.  ,  /     ||:  .  |     \\\\:  .  /
                 ||:   |_   _ ||_ . _ | _   _||:   |
                 ||:  .|||_|;|_|;|_|;|_|;|_|;||:.  |
                 ||:   ||.    .     .      . ||:  .|
                            ||: . || .     . .   .  ,   ||:   |       \\,/
                                ||:   ||:  ,  _______   .   ||: , |            /`\\
                 ||:   || .   /+++++++\\    . ||:   |
                 ||:   ||.    |+++++++| .    ||: . |
              __ ||: . ||: ,  |+++++++|.  . _||_   |
                        ____--`~    '--~~__|.    |+++++__|----~    ~`---,              ___""",TextObject.Scroll.BLOCK,LIMIT_X,LIMIT_Y).alignTextCenter();
    public final static TextObject TOMB= new ScreenManager.TextObjects.TextObject("""
 ,-=-.
/  +  \\
| ~~~ |
|R.I.P|
|_____|""",TextObject.Scroll.BLOCK,LIMIT_X,LIMIT_Y);
    public final static TextObject CROIX= new ScreenManager.TextObjects.TextObject("""
   _
 _|R|_
|_ I _|
  |P|
  |_|""",TextObject.Scroll.BLOCK,LIMIT_X,LIMIT_Y);

    public final static TextObject IN_MEMORIAM= new ScreenManager.TextObjects.TextObject("""
                         __ __  __    ___  ___  ____ ___  ___   ___   ____  __  ___  ___  ___
                         || ||\\ ||    ||\\\\//|| ||    ||\\\\//||  // \\\\  || \\\\ || // \\\\ ||\\\\//||
                         || ||\\\\||    || \\/ || ||==  || \\/ || ((   )) ||_// || ||=|| || \\/ ||
                         || || \\||    ||    || ||___ ||    ||  \\\\_//  || \\\\ || || || ||    ||""",
            TextObject.Scroll.BLOCK,LIMIT_X,LIMIT_Y).alignTextCenter();
    public final static TextObject WARRIOR_IMG= new ScreenManager.TextObjects.TextObject("""
___
_/    \\_
/ __|__ \\
( (_+ +_) )
_/`\\  |-|  /`\\_
( \\  \\_|=|_/  / )
|  \\   )X(   /  |""",TextObject.Scroll.BLOCK,20,10).alignTextCenter().alignTextMiddle();
    public final static TextObject WIZARD_IMG= new ScreenManager.TextObjects.TextObject("""
              *
              /*\\
              /***\\
              __/*****\\__
              (|+ | +|)
              \\ /=\\ /
              /`---'\\""",TextObject.Scroll.BLOCK,20,10).alignTextCenter().alignTextMiddle();
}
