package com.rubynaxela.citybusnavi.commands;

import com.rubynaxela.citybusnavi.CityBusNavi;
import com.rubynaxela.citybusnavi.assets.StringManager;
import com.rubynaxela.citybusnavi.data.DatabaseAccessor;
import com.rubynaxela.citybusnavi.data.DatabaseController;
import com.rubynaxela.citybusnavi.gui.DialogHandler;
import com.rubynaxela.citybusnavi.io.IOHandler;
import com.rubynaxela.citybusnavi.kml.KMLDocument;
import com.rubynaxela.citybusnavi.kml.KMLDocumentController;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public final class CommandHandler {

    private final StringManager stringManager;
    private final DialogHandler dialogHandler;
    private final IOHandler ioHandler;
    private final DatabaseController databaseController;
    private final DatabaseAccessor databaseAccessor;

    public CommandHandler(CityBusNavi instance) {
        stringManager = instance.getStringManager();
        dialogHandler = instance.getDialogHandler();
        ioHandler = instance.getIOHandler();
        databaseController = instance.getDatabaseController();
        databaseAccessor = instance.getDatabaseAccessor();
    }

    /**
     * Downloads and loads up-to-date GTFS data from the server. Displays error dialog on failure
     */
    public void updateSchedule() {
        try {
            // Downloading and unpacking GTFS data
            ioHandler.retrieveGTFSData(new URL(stringManager.get("string.gtfs_file.url")),
                    new File(stringManager.get("string.gtfs_file.directory"), stringManager.get("string.gtfs_file.name")));
            // Loading data from files to the database
            databaseController.loadAll();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            dialogHandler.showError(stringManager.get("lang.error.server_connection"));
        }
    }

    /**
     * Creates a demo KML file containing every stop and route (the primary trip)
     * in the database and shows a file chooser dialog to determine where to save it
     */
    public void createDemoKMLFile() {
        final File destination = dialogHandler.chooseFileToSave("demo.kml", "kml");
        if (destination != null) {
            KMLDocument kmlDocument = new KMLDocument("Dane ZTM");
            KMLDocumentController kmlController = new KMLDocumentController(kmlDocument, databaseAccessor);
            kmlController.createBasicStyleSets();
            kmlController.createDataDemo();
            kmlController.save(destination);
            dialogHandler.showInfo("Demo file saved as " + destination.getAbsolutePath());
        }
    }
}
