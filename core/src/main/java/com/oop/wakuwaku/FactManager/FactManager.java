package com.oop.wakuwaku.FactManager;

public class FactManager {
    public static String getFact(int level) {
        switch (level) {
            case 0:
                return "Isaac Newton invented the cat door";
            case 1:
                return "Cats never meow at each other. They meow only for humans.";
            case 2:
                return "Cats recognize their names (they just may not care)";
            case 3:
                return "Every cat’s nose print is unique";
            case 4:
                return "Cats are scared of cucumbers";
            case 5:
                return "October 18th 1963, Felicette was the first and only cat to go to space";
            default:
                return "CONGRATULATION! Approved to serve your feline overlord";
        }
    }
}
