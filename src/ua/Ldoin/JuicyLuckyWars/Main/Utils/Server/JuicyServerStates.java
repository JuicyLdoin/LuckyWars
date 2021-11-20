package ua.Ldoin.JuicyLuckyWars.Main.Utils.Server;

public enum JuicyServerStates {

    WAITING, INGAME, DEVELOPMENT, NULL;

    public String toDisplayString() {

        if (this == WAITING)
            return "§aОжидание";

        if (this == INGAME)
            return "§eВ игре";

        if (this == DEVELOPMENT)
            return "§cВ разработке";

        return "";

    }
}