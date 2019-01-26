package tcss450.uw.edu.phishapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import tcss450.uw.edu.phishapp.blog.BlogPost;
//import tcss450.uw.edu.phishapp.dummy.DummyContent;
import tcss450.uw.edu.phishapp.model.Credentials;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, RecyclerVieww.OnListFragmentInteractionListener {
    private String mUserName ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent i= getIntent();
        Object passintent = getIntent().getSerializableExtra(MainActivity.PASSINTENT);
    Credentials cr=(Credentials)passintent;

//        Bundle bundle=getIntent().getExtras();
//        Credentials cr = bundle.getParcelable("credentialname");
////Credentials cr=bundle.getParcelable("credential_name");
        System.out.println("-------this is from home---------"+cr.getEmail());

        if(savedInstanceState == null) {
            if (findViewById(R.id.frame_main_container) != null) {
                SuccessFragment successfragment;
                successfragment= new SuccessFragment();
                mUserName = cr.getEmail();
                Bundle args = new Bundle();

        args.putSerializable(getString(R.string.useremail), cr.getEmail());
        successfragment.setArguments(args);
        System.out.println("this is from home on create"+getString(R.string.useremail));
                getSupportFragmentManager().beginTransaction() .add(R.id.frame_main_container,  successfragment)
                        .addToBackStack(null) .commit();
            }
        }

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

         if (id == R.id.nav_successFragment) {
            System.out.println("-----mm,mmButton pressed");
            SuccessFragment successFragment = new SuccessFragment();

            Bundle args = new Bundle();

            args.putSerializable(getString(R.string.useremail), mUserName);
            successFragment.setArguments(args);
            loadFragment( successFragment);

        } else if (id == R.id.nav_recyclerView) {
            loadFragment( new RecyclerVieww());
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void loadFragment(Fragment frag) {
        System.out.println("Button pressed");
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction() .replace(R.id.frame_main_container, frag) .addToBackStack(null);
// Commit the transaction
        transaction.commit();
    }


    @Override
    public void onListFragmentInteraction(BlogPost item) {
        BlogPostFragment blogPost = new BlogPostFragment();
        Bundle args = new Bundle();
        args.putSerializable(getString(R.string.blog_title),item.getTitle());
        args.putSerializable(getString(R.string.publish_date),item.getPubDate());
        args.putSerializable(getString(R.string.sampling),item.getTeaser());
        args.putSerializable("url",item.getUrl());
        blogPost.setArguments(args);

        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction() .replace(R.id.frame_main_container, blogPost) .addToBackStack(null);
// Commit the transaction
        transaction.commit();

    }
}
