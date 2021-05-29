package com.rubynaxela.citybusnavi;

import com.rubynaxela.citybusnavi.assets.IconManager;
import com.rubynaxela.citybusnavi.assets.Language;
import com.rubynaxela.citybusnavi.assets.StringManager;
import com.rubynaxela.citybusnavi.commands.CommandHandler;
import com.rubynaxela.citybusnavi.commands.DataProcessor;
import com.rubynaxela.citybusnavi.data.Database;
import com.rubynaxela.citybusnavi.data.DatabaseAccessor;
import com.rubynaxela.citybusnavi.data.DatabaseController;
import com.rubynaxela.citybusnavi.gui.DialogHandler;
import com.rubynaxela.citybusnavi.gui.GUIManager;
import com.rubynaxela.citybusnavi.io.IOHandler;

/**
 * The main class
 */
@SuppressWarnings("FieldCanBeLocal")
public final class CityBusNavi {

    private StringManager stringManager;
    private IconManager iconManager;
    private IOHandler ioHandler;
    private DialogHandler dialogHandler;
    private DatabaseController databaseController;
    private DatabaseAccessor databaseAccessor;
    private CommandHandler commandHandler;
    private DataProcessor dataProcessor;
    private GUIManager guiManager;
    private Database gtfsData;

    /**
     * Application entry point
     *
     * @param args unused
     */
    public static void main(String[] args) {
        CityBusNavi app = new CityBusNavi();
        app.start();
    }

    private void loadModules() {
        stringManager = new StringManager();
        iconManager = new IconManager();
        ioHandler = new IOHandler(this);
        dialogHandler = new DialogHandler(this);
        databaseController = new DatabaseController(gtfsData, this);
        databaseAccessor = new DatabaseAccessor(gtfsData);
        commandHandler = new CommandHandler(this);
        dataProcessor = new DataProcessor(this);
        guiManager = new GUIManager(this);
    }

    public void start() {

        // Initialize an empty database object
        gtfsData = new Database();

        // Load all application handlers, managers and controllers
        loadModules();

        // Using english as the default language
        stringManager.useLanguage(Language.ENGLISH_US);

        // Initial GUI settings and loading the main window
        guiManager.initMainWindow();

        // Loading data from files to the database
        guiManager.lockMainWindowUntilDone(databaseController::loadAll);
    }

    public StringManager getStringManager() {
        return stringManager;
    }

    public IconManager getIconManager() {
        return iconManager;
    }

    public IOHandler getIOHandler() {
        return ioHandler;
    }

    public DatabaseController getDatabaseController() {
        return databaseController;
    }

    public DatabaseAccessor getDatabaseAccessor() {
        return databaseAccessor;
    }

    public DialogHandler getDialogHandler() {
        return dialogHandler;
    }

    public CommandHandler getCommandHandler() {
        return commandHandler;
    }

    public DataProcessor getDataProcessor() {
        return dataProcessor;
    }
}
