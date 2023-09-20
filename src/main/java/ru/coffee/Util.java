package ru.coffee;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {

    private String patternForXml = ".*city=\"(\\W*)\".*floor=\"(\\d*).*";
    private String patternForCsv = "\"(\\W*)\";\\W+\\d+;(\\d+)";
    private Map<String, Integer> repeat;
    private Map<String, Integer> mapFloor;

    public void statistic(String path) throws IOException {
        repeatCityWithCount(path);
        countCityWithFloor(path);
    }

    private void countCityWithFloor(String path) throws IOException {
        mapFloor = new HashMap<>();
        String lineFromFile;
        StringBuilder sb;
        Pattern pattern;
        if (path.endsWith(".csv")) {
            pattern = Pattern.compile(patternForCsv);
        } else pattern = Pattern.compile(patternForXml);
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            br.readLine();
            while ((lineFromFile = br.readLine()) != null) {
                sb = new StringBuilder();
                Matcher matcher = pattern.matcher(lineFromFile);
                String floor = "";
                String city = "";
                if (matcher.find()) {
                    city = matcher.group(1);
                    floor = matcher.group(2);
                }
                sb = sb.append("City: ").append(city).append(" floor: ").append(floor);
                mapFloor.computeIfPresent(sb.toString(), (town, counter) -> ++counter);
                mapFloor.putIfAbsent(sb.toString(), 1);
            }
            mapFloor.forEach((name, counter) -> System.out.println(name + " numbers of floor:" + counter));
        }
    }


    private void repeatCityWithCount(String path) throws IOException {
        repeat = new HashMap<>();
        String lineFromFile;
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            br.readLine();
            while ((lineFromFile = br.readLine()) != null) {
                repeat.computeIfPresent(lineFromFile, (string, val) -> ++val);
                repeat.putIfAbsent(lineFromFile, 1);
            }
        }
        repeat.entrySet()
                .stream()
                .filter(value -> value.getValue() > 1)
                .forEach(System.out::println);
    }
}
