package tcss450.uw.edu.arjun;

import android.content.Context;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
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

import me.pushy.sdk.Pushy;
import tcss450.uw.edu.arjun.model.Credentials;
import tcss450.uw.edu.arjun.utils.SendPostAsyncTask;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LoginFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {

    private OnFragmentInteractionListener mListener;
    private Credentials mCredentials;

    private String email;
    private View v;
    private String password;

    private String mJwt;
    public LoginFragment() {
        // Required empty public constructor
    }
    @Override
    public void onStart() {
        super.onStart();

        SharedPreferences prefs =
                getActivity().getSharedPreferences(
                        getString(R.string.keys_shared_prefs),
                        Context.MODE_PRIVATE);
//retrieve the stored credentials from SharedPrefs
        if (prefs.contains(getString(R.string.keys_prefs_email)) &&
                prefs.contains(getString(R.string.keys_prefs_password))) {

            final String email = prefs.getString(getString(R.string.keys_prefs_email), "");
            final String password = prefs.getString(getString(R.string.keys_prefs_password), "");

//Load the two login EditTexts with the credentials found in SharedPrefs
            EditText emailEdit = getActivity().findViewById(R.id.edittext_email);
            emailEdit.setText(email);
            EditText passwordEdit = getActivity().findViewById(R.id.edittext_password);
            passwordEdit.setText(password);

            doLogin(new Credentials.Builder(
                    emailEdit.getText().toString(),
                    passwordEdit.getText().toString())
                    .build());

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






        Button btn_register = (Button) v.findViewById(R.id.btn_register);
        btn_register.setOnClickListener(this);

        Button btn_sign_in = (Button) v.findViewById(R.id.btn_signin);
        btn_sign_in.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                attemptLogin(v);
            }

            private void attemptLogin(final View theButton) {
                EditText emailEdit = getActivity().findViewById(R.id.edittext_email);
                EditText passwordEdit = getActivity().findViewById(R.id.edittext_password);
                boolean hasError = false;
                if (emailEdit.getText().length() == 0) {
                    hasError = true;
                    emailEdit.setError("Field must not be empty.");
                } else if (emailEdit.getText().toString().chars().filter(ch -> ch == '@').count() != 1) {
                    hasError = true;
                    emailEdit.setError("Field must contain a valid email address."); }
                if (passwordEdit.getText().length() == 0) {hasError = true;
                    passwordEdit.setError("Field must not be empty."); }
                if (!hasError) {
                    doLogin(new Credentials.Builder( emailEdit.getText().toString(),
                            passwordEdit.getText().toString())
                            .build());
//                    Credentials credentials = new Credentials.Builder(
//                            emailEdit.getText().toString(),
//                            passwordEdit.getText().toString())
//                            .build();
////build the web service URL
//                    Uri uri = new Uri.Builder()
//                            .scheme("https")
//                            .appendPath(getString(R.string.ep_base_url)) .appendPath(getString(R.string.ep_login)) .build();
////build the JSONObject
//                    JSONObject msg = credentials.asJSONObject();
//                    mCredentials = credentials;
////instantiate and execute the AsyncTask.
//                    new SendPostAsyncTask.Builder(uri.toString(), msg)
//                            .onPreExecute(this::handleLoginOnPre) .onPostExecute(this::handleLoginOnPost)
//                            .onCancelled(this::handleErrorsInTask) .build().execute();
                }
            }
//
//            /**
//             * Handle onPostExecute of the AsynceTask. The result from our webservice is
//             * a JSON formatted String. Parse it for success or failure.
//             * * @param result the JSON formatted String response from the web service
//             * */
//            private void handleLoginOnPost(String result) {
//                try {
//                    JSONObject resultsJSON = new JSONObject(result);
//                    boolean success =
//                            resultsJSON.getBoolean(
//                                    getString(R.string.keys_json_login_success));
//
////Login was successful. Switch to the loadSuccessFragment.
//                        if (success) {
//                            //Login was successful. Switch to the loadSuccessFragment.
//                            mJwt = resultsJSON.getString(
//                                    getString(R.string.keys_json_login_jwt));
//                            saveCredentials(mCredentials);
//                            mListener.onLoginSuccess(mCredentials, mJwt);
//
//                            return;
//
//                    } else {
//                        //Login was unsuccessful. Don’t switch fragments and
//                        // inform the user
//                        ((TextView) getView().findViewById(R.id.edittext_email))
//                                .setError("Login Unsuccessful");
//                    }
//                    mListener.onWaitFragmentInteractionHide();
//                } catch (JSONException e) {
//                    //It appears that the web service did not return a JSON formatted
//                    //String or it did not have what we expected in it.
//                    Log.e("JSON_PARSE_ERROR",  result
//                            + System.lineSeparator()
//                            + e.getMessage());
//                    mListener.onWaitFragmentInteractionHide();
//                    ((TextView) getView().findViewById(R.id.edittext_email))
//                            .setError("Login Unsuccessful");
//                }
//            }
//            ////
//            /**
//             * Handle errors that may occur during the AsyncTask.
//             * @param result the error message provide from the AsyncTask */
//            private void handleErrorsInTask(String result) {
//
//                Log.e("ASYNC_TASK_ERROR", result);
//            }
//
//            /**
//             * Handle the setup of the UI before the HTTP call to the webservice.
//             */
//            private void handleLoginOnPre() {
//                mListener.onWaitFragmentInteractionShow();
//            }

        });


        return v;
    }


    @Override
    public void onClick(View view){

        EditText email_text = (EditText) v.findViewById(R.id.edittext_email);
        email = email_text.getText().toString();

        EditText pwd_text = (EditText) v.findViewById(R.id.edittext_password);
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
     * Handle errors that may occur during the AsyncTask.
     * @param result the error message provide from the AsyncTask */
    private void handleErrorsInTask(String result) {
        Log.e("ASYNC_TASK_ERROR", result);
    }

    /**
     * Handle the setup of the UI before the HTTP call to the webservice.
     */
    private void handleLoginOnPre() {
        mListener.onWaitFragmentInteractionShow();
    }

    /**
     * Handle onPostExecute of the AsynceTask. The result from our webservice is
     * a JSON formatted String. Parse it for success or failure.
     * * @param result the JSON formatted String response from the web service
     * */
    private void handleLoginOnPost(String result) {
        try {
            JSONObject resultsJSON = new JSONObject(result);
            boolean success =
                    resultsJSON.getBoolean(
                            getString(R.string.keys_json_login_success));
            if (success) {
//Login was successful. Switch to the loadSuccessFragment.
                mJwt = resultsJSON.getString(
                        getString(R.string.keys_json_login_jwt));
                new RegisterForPushNotificationsAsync().execute();
//                mListener.onLoginSuccess(mCredentials,
//                        resultsJSON.getString(
//                                getString(R.string.keys_json_login_jwt)));
                return;
            } else {
                //Login was unsuccessful. Don’t switch fragments and
                // inform the user
                ((TextView) getView().findViewById(R.id.edittext_email))
                        .setError("Login Unsuccessful");
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
                    .setError("Login Unsuccessful");
        }
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
    public interface OnFragmentInteractionListener extends WaitFragment.OnFragmentInteractionListener{
        // TODO: Update argument type and name
        //void onFragmentInteraction(Uri uri);
        void onLoginSuccess(tcss450.uw.edu.arjun.model.Credentials credentials, String jwt);
        void onRegisterClicked();
    }

    private void saveCredentials(final Credentials credentials) {
        SharedPreferences prefs = getActivity().getSharedPreferences(
                getString(R.string.keys_shared_prefs),
                Context.MODE_PRIVATE);
        //Store the credentials in SharedPrefs
        prefs.edit().putString(getString(R.string.keys_prefs_email), credentials.getEmail()).apply();
        prefs.edit().putString(getString(R.string.keys_prefs_password), credentials.getPassword()).apply();


    }
    private void doLogin(Credentials credentials) { //build the web service URL
        Uri uri = new Uri.Builder()
                .scheme("https") .appendPath(getString(R.string.ep_base_url)) .appendPath(getString(R.string.ep_login)) .build();
//build the JSONObject
        JSONObject msg = credentials.asJSONObject();
        mCredentials = credentials;
        Log.d("JSON Credentials", msg.toString());
//instantiate and execute the AsyncTask.
//Feel free to add a handler for onPreExecution so that a progress bar //is displayed or maybe disable buttons.
        new SendPostAsyncTask.Builder(uri.toString(), msg)
                .onPreExecute(this::handleLoginOnPre)
                .onPostExecute(this::handleLoginOnPost)
                .onCancelled(this::handleErrorsInTask)
                .build().execute();
    }

    //
    private void handlePushyTokenOnPost(String result) { try {
        Log.d("JSON result",result);
        JSONObject resultsJSON = new JSONObject(result);

        boolean success = resultsJSON.getBoolean("success");

        if (success) {
            saveCredentials(mCredentials);
            mListener.onLoginSuccess(mCredentials, mJwt);
            return;
        } else {
//Saving the token wrong. Don’t switch fragments and inform the user
        ((TextView) getView().findViewById(R.id.edittext_email))
            .setError("Login Unsuccessful");
        }
        mListener.onWaitFragmentInteractionHide(); } catch (JSONException e) {
        //It appears that the web service didn’t return a JSON formatted String
//or it didn’t have what we expected in it.
        Log.e("JSON_PARSE_ERROR", result
            + System.lineSeparator()
                + e.getMessage());
        mListener.onWaitFragmentInteractionHide();
        ((TextView) getView().findViewById(R.id.edittext_email)) .setError("Login Unsuccessful");
        }
    }
    //
    private class RegisterForPushNotificationsAsync extends AsyncTask<Void, String, String>
    {
        protected String doInBackground(Void... params) {
            String deviceToken = "";
            try {
                // Assign a unique token to this device
                deviceToken = Pushy.register(getActivity().getApplicationContext());
                //subscribe to a topic (this is a Blocking call)
                Pushy.subscribe("all", getActivity().getApplicationContext());
            }
            catch (Exception exc) {
                cancel(true);
                // Return exc to onCancelled
                return exc.getMessage();
            }
// Success
            return deviceToken;
        }
        @Override
        protected void onCancelled(String errorMsg) {
            super.onCancelled(errorMsg);
            Log.d("PhishApp", "Error getting Pushy Token: " + errorMsg);
        }
        @Override
        protected void onPostExecute(String deviceToken) {
// Log it for debugging purposes
            System.out.println("error here ---0");
            Log.d("PhishApp", "Pushy device token: " + deviceToken);

            //build the web service URL
            Uri uri = new Uri.Builder()
                    .scheme("https")
                    .appendPath(getString(R.string.ep_base_url))
                    .appendPath(getString(R.string.ep_pushy))
                    .appendPath(getString(R.string.ep_token))
                    .build();
//build the JSONObject
            JSONObject msg = mCredentials.asJSONObject();
            try {
                msg.put("token", deviceToken);
            } catch (JSONException e) {
                e.printStackTrace();
            }
//instantiate and execute the AsyncTask.
            new SendPostAsyncTask.Builder(uri.toString(), msg)
                    .onPostExecute(LoginFragment.this::handlePushyTokenOnPost)
                    .onCancelled(LoginFragment.this::handleErrorsInTask)
                    .addHeaderField("authorization", mJwt)
                    .build().execute();


//            saveCredentials(mCredentials);
//            mListener.onLoginSuccess(mCredentials, mJwt);
        }
    }


}
