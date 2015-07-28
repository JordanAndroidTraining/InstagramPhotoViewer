package com.example.jordanhsu.instagramphotoviewer;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by jordanhsu on 7/29/15.
 */
public class AllCommentDialogFragment extends DialogFragment implements OnAllCommentLoadListener {
    public static final String ALL_COMMENT_DIALOG_FRAGMENT_DEV_TAG = "allCommentDialogFragmentDevTag";


//    public static AllCommentDialogFragment newInstance(String title) {
//        AllCommentDialogFragment frag = new AllCommentDialogFragment();
//        Bundle args = new Bundle();
//        args.putString("title", title);
//        frag.setArguments(args);
//        return frag;
//    }

    private ArrayList<CommentRow> commentRowList;
    private Context self;
    private AllCommentFragmentAdapter commentAdapter;
    private ListView commentLv;

    public AllCommentDialogFragment() {
//        this.commentRowList = new ArrayList<CommentRow>();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_comment_dialog, container);
        commentLv = (ListView) view.findViewById(R.id.allCommentListView);
        self = view.getContext();

        getDialog().setCanceledOnTouchOutside(true);

        return view;
    }

    public void refreshView(ArrayList<CommentRow> commentRowList){
//        this.commentRowList = new ArrayList<CommentRow>(commentRowList);
        commentAdapter = new AllCommentFragmentAdapter(self,0,commentRowList);
        Log.d(ALL_COMMENT_DIALOG_FRAGMENT_DEV_TAG, commentRowList.toString());
        commentLv.setAdapter(commentAdapter);
    }

    @Override
    public void onCommendLoadFinished() {

    }
}
