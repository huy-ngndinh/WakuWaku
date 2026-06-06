package com.oop.wakuwaku.FactManger;

public class FactManager {
    public static String getFact(int level) {
        switch (level) {
            case 1:
                return "Cat loves jumping and climbing!!";
            case 2:
                return "Fact 2: Moew can see at night...";
            case 3:
                return "Fact 3: Moew can roll..";
            default:
                return "No fact";
        }
    }
}
