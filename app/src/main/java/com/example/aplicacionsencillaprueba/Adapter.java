package com.example.aplicacionsencillaprueba;

import android.content.Context;
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
        // Usa un layout simple de Android hasta que tengas tu XML personalizado
        View view = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new ViewHolder(view);
    }

    // Binds data to the ViewHolder for a specific position.
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Contacts contact = contactsArrayList.get(position);
        holder.contactTV.setText(contact.getUserName());

        // Click listener comentado hasta que Sofía haga su parte
        holder.itemView.setOnClickListener(v -> {
            // Intent intent = new Intent(context, ContactDetailActivity.class);
            // intent.putExtra("name", contact.getUserName());
            // intent.putExtra("contact", contact.getContactNumber());
            // context.startActivity(intent);
        });
    }

    // Returns the total number of items in the list.
    @Override
    public int getItemCount() {
        return contactsArrayList.size();
    }

    // ViewHolder class to hold and manage views for each RecyclerView item.
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView contactTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            contactTV = itemView.findViewById(android.R.id.text1);
        }
    }
}
