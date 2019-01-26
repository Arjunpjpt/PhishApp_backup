package tcss450.uw.edu.phishapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.

 */
public class SuccessFragment extends Fragment  {

//    private OnFragmentInteractionListener mListener;

    public SuccessFragment() {
        // Required empty public constructor
    }
@Override
public void onDetach() {
    super.onDetach();
    System.exit(0);
}

    @Override
    public void onStart() {
        super.onStart();
        System.out.println("[[[[This is from onstart]]]");
        if(getArguments() != null) {
            String uname = getArguments().getString(getString(R.string.useremail));
            System.out.println("++++++"+uname +"++++");
            updateContent(uname);
        }

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        System.out.println("################");
        return inflater.inflate(R.layout.fragment_success, container, false);
    }

    public void updateContent(String uname) {
        TextView tv = getActivity().findViewById(R.id.text_appname);
        tv.setText(uname);
    }

}






