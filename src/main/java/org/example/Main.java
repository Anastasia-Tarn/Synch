package org.example;

import java.util.*;


public class Main {

    public static final int THREADS = 1000;
    public static final int LINE_LENGTH = 100;
    public static final String LETTERS = "RLRFR";

    public static void main(String[] args) {

        for (int i = 0; i < THREADS; i++) {
            new Thread(() -> {
                String route = generateRoute(LETTERS, LINE_LENGTH);

                int count = (int) route.chars().filter(ch -> ch == 'R').count();

                synchronized (sizeToFreq) {
                    if (sizeToFreq.containsKey(count)) {
                        sizeToFreq.put(count, sizeToFreq.get(count) + 1);
                    } else {
                        sizeToFreq.put(count, 1);
                    }

                }
            }).start();
        }

        textTheMap(sizeToFreq);

    }

    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }

    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();

    public static void textTheMap(Map<Integer, Integer> sizeToFreq) {
        Map.Entry<Integer, Integer> max = sizeToFreq
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .get();
        System.out.println("Самое частое количество повторений " + max.getKey() + " (встретилось " + max.getValue() + " раз).");

        Set set = sizeToFreq.entrySet();
        Iterator i = set.iterator();

        System.out.println("Другие размеры:");
        while (i.hasNext()) {
            Map.Entry me = (Map.Entry) i.next();
            System.out.println("- " + me.getKey() + " (" + me.getValue() + " раз)");
        }

    }
}

