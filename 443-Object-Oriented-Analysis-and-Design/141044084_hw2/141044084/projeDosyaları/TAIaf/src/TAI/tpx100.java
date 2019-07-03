package TAI;

public class tpx100 extends Aircraft {
    private AircraftIngredientFactory IngredientFactory;
    public tpx100(AircraftIngredientFactory IngredientFactory){
        this.IngredientFactory = IngredientFactory;
    }

    /**
     * Uçağın yapılma amacıdır.
     * @return String
     */
    @Override
    public String Purpose() {
        Purpose = "Domestic flights";
        return Purpose;
    }

    /**
     * Uçağın hazırlanmasıdır.
     */
    @Override
    protected void prepare() {
        Seating = IngredientFactory.createSeatingCover();
        Engine = IngredientFactory.createEngineInjection();
    }
}
