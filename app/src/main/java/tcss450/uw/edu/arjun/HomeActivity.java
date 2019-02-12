package tcss450.uw.edu.arjun;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import me.pushy.sdk.Pushy;
import tcss450.uw.edu.arjun.blog.BlogPost;
//import tcss450.uw.edu.phishapp.dummy.DummyContent;
import tcss450.uw.edu.arjun.setList.ListPost;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        WaitFragment.OnFragmentInteractionListener, BlogFragment.OnListFragmentInteractionListener,
BlogPostFragment.OnFragmentInteractionListener, setListFragment.OnListFragmentInteractionListener,
        SetlistPost.OnFragmentInteractionListener{
    private String mUserName ="";
    private String mJwToken;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        mJwToken = getIntent().getStringExtra(getString(R.string.keys_intent_jwToken));
//        Intent i= this.getIntent();
//        Bundle bundle = i.getExtras();
//        Object passintent = getIntent().getSerializableExtra(MainActivity.PASSINTENT);
//    Credentials cr=(Credentials)passintent;

    //////

        String cr = getIntent().getStringExtra(getString(R.string.keys_intent_credentials));
        mJwToken = getIntent().getStringExtra(getString(R.string.keys_intent_jwt));
        ///////
//        Bundle bundle=getIntent().getExtras();
//        Credentials cr = bundle.getParcelable("credentialname");
////Credentials cr=bundle.getParcelable("credential_name");
//        System.out.println("-------this is from home---------"+cr.getEmail());

        if(savedInstanceState == null) {
            if (findViewById(R.id.frame_main_container) != null) {
                SuccessFragment successfragment;
                successfragment= new SuccessFragment();
//                mUserName = cr.getEmail();
                Bundle args = new Bundle();

        args.putSerializable(getString(R.string.useremail), cr);
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
//        if (id == R.id.action_settings) {
//            return true;
//        }
        if (id == R.id.action_logout) {
            logout();
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
//            loadFragment( new BlogFragment());
             Uri uri = new Uri.Builder()
                     .scheme("https")
                     .appendPath(getString(R.string.ep_base_url))
                     .appendPath(getString(R.string.ep_phish))
                     .appendPath(getString(R.string.ep_blog))
                     .appendPath(getString(R.string.ep_get))
                     .build();
             new GetAsyncTask.Builder(uri.toString())
                     .onPreExecute(this::onWaitFragmentInteractionShow)
                     .onPostExecute(this::handleBlogGetOnPostExecute)
                     .addHeaderField("authorization", mJwToken) //add the JWT as a header
                     .build().execute();
        } else if(id == R.id.nav_set_list){

             Uri uri = new Uri.Builder()
                     .scheme("https")
                     .appendPath(getString(R.string.ep_base_url))
                     .appendPath(getString(R.string.ep_phish))
                     .appendPath(getString(R.string.ep_setlists))
                     .appendPath(getString(R.string.ep_recent))
                     .build();
             new GetAsyncTask.Builder(uri.toString())
                     .onPreExecute(this::onWaitFragmentInteractionShow)
                     .onPostExecute(this::handleSetListGetOnPostExecute)
                     .addHeaderField("authorization", mJwToken) //add the JWT as a header
                     .build().execute();
         }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

//for setlist
private void handleSetListGetOnPostExecute(final String result) {
    System.out.println("----0");
    //parse JSON
    try {
        JSONObject root = new JSONObject(result);
        if (root.has(getString(R.string.keys_json_blogs_response))) {
            JSONObject response = root.getJSONObject(
                    getString(R.string.keys_json_blogs_response));
            System.out.println("----1");
            if (response.has(getString(R.string.keys_json_blogs_data))) {
                JSONArray data = response.getJSONArray(
                        getString(R.string.keys_json_blogs_data));
                List<ListPost> blogs = new ArrayList<>();
                System.out.println("----2");
                for(int i = 0; i < data.length(); i++) {
                    JSONObject jsonBlog = data.getJSONObject(i);
                    blogs.add(new ListPost.Builder(
                            jsonBlog.getString("long_date"),
                            jsonBlog.getString("location"),
                            (Html.fromHtml(jsonBlog.getString("venue"))).toString())
                            .addsetListData(jsonBlog.getString("setlistdata"))
                            .addlistnote(jsonBlog.getString("setlistnotes"))
                            .addUrl(jsonBlog.getString("url"))
                            .build());
                    System.out.println(jsonBlog.getString("venue"));
                }
                ListPost[] setListsAsArray = new ListPost[blogs.size()];
                setListsAsArray = blogs.toArray(setListsAsArray);
                Bundle args = new Bundle();
                args.putSerializable(setListFragment.ARG_SET_LIST, setListsAsArray);
                Fragment frag = new setListFragment();
                frag.setArguments(args);
                onWaitFragmentInteractionHide();
                System.out.println("Loading -------");
                loadFragment(frag); } else {
                Log.e("ERROR!", "No data array");
//notify user
                onWaitFragmentInteractionHide();
            }
        } else {
            Log.e("ERROR!", "No response"); //notify user onWaitFragmentInteractionHide();
        }
    } catch (JSONException e) {
        e.printStackTrace(); Log.e("ERROR!", e.getMessage()); //notify user onWaitFragmentInteractionHide();
    }
}


    //for blog
    private void handleBlogGetOnPostExecute(final String result) {
        //parse JSON
        try {
            JSONObject root = new JSONObject(result);
            if (root.has(getString(R.string.keys_json_blogs_response))) {
                JSONObject response = root.getJSONObject(
                        getString(R.string.keys_json_blogs_response));
                if (response.has(getString(R.string.keys_json_blogs_data))) {
                    JSONArray data = response.getJSONArray(
                            getString(R.string.keys_json_blogs_data));
                    List<BlogPost> blogs = new ArrayList<>();
                    for(int i = 0; i < data.length(); i++) {
                        JSONObject jsonBlog = data.getJSONObject(i);
                        blogs.add(new BlogPost.Builder(
                                jsonBlog.getString(
                                        getString(R.string.keys_json_blogs_pubdate)),
                                jsonBlog.getString(
                                        getString(R.string.keys_json_blogs_title)))
                                .addTeaser(jsonBlog.getString(
                                        getString(R.string.keys_json_blogs_teaser)))
                                .addUrl(jsonBlog.getString(
                                        getString(R.string.keys_json_blogs_url)))
                                .build());
                    }
                    BlogPost[] blogsAsArray = new BlogPost[blogs.size()];
                    blogsAsArray = blogs.toArray(blogsAsArray);
                    Bundle args = new Bundle();
                    args.putSerializable(BlogFragment.ARG_BLOG_LIST, blogsAsArray);
                    Fragment frag = new BlogFragment();
                    frag.setArguments(args);
                    onWaitFragmentInteractionHide();
                    loadFragment(frag); } else {
                    Log.e("ERROR!", "No data array");
//notify user
                    onWaitFragmentInteractionHide();
                }
            } else {
                Log.e("ERROR!", "No response"); //notify user onWaitFragmentInteractionHide();
            }
        } catch (JSONException e) {
            e.printStackTrace(); Log.e("ERROR!", e.getMessage()); //notify user onWaitFragmentInteractionHide();
        }
    }

    private void loadFragment(Fragment frag) {
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_main_container, frag)
                .addToBackStack(null);
        // Commit the transaction
        transaction.commit();
    }

//    private void loadFragment(Fragment frag) {
//        System.out.println("Button pressed");
//        FragmentTransaction transaction = getSupportFragmentManager()
//                .beginTransaction() .replace(R.id.frame_main_container, frag) .addToBackStack(null);
//// Commit the transaction
//        transaction.commit();
//    }
//
//
//    @Override
//    public void onListFragmentInteraction(BlogPost item) {
//        BlogPostFragment blogPost = new BlogPostFragment();
//        Bundle args = new Bundle();
//        args.putSerializable(getString(R.string.blog_title),item.getTitle());
//        args.putSerializable(getString(R.string.publish_date),item.getPubDate());
//        args.putSerializable(getString(R.string.sampling),item.getTeaser());
//        args.putSerializable("url",item.getUrl());
//        blogPost.setArguments(args);
//
//        FragmentTransaction transaction = getSupportFragmentManager()
//                .beginTransaction() .replace(R.id.frame_main_container, blogPost) .addToBackStack(null);
//// Commit the transaction
//        transaction.commit();
//
//    }


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

    @Override
    public void onListFragmentInteraction(BlogPost item) {
        BlogPostFragment blogPost = new BlogPostFragment();
        Bundle args = new Bundle();
        args.putSerializable(getString(R.string.blog_title),item.getTitle());
        args.putSerializable(getString(R.string.publish_date),item.getPubDate());
        args.putSerializable(getString(R.string.sampling),item.getTeaser());
        args.putSerializable("url",item.getUrl());
        blogPost.setArguments(args);
//
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction() .replace(R.id.frame_main_container, blogPost) .addToBackStack(null);
// Commit the transaction
        transaction.commit();
        System.out.println("----------"+item.getTitle());
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
System.out.println("posting button clicked");
    }

    @Override
    public void onListFragmentInteraction(ListPost list) {
        System.out.println("List is clicked----------");

        Bundle args = new Bundle();
        args.putSerializable("long_date", list.getmLongDate());
        args.putSerializable("location",list.getmLocation());
        args.putSerializable("venue",list.getmVenue());
        args.putSerializable("setlistdata",list.getmSetListData());
        args.putSerializable("setlistnotes",list.getmlistNotes());
        args.putSerializable("url",list.getmUrl());
        SetlistPost setlistPost = new SetlistPost();
        setlistPost.setArguments(args);
        loadFragment(setlistPost);


    }

    private void logout() {
        new DeleteTokenAsyncTask().execute();
        SharedPreferences prefs =
                getSharedPreferences(
                        getString(R.string.keys_shared_prefs),
                        Context.MODE_PRIVATE);

//remove the saved credentials from StoredPrefs
        prefs.edit().remove(getString(R.string.keys_prefs_password)).apply();
        prefs.edit().remove(getString(R.string.keys_prefs_email)).apply();

        //close the app
        finishAndRemoveTask();
        //or close this activity and bring back the Login
//        Intent i = new Intent(this, MainActivity.class);
//        startActivity(i);
//End this Activity and remove it from the Activity back stack.
        //finish();
    }
    // Deleting the Pushy device token must be done asynchronously. Good thing
// we have something that allows us to do that.
    class DeleteTokenAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            onWaitFragmentInteractionShow();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            //since we are already doing stuff in the background, go ahead
            //and remove the credentials from shared prefs here.
            SharedPreferences prefs =
                    getSharedPreferences(
                            getString(R.string.keys_shared_prefs),
                            Context.MODE_PRIVATE);
            prefs.edit().remove(getString(R.string.keys_prefs_password)).apply();
            prefs.edit().remove(getString(R.string.keys_prefs_email)).apply();
            //unregister the device from the Pushy servers
            Pushy.unregister(HomeActivity.this);
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //close the app
            finishAndRemoveTask();
//or close this activity and bring back the Login
//            Intent i = new Intent(this, MainActivity.class);
//            startActivity(i);
//            //Ends this Activity and removes it from the Activity back stack.
// finish();
 }
        }
}
