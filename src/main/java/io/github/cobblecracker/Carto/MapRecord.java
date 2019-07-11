package io.github.cobblecracker.Carto;


public class MapRecord {
    private int mapId;
    private String registrarName;
    private String registrarUuid;

    public MapRecord(int mapId, String registrarName, String registrarUuid) {
        this.mapId = mapId;
        this.registrarName = registrarName;
        this.registrarUuid = registrarUuid;
    }

    public int getMapId() {
        return mapId;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
    }

    public String getRegistrarName() {
        return registrarName;
    }

    public void setRegistrarName(String registrarName) {
        this.registrarName = registrarName;
    }

    public String getRegistrarUuid() {
        return registrarUuid;
    }

    public void setRegistrarUuid(String registrarUuid) {
        this.registrarUuid = registrarUuid;
    }
}
