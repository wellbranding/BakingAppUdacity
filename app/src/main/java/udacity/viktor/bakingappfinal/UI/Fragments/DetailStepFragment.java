package udacity.viktor.bakingappfinal.UI.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.support.AndroidSupportInjection;
import udacity.viktor.bakingappfinal.Data.Networking.Models.Step;
import udacity.viktor.bakingappfinal.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DetailStepFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class DetailStepFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBE

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private DetailsListListener detailsListListener;

    private List<Step> stepList;
    private int current_step_index;
    private Step current_step;
    private Context mContext;

    private boolean mTwoPane;

    private SimpleExoPlayer mExoPlayer;
    private SimpleExoPlayerView mPlayerView;
    private ConstraintLayout navigation;
    private TextView shortDesc;
    private TextView fullDesc;
    private ImageView coverOfVideo;
    private FloatingActionButton back;
    private FloatingActionButton forward;


    public DetailStepFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment DetailStepFragment.
     */
    // TODO: Rename and change types and number of parameters

    private void ManagePlayerAndView() {
        releasePlayer();
        fullDesc.setText(current_step.getDescription());
        if (!String.valueOf(current_step.getThumbnailURL()).isEmpty()) {
            Picasso.get()
                    .load(current_step.getThumbnailURL())
                    .placeholder(R.drawable.ic_image_black_24dp)
                    .into(coverOfVideo);
        }


        if (mTwoPane) {
            navigation.setVisibility(View.GONE);
        } else {
            //   if(current_step_index==0)
            shortDesc.setText(current_step.getShortDescription());
        }
        if (!current_step.getVideoURL().isEmpty()) {
            InitializePlayer(Uri.parse(current_step.getVideoURL()));
            coverOfVideo.setVisibility(View.GONE);
            // Log.d("important", String.valueOf(Uri.parse(current_step.getVideoURL())));
        } else {
            coverOfVideo.setVisibility(View.VISIBLE);
        }
    }

    private void InitializePlayer(Uri uri) {

        if (mExoPlayer == null) {
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(requireActivity(), trackSelector, loadControl);
            mPlayerView.setVisibility(View.VISIBLE);
            mPlayerView.setPlayer(mExoPlayer);
            String userAgent = Util.getUserAgent(mContext, getString(R.string.app_name));
            MediaSource mediaSource = new ExtractorMediaSource(uri, new DefaultDataSourceFactory(requireActivity(),
                    userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource, true, false);
            mExoPlayer.setPlayWhenReady(true);
        }
    }

    private void releasePlayer() {
        if (mExoPlayer != null) {
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            stepList = getArguments().getParcelableArrayList(getString(R.string.steps));
            current_step_index = getArguments().getInt(getString(R.string.current_step));
            current_step = stepList.get(current_step_index);
            mTwoPane = getArguments().getBoolean(getString(R.string.is_tablet));


        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail_step, container, false);
        mPlayerView = view.findViewById(R.id.playerView);
        navigation = view.findViewById(R.id.navigation);
        shortDesc = view.findViewById(R.id.shortdesc);
        fullDesc = view.findViewById(R.id.fulldesc);
        coverOfVideo = view.findViewById(R.id.launcher);
        back = view.findViewById(R.id.back);
        forward = view.findViewById(R.id.forward);
        ManagePlayerAndView();
        manageFloatingAction();

        return view;
    }

    private void manageFloatingAction() {
        if (current_step_index == 0) {
            back.setVisibility(View.INVISIBLE);
        }
        if (current_step_index == stepList.size() - 1) {
            forward.setVisibility(View.INVISIBLE);
        }
        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detailsListListener.setCurrentStep(current_step_index + 1, false);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detailsListListener.setCurrentStep(current_step_index - 1, false);
            }
        });

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
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
        mListener = null;
        detailsListListener = null;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
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
