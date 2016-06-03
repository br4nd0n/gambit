package mab;

import org.apache.commons.math3.distribution.BetaDistribution;

import java.util.*;

/**
 * Created by brobrien on 3/16/16.
 */
public class StrategyService {

    private StatsService statsService;

    public StrategyService(StatsService statsService) {
        this.statsService = statsService;
    }

    public VariantPerfBelief selectVariant(VariantContext ctx) {
        //get set of variants for context
        //interrogate stats service
        //compute beta dist
        //sort and return

        Set<Variant> variants = statsService.getVariantSetByContext(ctx);

        if (variants.size() == 0)
            return null; //no known variants

        VariantPerfBelief winningCandidate = null;

        for (Variant v : variants) {
            Stats stats = statsService.getStats(ctx, v);
            long losses = stats.getLosses() == 0 ? 1 : stats.getLosses(); //beta dist doesn't like zero
            BetaDistribution betaDist = new BetaDistribution(stats.getWins(), losses);

            double perfBelief = betaDist.sample();
            if (winningCandidate == null || perfBelief >= winningCandidate.getPerfBelief())
                winningCandidate = new VariantPerfBelief(v, perfBelief);
        }

        return winningCandidate;
    }
}
