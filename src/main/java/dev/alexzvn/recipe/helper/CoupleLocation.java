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

    public boolean contains(Location l) {
        return (a.x >= l.x && a.y >= l.y && b.x <= l.x && b.y <= l.y) ||
            (a.x <= l.x && a.y <= l.y && b.x >= l.x && b.y >= l.y);
    }

    public boolean contains(int x, int y) {
        return contains(new Location(x, y));
    }

    public boolean notContains(Location l) {
        return ! contains(l);
    }

    public boolean notContains(int x, int y) {
        return ! contains(x, y);
    }
}
