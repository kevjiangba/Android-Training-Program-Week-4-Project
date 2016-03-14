package com.example.kevinjiang.week4project;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetDataCallback;
import com.parse.Parse;
import com.parse.ParseFile;
import com.parse.ParseObject;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.CustomViewHolder> {

    private Context context;
    private List<ParseObject> recyclerItems;

    public RecyclerAdapter (Context context, List<ParseObject> recycleritems) {
        this.context = context;
        recyclerItems = recycleritems;
    }

    /* In simplified terms, a ViewHolder is an object that holds the pointers to the views in each
    each row. What does that mean? Every row has a TextView, ImageView, and CheckBox. Each row has
    a ViewHolder, and that ViewHolder holder these 3 views in it (hence "view holder").
    This function returns a single ViewHolder; it is called once for every row.
    */
    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        This "inflates" the views, using the layout R.layout.row_view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_view, parent, false);
        return new CustomViewHolder(view);
    }

    /* This function takes the previously made ViewHolder and uses it to actually display the
    data on the screen. Remember how the holder contains (pointers to) the 3 views? By doing, for
    example, "holder.imageView" we are accessing the imageView for that row and setting the
    ImageResource to be the corresponding image for that subject.
     */
    @Override
    public void onBindViewHolder(final CustomViewHolder holder, int position) {
        ParseObject parseObject = recyclerItems.get(position);

        ParseFile parseFile = (ParseFile) parseObject.get("image");
        parseFile.getDataInBackground(new GetDataCallback() {
            @Override
            public void done(byte[] data, com.parse.ParseException e) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                holder.imageView.setImageBitmap(bitmap);
            }
        });
        holder.name.setText(parseObject.getString("name"));
        holder.description.setText(parseObject.getString("description"));

        //holder.memeName.setText(trumpCard.meme);
        //holder.imageView.setImageResource(trumpCard.imageId);
        //holder.descriptionMeme.setText(trumpCard.description);
    }

    @Override
    public int getItemCount() {
        return recyclerItems.size();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView imageView;
        TextView description;

        public CustomViewHolder (View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            imageView = (ImageView) view.findViewById(R.id.image);
            description = (TextView) view.findViewById(R.id.description);

            /*Think about what we said in the comment above onCreateViewHolder to determine the
            purpose of the ViewHolder. Does it make sense why we are doing this in the constructor?
            */

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*Get adapter position is getting the number of the row that was clicked,
                    starting at 0
                    */
                    itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            MainActivity.createUpdateDialog(name.getText().toString(), description.getText().toString());
                        }
                    });
                }
            });
        }


    }
    public void setList(List<ParseObject> list) {
        recyclerItems = list;
    }
}
