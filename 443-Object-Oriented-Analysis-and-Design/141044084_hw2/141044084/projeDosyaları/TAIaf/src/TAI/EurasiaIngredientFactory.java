package TAI;

public class EurasiaIngredientFactory implements AircraftIngredientFactory {
    /**
     * Avrupada uçacak uçakların fabrika için motorunu
     * oluşturur
     * @return EngineInjection
     */
    @Override
    public EngineInjection createEngineInjection() {
        return new EurasiaEngineInjection();
    }

    /**
     * Avrupada uçacak uçakların fabrika için seatin cover
     * oluşturur
     * @return SeatingCover
     */
    @Override
    public SeatingCover createSeatingCover() {
        return new EurasiaSeatingCover();
    }
}
