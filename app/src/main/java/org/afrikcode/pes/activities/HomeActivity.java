package org.afrikcode.pes.activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import org.afrikcode.pes.R;
import org.afrikcode.pes.base.BaseFragment;
import org.afrikcode.pes.fragments.BranchsFragment;
import org.afrikcode.pes.fragments.ManagersFragment;
import org.afrikcode.pes.impl.AuthImp;
import org.afrikcode.pes.listeners.FragmentListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, FragmentListener, FragmentManager.OnBackStackChangedListener {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.container)
    FrameLayout container;
    private FragmentManager fragmentManager;
    private SearchView searchView;
    private AuthImp authImp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        fragmentManager = getSupportFragmentManager();
        fragmentManager.addOnBackStackChangedListener(this);
        setuptoolbarAndDrawer();
        openDefaultFragment();

        authImp = new AuthImp();
    }

    private void openDefaultFragment() {
        navigationView.setCheckedItem(R.id.nav_branches);
        moveToFragment(new BranchsFragment());
    }

    private void setuptoolbarAndDrawer() {
        setSupportActionBar(mToolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            if (fragmentManager.getBackStackEntryCount() > 0) {
                fragmentManager.popBackStack();
            } else {
                super.onBackPressed();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.center, menu);


        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        if (searchManager != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        }
        searchView.setMaxWidth(Integer.MAX_VALUE);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_branches:
                moveToFragment(new BranchsFragment());
                break;
            case R.id.nav_managers:
                moveToFragment(new ManagersFragment());
                break;
            case R.id.nav_settings:
                Toast.makeText(getApplicationContext(), "Settings", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_about:
                Toast.makeText(getApplicationContext(), "About", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_logout:
                signout();
                break;
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void moveToFragment(BaseFragment fragment) {
        if (fragment != null) {
            if (getCurrentFragment() != null) {

                if (!getCurrentFragment().getTitle().equalsIgnoreCase(fragment.getTitle())) {
                    fragment.setFragmentListener(this);
                    FragmentTransaction ft = fragmentManager.beginTransaction();
                    ft.replace(container.getId(), fragment);
                    ft.commit();
                    ft.addToBackStack(getCurrentFragment().getTitle());
                    setTitle(fragment.getTitle());
                }

            } else {

                fragment.setFragmentListener(this);
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.replace(container.getId(), fragment);
                ft.commit();
                setTitle(fragment.getTitle());

            }
        }
    }

    public SearchView getSearchView() {
        return searchView;
    }

    @Override
    public void onBackStackChanged() {
        String title = getCurrentFragment().getTitle();
        setTitle(title);

        switch (title) {
            case "Available Branches":
                navigationView.setCheckedItem(R.id.nav_branches);
                break;
            case "Available Managers":
                navigationView.setCheckedItem(R.id.nav_managers);
                break;
        }
    }

    private BaseFragment getCurrentFragment() {
        return (BaseFragment) fragmentManager.findFragmentById(container.getId());
    }

    private void signout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("Sign Out")
                .setMessage("Are you sure you want to sign out ?")
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        authImp.logout();
                        dialog.dismiss();
                        Intent iii = new Intent(HomeActivity.this, SplashActivity.class);
                        iii.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(iii);
                    }
                }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
