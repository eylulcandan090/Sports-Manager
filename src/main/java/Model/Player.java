package Model;

import java.util.HashMap;
import java.util.Map;

public abstract class Player {
    private String name;
    private int age;
    private String Nation;
    private Map<String, Integer> attributes = new HashMap<>();


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getNation() {
        return Nation;
    }

    public void setNation(String nation) {
        Nation = nation;
    }

    public int getAttribute(String key) {
        return attributes.getOrDefault(key, 0);
    }

    public void setAttribute(String key, int value) {
        attributes.put(key, value);
    }

    public int getStrength() {
        if (attributes.isEmpty()) return 0;
        int total = 0;
        for (int value : attributes.values()) {
            total += value;
        }
        return total / attributes.size();
    }

}










