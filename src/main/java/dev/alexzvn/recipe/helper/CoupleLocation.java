package dev.alexzvn.recipe.helper;

public class CoupleLocation {

    public Location a;

    public Location b;

    public CoupleLocation(Location a, Location b) {
        this.a = a;
        this.b = b;
    }

    public CoupleLocation(int x1, int y1, int x2, int y2) {
        a = new Location(x1, y1);
        b = new Location(x2, y2);
    }

    public boolean inRange(Location l) {
        return (a.x >= l.x && a.y >= l.y && b.x <= l.x && b.y <= l.y) ||
            (a.x <= l.x && a.y <= l.y && b.x >= l.x && b.y >= l.y);
    }

    public boolean inRange(int x, int y) {
        return inRange(new Location(x, y));
    }
}
