package com.rubynaxela.citybusnavi.kml;

import com.rubynaxela.citybusnavi.data.DatabaseAccessor;
import com.rubynaxela.citybusnavi.data.datatypes.auxiliary.Pair;
import com.rubynaxela.citybusnavi.data.datatypes.derived.Shape;
import com.rubynaxela.citybusnavi.data.datatypes.derived.Zone;
import com.rubynaxela.citybusnavi.data.datatypes.gtfs.Route;
import com.rubynaxela.citybusnavi.data.datatypes.gtfs.Stop;
import com.rubynaxela.citybusnavi.data.datatypes.gtfs.Trip;
import com.rubynaxela.citybusnavi.util.Utils;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public final class KMLDocumentController {

    private final KMLDocument document;
    private final DatabaseAccessor databaseAccessor;

    public KMLDocumentController(KMLDocument document, DatabaseAccessor databaseAccessor) {
        this.document = document;
        this.databaseAccessor = databaseAccessor;
    }

    public void addPinStyleSet(String id) {
        document.getRoot().add(KMLFactory.createStyleMap("msn_" + id,
                new Pair<>("normal", "#sn_" + id), new Pair<>("highlight", "#sh_" + id)));
        document.getRoot().add(KMLFactory.createPaddlePinStyle("sn_" + id, 1.1, id));
        document.getRoot().add(KMLFactory.createPaddlePinStyle("sh_" + id, 1.3, id));
    }

    public void addLineStyleSet(String id, Color color) {
        document.getRoot().add(KMLFactory.createStyleMap("msn_" + id,
                new Pair<>("normal", "#sn_" + id), new Pair<>("highlight", "#sh_" + id)));
        document.getRoot().add(KMLFactory.createLineStyle("sn_" + id, 1.1, Utils.colorToABGR(color)));
        document.getRoot().add(KMLFactory.createLineStyle("sh_" + id, 1.3, Utils.colorToABGR(color)));
    }

    public void createBasicStyleSets() {
        addPinStyleSet("red-circle");
        addPinStyleSet("ylw-circle");
        addPinStyleSet("grn-circle");
        addPinStyleSet("blu-circle");
        addLineStyleSet("night-line", new Color(0x40b7ff));
        addLineStyleSet("bus-day-line", new Color(0xe51600));
    }

    public Element addFolder(Element parent, String name, boolean opened) {
        Element folder = KMLFactory.createFolder(name, opened);
        parent.add(folder);
        return folder;
    }

    public Element addFolder(String name) {
        Element folder = KMLFactory.createFolder(name, true);
        document.getRoot().add(folder);
        return folder;
    }

    public void addStops(Element folder, ArrayList<Stop> stops) {
        Element zoneA = addFolder(folder, "Strefa A", false);
        Element zoneB = addFolder(folder, "Strefa B", false);
        Element zoneC = addFolder(folder, "Strefa C", false);
        Element zoneD = addFolder(folder, "Strefa D", false);
        Element zonePR = addFolder(folder, "Strefa PR", false);
        for (Stop stop : stops) {
            if (stop.zone == Zone.A)
                zoneA.add(KMLFactory.createPin(stop.getFullName(), stop.stopLat, stop.stopLon, "grn-circle"));
            else if (stop.zone == Zone.B)
                zoneB.add(KMLFactory.createPin(stop.getFullName(), stop.stopLat, stop.stopLon, "red-circle"));
            else if (stop.zone == Zone.C)
                zoneC.add(KMLFactory.createPin(stop.getFullName(), stop.stopLat, stop.stopLon, "ylw-circle"));
            else if (stop.zone == Zone.D)
                zoneD.add(KMLFactory.createPin(stop.getFullName(), stop.stopLat, stop.stopLon, "blu-circle"));
            else if (stop.zone == Zone.PR)
                zonePR.add(KMLFactory.createPin(stop.getFullName(), stop.stopLat, stop.stopLon, "pink-circle"));
            else
                folder.add(KMLFactory.createPin(stop.getFullName(), stop.stopLat, stop.stopLon, ""));
        }
    }

    public void addTrips(Element folder, ArrayList<Trip> trips) {
        Element tramTrips = addFolder(folder, "Linie tramwajowe", false);
        Element nightTrips = addFolder(folder, "Linie nocne", false);
        Element busTrips = addFolder(folder, "Linie autobusowe", false);
        ArrayList<String> addedRoutes = new ArrayList<>();
        for (Trip trip : trips) {
            if (!addedRoutes.contains(trip.routeId) && trip.tripId.contains("+")) {
                Shape linkedShape = databaseAccessor.getShapeById(trip.shapeId);
                if (trip.getRouteGroup() == 0)
                    tramTrips.add(KMLFactory.createPath(trip.getFullName(), linkedShape.points,
                            "route" + trip.routeId + "-line"));
                else if (trip.getRouteGroup() == 2)
                    nightTrips.add(KMLFactory.createPath(trip.getFullName(), linkedShape.points, "night-line"));
                else
                    busTrips.add(KMLFactory.createPath(trip.routeId + " -> " + trip.tripHeadsign + " #" + trip.tripId,
                            linkedShape.points, "bus-day-line"));
                addedRoutes.add(trip.routeId);
            }
        }
    }

    public void createDataDemo() {
        for (Route route : databaseAccessor.getRoutes())
            if (route.getGroup() == 0) addLineStyleSet("route" + route.routeId + "-line", route.routeColor);
        addTrips(addFolder("Kursy"), databaseAccessor.getTrips());
        addStops(addFolder("Przystanki"), databaseAccessor.getStops());
    }

    public void save(File destination) {
        try {
            OutputFormat format = new OutputFormat();
            format.setIndent("\t");
            format.setNewlines(true);
            XMLWriter writer = new XMLWriter(new FileOutputStream(destination), format);
            writer.write(document.getDocument());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
