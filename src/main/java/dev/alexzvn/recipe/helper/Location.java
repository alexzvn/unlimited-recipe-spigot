package dev.alexzvn.recipe.helper;

public class Location {
    public int x;
    public int y;
    public int z;

    public Location(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean is(Location l) {
        return l.x == x && l.y == y && l.z == z;
    }

    public boolean is(int index) {
        return is(Chest.indexToCoordinate(index));
    }

    public boolean isNot(Location l) {
        return ! is(l);
    }

    public boolean isNot(int index) {
        return ! is(index);
    }
}
