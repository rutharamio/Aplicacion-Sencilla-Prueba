package com.example.aplicacionsencillaprueba;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

// Adapter class for handling the display of contacts in a RecyclerView
public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private final Context context;
    private ArrayList<Contacts> contactsArrayList;

    public Adapter(Context context, ArrayList<Contacts> contactsArrayList) {
        this.context = context;
        this.contactsArrayList = contactsArrayList;
    }

    // Creates and returns a ViewHolder object for each item in the RecyclerView.
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.contacts_rv_item, parent, false);
        return new ViewHolder(view);
    }

    // Binds data to the ViewHolder for a specific position.
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Contacts contact = contactsArrayList.get(position);
        holder.nameTV.setText(contact.getUserName());
        holder.numberTV.setText(contact.getContactNumber());

        // Click listener comentado hasta que Sofía haga su parte
        holder.itemView.setOnClickListener(v -> {
             Intent intent = new Intent(context, ContactDetailActivity.class);
             intent.putExtra("name", contact.getUserName());
             intent.putExtra("contact", contact.getContactNumber());
             context.startActivity(intent);
        });
    }

    // Returns the total number of items in the list.
    @Override
    public int getItemCount() {
        return contactsArrayList.size();
    }

    // ViewHolder class to hold and manage views for each RecyclerView item.
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView nameTV;
        private final TextView numberTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTV = itemView.findViewById(R.id.tvName);
            numberTV = itemView.findViewById(R.id.tvNumber);
        }
    }
}
