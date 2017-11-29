package com.nadav.docit.Activities.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.nadav.docit.Activities.Adapters.StoryBookAdapter;
import com.nadav.docit.Activities.CreateStoryActivity;

import com.nadav.docit.Activities.Dialogs.ImageDialog;
import com.nadav.docit.Activities.Dialogs.NewBornDialog;
import com.nadav.docit.Activities.Dialogs.QuoteDialog;
import com.nadav.docit.Activities.Dialogs.StoryDialog;
import com.nadav.docit.Class.DataType.Image;
import com.nadav.docit.Class.DataType.Milestones.NewBornMS.NewBorn;
import com.nadav.docit.Class.DataType.Quote;
import com.nadav.docit.Class.DataType.Story;
import com.nadav.docit.Class.StoryBook;
import com.nadav.docit.Class.StoryData;
import com.nadav.docit.Models.Model;
import com.nadav.docit.R;

import java.util.ArrayList;

public class StoryBookFragment extends ListFragment {
    private static final String TAG = "BOOKDATA FRAG";
    protected StoryBookAdapter _storyBookListAdapter;
    protected String _storyBookID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_story_book, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        _storyBookID = getArguments().getString(StoryBookAdapter.BOOKID);

        setHasOptionsMenu(true);

        // Creates new adapter
        _storyBookListAdapter = new StoryBookAdapter(getActivity(), new ArrayList<StoryData>());

        // Attach the adapter to a ListView
        ListView listView = (ListView) getView().findViewById(android.R.id.list);
        listView.setAdapter(_storyBookListAdapter);

        // Gets story book data and display
        Model.getInstance().getStoryBookData(_storyBookID, new Model.GetStoryBookDataListener() {
            @Override
            public void addStoryData(StoryData storyData) {
                _storyBookListAdapter.changeMember(storyData);
            }

            @Override
            public void removeStoryData(String sdId) {
                _storyBookListAdapter.removeMemberByID(sdId);
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.story_book_menu, menu);
        return;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = null;
        intent = new Intent(getActivity(), CreateStoryActivity.class);
        intent.putExtra(CreateStoryActivity.STORY_BOOK_ID, _storyBookID);
        switch (item.getItemId()) {
            case (R.id.add_quote_story):
                intent.putExtra(CreateStoryActivity.STORY_TYPE, StoryData.Types.QUOTE);
                startActivity(intent);
                return true;
            case (R.id.add_img_story):
                intent.putExtra(CreateStoryActivity.STORY_TYPE, StoryData.Types.IMAGE);
                startActivity(intent);
                return true;
            case (R.id.add_story_story):
                intent.putExtra(CreateStoryActivity.STORY_TYPE, StoryData.Types.STORY);
                startActivity(intent);
                return true;
            case (R.id.add_milestone_story):
                final AlertDialog.Builder milestoneBuilder = new AlertDialog.Builder(getContext());
                Model.getInstance().getStoryBook(_storyBookID, new Model.StoryBookListener() {
                    @Override
                    public void onStoryBookLoaded(StoryBook sb) {
                        switch (StoryBook.Types.valueOf(sb.getType())) {
                            case BASE:
                                break;
                            case BLANK:
                                milestoneBuilder.setTitle("Milestones");
                                milestoneBuilder.setMessage("No milestones available");
                                milestoneBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });

                                break;
                            case NEWBORN:
                                milestoneBuilder.setTitle("Which Milestone :");

                                final ArrayAdapter<String> bornAdapter = new ArrayAdapter<String>(getContext(),
                                        android.R.layout.select_dialog_item);
                                bornAdapter.add("New Born");
                                bornAdapter.add("Hair cut");

                                milestoneBuilder.setAdapter(bornAdapter, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if (bornAdapter.getItem(which).equals("New Born")) {
                                            Intent intent1 = new Intent();
                                            intent1 = new Intent(getActivity(), CreateStoryActivity.class);
                                            intent1.putExtra(CreateStoryActivity.STORY_TYPE, StoryData.Types.MILESTONE);
                                            intent1.putExtra(CreateStoryActivity.STORY_BOOK_ID, _storyBookID);
                                            startActivity(intent1);
                                        } else {
                                            Toast.makeText(getActivity(), "Not yet implemented", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                                break;
                            case RELATIONSHIP:
                                milestoneBuilder.setTitle("Which Milestone :");

                                final ArrayAdapter<String> relAdapter = new ArrayAdapter<String>(getContext(),
                                        android.R.layout.select_dialog_item);
                                relAdapter.add("First Date");

                                milestoneBuilder.setAdapter(relAdapter, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast.makeText(getActivity(), "Not yet implemented", Toast.LENGTH_SHORT).show();
                                    }
                                });

                                break;
                        }

                        milestoneBuilder.show();
                    }

                    @Override
                    public void onError(Exception e) {
                        e.printStackTrace();
                    }
                });
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // Display dialogs
        StoryData storyData = (StoryData) _storyBookListAdapter.getItem(position);
        Dialog dialog;
        switch (storyData.getTypeVal()) {
            case IMAGE:
                Image image = (Image) _storyBookListAdapter.getItem(position);
                dialog = new ImageDialog(getActivity(), image);
                dialog.show();
                break;
            case QUOTE:
                Quote q = (Quote) _storyBookListAdapter.getItem(position);
                dialog = new QuoteDialog(getActivity(), q);
                dialog.show();
                break;
            case STORY:
                Story story = (Story) _storyBookListAdapter.getItem(position);
                dialog = new StoryDialog(getActivity(), story);
                dialog.show();
                break;
            case MILESTONE:
                NewBorn newBorn = (NewBorn) _storyBookListAdapter.getItem(position);
                dialog = new NewBornDialog(getActivity(), newBorn);
                dialog.show();
                break;
        }
    }
}
