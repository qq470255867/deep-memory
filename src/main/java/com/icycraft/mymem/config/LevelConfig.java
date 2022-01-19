package com.icycraft.mymem.config;

public class LevelConfig {


    public static int LV1_2 = 100;

    public static int LV2_3 = 300;

    public static int LV3_4 = 500;

    public static int LV4_5 = 500;

    public static int LV5_6 = 1000;

    public static int LV6_7 = 1000;

    public static int LV7_8 = 1500;

    public static int LV8_9 = 2000;

    public static int LV9_10 = 3000;

    public static int LV10_11 = 4000;

    public static int LV11_12 = 5000;

    public static int LV12_13 = 5000;

    public static int LV13_14 = 5000;

    public static int LV14_15 = 5000;

    public static int LV15_16 = 5000;

    public static int LV16_17 = 5000;

    public static int LV17_18 = 5000;

    public static int LV18_19 = 5000;

    public static int LV19_20 = 5000;



    public static int canUpLv(int curLv,int curExp) {
        //返回经验余量，如果余量为<0 表示不满足升级条件
        if (curLv == 1) {
            return curExp - LV1_2;
        }else if (curLv ==2) {
            return curExp - LV2_3;
        }else if (curLv ==3) {
            return curExp - LV3_4;
        }else if (curLv ==4) {
            return curExp - LV4_5;
        }else if (curLv ==5) {
            return curExp - LV5_6;
        }else if (curLv ==6) {
            return curExp - LV6_7;
        }else if (curLv ==7) {
            return curExp - LV7_8;
        }else if (curLv ==8) {
            return curExp - LV8_9;
        }else if (curLv ==9) {
            return curExp - LV9_10;
        }else if (curLv ==10) {
            return curExp - LV10_11;
        }else if (curLv ==11) {
            return curExp - LV11_12;
        }else if (curLv ==12) {
            return curExp - LV12_13;
        }else if (curLv ==13) {
            return curExp - LV13_14;
        }else if (curLv ==14) {
            return curExp - LV14_15;
        }else if (curLv ==15) {
            return curExp - LV15_16;
        }else if (curLv ==16) {
            return curExp - LV16_17;
        }else if (curLv ==17) {
            return curExp - LV17_18;
        }else if (curLv ==18) {
            return curExp - LV18_19;
        }else if (curLv ==19) {
            return curExp - LV19_20;
        }else {
            return -1;
        }
    }

    public static int getUpExp(int curLv){

            if (curLv == 1) {
                return  LV1_2;
            }else if (curLv ==2) {
                return  LV2_3;
            }else if (curLv ==3) {
                return  LV3_4;
            }else if (curLv ==4) {
                return  LV4_5;
            }else if (curLv ==5) {
                return  LV5_6;
            }else if (curLv ==6) {
                return  LV6_7;
            }else if (curLv ==7) {
                return  LV7_8;
            }else if (curLv ==8) {
                return  LV8_9;
            }else if (curLv ==9) {
                return  LV9_10;
            }else if (curLv ==10) {
                return  LV10_11;
            }else if (curLv ==11) {
                return  LV11_12;
            }else if (curLv ==12) {
                return  LV12_13;
            }else if (curLv ==13) {
                return  LV13_14;
            }else if (curLv ==14) {
                return  LV14_15;
            }else if (curLv ==15) {
                return  LV15_16;
            }else if (curLv ==16) {
                return  LV16_17;
            }else if (curLv ==17) {
                return  LV17_18;
            }else if (curLv ==18) {
                return  LV18_19;
            }else if (curLv ==19) {
                return  LV19_20;
            }else {
                return -1;
            }

    }


    public static String getLVColor(int level) {

        if (level == 1) {

            return "rgb(127,255,0)";

        } else if (level == 2) {

            return "rgb(255,127,80)";

        } else if (level == 3) {

            return "rgb(0,255,255)";

        } else if (level == 4) {

            return "rgb(255,140,0)";

        } else if (level == 5) {

            return "rgb(255,20,147)";

        } else if (level == 6) {

            return "rgb(125, 250, 9)";

        } else if (level == 7) {

            return "rgb(30,144,255)";

        } else if (level == 8) {

            return "rgb(125, 250, 9)";

        } else if (level == 9) {

            return "rgb(255,0,255)";

        } else if (level == 10) {

            return "rgb(75,0,130)";

        } else if (level == 11) {

            return "rgb(240,128,128)";

        } else if (level == 12) {

            return "rgb(128,0,0)";

        } else if (level == 13) {

            return "rgb(65,105,225)";

        } else if (level == 14) {

            return "rgb(106,90,205)";

        } else if (level == 15) {

            return "rgb(47,79,79)";

        } else if (level == 16) {

            return "rgb(255,20,147)";

        } else if (level == 17) {

            return "rgb(148,0,211)";

        } else if (level == 18) {

            return "rgb(255,215,0)";

        } else if (level == 19) {

            return "rgb(255,69,0)";

        } else if (level == 20) {

            return "rgb(255,0,0)";
        }else {
            return "";
        }


    }

}
