package mab;

/**
 * Created by brobrien on 3/31/16.
 */
public enum Variant {
    HOTEL1(.03),
    HOTEL2(.04),
    HOTEL3(.055),
    HOTEL4(.04);

    private double conversionChance;

    Variant(double conversionChance) {
        this.conversionChance = conversionChance;
    }

    public double getConversionChance() {
        return conversionChance;
    }

    public void setConversionChance(double conversionChance) {
        this.conversionChance = conversionChance;
    }
}
