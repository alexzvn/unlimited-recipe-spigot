package dev.alexzvn.recipe.helper;

import java.util.Arrays;

import org.bukkit.inventory.ItemStack;

public class ItemPaginator {

    public ItemStack[][] items;

    public boolean next;

    public boolean prev;

    public int total;

    public int currentPage;

    public int totalPage;

    public int perPage;

    protected int offset;

    protected int perRow;

    protected ItemStack[] rawItems;

    public ItemPaginator(ItemStack[] items, int perPage, int perRow, int page) {
        this.currentPage = page;
        this.rawItems = items;
        this.perPage = perPage;
        this.offset = page * (perPage - 1);
        this.total = rawItems.length;
        this.perRow = perRow;

        init();
    }

    protected void init() {
        ItemStack[] items = Arrays.copyOfRange(
            rawItems, offset, Math.min(rawItems.length, offset + perPage - 1)
        );

        this.items = Chest.chunk(items, perRow);

        next = total - 1 > offset + perPage - 1;
        prev = currentPage > 0;
    }
}
