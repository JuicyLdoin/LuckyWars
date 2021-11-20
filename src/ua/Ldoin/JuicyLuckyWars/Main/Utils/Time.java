package ua.Ldoin.JuicyLuckyWars.Main.Utils;

public class Time {

    public static String makeReadable(int secs) {

        long days = (secs / 86400);
        long hour = (secs % 86400 / 3600);
        long min = (secs / 60 % 60);
        long sec = (secs % 60);

        StringBuilder s = new StringBuilder();

        if (days > 0L)
            s.append(days).append("д. ");

        if (hour > 0L)
            s.append(hour).append("ч. ");

        if (min > 0L)
            s.append(min).append("м. ");

        if (sec > 0L)
            s.append(sec).append("с. ");

        return s.toString();

    }
}