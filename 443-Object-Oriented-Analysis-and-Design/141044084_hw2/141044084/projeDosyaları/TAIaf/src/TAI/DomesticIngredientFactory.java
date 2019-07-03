package TAI;

public class DomesticIngredientFactory implements AircraftIngredientFactory {

    /**
     * Yurt içi uçacak uçakların fabrika için motorunu
     * oluşturur
     * @return EngineInjection
     */
    @Override
    public EngineInjection createEngineInjection() {
        return new DomesticEngineInjection();
    }

    /**
     * Yurt içi uçacak uçakların fabrika için seatin cover
     * oluşturur
     * @return SeatingCover
     */
    @Override
    public SeatingCover createSeatingCover() {
        return new DomesticSeatingCover();
    }
}
