package com.octanepvp.splityosis.octaneengine.utility;

public enum Symbol {

    BULLET("•"),
    HEART("❤"),
    HEARTS("💕"),
    DOUBLE_HEARTS("❣"),
    HEART_BEAT("💓"),
    HEART_PULSE("💗"),
    RIGHT_ARROW("→"),
    LEFT_ARROW("←"),
    SMALL_RIGHT("»"),
    SMALL_LEFT("«"),
    DOUBLE_ARROW("⇒"),
    SQUARE("█"),
    CIRCLE("⬤"),
    PICKAXE("⛏"),
    SWORD("\uD83D\uDDE1"),
    BOW("\uD83C\uDFF9"),
    AXE("\uD83E\uDE93"),
    TRIDENT("\uD83D\uDD31"),
    SHIELD("🛡"),
    CRUSADER_SHIELD("⛨"),
    SWORDS("⚔"),
    POTION("\uD83E\uDDEA"),
    COIN("⛀"),
    COINS("⛁"),
    BOLDER_COINS("⛃"),
    DIAMOND("♦"),
    SPADE("♠"),
    CLUB("♣"),
    HEART_SUIT("♥"),
    MUSIC_NOTE("♫"),
    LIGHTNING("⚡"),
    FLAME("🔥"),
    GEM("\uD83D\uDC8E"),
    BUCKET("\uD83E\uDEA3"),
    SKULL("☠"),
    SUN("☀"),
    MOON("☽"),
    CLOUD("☁"),
    UMBRELLA("☂"),
    SNOWMAN("☃"),
    COMET("☄"),
    STAR("⭐"),
    BOLD_STAR("★"),
    STARS("✨"),
    FLEUR_DE_LIS("⚜"),
    CYCLONE("\uD83C\uDF00"),
    KEY("🔑"),
    LOCK("🔒"),
    UNLOCK("🔓"),
    HOURGLASS("⌛"),
    WATCH("⌚"),
    TELESCOPE("🔭"),
    TENT("⛺"),
    COMPASS("🧭"),
    GLOBE("🌐"),
    RIBBON("\uD83C\uDF80"),
    BEGINNER("🔰"),
    GIFT("🎁"),
    JAPANESE_CASTLE("\uD83C\uDFEF"),
    BELL("🔔"),
    COFFEE("☕"),
    PENCIL("✎"),
    TICK("✓"),
    CROSS("✖");

    private final String symbol;

    Symbol(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

}

