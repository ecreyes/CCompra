package com.example.ecreyes.ccompra;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class NavigationDrawer extends AppCompatActivity
/*<<<<<<< design
        implements NavigationView.OnNavigationItemSelectedListener,
        AgregarTiendaFragment.OnFragmentInteractionListener, CategoriaFragment.OnFragmentInteractionListener,ListaTiendasFragment.OnFragmentInteractionListener, VistaTiendaFragment.OnFragmentInteractionListener {
*/
        implements NavigationView.OnNavigationItemSelectedListener, ListaCategoriaFragment.OnFragmentInteractionListener,
        AgregarTiendaFragment.OnFragmentInteractionListener, CategoriaFragment.OnFragmentInteractionListener,ListaTiendasFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Alertar de robo", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

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
        getMenuInflater().inflate(R.menu.navigation_drawer, menu);
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

        int id = item.getItemId();
        Fragment fragment = null;
        Boolean FragmentSeleccionado = false;
        if (id == R.id.categoria) {
            fragment = new CategoriaFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.Contenedor, fragment);
            FragmentSeleccionado = true;
        } else if (id == R.id.tienda_agregar) {
            fragment = new AgregarTiendaFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.Contenedor, fragment);
            FragmentSeleccionado = true;
        } else if (id == R.id.tienda_lista) {
            fragment = new ListaTiendasFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.Contenedor, fragment);
            FragmentSeleccionado = true;
/*<<<<<<< design
        } else if (id == R.id.vista_tienda) {
            fragment = new VistaTiendaFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.Contenedor, fragment);
======= */
        } else if (id == R.id.lista_categoria){
            fragment = new ListaCategoriaFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.Contenedor,fragment);

            FragmentSeleccionado = true;
        }
        if (FragmentSeleccionado){
            getSupportFragmentManager().beginTransaction().replace(R.id.Contenedor,fragment).commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
