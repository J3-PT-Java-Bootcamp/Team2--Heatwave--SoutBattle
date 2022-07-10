package ScreenManager;

import static ScreenManager.ColorFactory.BLANK_SPACE;

public class PrinterConstants {
    public final static int LIMIT_X=120,LIMIT_Y=20,TAB_INDENT=5; //Screen sizes in characters
    public final static String CENTER_CARET = BLANK_SPACE.repeat(LIMIT_X / 2);
    public final static String GAME_NAME="S.OUT.Battle";
    public final static ScreenManager.TextObjects.TextObject HEADER=new ScreenManager.TextObjects.TextObject("=".repeat(LIMIT_X)+"\n"
            +"-".repeat(LIMIT_X-GAME_NAME.length())+GAME_NAME+"\n"+"=".repeat(LIMIT_X), ScreenManager.TextObjects.TextObject.Scroll.NO, LIMIT_X,LIMIT_Y/2);

    public final static ScreenManager.TextObjects.TextObject GAME_LOGO= new ScreenManager.TextObjects.TextObject("""
                                                                            ,,
    .M""\"bgd                      mm       `7MM""\"Yp,          mm     mm   `7MM
  ,MI    "Y                      MM         MM    Yb          MM     MM     MM
           `MMb.      ,pW"Wq.`7MM  `7MM mmMMmm       MM    dP  ,6"Yb.mmMMmm mmMMmm   MM  .gP"Ya
              `YMMNq. 6W'   `Wb MM    MM   MM         MM""\"bg. 8)   MM  MM     MM     MM ,M'   Yb
            .     `MM 8M     M8 MM    MM   MM         MM    `Y  ,pm9MM  MM     MM     MM 8M""\"""\"
            Mb     dM YA.   ,A9 MM    MM   MM    d8b  MM    ,9 8M   MM  MM     MM     MM YM.    ,
            P"Ybmmd"   `Ybmd9'  `Mbod"YML. `Mbmo Y8P.JMMmmmd9  `Moo9^Yo.`Mbmo  `Mbmo.JMML.`Mbmmd'""", ScreenManager.TextObjects.TextObject.Scroll.BLOCK, LIMIT_X, LIMIT_Y);
    public final static ScreenManager.TextObjects.TextObject TEAM_LOGO= new ScreenManager.TextObjects.TextObject( """
                __  __           __ _       __
               / / / /__  ____ _/ /| |     / /___ __   _____
              / /_/ / _ \\/ __ `/ __/ | /| / / __ `/ | / / _ \\
             / __  /  __/ /_/ / /_ | |/ |/ / /_/ /| |/ /  __/
            /_/ /_/\\___/\\__,_/\\__/ |__/|__/\\__,_/ |___/\\___/ 		 _  __   __  _ _/_ _
            														/_///_'_\\/_'/ // _\\  ...
            													   /
            														""", ScreenManager.TextObjects.TextObject.Scroll.BLOCK,LIMIT_X,LIMIT_Y);
    public final static ScreenManager.TextObjects.TextObject EMPTY_LINE=new ScreenManager.TextObjects.TextObject(BLANK_SPACE.repeat(LIMIT_X-1), ScreenManager.TextObjects.TextObject.Scroll.NO, LIMIT_X,1);
}
