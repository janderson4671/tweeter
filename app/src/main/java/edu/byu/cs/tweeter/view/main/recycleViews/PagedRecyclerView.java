package edu.byu.cs.tweeter.view.main.recycleViews;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

abstract public class PagedRecyclerView<HolderType extends RecyclerView.ViewHolder, DataType> {

    private static final int LOADING_DATA_VIEW = 0;
    private static final int ITEM_VIEW = 1;

    private static final int PAGE_SIZE = 10;

    protected final List<DataType> itemList = new ArrayList<>();
    protected DataType lastItem;

    protected boolean hasMorePages;
    protected boolean isLoading = false;

    protected Context context;
    protected RecyclerView recyclerView;
    protected PagedRecyclerViewAdapter pagedRecyclerViewAdapter;

    public PagedRecyclerView(Context context, RecyclerView recyclerView) {
        this.context = context;
        this.recyclerView = recyclerView;

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.addOnScrollListener(new PagedRecyclerViewPaginationScrollListener(layoutManager));
    }

    public List<DataType> getList() {
        return itemList;
    }

    abstract protected class PagedRecyclerViewAdapter extends RecyclerView.Adapter<HolderType> {

        public PagedRecyclerViewAdapter() {
            loadMoreItems();
        }

        //Children must override these functions
        protected abstract void loadMoreItems();
        protected abstract void addLoadingFooter();

        protected void addItems(List<DataType> newItems) {
            int startInsertPosition = itemList.size();
            itemList.addAll(newItems);
            this.notifyItemRangeInserted(startInsertPosition, newItems.size());
        }

        protected void addItem(DataType item) {
            itemList.add(item);
            this.notifyItemInserted(itemList.size() - 1);
        }

        protected void removeItem(DataType item) {
            int position = itemList.indexOf(item);
            itemList.remove(position);
            this.notifyItemRemoved(position);
        }

        protected void removeLoadingFooter() {
            removeItem(itemList.get(itemList.size() - 1));
        }

        @Override
        public int getItemCount() {
            return itemList.size();
        }

        @Override
        public int getItemViewType(int position) {
            return (position == itemList.size() - 1 && isLoading) ? LOADING_DATA_VIEW : ITEM_VIEW;
        }

    }

    private class PagedRecyclerViewPaginationScrollListener extends RecyclerView.OnScrollListener {

        private final LinearLayoutManager layoutManager;

        /**
         * Creates a new instance.
         *
         * @param layoutManager the layout manager being used by the RecyclerView.
         */
        PagedRecyclerViewPaginationScrollListener(LinearLayoutManager layoutManager) {
            this.layoutManager = layoutManager;
        }

        /**
         * Determines whether the user has scrolled to the bottom of the currently available data
         * in the RecyclerView and asks the adapter to load more data if the last load request
         * indicated that there was more data to load.
         *
         * @param recyclerView the RecyclerView.
         * @param dx the amount of horizontal scroll.
         * @param dy the amount of vertical scroll.
         */
        @Override
        public void onScrolled(@NotNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            int visibleItemCount = layoutManager.getChildCount();
            int totalItemCount = layoutManager.getItemCount();
            int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

            if (!isLoading && hasMorePages) {
                if ((visibleItemCount + firstVisibleItemPosition) >=
                        totalItemCount && firstVisibleItemPosition >= 0) {
                    pagedRecyclerViewAdapter.loadMoreItems();
                }
            }
        }
    }

}
