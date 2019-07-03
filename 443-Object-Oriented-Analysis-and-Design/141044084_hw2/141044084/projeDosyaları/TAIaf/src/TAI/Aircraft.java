package TAI;

public abstract class Aircraft {
    EngineInjection Engine;
    SeatingCover Seating;
    String Purpose;

    /**
     *
     * @return
     */
    public abstract String Purpose();

    /**
     *
     */
    abstract protected void prepare();

    /**
     * Uçak özelliklerini dışarıya açmak amaçlı yazıldı.
     */
    public void showProperty(){
        System.out.println("Amac        :"+Purpose());
        System.out.println("Motor       :"+Engine.toString());
        System.out.println("Koltuk sayisi:"+Seating.toString());
    }
}
