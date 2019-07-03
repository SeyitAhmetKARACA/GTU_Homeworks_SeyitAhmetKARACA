package tai;

public class tpx200 extends Aircraft {
    /**
     * Uçağın yapılma amacı
     * @return String
     */
    @Override
    public String Purpose() {
        Purpose = "Domestic and short international flights";
        return Purpose;
    }
    /**
     * uçağın iskeletini oluşturur.
     * @return String
     */
    @Override
    public String constructSkeleton() {
        Skeleton = "Nickel alloy";
        return Skeleton;
    }
    /**
     * Uçağın motorunu yerleştirir.
     * @return String
     */
    @Override
    public String placeEngines() {
        Engine = "Twin jet engine";
        return Engine;
    }
    /**
     * Uçağın koltuk sayısını oluşturur.
     * @return String
     */
    @Override
    public String placeSeats() {
        Seating = "100 seats";
        return Seating;
    }
    /**
     * uygun sıra ile uçağı oluşturur.
     */
    @Override
    public void prepare() {
        placeEngines();
        constructSkeleton();
        placeSeats();
    }
}
