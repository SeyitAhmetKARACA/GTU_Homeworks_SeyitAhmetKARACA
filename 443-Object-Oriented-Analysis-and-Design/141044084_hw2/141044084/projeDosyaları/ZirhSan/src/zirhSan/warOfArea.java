package zirhSan;

public class warOfArea {

    public static void main(String args[]) {
        suit suitOra = new ora();
        suitOra = new Laser(suitOra);
        System.out.println(suitOra.getDescription()
                + " "+ suitOra.cost()+"TL  "+suitOra.weight()+"KG");
        suit suitOra2 = new ora();
        System.out.println(suitOra2.getDescription()
                + " "+ suitOra2.cost()+"TL  "+suitOra2.weight()+"KG");

/*
        suit santa = new santaCostume();
        santa = new AutoRifle(santa);
        santa = new AutoRifle(santa);
        santa = new Laser(santa);
        System.out.println(santa.getDescription() +" "+ santa.cost()+"TL  "+ santa.weight()+"KG");

        suit decCostume = new dec();
        decCostume = new RocketLauncher(decCostume);
        decCostume = new AutoRifle(decCostume);
        decCostume = new Laser(decCostume);
        System.out.println(decCostume.getDescription() +" "+ decCostume.cost()+"TL  "+ decCostume.weight()+"KG");*/
    }
}