package tcss450.uw.edu.phishapp;

import android.content.Context;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import tcss450.uw.edu.phishapp.model.Credentials;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RegisterFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class RegisterFragment extends Fragment implements View.OnClickListener {

    private OnFragmentInteractionListener mListener;
    private View view;
    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_register, container, false);
        Button btn_register = (Button) view.findViewById(R.id.btn_register);
        btn_register.setOnClickListener(this);

        return view;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }




    @Override
    public void onClick(View v) {
        EditText email_text = (EditText) view.findViewById(R.id.edittext_email);
        EditText pwd_text = (EditText) view.findViewById(R.id.editText_password);
        EditText re_pwd_text = (EditText) view.findViewById(R.id.editText_retype_password);
        String email = email_text.getText().toString();
        String password = pwd_text.getText().toString();
        String re_password = re_pwd_text.getText().toString();


        if (!email.isEmpty() && (!password.isEmpty()) && (!re_password.isEmpty())) {
            if(!password.equals(re_password)){
                re_pwd_text.setError("Password not match");
            } else if(!checkspecialcharacter(email)){
                email_text.setError("email should contain @");
            }else if(password.length()<6){
                pwd_text.setError("password lenght smaller than 6");
            }
            else{
                //success
                Credentials.Builder build= new Credentials.Builder(email,password);
                Credentials credential = build.build();

                mListener.onRegisterSuccess(credential);
            }

        } else {
            if (email.isEmpty()) {
                email_text.setError("Email is empty");
            }
            if(password.isEmpty()) {
                pwd_text.setError("Password is empty");
            }
            if(re_password.isEmpty()){
                re_pwd_text.setError("Re-Password is empty");
            }
        }
    }

    /**
     * method to check if the string contains @
     * @param email
     * @return
     */
    public boolean checkspecialcharacter(String email){
        char a;
        for(int i=0; i<email.length(); i++){

            if(email.charAt(i) =='@'){
                return true;
            }

        }
        return false;
    }
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onRegisterSuccess(tcss450.uw.edu.phishapp.model.Credentials credential);
    }
}
