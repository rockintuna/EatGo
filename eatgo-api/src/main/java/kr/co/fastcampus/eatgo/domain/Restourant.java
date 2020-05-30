package kr.co.fastcampus.eatgo.domain;

public class Restourant {
    private final String name;
    private final String address;

    public Restourant(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getInformation() {
        return name+" in "+address;
    }
}
