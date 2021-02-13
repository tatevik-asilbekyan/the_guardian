package com.example.nf.features.newsfeed.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.example.nf.R;
import com.example.nf.data.entity.Content;
import com.example.nf.data.entity.Field;
import com.example.nf.features.newsfeed.contract.FeedContract.OnFeedInteractionListener;
import com.example.nf.helpers.UIHelper;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class FeedItemDetailFragment extends BottomSheetDialogFragment {

    private static final String ARG_CONTENT = "arg_content";

    private OnFeedInteractionListener listener;
    private Content content;

    public FeedItemDetailFragment() {
    }

   static FeedItemDetailFragment newInstance(@NonNull Content content) {
        final FeedItemDetailFragment fragment = new FeedItemDetailFragment();
        final Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_CONTENT, content);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnFeedInteractionListener) {
            listener = (OnFeedInteractionListener) context;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null && getArguments().containsKey(ARG_CONTENT)) {
            content = getArguments().getParcelable(ARG_CONTENT);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_feed_item_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (content != null) {
            AppCompatTextView title = view.findViewById(R.id.feed_item_detail_title);
            AppCompatTextView category = view.findViewById(R.id.feed_item_detail_category);
            AppCompatTextView body = view.findViewById(R.id.feed_item_detail_body);
            AppCompatImageView thumb = view.findViewById(R.id.feed_item_detail_thumb);
            FloatingActionButton saveFab = view.findViewById(R.id.feed_item_detail_save);
            FloatingActionButton pinFab = view.findViewById(R.id.feed_item_detail_pin);
            AppCompatImageView close = view.findViewById(R.id.feed_item_detail_close);

            final String url = (content.fields == null || content.fields.thumbnail == null) ? Field.PLACEHOLDER : content.fields.thumbnail;
            UIHelper.asyncLoadImage(thumb, url, 0);
            category.setText(content.sectionName);
            title.setText(content.webTitle);
            Spanned bodyText;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                bodyText = Html.fromHtml(content.fields.body, Html.FROM_HTML_MODE_COMPACT);
            } else {
                bodyText = Html.fromHtml(content.fields.body);
            }
            body.setText(bodyText);
            saveFab.setVisibility(content.saved ? GONE : VISIBLE);
            pinFab.setVisibility(content.pinned ? GONE : VISIBLE);
            saveFab.setOnClickListener(view1 -> {
                listener.onSaveItemClicked(content);
                saveFab.hide();
            });
            pinFab.setOnClickListener(view2 -> {
                listener.onPinItemClicked(content);
                pinFab.hide();
            });
            close.setOnClickListener(view3 -> dismiss());
        }
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        listener.onFeedDetailClosed();
        super.onDismiss(dialog);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}
