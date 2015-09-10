package com.example.facundo.recorridaszotp._5_Presentation;

import android.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.facundo.recorridaszotp.R;
import com.example.facundo.recorridaszotp._1_Infraestructure.AdaptadorListaMenu;
import com.example.facundo.recorridaszotp._3_Domain.ItemLista;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private DrawerLayout navDrawerLayout;
    private ListView navList;
    private Toolbar appbar;
    private ArrayList<ItemLista> navItms;
    AdaptadorListaMenu navAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appbar = (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(appbar);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_drawer);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Drawer layout
        navDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navList = (ListView) findViewById(R.id.nav_list);
        //Declaramos el header el caul sera el layout de header.xml
        View header = getLayoutInflater().inflate(R.layout.header, null);
        //Establecemos header
        navList.addHeaderView(header);

        navItms = new ArrayList<ItemLista>();
        navItms.add(new ItemLista("Fragment 1", R.drawable.abc_ic_menu_cut_mtrl_alpha));
        navItms.add(new ItemLista("Personas", R.drawable.ic_action_user));
        navItms.add(new ItemLista("Mapa", R.drawable.ic_action_place));
        navItms.add(new ItemLista("Perfil", R.drawable.abc_ic_menu_share_mtrl_alpha));
        navAdapter = new AdaptadorListaMenu(this, navItms);
        navList.setAdapter(navAdapter);
        setSupportActionBar(appbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_drawer2);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /*
        //Eventos del Drawer Layout
        drawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
            }
            @Override
            public void onDrawerOpened(View drawerView) {
            }
            @Override
            public void onDrawerClosed(View drawerView) {
            }
            @Override
            public void onDrawerStateChanged(int newState) {
            }
        });
        */

        navList = (ListView) findViewById(R.id.nav_list);
        navList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
                //  MostrarFragment(position);
                boolean fragmentTransaction = false;
                Fragment fragment = null;

                switch (position) {
                    case 1:
                        fragment = new Fragment1();
                        fragmentTransaction = true;
                        break;
                    case 2:
                        fragment = new ListaPersonas();
                        fragmentTransaction = true;
                        break;
                    case 3:
                        fragment = new MapsFragment();
                        fragmentTransaction = true;
                        break;
                    case 4:
                        fragment = new ProfileFragment();
                        fragmentTransaction = true;
                        break;
                }

                if (fragmentTransaction) {
                    getFragmentManager().beginTransaction()
                            .replace(R.id.content_frame, fragment)
                            .commit();

                    //menuItem.setChecked(true);
                    //getSupportActionBar().setTitle(menuItem.getTitle());
                }

                navDrawerLayout.closeDrawers();

            }
        });
        Fragment fragmentHome = new HomeFragment();
        getFragmentManager().beginTransaction()
                .replace(R.id.content_frame, fragmentHome)
                .commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {
            case android.R.id.home:
                navDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
