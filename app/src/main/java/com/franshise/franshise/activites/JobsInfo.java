package com.franshise.franshise.activites;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.franshise.franshise.R;
import com.franshise.franshise.fragments.AddJobs;
import com.franshise.franshise.models.ResultNetworkModels.DataResult;
import com.franshise.franshise.models.SharedPrefrenceModel;
import com.franshise.franshise.models.dataModels.JobModel;
import com.franshise.franshise.models.dataModels.StatusModel;
import com.franshise.franshise.utils.CustomProgressDialog;
import com.franshise.franshise.viewmodels.AddJobsViewModel;
import com.franshise.franshise.viewmodels.LoginViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class JobsInfo extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    LinearLayout spaceParent,spaceParent2;
    TextView re,re2;
    AddJobsViewModel addJobsViewModel;

    Spinner job_coountry,job_qualification;
    List<Integer> countryIds=new ArrayList<>();
    List<Integer>qualificationIds=new ArrayList<>();
    List<String>cityList=new ArrayList<>();
    List<Integer>cityIds=new ArrayList<>();
    List<Integer>selectedcityIds=new ArrayList<>();
    LayoutInflater layoutInflater;
    Observer<DataResult> cityObserver;
    EditText job_number,job_name,job_description,job_vacancy,sallary_from,sallary_to,sallary_currency;
    Button add_city,add_job;
    int type=-1;
    RadioGroup user_type;
    JobModel jobModel;
