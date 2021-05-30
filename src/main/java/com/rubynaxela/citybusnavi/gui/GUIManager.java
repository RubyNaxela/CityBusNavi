package com.rubynaxela.citybusnavi.gui;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.rubynaxela.citybusnavi.CityBusNavi;
import com.rubynaxela.citybusnavi.assets.StringManager;
import com.rubynaxela.citybusnavi.commands.CommandHandler;
import com.rubynaxela.citybusnavi.commands.DataProcessor;
import com.rubynaxela.citybusnavi.data.DatabaseAccessor;
import com.rubynaxela.citybusnavi.data.datatypes.auxiliary.Pair;
import com.rubynaxela.citybusnavi.data.datatypes.derived.RouteSchedule;
import com.rubynaxela.citybusnavi.data.datatypes.gtfs.Route;
import com.rubynaxela.citybusnavi.data.datatypes.gtfs.Stop;
import com.rubynaxela.citybusnavi.data.datatypes.gtfs.Trip;
import com.rubynaxela.citybusnavi.gui.components.MainWindow;
import com.rubynaxela.citybusnavi.gui.components.RouteStopSelectionWindow;
import com.rubynaxela.citybusnavi.gui.components.RouteStopTimetable;
import com.rubynaxela.citybusnavi.gui.components.renderers.RouteButton;
import com.rubynaxela.citybusnavi.gui.components.renderers.StopsList;
import com.rubynaxela.citybusnavi.gui.components.std.DefaultJFrame;
import com.rubynaxela.citybusnavi.gui.components.std.DefaultJLabel;
import com.rubynaxela.citybusnavi.util.OsCheck;
import com.rubynaxela.citybusnavi.util.Utils;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

import static com.rubynaxela.citybusnavi.util.Utils.gridElementSettings;

public final class GUIManager {

    private final StringManager stringManager;
    private final DialogHandler dialogHandler;
    private final DatabaseAccessor databaseAccessor;
    private final CommandHandler commandHandler;
    private final DataProcessor dataProcessor;

    private MainWindow window;

    public GUIManager(CityBusNavi instance) {
        stringManager = instance.getStringManager();
        dialogHandler = instance.getDialogHandler();
        databaseAccessor = instance.getDatabaseAccessor();
        commandHandler = instance.getCommandHandler();
        dataProcessor = instance.getDataProcessor();
    }

    @Contract(pure = true)
    @NotNull
    public static Font getGlobalFont(int size, int style) {
        return new Font("Verdana", style, size);
    }

