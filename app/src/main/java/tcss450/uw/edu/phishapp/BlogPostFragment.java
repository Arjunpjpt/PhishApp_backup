package tcss450.uw.edu.phishapp;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class BlogPostFragment extends Fragment {

String url;
    public BlogPostFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_blog_post, container, false);
        Button btn = (Button) v.findViewById(R.id.btn_view_browser);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getArguments() != null) {
            String title = getArguments().getString("Blog Title");
            String date = getArguments().getString("Publish Date");
            String sample = getArguments().getString("Sampling");
             url = getArguments().getString("url");
            TextView tv = getActivity().findViewById(R.id.txt_blogtitle);
            tv.setText("Blog Title: "+ title);
            tv = getActivity().findViewById(R.id.txt_publishdate);
            tv.setText("Publish Date: "+date);
            tv = getActivity().findViewById(R.id.txt_sampling);
            tv.setText("Teaser: "+ Html.fromHtml(sample));
            //updateContent(uname, pwd);
        }

    }

}
