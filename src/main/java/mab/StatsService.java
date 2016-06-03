package mab;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by brobrien on 3/16/16.
 */
public class StatsService {

    private Map<String, Long> trials = new HashMap<String, Long>();
    private Map<String, Long> wins = new HashMap<String, Long>();

    private Map<VariantContext, Set<Variant>> variantSetLookup = new HashMap<VariantContext, Set<Variant>>();

    public Stats getStats(VariantContext ctx, Variant v) {
        String key = getVariantKey(ctx, v);
        long trialCount = trials.get(key) == null ? 0 : trials.get(key);
        long winCount = wins.get(key) == null ? 0 : wins.get(key);

        return new Stats(trialCount, winCount);
    }

    public Set<Variant> getVariantSetByContext(VariantContext ctx) {
        Set<Variant> variantSet = variantSetLookup.get(ctx);

        if (variantSet == null)
            variantSetLookup.put(ctx, new HashSet<Variant>());

        return variantSetLookup.get(ctx);
    }

    public void registerTrial(VariantContext ctx, Variant v) {
        increment(trials, ctx, v);
    }

    public void registerWin(VariantContext ctx, Variant v) {
        increment(wins, ctx, v);
    }


    private void registerVariantForContext(VariantContext ctx, Variant v) {
        Set<Variant> machineSet = getVariantSetByContext(ctx);
        machineSet.add(v);
    }

    private static String getVariantKey(VariantContext ctx, Variant v) {
        return ctx.toString() + "_" + v.toString();
    }

    private void increment(Map<String, Long> counts, VariantContext ctx, Variant v) {
        registerVariantForContext(ctx, v);
        String key = getVariantKey(ctx, v);
        Long count = counts.get(key);
        if (count == null)
            count = 0l;

        counts.put(key, ++count);
    }
}
