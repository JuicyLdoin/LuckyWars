package ua.Ldoin.JuicyLuckyWars.Game.Arena;

public class Arena {

    public Arena() {

        this.storage = new LuckyBlockStorage();

        arena = this;

    }

    public static Arena arena;

    private final LuckyBlockStorage storage;

    public LuckyBlockStorage getStorage() {

        return storage;

    }
}