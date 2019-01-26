package tcss450.uw.edu.phishapp;

import android.content.Context;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import tcss450.uw.edu.phishapp.model.Credentials;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LoginFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class LoginFragment extends WaitFragment implements View.OnClickListener,  {

    private OnFragmentInteractionListener mListener;

    private String email;
    private View v;
    private String password;
    public LoginFragment() {
        // Required empty public constructor
    }
    public void onStart() {
        super.onStart();
        if(getArguments() != null) {
            String uname = getArguments().getString(getString(R.string.useremail));
            String pwd = getArguments().getString(getString(R.string.userpassword));
            updateContent(uname, pwd);
        }

    }
    public void updateContent(String uname, String pwd) {
        EditText editText_email = getActivity().findViewById(R.id.edittext_email);
        editText_email.setText(uname);
        EditText editText_pwd = getActivity().findViewById(R.id.edittext_password);
        editText_pwd.setText(pwd);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       // return inflater.inflate(R.layout.fragment_login, container, false);
         v = inflater.inflate(R.layout.fragment_login, container, false);






        Button btn_register = v.findViewById(R.id.btn_register);
        btn_register.setOnClickListener(this);

        Button btn_sign_in = v.findViewById(R.id.btn_signin);
        btn_sign_in.setOnClickListener(this);

        return v;
    }


    @Override
    public void onClick(View view){

        EditText email_text = v.findViewById(R.id.edittext_email);
        email = email_text.getText().toString();

        EditText pwd_text = v.findViewById(R.id.edittext_password);
        password = pwd_text.getText().toString();
        //System.out.println("$$$$"+email+"$$$----"+password);



        if(mListener != null){
            switch(view.getId()){
                case R.id.btn_register: mListener.onRegisterClicked();
                break;
                case R.id.btn_signin:
                    if(!email.isEmpty() && (!password.isEmpty())) {
                        if(checkspecialcharacter(email)) {
                            Credentials.Builder build= new Credentials.Builder(email,password);
                            Credentials credential = build.build();

                            mListener.onLoginSuccess(credential, "");
                        }else{
                            email_text.setError("Email should contain @");
                        }
                    }
                    else{
                        if(email.isEmpty()){
                            email_text.setError("Empty Email");
                        }
                        if(password.isEmpty()){
                            pwd_text.setError("Empty Password");
                        }

                    }

                break;
                default:
                    Log.wtf("LoginFragment","Error in Register / Signin button");
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
            a = email.charAt(i);
            if(Character.toString(a).equals("@")){
                return true;
            }

        }
        return false;
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
        //void onFragmentInteraction(Uri uri);
        void onLoginSuccess(tcss450.uw.edu.phishapp.model.Credentials credentials, String jwt);
        void onRegisterClicked();
    }
}
