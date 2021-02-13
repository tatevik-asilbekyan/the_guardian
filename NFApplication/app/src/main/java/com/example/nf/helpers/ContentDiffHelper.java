package com.example.nf.helpers;

import androidx.annotation.Nullable;

import com.example.nf.data.entity.Content;

import java.util.List;

public final class ContentDiffHelper {

    private ContentDiffHelper() {
    }

    public static List<Content> checkIfNewItems(List<Content> newItems, @Nullable String lastItemId) {
        if (lastItemId != null) {
            int position = -1;
            for (Content content : newItems) {
                if (content.id.contentEquals(lastItemId)) {
                    position = newItems.indexOf(content);
                }
            }

            if (position == -1) {
                return newItems;
            } else if (position == 0) {
                return null;
            } else {
                return newItems.subList(0, position);
            }
        }

        return newItems;
    }
}
