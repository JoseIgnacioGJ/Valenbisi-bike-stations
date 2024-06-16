package com.example.myapplication;

/*A report must have the following information:
- Name
- Description
- Bike station
- Status:
    • Open
    • Processing
    • Closed
- Type:
    • Mechanical
    • Electric
    • Painting
    • Masonry
*/
public class Report {
    public int id;
    public String name;
    public String description;
    public int bike_station;
    public String status;
    public String type;
}
