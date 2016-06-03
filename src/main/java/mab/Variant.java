package mab;

/**
 * Created by brobrien on 3/31/16.
 */
public enum Variant {
    VARIANT1(.03),
    VARIANT2(.04),
    VARIANT3(.055),
    VARIANT4(.04);

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
