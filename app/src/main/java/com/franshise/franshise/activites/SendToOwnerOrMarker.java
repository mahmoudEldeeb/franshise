package com.franshise.franshise.activites;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.franshise.franshise.R;
import com.franshise.franshise.models.dataModels.StatusModel;
import com.franshise.franshise.utils.CustomProgressDialog;
import com.franshise.franshise.viewmodels.SendMessageViewModel;
import com.squareup.picasso.Picasso;

public class SendToOwnerOrMarker extends AppCompatActivity {
int ownerOrMarker=0;
TextView marker_name,tomarker;

    EditText message,email,from,country;
    Button send;
    Observer<StatusModel> messageObserver;
    SendMessageViewModel sendMessageViewModel;
    ImageView francise_image;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_to_owner_or_marker);
        marker_name=findViewById(R.id.marker_name);
        tomarker=findViewById(R.id.tomarker);
        francise_image=findViewById(R.id.francise_image);

        ownerOrMarker=getIntent().getIntExtra("ownerOrMarker",0);
        id=getIntent().getIntExtra("id",0);
        if(ownerOrMarker==0){
            String image=getIntent().getStringExtra("image");
            Picasso.get()
                    .load(getBaseContext().getResources().getString(R.string.base_image_url)+image)
                    .into(francise_image);
            marker_name.setText(getIntent().getStringExtra("title"));
            marker_name.setVisibility(View.VISIBLE);
            tomarker.setVisibility(View.VISIBLE);
        }
        from=findViewById(R.id.from);
        email=findViewById(R.id.email);
        message=findViewById(R.id.message);
        country=findViewById(R.id.password);
        send=findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkValuse())sendMessage();
                else Toast.makeText(getBaseContext(),R.string.fill_data,Toast.LENGTH_SHORT).show();
            }
        });


        sendMessageViewModel= ViewModelProviders.of(this).get(SendMessageViewModel.class);

        messageObserver=new Observer<StatusModel>() {
            @Override
            public void onChanged(@Nullable StatusModel statusModel) {
                CustomProgressDialog.clodseProgress();
                Toast.makeText(getBaseContext(),statusModel.getMessage(),Toast.LENGTH_SHORT).show();
            }
        };

    }
    private void sendMessage() {
        Log.v("eeee",id+"");
        if(ownerOrMarker==0){
            sendMessageViewModel.send_for_owner(from.getText().toString(), email.getText().toString(), country.getText().toString(),
                    message.getText().toString(),id).observe(this, messageObserver);
            CustomProgressDialog.showProgress(this);
        }
       else {
            sendMessageViewModel.send_for_consultant(from.getText().toString(), email.getText().toString(), country.getText().toString(),
                    message.getText().toString()).observe(this, messageObserver);

            CustomProgressDialog.showProgress(this);
       }

    }

    private boolean checkValuse() {
        if(from.getText().toString().isEmpty()||email.getText().toString().isEmpty()
                ||country.getText().toString().isEmpty()||message.getText().toString().isEmpty())
            return false;
        else return true;
    }

}
