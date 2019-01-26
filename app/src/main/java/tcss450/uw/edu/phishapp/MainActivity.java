package tcss450.uw.edu.phishapp;

import android.content.Intent;
import android.net.Credentials;
import android.os.Parcelable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity implements LoginFragment.OnFragmentInteractionListener,
        RegisterFragment.OnFragmentInteractionListener, WaitFragment.OnFragmentInteractionListener {

    public static final String PASSINTENT="pass";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null){
            if (findViewById(R.id.frame_main_container) !=null){
                getSupportFragmentManager().beginTransaction().add(R.id.frame_main_container, new LoginFragment()).commit();
            }
        }
    }

@Override
public void onRegisterSuccess(tcss450.uw.edu.phishapp.model.Credentials credential){
    Log.d("MainActivity", "Register Success");
//    LoginFragment loginFragment;
//    loginFragment = new LoginFragment();
//    Bundle args = new Bundle();
//    args.putSerializable(getString(R.string.useremail), credential.getEmail());
//    args.putSerializable(getString(R.string.userpassword), credential.getPassword());
//    loginFragment.setArguments(args);

    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
    intent.putExtra(PASSINTENT,  credential);
    startActivity(intent);

//    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction()
//            .replace(R.id.frame_main_container, loginFragment).addToBackStack(null);
//    transaction.commit();
}
    @Override
    public void onLoginSuccess(tcss450.uw.edu.phishapp.model.Credentials credential, String jwt) {
        Log.d("MainActivity", "Login Button");
//        SuccessFragment successfragment;
//        successfragment = new SuccessFragment();
//        Bundle args = new Bundle();
//        args.putSerializable(getString(R.string.useremail), credential.getEmail());
//        successfragment.setArguments(args);
        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
        intent.putExtra(PASSINTENT,  credential);
        System.out.println("THis is from main---- "+credential.getEmail());
        startActivity(intent);
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction()
//                .replace(R.id.frame_main_container, successfragment).addToBackStack(null);
//        transaction.commit();

    }

    @Override
    public void onRegisterClicked() {
        Log.d("MainActivity", "Register Button");

        RegisterFragment registerfragment;
        registerfragment = new RegisterFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_main_container, registerfragment).addToBackStack(null);
        transaction.commit();
//        System.out.println(".......------------");
    }

    @Override
    public void onWaitFragmentInteractionShow() {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.frame_main_container, new WaitFragment(), "WAIT")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onWaitFragmentInteractionHide() {
        getSupportFragmentManager()
                .beginTransaction()
                .remove(getSupportFragmentManager().findFragmentByTag("WAIT"))
                .commit();
    }
}
