package com.atherys.towns.facade;

import com.atherys.core.utils.TextUtils;
import com.atherys.towns.TownsConfig;
import com.atherys.towns.api.command.TownsCommandException;
import com.atherys.towns.api.permission.TownsPermissionContext;
import com.atherys.towns.model.entity.RentInfo;
import com.atherys.towns.model.entity.Resident;
import com.atherys.towns.model.entity.TownPlot;
import com.atherys.towns.service.PlotService;
import com.atherys.towns.service.RentService;
import com.atherys.towns.service.ResidentService;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.service.economy.transaction.TransferResult;
import org.spongepowered.api.text.Text;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

import static org.spongepowered.api.text.format.TextColors.DARK_GREEN;
import static org.spongepowered.api.text.format.TextColors.GOLD;

@Singleton
public class RentFacade implements EconomyFacade {
    @Inject
    private PlotFacade plotFacade;

    @Inject
    private ResidentFacade residentFacade;

    @Inject
    private TownFacade townFacade;

    @Inject
    private TownsMessagingFacade townsMsg;

    @Inject
    private ResidentService residentService;

    @Inject
    private RentService rentService;

    @Inject
    private PlotService plotService;

    @Inject
    private TownsConfig config;

    public void makePlotRentable(Player source, BigDecimal price, Duration rentPeriod, TownsPermissionContext context) throws TownsCommandException {
        TownPlot plot = plotFacade.getPlotAtPlayer(source);

        if (plot.getRentInfo().isPresent() && plot.getRentInfo().get().getRenter() != null) {
            throw new TownsCommandException("Someone is renting this plot.");
        }

        if (config.TOWN.RENT_CONFIG.MINIMUM_RENT_COST > price.doubleValue()) {
            throw new TownsCommandException("Rent price is below the minimum.");
        }

        if (config.TOWN.RENT_CONFIG.MAXIMUM_RENT_COST < price.doubleValue()) {
            throw new TownsCommandException("Rent price is above the maximum.");
        }

        if (rentPeriod.compareTo(config.TOWN.RENT_CONFIG.MINIMUM_RENT_PERIOD) < 0) {
            throw new TownsCommandException("Rent period is below the minimum.");
        }

        if (rentPeriod.compareTo(config.TOWN.RENT_CONFIG.MAXIMUM_RENT_PERIOD) > 0) {
            throw new TownsCommandException("Rent period is above the maximum.");
        }

        rentService.setPlotRentInfo(plot, price, rentPeriod, context);
        townsMsg.info(source, "Plot made rentable.");
    }

    public void sendRentInfo(Player source) throws TownsCommandException {
        TownPlot plot = plotFacade.getPlotAtPlayer(source);
        RentInfo rentInfo = getRentInfoFromPlot(plot);

        Text.Builder rentText = Text.builder();
        rentText.append(townsMsg.createTownsHeader(plot.getName().toPlain()), Text.NEW_LINE);

        if (rentInfo.getRenter() == null) {
            rentText.append(Text.of(DARK_GREEN, "Rent Cost: "), config.DEFAULT_CURRENCY.format(rentInfo.getPrice()));
            rentText.append(Text.of(DARK_GREEN, "Rent For: ", GOLD, TextUtils.formatDuration(rentInfo.getPeriod().toMillis())));
        } else {
            rentText.append(Text.of(DARK_GREEN, "Rented By: "), residentFacade.renderResident(rentInfo.getRenter()));
            Duration totalRent = rentInfo.getPeriod().multipliedBy(rentInfo.getPeriodsRented());
            LocalDateTime endTime = rentInfo.getTimeRented().plus(totalRent);
            Duration timeLeft = Duration.between(LocalDateTime.now(), endTime);
            rentText.append(Text.of(DARK_GREEN, "Rented For: ", GOLD, TextUtils.formatDuration(timeLeft.toMillis())));
        }

        source.sendMessage(rentText.build());
    }

    private RentInfo getRentInfoFromPlot(TownPlot plot) throws TownsCommandException {
        return plot.getRentInfo().orElseThrow(() ->
                new TownsCommandException("The plot you are standing on is not rentable.")
        );
    }

    public void purchaseRent(Player source, int periods) throws TownsCommandException {
        TownPlot plot = plotFacade.getPlotAtPlayer(source);
        RentInfo rentInfo = getRentInfoFromPlot(plot);
        Resident resident = residentService.getOrCreate(source);

        // Someone else is renting this plot
        if (rentInfo.getRenter() != null && rentInfo.getRenter() != resident) {
            throw new TownsCommandException("This plot is already being rented!");
        }

        // Player is not allowed to rent this plot
        if (!plotFacade.getRelevantResidentContexts(plot, resident).contains(rentInfo.getPermissionContext())) {
            throw new TownsCommandException("You are not allowed to rent this plot.");
        }

        BigDecimal totalPrice = rentInfo.getPrice().multiply(BigDecimal.valueOf(periods));

        TransferResult paymentResult = townFacade.depositToTown(source, plot.getTown(), totalPrice);

        Text feedback = getResultFeedback(
                paymentResult.getResult(),
                Text.of("Plot rented."),
                Text.of("You do not have enough to rent this plot."),
                Text.of("Rent failed.")
        );

        if (rentInfo.getRenter() == resident) {
            rentService.setRentPeriods(rentInfo, rentInfo.getPeriodsRented() + periods);
        } else {
            rentService.setPlotRenter(rentInfo, resident, periods);
        }

        townsMsg.info(source, feedback);
    }

    public void evictPlotRenter(Player source) throws TownsCommandException {
        TownPlot plot = plotFacade.getPlotAtPlayer(source);
        RentInfo rentInfo = getRentInfoFromPlot(plot);

        rentService.clearPlotRenter(rentInfo);
        townsMsg.info(source, "Plot has been evicted.");
    }

    public void vacatePlot(Player source) throws TownsCommandException {
        TownPlot plot = plotFacade.getPlotAtPlayer(source);
        RentInfo rentInfo = getRentInfoFromPlot(plot);

        if (rentInfo.getRenter() == residentService.getOrCreate(source)) {
            rentService.clearPlotRenter(rentInfo);
            townsMsg.info(source, "You have vacated this plot.");
        } else {
            throw new TownsCommandException("You are not renting this plot.");
        }
    }
}