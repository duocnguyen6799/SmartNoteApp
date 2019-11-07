package com.npdstudio.smartnote;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.MyViewHolder> implements Filterable {
    ArrayList<Note> arrNotes;
    ArrayList<Note> arrNotesFull;
    Context mContext;
    MyDBHelper helper;
    private boolean multiSelect = false;
    private ArrayList<Note> selectedItems = new ArrayList<Note>();

    public NoteAdapter(ArrayList<Note> arrNotes, Context mContext) {
        this.arrNotes = arrNotes;
        this.mContext = mContext;
        this.arrNotesFull = new ArrayList<>(arrNotes);
        helper = new MyDBHelper(mContext);
        //this.myActivity = activity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_note,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int i) {
        holder.txtTitle.setText(Html.fromHtml(arrNotes.get(i).getmTitle()));
        holder.txtTag.setText("#"+arrNotes.get(i).getmTag());
        holder.txtTime.setText(arrNotes.get(i).getmTime());


        holder.lnItemNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,EditorNoteActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(mContext.getResources().getString(R.string.key_note_is_new),false);
                intent.putExtra(mContext.getResources().getString(R.string.key_note_send),arrNotes.get(i));
                mContext.startActivity(intent);
            }
        });
        holder.imbDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder  = new AlertDialog.Builder(v.getRootView().getContext());
                builder.setTitle("Thông báo");
                builder.setMessage("Bạn có muốn xóa ["+arrNotes.get(i).getmTitle()+"]!");
                builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(mContext,"Đã xóa ["+arrNotes.get(i).getmTitle()+"]!",Toast.LENGTH_SHORT).show();
                        helper.deleteNoteByID(arrNotes.get(i).getmID());
                        arrNotes.remove(i);
                        notifyItemRemoved(i);
                    }
                });
                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrNotes.size();
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            //notifyDataSetChanged();
            ArrayList<Note> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(arrNotesFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Note item : arrNotesFull) {
                    if (item.getmTitle().toLowerCase().contains(filterPattern)||item.getmTag().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            arrNotes.clear();
            arrNotes.addAll((ArrayList) results.values);
            notifyDataSetChanged();
        }
    };
    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView txtTitle,txtTag,txtTime;
        ImageButton imbDelete;
        LinearLayout lnItemNote;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtTag = itemView.findViewById(R.id.txtTag);
            txtTime = itemView.findViewById(R.id.txtTime);
            lnItemNote = itemView.findViewById(R.id.lnItemNote);
            imbDelete = itemView.findViewById(R.id.imbDelete);
        }

    }
}