NavigationView navigationView;
    Button delete,update;
    RadioButton male,no_preference,female;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String languageToLoad="en";
        if(new SharedPrefrenceModel(this).getLanguage().equals("ar")){
            languageToLoad = "ar"; // your language
        }
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());

        setContentView(R.layout.activity_jobs_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        layoutInflater=this.getLayoutInflater();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        job_coountry=findViewById(R.id.job_coountry);
        male=findViewById(R.id.male);
        no_preference=findViewById(R.id.no_preference);
        female=findViewById(R.id.female);
        job_qualification=findViewById(R.id.job_qualification);
        spaceParent=findViewById(R.id.spaceParent);
        add_city=findViewById(R.id.add_city);
        user_type=findViewById(R.id.user_type);
        add_job=findViewById(R.id.add_job);
        job_number=findViewById(R.id.job_number);
        job_name=findViewById(R.id.job_name);
        job_description=findViewById(R.id.job_description);
        job_vacancy=findViewById(R.id.job_vacancy);
        sallary_from=findViewById(R.id.sallary_from);
        sallary_to=findViewById(R.id.sallary_to);
        sallary_currency=findViewById(R.id.sallary_currency);

    navigationView = (NavigationView) findViewById(R.id.nav_view);
    navigationView.setNavigationItemSelectedListener(this);
    jobModel = (JobModel) getIntent().getSerializableExtra("job");
    job_number.setText(jobModel.number);
    job_name.setText(jobModel.name);
    job_description.setText(jobModel.details);
    job_vacancy.setText(jobModel.number_require);
    sallary_from.setText(jobModel.start);
    sallary_to.setText(jobModel.end);
    sallary_currency.setText(jobModel.currency);

    if(Integer.parseInt(jobModel.gender)==0){
        Log.v("sssssssssssss","0m");
        male.setSelected(true);
        //((RadioButton)user_type.getChildAt(0)).setChecked(true);
            user_type.check(R.id.male);
        type=0;

    }
    else if(Integer.parseInt(jobModel.gender)==1)
    { user_type.check(R.id.female);
        type=1;}

    else    {user_type.check(R.id.no_preference);
        type=2;}
        update=findViewById(R.id.update);
        delete=findViewById(R.id.delete);
        addJobsViewModel= ViewModelProviders.of(this).get(AddJobsViewModel.class);
        add_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addElement2(0);
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                creatJob();
            }
        });


        List<String> list=new ArrayList<>();
        ArrayAdapter<String> adp1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, list);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        job_coountry.setAdapter(adp1);

        List<String> qualificatiolist=new ArrayList<>();
        ArrayAdapter<String> adp2= new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, qualificatiolist);
        adp2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        job_qualification.setAdapter(adp2);

        user_type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.male:type=0;break;
                    case R.id.female:type=1;break;
                    case R.id.no_preference:type=2;
                }
            }
        });
        cityObserver=new Observer<DataResult>() {
            @Override
            public void onChanged(@Nullable DataResult result) {
                cityList.clear();
                cityIds.clear();
                spaceParent.removeAllViews();
                for(int i=0;i<result.getData().size();i++) {
                    cityIds.add(result.getData().get(i).getId());
                    if (new SharedPrefrenceModel(JobsInfo.this).getLanguage().equals("en")) {
                        cityList.add(result.getData().get(i).getEn_name()) ;
                    } else {
                        cityList.add(result.getData().get(i).getEn_name()) ;

                    }
                }

                String cities []=jobModel.city_id.split(",");
                for(int i=0;i<cities.length;i++) {
                    addElement2(Integer.parseInt(cities[i]));
                }
            }
        };
        addJobsViewModel.getCountries(this).observe(this, new Observer<DataResult>() {
            @Override
            public void onChanged(@Nullable DataResult result) {
                // addJobsViewModel

                for(int i=0;i<result.getData().size();i++) {
                    countryIds.add(result.getData().get(i).getId());
                    if (new SharedPrefrenceModel(JobsInfo.this).getLanguage().equals("en")) {
                        list.add(result.getData().get(i).getEn_name()) ;
                    } else {
                        list.add(result.getData().get(i).getEn_name()) ;

                    }
                }
                adp1.notifyDataSetChanged();
                for(int i=0;i<countryIds.size();i++) {
                        if(Integer.parseInt(jobModel.country_id)==countryIds.get(i)){
                            job_coountry.setSelection(i);
                            addJobsViewModel.city_with_country(countryIds.get(i)).observe(JobsInfo.this,cityObserver);
                        }
                }



            }
        });
        addJobsViewModel.getQualification(this).observe(this, new Observer<DataResult>() {
            @Override
            public void onChanged(@Nullable DataResult result) {
                for(int i=0;i<result.getData().size();i++) {
                    qualificationIds.add(result.getData().get(i).getId());
                    if (new SharedPrefrenceModel(JobsInfo.this).getLanguage().equals("en")) {
                        qualificatiolist.add(result.getData().get(i).getEn_name()) ;
                    } else {
                        qualificatiolist.add(result.getData().get(i).getEn_name()) ;

                    }
                }
                adp2.notifyDataSetChanged();
                for(int i=0;i<qualificationIds.size();i++) {
                    if(Integer.parseInt(jobModel.qualification_id)==qualificationIds.get(i)){
                        job_qualification.setSelection(i);
                    }
                }

            }
        });
        job_coountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                addJobsViewModel.city_with_country(countryIds.get(position)).observe(JobsInfo.this,cityObserver);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addJobsViewModel.deleteJob(jobModel.id).observe(JobsInfo.this, new Observer<StatusModel>() {
                    @Override
                    public void onChanged(@Nullable StatusModel statusModel) {
                        if(statusModel.getStatus()==1){
                            Toast.makeText(JobsInfo.this,statusModel.getMessage(),Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        else Toast.makeText(JobsInfo.this,statusModel.getMessage(),Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });


    }

    private void creatJob() {
        if(job_name.getText().toString().isEmpty()||job_number.getText().toString().isEmpty()||job_description.getText().toString().isEmpty()
                ||job_vacancy.getText().toString().isEmpty()||sallary_from.getText().toString().isEmpty()||sallary_to.getText().toString().isEmpty()
                ||type==-1||countryIds.size()==0||cityIds.size()==0||qualificationIds.size()==0){

            Toast.makeText(this,getResources().getString(R.string.fill_data),Toast.LENGTH_LONG).show();
        }
        else {
            getCitiesIds();
        }
    }

    private void getCitiesIds() {
        selectedcityIds.clear();
        for(int i=0;i<spaceParent.getChildCount();i++) {
            View v2 = spaceParent.getChildAt(i);
            Spinner s = v2.findViewById(R.id.value);
            selectedcityIds.add(cityIds.get(s.getSelectedItemPosition()));
        }
        if(selectedcityIds.size()==spaceParent.getChildCount())
        {
            addJobsViewModel.updateJob(jobModel.id,job_name.getText().toString(),Integer.parseInt(job_number.getText().toString()),qualificationIds.get(job_qualification.getSelectedItemPosition())
                    ,job_description.getText().toString(),countryIds.get(job_coountry.getSelectedItemPosition()),selectedcityIds,Integer.parseInt(sallary_from.getText().toString()),
                    Integer.parseInt(sallary_to.getText().toString()),type,sallary_currency.getText().toString(),Integer.parseInt(job_vacancy.getText().toString()))
                    .observe(this, new Observer<StatusModel>() {
                        @Override
                        public void onChanged(@Nullable StatusModel statusModel) {
                            if(statusModel.getStatus()==1){
                                Toast.makeText(JobsInfo.this,statusModel.getMessage(),Toast.LENGTH_SHORT).show();
                                finish();
                            }
                            else Toast.makeText(JobsInfo.this,statusModel.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }


    private void addElement2(int id) {
        View v3=layoutInflater.inflate(R.layout.city_model, spaceParent, false);
        re2=v3.findViewById(R.id.re);
        ImageButton delete=v3.findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View v2=spaceParent.getChildAt( spaceParent.indexOfChild(v3));
                spaceParent.removeView(v2);
            }
        });

        /*


         */

            Spinner spinner = v3.findViewById(R.id.value);
            ArrayAdapter<String> adp1 = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, cityList);
            adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adp1);
            //re2.setText("Country ");
            spaceParent.addView(v3, spaceParent.getChildCount());
        for(int i=0;i<cityIds.size();i++) {
            if(id==cityIds.get(i)){
                spinner.setSelection(i);
            }
        }

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

    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Intent intent=new Intent(JobsInfo.this,NavigationShow.class);
        switch (id){

            // case  R.id.nav_events: selected=new EventsFragment();break;
            //  case R.id.nav_training:selected=new TrainingFragment();break;
            case R.id.nav_subscriptions:
                intent.putExtra("framid",0);
                startActivity(intent);
                break;
            case R.id.nav_Complaints:
                intent.putExtra("framid",1);
                startActivity(intent);
                break;
            case R.id.nav_myfranchises:
                startActivity(new Intent(JobsInfo.this,MarkerOwnerRegister.class));
                break;
            case R.id.nav_language:
                new SharedPrefrenceModel(this).changeLanguage();
                startActivity(new Intent(this,Main.class));
                this.finish();break;
            case R.id.nav_questions:
                intent.putExtra("framid",2);
                startActivity(intent);
                break;
            case R.id.nav_contact:
                intent.putExtra("framid",11);
                startActivity(intent);
                break;
            case R.id.nav_franshise:
                intent.putExtra("framid",3);
                startActivity(intent);
                break;
            case R.id.nav_terms:
                intent.putExtra("framid",4);
                startActivity(intent);break;
            case R.id.nav_training :
                Intent intent1=new Intent(JobsInfo.this,NavigationShow.class);
                intent1.putExtra("framid",9);
                startActivity(intent1);break;

            case R.id.nav_services :
                Intent intent2=new Intent(JobsInfo.this,NavigationShow.class);
                intent2.putExtra("framid",10);
                startActivity(intent2);break;
            case R.id.nav_events:
                Intent intent3=new Intent(JobsInfo.this,NavigationShow.class);
                intent3.putExtra("framid",8);
                startActivity(intent3);break;
            case R.id.nav_jobs:
                Intent intent4=new Intent(JobsInfo.this,NavigationShow.class);
                intent4.putExtra("framid",12);
                startActivity(intent4);break;
            case R.id.nav_share:share();break;
            case R.id.nav_logout:logOut();break;
            case R.id.nav_setting:startActivity(new Intent(JobsInfo.this,Setting.class));break;
            case R.id.nav_funding:startActivity(new Intent(JobsInfo.this,FundingCompanies.class));break;
            default:break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void logOut(){
        String token= new SharedPrefrenceModel(this).getTToken();
        String api_token=
                new SharedPrefrenceModel(this).getApiToken();
        LoginViewModel loginViewModel;
        Observer<Integer>outObserver;
        MenuItem nav_camara;
        Menu menu = navigationView.getMenu();

        nav_camara = menu.findItem(R.id.nav_logout);

        loginViewModel= ViewModelProviders.of(this).get(LoginViewModel.class);
        outObserver=new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                Log.v("eee",integer+"");
                SharedPrefrenceModel shared=new SharedPrefrenceModel(JobsInfo.this);
                if(integer==1){
                    if(shared.isLogined())
                        nav_camara.setTitle(R.string.logout);

                    shared.setLogined(false);
                    shared.setCompleteRegister(false);
                    startActivity(new Intent(JobsInfo.this,LoginActivity.class));
                    // finish();
                }
            }
        };
        CustomProgressDialog.showProgress(this);

        loginViewModel.LogOut(token,api_token).observe(this,outObserver);
        new SharedPrefrenceModel(this).setCompleteRegister(false);

    }
    public void share(){
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = "https://play.google.com/store/apps/details?id=com.franshise.franshise";
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "share franchise");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));

    }
}
