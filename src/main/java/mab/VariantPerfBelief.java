package mab;

/**
 * Created by brobrien on 3/31/16.
 */
public class VariantPerfBelief {
    private Variant variant;
    private double perfBelief;

    public VariantPerfBelief(Variant v, double perfBelief) {
        this.perfBelief = perfBelief;
        this.variant = v;
    }

    public Variant getVariant() {
        return variant;
    }

    public double getPerfBelief() {
        return perfBelief;
    }
}
