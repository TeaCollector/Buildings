package ru.coffee;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Main {
    private Util util;

    public static void main(String[] args) throws IOException {
        Main main = new Main();
        main.runApp();
    }

    public void runApp() throws IOException {
        util = new Util();
        String line;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            while (true) {
                System.out.println("Please insert path to make statistic or input \"exit\" to exit from app: ");
                line = br.readLine();
                if (line.equals("address.xml") || line.equals("address.csv")) {
                    util.statistic(line);
                } else if (line.equals("exit")) return;
            }
        }
    }
}
