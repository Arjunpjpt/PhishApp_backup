package tcss450.uw.edu.arjun;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import tcss450.uw.edu.arjun.BlogFragment.OnListFragmentInteractionListener;
import tcss450.uw.edu.arjun.blog.BlogPost;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link BlogPost} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyBlogRecyclerViewAdapter extends RecyclerView.Adapter<MyBlogRecyclerViewAdapter.ViewHolder> {

    private final List<BlogPost> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyBlogRecyclerViewAdapter(List<BlogPost> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_blog, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = (BlogPost) mValues.get(position);
        holder.mBlogTitle.setText(Html.fromHtml(((BlogPost) mValues.get(position)).getTitle()));
        holder.mpublishDate.setText(Html.fromHtml(((BlogPost) mValues.get(position)).getPubDate()));
        holder.msamplingview.setText(Html.fromHtml(((BlogPost) mValues.get(position)).getTeaser()));


        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
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
        public final TextView mBlogTitle;
        public final TextView mpublishDate;
        public final TextView msamplingview;
        public BlogPost mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mBlogTitle = (TextView) view.findViewById(R.id.txt_title);
            mpublishDate = (TextView) view.findViewById(R.id.txt_publishdate);
            msamplingview = (TextView) view.findViewById(R.id.txt_sampling);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mBlogTitle.getText() + "'"+ mpublishDate.getText() + "'"
                    + msamplingview.getText() + "'";
        }
    }
}
