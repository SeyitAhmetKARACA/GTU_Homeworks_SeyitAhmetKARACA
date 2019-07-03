package TAI;

public class OtherIngredientFactory implements AircraftIngredientFactory {
    /**
     * Avrupa veya yurt için olmayan uçakların fabrika için motorunu
     * oluşturur
     * @return EngineInjection
     */
    @Override
    public EngineInjection createEngineInjection() {
        return new OtherEngineInjection();
    }
    /**
     * Avrupa veya yurt için olmayan uçakların fabrika için seatin cover
     * oluşturur
     * @return SeatingCover
     */
    @Override
    public SeatingCover createSeatingCover() {
        return new EurasiaSeatingCover();
    }
}
