package com.scet.saloonspot.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.scet.saloonspot.R;
import com.scet.saloonspot.models.MySaloon;
import com.scet.saloonspot.models.Review;

import java.util.ArrayList;
import java.util.Random;

public class ShowSalonsAdapter extends RecyclerView.Adapter<ShowSalonsAdapter.ViewHolder> {

    Context context;
    ArrayList<MySaloon> list = new ArrayList<>();

    int[] images = new int[]{R.drawable.s1,R.drawable.s2,R.drawable.s3,R.drawable.s4,R.drawable.s5};


    public ShowSalonsAdapter(Context context, ArrayList<MySaloon> list){
        this.context = context;
        this.list = list;
    }
    @NonNull
    @Override
    public ShowSalonsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.show_salon_item,null,false);
        return new ShowSalonsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowSalonsAdapter.ViewHolder holder, int position) {
        int min = 0;
        int max = 4;

        Random r = new Random();
        final int i1 = r.nextInt(max - min + 1) + min;
        holder.shopImg.setImageDrawable(context.getDrawable(images[i1]));
        holder.name.setText(list.get(position).getName().toString());
        holder.phone.setText(list.get(position).getMobileNo().toString());
        holder.mail.setText(list.get(position).getMail().toString());
        holder.aera.setText(list.get(position).getAera().toString());
        holder.address.setText(list.get(position).getAddress().toString());
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Confirm Delete..!!!");
                builder.setIcon(R.drawable.slogo);
                builder.setMessage("Are you sure,You want to delete this account");
                builder.setCancelable(false);
                builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        deleteuser(list.get(position).getMail().toString(),list.get(position).getPass().toString());
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context,"Not Deleted",Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

    }

    private void deleteuser(String email, String password) {

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        // Get auth credentials from the user for re-authentication. The example below shows
        // email and password credentials but there are multiple possible providers,
        // such as GoogleAuthProvider or FacebookAuthProvider.
        AuthCredential credential = EmailAuthProvider.getCredential(email, password);

        // Prompt the user to re-provide their sign-in credentials
        if (user != null) {
            user.reauthenticate(credential)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            user.delete()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(context,"Deleted this account",Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }
                    });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name,phone,mail,aera,address;
        Button btnDelete;
        ImageView shopImg;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.lbname);
            phone = itemView.findViewById(R.id.lbphone);
            mail = itemView.findViewById(R.id.lbmail);
            aera = itemView.findViewById(R.id.lbaera);
            address = itemView.findViewById(R.id.lbaddress);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            shopImg = itemView.findViewById(R.id.shopImg);
        }
    }
}
