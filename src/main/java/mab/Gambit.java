package mab;

import org.apache.commons.math3.distribution.BetaDistribution;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by brobrien on 3/16/16.
 */
public class Gambit {
    public static void main(String[] args) {

        //testBeta();
        runGambit();
    }

    //test adding a new variant after convergence

    private static void runGambit() {
        System.out.println("Gambit: Real Time Multi-Armed Bandit");

        StatsService statsService = new StatsService();
        StrategyService strategyService = new StrategyService(statsService);

        VariantContext ctx = VariantContext.REGION1;
        Random random = new Random();
        int numTrials = 5000;
        boolean isUseText = false;

        //bootstrap dataset
        for (int i = 0; i < 10; ++i) {
            statsService.registerTrial(ctx, Variant.HOTEL1);
            statsService.registerTrial(ctx, Variant.HOTEL2);
            statsService.registerTrial(ctx, Variant.HOTEL3);
        }
        for (int i = 0; i < 1; ++i) {
            statsService.registerWin(ctx, Variant.HOTEL1);
            statsService.registerWin(ctx, Variant.HOTEL2);
            statsService.registerWin(ctx, Variant.HOTEL3);
        }

        Map<Variant, String> variantDisplayText = new HashMap() {{
            put(Variant.HOTEL1, "HOTEL1                      ");
            put(Variant.HOTEL2, "       HOTEL2               ");
            put(Variant.HOTEL3, "              HOTEL3        ");
            put(Variant.HOTEL4, "                     HOTEL4 ");
        }};

        int impressions = 0;
        int conversions = 0;
        boolean updatedPerformanceTest = false;
        boolean newVariantTest = false;

        int cutOverTrial = 1500;

        for (int i = 0; i < numTrials; ++i) {
            /* test here indicates after convergence to hotel3, it will still switch back to hotel1 if hotel1 performances well enough */
            if (updatedPerformanceTest && i == cutOverTrial) {
                System.out.println("---------------------------------------------------------------------------");
                System.out.println("-------------------- HOTEL1 SUDDENLY STARTS PERFORMING --------------------");
                System.out.println("---------------------------------------------------------------------------");
                Variant.HOTEL1.setConversionChance(.09);
            }

            /* test here to add a new machine after convergence. hotel3 is #1 performer, hotel4 is #2 performer. after exploring hotel4, Gambit re-converges on hotel3 */
            if (newVariantTest && i == cutOverTrial) {
                System.out.println("---------------------------------------------------------------------------");
                System.out.println("---------------------------  NEW VARIANT: HOTEL4  -------------------------");
                System.out.println("---------------------------------------------------------------------------");

                for (int j=0;j<5;++j)
                    statsService.registerTrial(ctx, Variant.HOTEL4);
                statsService.registerWin(ctx, Variant.HOTEL4);
            }

            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
            }
            if (isUseText)
                System.out.println("\nRunning trial #" + i + " in context " + ctx.toString());

            //Select variant, register trial for variant
            VariantPerfBelief variantPerfBelief = strategyService.selectVariant(ctx);
            if (variantPerfBelief == null) {
                System.out.println("No variants known");
                continue;
            }

            Variant variant = variantPerfBelief.getVariant();
            statsService.registerTrial(ctx, variant);

            if (isUseText)
                System.out.println(String.format("Variant %s selected. (with belief on performance %.2f)", variant.toString(), variantPerfBelief.getPerfBelief()));

            //roll the dice to determine if it's a winner. if so, register a win
            //it did converge on one that was not the winner with only 5% and 4% perf, respectively
            boolean isWin = random.nextDouble() < variant.getConversionChance();

            if (isWin) {
                ++conversions;
                statsService.registerWin(ctx, variant);
            }

            ++impressions;
            System.out.println(variantDisplayText.get(variant) + (isWin ? " *converted" : " "));
            if (isUseText)
                System.out.println(String.format("Variant %s %s", variant.toString(), isWin ? "won" : "lost"));
        }

        double winRate = (new Double(conversions) / impressions);
        //double tuition = Variant.HOTEL3.getWinChance() - winRate;
        //System.out.println(String.format("Tuition cost:    %.4f", tuition));
        System.out.println("------------------------------------------------------------------------------------");
        System.out.println("");
        System.out.println("Impressions: " + impressions);
        System.out.println("Conversions: " + conversions);
        System.out.println(String.format("%.4f - achieved overall conversion rate", winRate));
        System.out.println("");
        for (Variant v : Variant.values()) {
            Stats stats = statsService.getStats(ctx, v);
            double variantWinRate = new Double(stats.getWins()) / stats.getTrials();
            System.out.println(String.format("%.4f - observed conversion rate for %s. (Actual conversion: %.3f)", variantWinRate, v.toString(), v.getConversionChance()));
        }
    }

    private static void testBeta() {
        //center of the curve = wins/losses
        //spread of the curve = 1/(wins+losses)
        double wins = 300;
        double losses = 300;
        BetaDistribution dist = new BetaDistribution(wins, losses);

        int size = 30;
        //double[] samples = new double[size];

        for (int i = 0; i < size; ++i) {
            System.out.println(dist.sample());
        }
    }
}
