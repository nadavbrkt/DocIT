package com.nadav.docit.Activities.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.nadav.docit.Activities.StoryCreators.IStoryCreator;
import com.nadav.docit.Activities.StoryCreators.ImageCreator;
import com.nadav.docit.Activities.StoryCreators.Milestones.NewBornCreator;
import com.nadav.docit.Activities.StoryCreators.QuoteCreator;
import com.nadav.docit.Activities.StoryCreators.StoryCreator;
import com.nadav.docit.Class.StoryData;
import com.nadav.docit.R;

/**
 * Created by Nadav on 7/2/2016.
 */
public class CreateExtenedStoryFragment extends Fragment {
    public static final String TYPE = "STORYTYPE";
    private IStoryCreator _storyCreator;
    private OnDoneEditData _dataListener;

    public interface OnDoneEditData {
        public void setExtendedData(StoryData storyData);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnDoneEditData) {
            _dataListener = (OnDoneEditData) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement CreateExtenedStoryFragment.OnDoneEditData");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_extended_story, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        StoryData.Types type = StoryData.Types.valueOf((String) getArguments().get(TYPE));

        FrameLayout f = (FrameLayout) getView().findViewById(R.id.create_extended_story);
        f.removeAllViews();

        // Creates desiered creator
        switch (type) {
            case IMAGE:
                _storyCreator = new ImageCreator(f.getContext(), f);
                break;
            case QUOTE:
                _storyCreator = new QuoteCreator(f.getContext(), f);
                break;
            case STORY:
                _storyCreator = new StoryCreator(f.getContext(), f);
                break;
            case MILESTONE:
                _storyCreator = new NewBornCreator(f.getContext(), f);
                break;
        }


        f.addView(_storyCreator.createView());

        Button ok = (Button) getView().findViewById(R.id.okbtn);
        Button cancel = (Button) getView().findViewById(R.id.cancelbtn);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (_storyCreator.validate()) {
                    _dataListener.setExtendedData(_storyCreator.populateStoryData());
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ImageCreator.REQUEST_IMAGE_CAPTURE ||
                requestCode == ImageCreator.PICK_PHOTO) {
            if (_storyCreator instanceof ImageCreator)
                ((ImageCreator) _storyCreator).onActivityResult(requestCode, resultCode, data);
            else if (_storyCreator instanceof StoryCreator)
                ((StoryCreator) _storyCreator).onActivityResult(requestCode, resultCode, data);
            else if (_storyCreator instanceof NewBornCreator)
                ((NewBornCreator) _storyCreator).onActivityResult(requestCode, resultCode, data);
        } else if ((requestCode >= NewBornCreator.REQUEST_BABY_IMAGE_CAPTURE && requestCode <= NewBornCreator.REQUEST_ROOM_IMAGE_CAPTURE ) ||
            ((requestCode >= NewBornCreator.BABY_PICK_PHOTO && requestCode <= NewBornCreator.ROOM_PICK_PHOTO))){
            ((NewBornCreator) _storyCreator).onActivityResult(requestCode, resultCode, data);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
