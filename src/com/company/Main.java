package com.company;

import java.util.Random;


public class Main {

    public static int bossHealth = 600;
    public static int bossDamage = 50;
    public static String bossDefenceType;
    public static int[] heroesHealth = {260, 250, 270, 250};
    public static int[] heroesDamage = {20, 15, 10, 0};
    public static String[] heroesAttackType = {"Physical", "Magical", "Kinetic", "Doc"};
    public static int roundNumber;
    public static Random random = new Random();

    public static void main(String[] args) {
        printStatistics();

        while (!isGameFinished()) {
            round();
        }
    }

    public static void chooseBossDefence() {
        int randomIndex = random.nextInt(heroesAttackType.length); // 0,1,2
        if (randomIndex == 3) {
            chooseBossDefence();
        } else {
            bossDefenceType = heroesAttackType[randomIndex];
            System.out.println("Boss choose " + bossDefenceType);
        }
    }

    public static void round() {
        roundNumber++;
        chooseBossDefence();
        bossHits();
        doc1();
        heroesHit();
        printStatistics();
    }

    public static void doc1() {
        int dochp = 30;
        int index = random.nextInt(heroesHealth.length);
        if (index == 3) {
            doc1();
        } else if (heroesHealth[3] > 0) {
            if (heroesHealth[index] < 100 && heroesHealth[index] > 0) {
                heroesHealth[index] = heroesHealth[index] + dochp;
                System.out.println("Кого выбрал медик:" + heroesAttackType[index]);
            }
        }
    }

    public static void bossHits() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                if (heroesHealth[i] - bossDamage < 0) {
                    heroesHealth[i] = 0;
                } else {
                    heroesHealth[i] = heroesHealth[i] - bossDamage;
                }
            }
        }
    }

    public static void heroesHit() {
        for (int i = 0; i < heroesDamage.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                if (bossDefenceType == heroesAttackType[i]) {
                    Random random = new Random();
                    int coeff = random.nextInt(12); //0,1,2,3,4,5,6,7,8,9,10,11
                    if (bossHealth - heroesDamage[i] * coeff < 0) {
                        bossHealth = 0;
                    } else {
                        bossHealth = bossHealth - heroesDamage[i] * coeff;
                    }
                    System.out.println("Critical damage: " + heroesDamage[i] * coeff);
                } else {
                    if (bossHealth - heroesDamage[i] < 0) {
                        bossHealth = 0;
                    } else {
                        bossHealth = bossHealth - heroesDamage[i];
                    }
                }
            }
        }
    }

    public static boolean isGameFinished() {
        if (bossHealth <= 0) {
            System.out.println("Heroes won!!!");
            return true;
        }
        /*if (heroesHealth[0] <= 0 && heroesHealth[1] <= 0 && heroesHealth[2] <= 0) {
            System.out.println("Boss won!!!");
            return true;
        }
        return false;*/
        boolean allHeroesDead = true;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                allHeroesDead = false;
                break;
            }
        }
        if (allHeroesDead) {
            System.out.println("Boss won!!!");
        }
        return allHeroesDead;
    }

    public static void printStatistics() {
        System.out.println("\n"+roundNumber + " ROUND -------------------");
        System.out.println("Boss health: " + bossHealth + " (" + bossDamage + ")\n");
        for (int i = 0; i < heroesHealth.length; i++) {
            System.out.println(heroesAttackType[i] +
                    " health: " + heroesHealth[i] + " (" + heroesDamage[i] + ")");
        }
    }
}
