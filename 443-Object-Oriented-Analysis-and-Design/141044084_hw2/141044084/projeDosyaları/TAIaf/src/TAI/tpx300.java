package TAI;

public class tpx300 extends Aircraft {

    private AircraftIngredientFactory IngredientFactory;
    public tpx300(AircraftIngredientFactory IngredientFactory){
        this.IngredientFactory = IngredientFactory;
    }

    /**
     * Uçağın yapılma amacıdır.
     * @return String
     */
    @Override
    public String Purpose() {
        Purpose = "Transatlantic flights";
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
