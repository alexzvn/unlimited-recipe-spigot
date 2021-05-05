package dev.alexzvn.recipe.helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Chest {

    protected Inventory inv;

    public Chest(Inventory inv) {
        this.inv = inv;
    }

    public Inventory getInventory() {
        return inv;
    }

    public ItemStack[][] rageMatrixItemStack(CoupleLocation l) {
        return rangeMatrixItemStack(l.a.x, l.a.y, l.b.x, l.b.y);
    }

    public ItemStack[][] rangeMatrixItemStack(int x1, int y1, int x2, int y2) {
        ItemStack[][] items = new ItemStack[Math.abs(y1 - y2) + 1][];

        int[][] matrixIndexs = rangeToMatrixIndexs(x1, y1, x2, y2);

        for (int y = 0; y < matrixIndexs.length; y++) {
            ItemStack[] row = new ItemStack[Math.abs(x1 - x2) + 1];

            for (int x = 0; x < matrixIndexs[y].length; x++) {
                row[x] = slot(matrixIndexs[y][x]);
            }

            items[y] = row;
        }

        return items;
    }

    public void matrixFill(ItemStack[][] items, int x, int y) {
        for (int y1 = 0; y1 < items.length; y1++) {
            for (int x1 = 0; x1 < items[y1].length; x1++) {
                fill(items[y1][x1], x1 + x, y1 + y);
            }
        }
    }

    public void matrixFill(ItemStack[][] items, int index) {
        Location location = indexToCoordinate(index);

        matrixFill(items, location.x, location.y);
    }

    public void matrixFill(ItemStack[][] items, Location location) {
        matrixFill(items, location.x, location.y);
    }

    public void fill(ItemStack item, int x1, int y1, int x2, int y2) {
        Set<Integer> indexs = rangeToIndexs(x1, y1, x2, y2);

        item = item == null ? Util.airItem() : item;

        for (Integer index : indexs) {
            inv.setItem(index, item);
        }
    }

    public void fill(ItemStack item, int x, int y) {
        item = item == null ? Util.airItem() : item;

        inv.setItem(coordinateToIndex(x, y), item);
    }

    public void fill(ItemStack item, CoupleLocation l) {
        fill(item, l.a.x, l.a.y, l.b.x, l.b.y);
    }

    public void fill(ItemStack item, Location l) {
        fill(item, l.x, l.y);
    }

    public void fill(ItemStack item) {
        for (int i = 0; i < inv.getSize(); i++) {
            inv.setItem(i, item);
        }
    }

    public ItemStack slot(int x, int y) {
        return inv.getItem(coordinateToIndex(x, y));
    }

    public ItemStack slot(int index) {
        return inv.getItem(index);
    }

    public ItemStack slot(Location l) {
        return slot(l.x, l.y);
    }

    public void clear(Location location) {
        inv.clear(coordinateToIndex(location.x, location.y));
    }

    public void clear(CoupleLocation location) {
        fill(Util.airItem(), location);
    }

    public static boolean isClick(int indexSlotClicked, Location location) {
        return isClick(indexSlotClicked, location.x, location.y);
    }

    public static boolean isNotClick(int indexSlotClicked, Location location) {
        return ! isClick(indexSlotClicked, location.x, location.y);
    }

    public static boolean isClick(int indexSlotClicked, int x, int y) {
        return indexSlotClicked == coordinateToIndex(x, y);
    }

    public static boolean isNotClick(int indexSlotClicked, int x, int y) {
        return ! isClick(indexSlotClicked, x, y);
    }

    public static int coordinateToIndex(int x, int y) {
        return (x * y) + ((9 - x) * y) - (9 - x) - 1;
    }

    public static Location indexToCoordinate(int index) {
        index += 1; //offset index array

        int mod = index % 9;

        int x = mod == 0 ? 9 : mod;
        int y = mod == 0 ? index / 9 : (int) (index / 9) + 1;

        return new Location(x, y);
    }
 
    public static int[][] rangeToMatrixIndexs(int x1, int y1, int x2, int y2) {
        int largeX = x1 > x2 ? x1 : x2;
        int smallX = x1 < x2 ? x1 : x2;
        int largeY = y1 > y2 ? y1 : y2;
        int smallY = y1 < y2 ? y1 : y2;

        int[][] matrixIndexs = new int[largeY - smallY + 1][];

        int i = 0;
        for (int y = smallY; y <= largeY; y++) {
            int j = 0;
            int[] indexs = new int[largeX - smallX + 1];

            for (int x = smallX; x <= largeX; x++) {
                indexs[j] = coordinateToIndex(x, y);
                j++;
            }

            matrixIndexs[i] = indexs;
            i++;
        }

        return matrixIndexs;
    }

    public static Set<Integer> rangeToIndexs(int x1, int y1, int x2, int y2) {
        int largeX = x1 > x2 ? x1 : x2;
        int smallX = x1 < x2 ? x1 : x2;
        int largeY = y1 > y2 ? y1 : y2;
        int smallY = y1 < y2 ? y1 : y2;

        HashSet<Integer> indexs = new HashSet<Integer>();

        for (int y = smallY; y <= largeY; y++) {
            for (int x = smallX; x <= largeX; x++) {
                indexs.add(coordinateToIndex(x, y));
            }
        }

        return indexs;
    }

    public static ItemStack[][] chunk(ItemStack[] items, int length) {
        List<ItemStack[]> chunkList = new ArrayList<ItemStack[]>();

        for (int i = 0; i < items.length; i+= length) {
            chunkList.add(
                Arrays.copyOfRange(items, i, Math.min(items.length, i+length))
            );
        }

        ItemStack[][] chunk = new ItemStack[chunkList.size()][length];

        for (int i = 0; i < chunkList.size(); i++) {
            chunk[i] = chunkList.get(i);
        }

        return chunk;
    }

    public static ItemStack[][] trimMatrix(ItemStack[][] items) {
        // return trimMatrixX(trimMatrixY(items));

        return items;
    }

    public static ItemStack[][] trimMatrixX(ItemStack[][] items) {
        Integer min = null, max = null;

        for (ItemStack[] row : items) {
            for (int i = 0; i < row.length; i++) {
                if (! Util.isAirItem(row[i])) {
                    max = (max == null || i > max) ? i : max;
                    min = (min == null || min > i) ? i : min;
                }
            }
        }

        min = (min == null) ? -1 : min;
        max = (max == null) ? -1 : max;
        ItemStack[][] compact = new ItemStack[items.length][];

        for (int i = 0; i < items.length; i++) {
            int j = 0;
            ItemStack[] row = new ItemStack[max - min + 1];

            for (int col = min; col >= 0 && col <= max; col++) {
                row[j] = items[i][col]; j++;
            }

            compact[i] = row;
        }

        return compact;
    }

    public static ItemStack[][] trimMatrixY(ItemStack[][] items) {
        int rows = 0;

        /**
         * TODO fix index array bound
         * 
         * 2nd row will be ignored for example:
         * @ @ @
         * 
         * @ @
         * 
         */
        for (ItemStack[] row : items) {
            if (Util.containItems(row)) rows++;
        }

        ItemStack[][] compact = new ItemStack[rows][];

        for (int i = 0; i < items.length; i++) {
            ItemStack[] row = items[i];

            if (Util.containItems(row)) {
                compact[i] = row;
            }
        }

        return compact;
    }

    public static boolean isEmptyMatrix(ItemStack[][] items) {

        for (ItemStack[] itemRow : items) {
            for (ItemStack item : itemRow) {
                if (Util.isAirItem(item) == false) {
                    return false;
                }
            }
        }

        return true;
    }

    public static List<ItemStack> flatMatrix(ItemStack[][] items) {
        List<ItemStack> compact = new ArrayList<ItemStack>();

        for (ItemStack[] row : items) {
            for (ItemStack item : row) {
                compact.add(item);
            }
        }

        return compact;
    }

    public static List<ItemStack> removeAir(List<ItemStack> items) {
        List<ItemStack> compact = new ArrayList<ItemStack>();

        for (ItemStack item : items) {
            if (! Util.isAirItem(item)) compact.add(item);
        }

        return compact;
    }
}
