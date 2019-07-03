package TAI;

public class tpx200 extends Aircraft {

    private AircraftIngredientFactory IngredientFactory;
    public tpx200(AircraftIngredientFactory IngredientFactory){
        this.IngredientFactory = IngredientFactory;
    }

    /**
     * Uçağın yapılma amacıdır.
     * @return String
     */
    @Override
    public String Purpose() {
        Purpose = "Domestic and short international flights";
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
