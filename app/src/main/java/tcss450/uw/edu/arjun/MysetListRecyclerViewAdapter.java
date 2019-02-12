package tcss450.uw.edu.arjun;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import tcss450.uw.edu.arjun.setList.ListPost;
import tcss450.uw.edu.arjun.setListFragment.OnListFragmentInteractionListener;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link tcss450.uw.edu.arjun.setList.ListPost} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MysetListRecyclerViewAdapter extends RecyclerView.Adapter<MysetListRecyclerViewAdapter.ViewHolder> {

    private final List<ListPost> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MysetListRecyclerViewAdapter(List<ListPost> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_setlist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = (ListPost) mValues.get(position);

        holder.mLongDate.setText(Html.fromHtml(((ListPost) mValues.get(position)).getmLongDate()));
        holder.mLocation.setText(Html.fromHtml(((ListPost) mValues.get(position)).getmLocation()));
        holder.mVenue.setText(Html.fromHtml(((ListPost) mValues.get(position)).getmVenue()));



        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction( holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mLongDate;
        public final TextView mLocation;
        public final TextView mVenue;
        public ListPost mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mLongDate = (TextView) view.findViewById(R.id.txt_setlist_longdate);
            mLocation = (TextView) view.findViewById(R.id.txt_setlist_location);
            mVenue = (TextView) view.findViewById(R.id.txt_setlist_venue);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mLocation.getText() + "'";
        }
    }
}
