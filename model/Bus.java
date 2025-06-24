package model;

public class Bus {
    private int id;
    private String name;
    private String type;
    private int totalSeats;

    public Bus(int id, String name, String type, int totalSeats) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.totalSeats = totalSeats;
    }

    public Bus(String name, String type, int totalSeats) {
        this.name = name;
        this.type = type;
        this.totalSeats = totalSeats;
    }

    // Getter & Setter
    public int getId() { return id; }
    public String getName() { return name; }
    public String getType() { return type; }
    public int getTotalSeats() { return totalSeats; }

    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setType(String type) { this.type = type; }
    public void setTotalSeats(int totalSeats) { this.totalSeats = totalSeats; }
}