    private void initialSettings() {

        // Platform-dependent utilization of the application top bar
        switch (OsCheck.getOperatingSystemType()) {
            case MAC_OS:
                try {
                    System.setProperty("apple.awt.application.name", stringManager.get("string.app.title"));
                } catch (NullPointerException e) {
                    e.printStackTrace();
                    System.err.println("Could not set the appbar title. String manager not initialized");
                }
                System.setProperty("apple.laf.useScreenMenuBar", "true");
                break;
            case WINDOWS:
                JFrame.setDefaultLookAndFeelDecorated(true);
                JDialog.setDefaultLookAndFeelDecorated(true);
                break;
        }

        // Darcula theme look and feel
        try {
            UIManager.setLookAndFeel(new FlatDarculaLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }
        UIManager.put("CheckBox.icon.style", "filled");
    }

    /**
     * Initial GUI settings. Sets the location of the navigation bar and other top bar options, as well as the theme
     * of the interface, then creates and initializes the main application window. This method should be called
     * after initializing the application {@link StringManager} on order for it to work properly
     */
    public void initMainWindow() {

        // Initial GUI settings
        initialSettings();

        // Main window settings
        window = new MainWindow();
        window.setTitle(stringManager.get("string.app.title"));
        dialogHandler.setAnchor(window);

        // GUI components
        window.register(window.updateButton, gridElementSettings(0, 0));
        window.updateButton.setText(stringManager.get("lang.button.update_schedule"));
        window.updateButton.setFontSize(15);
        window.updateButton.addActionListener(ev -> lockMainWindowUntilDone(commandHandler::updateSchedule));

        window.register(window.demoMapButton, gridElementSettings(1, 0));
        window.demoMapButton.setText(stringManager.get("lang.button.create_demo"));
        window.demoMapButton.setFontSize(15);
        window.demoMapButton.addActionListener(ev -> commandHandler.createDemoKMLFile());

        window.register(window.openLineScheduleButton, gridElementSettings(2, 0));
        window.openLineScheduleButton.setText(stringManager.get("lang.button.open_line_schedule"));
        window.openLineScheduleButton.setFontSize(15);
        window.openLineScheduleButton.addActionListener(ev -> initRouteSelectionWindow());

        window.register(window.openStopScheduleButton, gridElementSettings(3, 0));
        window.openStopScheduleButton.setText(stringManager.get("lang.button.open_stop_schedule"));
        window.openStopScheduleButton.setFontSize(15);
        window.openStopScheduleButton.addActionListener(ev -> {});

        window.pack();
    }

    public void lockMainWindow(boolean lock) {
        window.forEachComponent(c -> c.setEnabled(!lock));
    }

    public void lockMainWindowUntilDone(Runnable action) {
        lockMainWindow(true);

        action.run();
        lockMainWindow(false);
    }

    private void initRouteSelectionWindow() {

        // Window settings
        final DefaultJFrame routeSelection;
        routeSelection = new DefaultJFrame(false);
        routeSelection.setTitle(stringManager.get("lang.window.title.select_route"));

        // GUI components
        int row = -1, col = 1;
        final int fontSize = 12, maxColumns = 20;
        Route previous = databaseAccessor.getRoutes().get(0);
        for (final Route route : databaseAccessor.getRoutes()) {
            if (row == -1 || route.getGroup() != previous.getGroup()) {

                String groupTitle;
                int group = route.getGroup();
                if (group == 0) groupTitle = stringManager.get("lang.routes.tram");
                else if (group == 1) groupTitle = stringManager.get("lang.routes.city_bus");
                else if (group == 2) groupTitle = stringManager.get("lang.routes.night");
                else if (group == 3) groupTitle = stringManager.get("lang.routes.suburban");
                else groupTitle = "";

                final DefaultJLabel groupLabel = new DefaultJLabel(groupTitle);
                groupLabel.setFontSizeAndStyle((int) (1.33 * fontSize), Font.BOLD);
                groupLabel.setBorder(new CompoundBorder(groupLabel.getBorder(),
                        new EmptyBorder(0, 5, 0, 0)));
                routeSelection.register(groupLabel,
                        Utils.gridElementSettings(row + 1, 0, maxColumns - 1, 1));

                row += 2;
                col = 1;
            } else if (col > maxColumns) {
                row++;
                col = 1;
            }
            final RouteButton routeButton = new RouteButton(route, fontSize);
            routeSelection.register(routeButton, gridElementSettings(row, col));
            routeButton.addActionListener(ev -> {
                routeSelection.dispose();
                initRouteStopSelectionWindow(route);
            });
            previous = route;
            col++;
        }

        routeSelection.pack();
    }

    public void initRouteStopSelectionWindow(Route route) {

        // Window settings
        final RouteStopSelectionWindow stopSelection = new RouteStopSelectionWindow(route);
        stopSelection.setTitle(stringManager.get("lang.window.title.select_stop"));

        // GUI components
        stopSelection.register(stopSelection.routeNumberLabel, gridElementSettings(0, 0));

        final String[] relations = route.routeLongName.split("\\|");
        stopSelection.register(stopSelection.routeNameLabel, gridElementSettings(0, 1));
        stopSelection.routeNameLabel.setText(relations[relations.length - 1].replace("-", "↔"));

        stopSelection.register(stopSelection.stopLists, Utils.gridElementSettings(1, 0, 2, 1));

        final Pair<ArrayList<Trip>, Integer> mainVariants = dataProcessor.findMainVariantTrips(route);
        int trips = 0, maxStops = mainVariants.getValue();
        for (final Trip trip : mainVariants.getKey()) {
            final StopsList stopList = new StopsList(trip, maxStops, databaseAccessor);
            stopList.directionLabel.setText(stringManager.get("lang.label.direction",
                    new Pair<>("direction", trip.tripHeadsign)));
            stopList.stopButtons.forEach(b -> b.addActionListener(ev -> initRouteStopTimetableWindow(route, b.getResource())));
            stopSelection.stopLists.register(stopList, gridElementSettings(0, trips));
            trips++;
        }

        stopSelection.pack();
    }

    public void initRouteStopTimetableWindow(Route route, Stop stop) {
        final RouteStopTimetable timeTableWindow = new RouteStopTimetable(
                new RouteSchedule(route, stop, databaseAccessor), stringManager);
        timeTableWindow.setTitle(stringManager.get("lang.window.title.route_schedule",
                new Pair<>("number", route.routeShortName), new Pair<>("stop", stop.getFullName())));
    }
}
