package com.oop.wakuwaku;

public class FactManager {
    public static String getFact(int level) {
        switch (level) {
            case 1:
                return "Fact 1: Mew has 9 lives...";
            case 2:
                return "Fact 2: Moew can see at night...";
            case 3:
                return "Fact 3: Moew can roll..";
            default:
                return "No fact";
        }
    }
}
