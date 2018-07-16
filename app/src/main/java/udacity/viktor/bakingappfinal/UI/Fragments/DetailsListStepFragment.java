package udacity.viktor.bakingappfinal.UI.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.support.AndroidSupportInjection;
import udacity.viktor.bakingappfinal.Data.Networking.Models.Ingredient;
import udacity.viktor.bakingappfinal.Data.Networking.Models.Step;
import udacity.viktor.bakingappfinal.R;
import udacity.viktor.bakingappfinal.UI.Adapters.IngridientsActivityAdapter;
import udacity.viktor.bakingappfinal.UI.Adapters.StepsActivityAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DetailsListStepFragment.OnFragmentInteractionListener} interface
 * to handle interaction events
 * create an instance of this fragment.
 */
public class DetailsListStepFragment extends Fragment {


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    // TODO: Rename and change types of parameters

    //Ingredients
    private List<Ingredient> ingredientList;
    ViewPager viewPager;
    LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;

    private int currentRVPosition =0;
    //Steps
    private List<Step> stepList;
    RecyclerView recyclerView;
    StepsActivityAdapter stepsActivityAdapter;

    private OnFragmentInteractionListener mListener;
    private DetailsListStepFragment detailsListStepFragment;
    private DetailsListListener detailsListListener;

    boolean mTwoPane;
    @Inject
    SharedPreferences sharedPreferences;

    public DetailsListStepFragment() {
        // Required empty public constructor
    }

//    public void setFragmentListener(DetailsListListener detailsListListener) {
//        this.detailsListListener = detailsListListener;
//    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment DetailsListStepFragment.
     */
    // TODO: Rename and change types and number of parameters

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            ingredientList = getArguments().getParcelableArrayList(getString(R.string.recipe_ingredients));
            stepList = getArguments().getParcelableArrayList(getString(R.string.recipe_steps));
            mTwoPane = getArguments().getBoolean(getString(R.string.is_tablet));
        }
        if(savedInstanceState!=null)
            currentRVPosition = savedInstanceState.getInt(getString(R.string.position_recyler_view), 0);



    }

    private void InitializeRecyclerView(View view) {
    //    currentRVPosition = sharedPreferences.getInt(getString(R.string.position_recyler_view), 0);
        recyclerView = view.findViewById(R.id.recycler_view_main_activity);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        stepsActivityAdapter = new StepsActivityAdapter(requireActivity(),currentRVPosition );
        stepsActivityAdapter.setStepList(stepList);
        recyclerView.setAdapter(stepsActivityAdapter);

        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        if(mTwoPane) {
            stepsActivityAdapter.setSelectedPos(currentRVPosition);
            stepsActivityAdapter.notifyItemChanged(currentRVPosition);
        }

        stepsActivityAdapter.setOnItemCLickListener(new StepsActivityAdapter.OnItemCLickListener() {
            @Override
            public void OnItemClick(int position) {
                if(mTwoPane) {
                    currentRVPosition = position;
                    stepsActivityAdapter.notifyItemChanged(currentRVPosition);
                    stepsActivityAdapter.notifyDataSetChanged();
                    stepsActivityAdapter.setSelectedPos(currentRVPosition);
                    sharedPreferences.edit().putInt(getString(R.string.position_recyler_view), currentRVPosition).apply();
                }
                detailsListListener.setCurrentStep(position, true);
            }
        });

    }
    @Override
    public void onSaveInstanceState(Bundle _outState)
    {

        super.onSaveInstanceState(_outState);
        sharedPreferences.edit().putInt(getString(R.string.position_recyler_view), currentRVPosition).commit();
        _outState.putInt(getString(R.string.position_recyler_view), currentRVPosition);

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void InitializeViewPager(View view) {
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);

        sliderDotspanel = (LinearLayout) view.findViewById(R.id.SliderDots);

        IngridientsActivityAdapter viewPagerAdapter = new IngridientsActivityAdapter(getContext());
        viewPagerAdapter.setIngredientList(ingredientList);

        viewPager.setAdapter(viewPagerAdapter);
        viewPagerAdapter.notifyDataSetChanged();

        dotscount = viewPagerAdapter.getCount();
        dots = new ImageView[dotscount];
        sliderDotspanel.removeAllViews();

        for (int i = 0; i < dotscount; i++) {

            dots[i] = new ImageView(getContext());
            dots[i].setImageDrawable(ContextCompat.getDrawable(requireActivity().getApplicationContext(), R.drawable.nonactive_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8, 0, 8, 0);

            sliderDotspanel.addView(dots[i], params);

        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(requireActivity().getApplicationContext(), R.drawable.active_dot));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                for (int i = 0; i < dotscount; i++) {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(requireActivity().getApplicationContext(), R.drawable.nonactive_dot));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(requireActivity().getApplicationContext(), R.drawable.active_dot));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_details_list_step, container, false);
        InitializeRecyclerView(view);
        InitializeViewPager(view);
        return view;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            requireActivity().onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);

        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }
        if (context instanceof DetailsListListener) {
            this.detailsListListener = (DetailsListListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        sharedPreferences.edit().putInt(getString(R.string.position_recyler_view), 0).apply();
        mListener = null;
        detailsListListener = null;
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
