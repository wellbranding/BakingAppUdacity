package udacity.viktor.bakingappfinal.UI.Adapters;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import udacity.viktor.bakingappfinal.Data.Networking.Models.Recipe;
import udacity.viktor.bakingappfinal.R;
import udacity.viktor.bakingappfinal.Repository.Resource;

/**
 * Created by vvost on 3/18/2018.
 */

public class MainActivityAdapter extends RecyclerView.Adapter<MainActivityAdapter.MyViewHolder> {

    public interface OnItemCLickListener{
        void OnItemClick(int position);
    }

    private OnItemCLickListener mOnItemCLickListener;
    public void setOnItemCLickListener(OnItemCLickListener onItemCLickListener) {
        this.mOnItemCLickListener = onItemCLickListener;
    }

    private LayoutInflater inflater;
    private Context context;
    private List<Recipe> recipeList;
    private Cursor mCursor;

    public List<Recipe> getRecipeList() {
        return recipeList;
    }

    public void setRecipeList(List<Recipe> recipeList) {
        this.recipeList = recipeList;
    }

    public MainActivityAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        recipeList = new ArrayList<>();
         }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.recipe_card, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
      //  if(recipeList.get(position).getImage().equals(""))

        try{
            Picasso.with(context)
                    .load(recipeList.get(position).getImage())
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(holder.imageView);
        }catch (Exception e){
            e.printStackTrace();
            Picasso.with(context)
                    .load(R.drawable.sad_03)
                    .into(holder.imageView);
        }

        holder.RecipeName.setText(recipeList.get(position).getName());

    }


    @Override
    public int getItemCount() {

        if (recipeList == null)
            return 0;
        return recipeList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        TextView RecipeName;

        MyViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            RecipeName = itemView.findViewById(R.id.recipe_name);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemCLickListener.OnItemClick(getAdapterPosition());


//
//                    Intent intent = new Intent(context, MovieActivity.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    intent.putExtra("id", movieList.get(getAdapterPosition()).getId());
//                    context.startActivity(intent);

                }
            });


        }
    }
}
