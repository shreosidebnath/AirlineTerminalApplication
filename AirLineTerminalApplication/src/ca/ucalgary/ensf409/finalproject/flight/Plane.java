package ca.ucalgary.ensf409.finalproject.flight;

import java.util.ArrayList;

public class Plane {

    private String name;
    private String IATA;
    private String ICAO;
    private String totalCapacity;
    private String firstCap;
    private String businessCap;
    private String PremEcoCap;
    private String economyCap;

    public Plane(ArrayList<String> planeInfo) {
        this.name = planeInfo.get(0);
        this.IATA = planeInfo.get(1);
        this.ICAO = planeInfo.get(2);
        this.totalCapacity = planeInfo.get(3);
        this.firstCap = planeInfo.get(4);
        this.businessCap = planeInfo.get(5);
        this.PremEcoCap = planeInfo.get(6);
        this.economyCap = planeInfo.get(7);
    }

    public void display(){
        System.out.println("name: " + this.name + ", IATA: " + this.IATA + ", ICAO: " + this.ICAO + ", totalCapacity: " + this.totalCapacity +
          ", firstCap: " + this.firstCap + ", businessCap: " + this.businessCap + ", premEcoCap: " + this.PremEcoCap + ", economyCap: " +
          this.economyCap
        );
    }

    public int getFirstCap(){
        return Integer.parseInt(this.firstCap);
    }

    public int getBusinessCap(){
        return Integer.parseInt(this.businessCap);
    }

    public int getPremEcoCap(){
        return Integer.parseInt(this.PremEcoCap);
    }

    public int getEconomyCap(){
        return Integer.parseInt(this.economyCap);
    }
}
