package tai;

public class tpx100 extends Aircraft {
    /**
     * Uçağın yapılma amacı
     * @return String
     */
    @Override
    public String Purpose() {
        Purpose = "Domestic flights";
        return Purpose;
    }

    /**
     * uçağın iskeletini oluşturur.
     * @return String
     */
    @Override
    public String constructSkeleton() {
        Skeleton = "Aluminum alloy";
        return Skeleton;
    }

    /**
     * Uçağın motorunu yerleştirir.
     * @return String
     */
    @Override
    public String placeEngines() {
        Engine = "Single jet engine";
        return Engine;
    }

    /**
     * Uçağın koltuk sayısını oluşturur.
     * @return String
     */
    @Override
    public String placeSeats() {
        Seating = "50 seats";
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
