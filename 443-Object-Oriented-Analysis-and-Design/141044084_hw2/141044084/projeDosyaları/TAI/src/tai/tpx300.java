package tai;

public class tpx300 extends Aircraft {
    /**
     * Uçağın yapılma amacı
     * @return
     */
    @Override
    public String Purpose() {
        Purpose = "Transatlantic flights";
        return Purpose;
    }
    /**
     * uçağın iskeletini oluşturur.
     * @return String
     */
    @Override
    public String constructSkeleton() {
        Skeleton = "Titanium alloy";
        return Skeleton;
    }
    /**
     * Uçağın motorunu yerleştirir.
     * @return String
     */
    @Override
    public String placeEngines() {
        Engine = "Quadro jet engine";
        return Engine;
    }
    /**
     * Uçağın koltuk sayısını oluşturur.
     * @return String
     */
    @Override
    public String placeSeats() {
        Seating = "250 seats";
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
