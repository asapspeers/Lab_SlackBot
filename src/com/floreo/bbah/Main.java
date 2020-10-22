package com.floreo.bbah;
import java.io.IOException;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) throws IOException {
        Bot myBot = new Bot();
        System.out.println("Please choose an pokemon number:");
        //myBot.testApi();

        Scanner sc = new Scanner(System.in);
        int input = sc.nextInt();
        String output = myBot.findPokemonByNumber(input);
        myBot.sendMessageToBotsChannel(output);

    }
}

