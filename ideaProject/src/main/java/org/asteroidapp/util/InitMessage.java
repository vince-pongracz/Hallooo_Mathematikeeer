package org.asteroidapp.util;

import java.util.ArrayList;

public class InitMessage {
    private int playerNum = 0;
    private int settlerNum = 0;
    private int asteroidNum = 40;
    private int ufoNum = 1;
    private int maxRound = 30;
    private int sunFlairInEveryXRound = 10;
    private int homeCapacity = 5;
    private int settlerCapacity = 10;
    private int defOfCloseToSun = 500;

   public String[] getNames() {
        return names;
   }

    public InitMessage setNames(String[] names) {
        this.names = names;
        return this;
    }


      private String[] names;


    public boolean check() {
        if (playerNum > 0
                && settlerNum > 0
                && asteroidNum >= 20
                && asteroidNum <= 90
                && ufoNum >= 0
                && ufoNum <= 15
                && maxRound > 0
                && maxRound <= 1000
                && sunFlairInEveryXRound >= 1
                && sunFlairInEveryXRound < maxRound
                && homeCapacity >= 1
                && homeCapacity < 20
                && settlerCapacity > 3
                && settlerCapacity < 20
                && defOfCloseToSun > 0
                && names.length == playerNum) {
            return true;
        } else {
            return false;
        }
    }

    public int getPlayerNum() {
        return playerNum;
    }

    public InitMessage setPlayerNum(int playerNum) {
        this.playerNum = Math.abs(playerNum);
        return this;
    }

    public int getSettlerNum() {
        return settlerNum;
    }

    public InitMessage setSettlerNum(int settlerNum) {
        this.settlerNum = Math.abs(settlerNum);
        return this;
    }

    public int getAsteroidNum() {
        return asteroidNum;
    }

    public InitMessage setAsteroidNum(int asteroidNum) {
        this.asteroidNum = Math.abs(asteroidNum);
        return this;
    }

    public int getUfoNum() {
        return ufoNum;
    }

    public InitMessage setUfoNum(int ufoNum) {
        this.ufoNum = Math.abs(ufoNum);
        return this;
    }

    public int getMaxRound() {
        return maxRound;
    }

    public InitMessage setMaxRound(int maxRound) {
        this.maxRound = Math.abs(maxRound);
        return this;
    }

    public int getSunFlairInEveryXRound() {
        return sunFlairInEveryXRound;
    }

    public InitMessage setSunFlairInEveryXRound(int sunFlairInEveryXRound) {
        this.sunFlairInEveryXRound = Math.abs(sunFlairInEveryXRound);
        return this;
    }

    public int getHomeCapacity() {
        return homeCapacity;
    }

    public InitMessage setHomeCapacity(int homeCapacity) {
        this.homeCapacity = Math.abs(homeCapacity);
        return this;
    }

    public int getSettlerCapacity() {
        return settlerCapacity;
    }

    public InitMessage setSettlerCapacity(int settlerCapacity) {
        this.settlerCapacity = Math.abs(settlerCapacity);
        return this;
    }

    public int getDefOfCloseToSun() {
        return defOfCloseToSun;
    }

    public InitMessage setDefOfCloseToSun(int defOfCloseToSun) {
        this.defOfCloseToSun = Math.abs(defOfCloseToSun);
        return this;
    }
}
