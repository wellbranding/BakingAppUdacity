package udacity.viktor.bakingappfinal.UI.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.preference.PreferenceManager;
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

    public interface OnItemCLickListener {
        void OnItemClick(int position);
    }
    private int currentIndex=-1;

    private OnItemCLickListener mOnItemCLickListener;

    public void setOnItemCLickListener(OnItemCLickListener onItemCLickListener) {
        this.mOnItemCLickListener = onItemCLickListener;
    }

    private LayoutInflater inflater;
    private Context mContext;
    private Cursor mCursor;
    private List<Step> stepList;
    private int currentPosition;
    private int selectedPos = RecyclerView.NO_POSITION;

    public int getSelectedPos() {
        return selectedPos;
    }

    public void setSelectedPos(int selectedPos) {
        this.selectedPos = selectedPos;
    }

    public StepsActivityAdapter(Context context, int currentPosition) {
        inflater = LayoutInflater.from(context);
        mContext = context;
        this.currentPosition = currentPosition;
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
        holder.StepName.setText(stepList.get(position).getShortDescription());
        if(selectedPos==position)
        holder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.colorAccent));
        else
            holder.itemView.setBackgroundColor(Color.TRANSPARENT);

    }



    @Override
    public int getItemCount() {

        if (stepList == null)
            return 0;
        return stepList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        TextView StepName;

           MyViewHolder(View itemView) {
            super(itemView);
            StepName = itemView.findViewById(R.id.step_name);
            itemView.setOnClickListener(v -> {
                currentIndex = getAdapterPosition();
                mOnItemCLickListener.OnItemClick(getAdapterPosition());
                if(currentIndex==getAdapterPosition())
                {
                    //itemView.setBackgroundColor(mContext.getResources().getColor(R.color.colorAccent));
                }
            });


        }
    }
}
