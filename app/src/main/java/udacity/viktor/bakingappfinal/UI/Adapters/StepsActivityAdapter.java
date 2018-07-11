package udacity.viktor.bakingappfinal.UI.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import udacity.viktor.bakingappfinal.Data.Networking.Models.Step;
import udacity.viktor.bakingappfinal.R;

/**
 * Created by vvost on 3/18/2018.
 */

public class StepsActivityAdapter extends RecyclerView.Adapter<StepsActivityAdapter.MyViewHolder> {

    public interface OnItemCLickListener{
        void OnItemClick(int position);
    }

    private OnItemCLickListener mOnItemCLickListener;
    public void setOnItemCLickListener(OnItemCLickListener onItemCLickListener) {
        this.mOnItemCLickListener = onItemCLickListener;
    }

    private LayoutInflater inflater;
    private Context context;
    private Cursor mCursor;
    private List<Step> stepList;


    public StepsActivityAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        stepList = new ArrayList<>();
         }

    public List<Step> getStepList() {
        return stepList;
    }

    public void setStepList(List<Step> stepList) {
        this.stepList = stepList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.step_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
      //  if(recipeList.get(position).getImage().equals(""))


        holder.StepName.setText(stepList.get(position).getShortDescription());

    }


    @Override
    public int getItemCount() {

        if (stepList == null)
            return 0;
        return stepList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        TextView StepName;

        MyViewHolder(View itemView) {
            super(itemView);
            StepName = itemView.findViewById(R.id.step_name);
            itemView.setOnClickListener(v -> {
                mOnItemCLickListener.OnItemClick(getAdapterPosition());


//
//                    Intent intent = new Intent(context, MovieActivity.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    intent.putExtra("id", movieList.get(getAdapterPosition()).getId());
//                    context.startActivity(intent);

            });


        }
    }
}
