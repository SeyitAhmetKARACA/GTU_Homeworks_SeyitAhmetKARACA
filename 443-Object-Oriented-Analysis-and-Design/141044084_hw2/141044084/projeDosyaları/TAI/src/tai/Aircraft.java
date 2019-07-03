package tai;

public abstract class Aircraft {
    String Skeleton;
    String Engine;
    String Seating;
    String Purpose;
    public abstract String Purpose();
    public abstract String constructSkeleton();
    public abstract String placeEngines();
    public abstract String placeSeats();
    /**
     * Uçağın hazırlanmasındaki sıra ile uçağı oluşturuyor.
     */
    public abstract void prepare();

    public void showProperty(){
        System.out.println("Amac        :"+Purpose());
        System.out.println("Motor       :"+placeEngines());
        System.out.println("İskelet     :"+constructSkeleton());
        System.out.println("Koltuk sayisi:"+placeSeats());
    }
}
