package tcss450.uw.edu.phishapp;

import android.content.Context;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import tcss450.uw.edu.phishapp.model.Credentials;
import tcss450.uw.edu.phishapp.utils.SendPostAsyncTask;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RegisterFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class RegisterFragment extends Fragment implements View.OnClickListener {

    private OnFragmentInteractionListener mListener;
    private View view;
    private Credentials mCredentials;
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
        EditText userName_text = (EditText) view.findViewById(R.id.editText_username);
        EditText firstName_text = (EditText) view.findViewById(R.id.editText_firstName);
        EditText lastName_text = (EditText) view.findViewById(R.id.editText_lastname);
        EditText email_text = (EditText) view.findViewById(R.id.edittext_email);
        EditText pwd_text = (EditText) view.findViewById(R.id.editText_password);
        EditText re_pwd_text = (EditText) view.findViewById(R.id.editText_retype_password);
        String userName = userName_text.getText().toString();
        String firstName = firstName_text.getText().toString();
        String lastName = lastName_text.getText().toString();

        String email = email_text.getText().toString();
        String password = pwd_text.getText().toString();
        String re_password = re_pwd_text.getText().toString();


        if (!email.isEmpty() && (!password.isEmpty()) && (!re_password.isEmpty())
                && (!userName.isEmpty()) && (!firstName.isEmpty()) && (!lastName.isEmpty())) {
            if(!password.equals(re_password)){
                re_pwd_text.setError("Password not match");
            }  if(!checkspecialcharacter(email)){
                email_text.setError("email should contain @");
            }
            if(password.length()<6){
                pwd_text.setError("password lenght smaller than 6");
            }
            if((checkspecialcharacter(email)) && (password.equals(re_password)) && (password.length() > 5)){
                //success
                Credentials.Builder build= new Credentials.Builder(email,password);

                build.addFirstName(firstName);
                build.addLastName(lastName);
                build.addUsername(userName);

                Credentials credential = build.build();
//build the web service URL
                Uri uri = new Uri.Builder()
                        .scheme("https")
                        .appendPath(getString(R.string.ep_base_url_register)) .appendPath(getString(R.string.ep_register)) .build();
//build the JSONObject
                JSONObject msg = credential.asJSONObject();
                mCredentials = credential;
//instantiate and execute the AsyncTask.
                new SendPostAsyncTask.Builder(uri.toString(), msg)
                        .onPreExecute(this::handleRegisterOnPre) .onPostExecute(this::handleRegisterOnPost)
                        .onCancelled(this::handleErrorsInTask) .build().execute();
//                mListener.onRegisterSuccess(credential);
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
            if(firstName.isEmpty()){
                firstName_text.setError("FirstName is empty");
            }
            if(lastName.isEmpty()){
                lastName_text.setError("Last Name is empty");
            }
            if(userName.isEmpty()){
                userName_text.setError("User Name is empty");
            }
        }
    }
    /**
     * Handle errors that may occur during the AsyncTask.
     * @param result the error message provide from the AsyncTask */
    private void handleErrorsInTask(String result) { Log.e("ASYNC_TASK_ERROR", result);
    }
    /**
     * Handle the setup of the UI before the HTTP call to the webservice.
     */
    private void handleRegisterOnPre() {
        mListener.onWaitFragmentInteractionShow();
    }
    /**
     * Handle onPostExecute of the AsynceTask. The result from our webservice is
     * a JSON formatted String. Parse it for success or failure.
     * * @param result the JSON formatted String response from the web service
     * */

    private void handleRegisterOnPost(String result) {
        try {

            JSONObject resultsJSON = new JSONObject(result);
            boolean success =
                    resultsJSON.getBoolean(
                            getString(R.string.keys_json_login_success));
            if (success) {
//Login was successful. Switch to the loadSuccessFragment.
                mListener.onRegisterSuccess(mCredentials);//can remove second argument
                return;
            } else {
                //Login was unsuccessful. Don’t switch fragments and
                // inform the user
                ((TextView) getView().findViewById(R.id.edittext_email))
                        .setError("---Register Unsuccessful");
            }
            mListener.onWaitFragmentInteractionHide();
        } catch (JSONException e) {
            //It appears that the web service did not return a JSON formatted
            //String or it did not have what we expected in it.
            Log.e("JSON_PARSE_ERROR",  result
                    + System.lineSeparator()
                    + e.getMessage());
            mListener.onWaitFragmentInteractionHide();
            ((TextView) getView().findViewById(R.id.edittext_email))
                    .setError("Register Unsuccessful");
        } }
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

        void onWaitFragmentInteractionShow();

        void onWaitFragmentInteractionHide();
    }
}
