package udacity.viktor.bakingappfinal.UI.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import udacity.viktor.bakingappfinal.Data.Networking.Models.Ingredient;
import udacity.viktor.bakingappfinal.Data.Networking.Models.Recipe;
import udacity.viktor.bakingappfinal.R;

/**
 * Created by vvost on 3/18/2018.
 */

public class IngridientsActivityAdapter extends PagerAdapter {

    @Override
    public int getCount() {
        return ingredientList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.single_ingrdient, null);
        TextView ingridient = view.findViewById(R.id.ingridient);
        TextView quantity = view.findViewById(R.id.quantity);
        TextView measure = view.findViewById(R.id.measure);
        ingridient.setText(ingredientList.get(position).getIngredient());
        quantity.setText(String.valueOf(ingredientList.get(position).getQuantity()));
        measure.setText(String.valueOf(ingredientList.get(position).getMeasure()));


        ViewPager vp = (ViewPager) container;
        vp.addView(view, 0);
        return view;

    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);

    }

    private LayoutInflater inflater;
    private Context context;
    private List<Ingredient> ingredientList;
    private Cursor mCursor;

    public List<Ingredient> getIngredientList() {
        return ingredientList;
    }

    public void setIngredientList(List<Ingredient> ingredientList) {
        this.ingredientList = ingredientList;
    }

    public IngridientsActivityAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        ingredientList = new ArrayList<>();
        }


}
