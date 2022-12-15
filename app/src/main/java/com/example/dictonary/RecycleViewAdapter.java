package com.example.dictonary;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public
class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ViewHolder> {
    Context context;
    ArrayList<Expenses> arrayExpenses;
    DatabaseHelper databaseHelper;
    public
    RecycleViewAdapter(Context context, ArrayList<Expenses> arrayExpenses,DatabaseHelper databaseHelper){
        this.context = context;
        this.arrayExpenses = arrayExpenses;
        this.databaseHelper=databaseHelper;

    }


    @NonNull
    @Override
    public
    ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.basic_layout,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public
    void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
            holder.word.setText(arrayExpenses.get(position).getTitle());
            holder.meaing.setText(arrayExpenses.get(position).getAmount());
            holder.linearLayout.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public
                boolean onLongClick(View v) {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                    alertDialog.setTitle("Delete")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public
                                void onClick(DialogInterface dialog, int which) {
                                    // deleting algo
                                    databaseHelper.expenseDAO().deleteTx(new Expenses(arrayExpenses.get(position).getId(),arrayExpenses.get(position).getTitle(),arrayExpenses.get(position).getAmount()));
                                    Toast.makeText(context, "deleted", Toast.LENGTH_SHORT).show();
                                    ((MainActivity)context).showDicNary();


                                }
                            })
                            .setNegativeButton("No",null);
                    alertDialog.show();

                    return true;
                }
            });

    }

    @Override
    public
    int getItemCount() {
        // give the count of the number of the recycle view in return value
        return arrayExpenses.size();
    }

    public
    class ViewHolder extends RecyclerView.ViewHolder {
        // All the variable declear hear
//        EditText wordId ,meainingId;
//        Button enterBtn;
        TextView word ,meaing;
        LinearLayout linearLayout;

        public
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            // itemView.findViewById
            word = itemView.findViewById(R.id.word);
            meaing = itemView.findViewById(R.id.meaning);
            linearLayout= itemView.findViewById(R.id.basicLayout);


        }
    }
}
