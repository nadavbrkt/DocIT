package com.nadav.docit.Activities.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.nadav.docit.Activities.Adapters.StoryBookListAdapter;
import com.nadav.docit.Class.StoryBook;
import com.nadav.docit.Models.Model;
import com.nadav.docit.R;

import java.util.ArrayList;

public class StoryBookListFragment extends ListFragment {
    private static final String TAG = "BOOK FRAG";
    public static final String BOOKNAME = "com.nadav.docit.Activities.Fragments.StoryBookListFragment.BOOKNAME";
    protected StoryBookListAdapter _storyBookListAdapter;
    private OnItemClick listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_story_book_list, container, false);
        return view;
    }

    public interface OnItemClick {
        public void onBookClicked(StoryBook book);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnItemClick) {
            listener = (OnItemClick) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement StoryBookListFragment.OnItemClick");
        }
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Creates new adapter
        _storyBookListAdapter = new StoryBookListAdapter(getActivity().getApplicationContext(), new ArrayList<StoryBook>());

        // Attach the adapter to a ListView
        ListView listView = (ListView) getView().findViewById(android.R.id.list);
        listView.setAdapter(_storyBookListAdapter);

        // Loads storybook data
        Model.getInstance().loadStoryBooks(new Model.StoryBookLoaderListener() {
            @Override
            public void addStoryBook(StoryBook sb) {
                _storyBookListAdapter.add(sb);
            }

            @Override
            public void removeStoryBook(String sbId) {
                _storyBookListAdapter.removeMemberByID(sbId);
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(getActivity(), "Unable to get story book data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // On book clicked
        listener.onBookClicked(_storyBookListAdapter.getItem(position));
    }

    @Override
    public void onResume() {
        super.onResume();

        // Sets title
        getActivity().setTitle(R.string.app_name);
    }
}
