package com.backbase.android.weatherapp;

import android.content.res.Resources;
import android.view.View;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Sam on 28/04/2019.
 * RecyclerViewMatcher class for RecyclerView espresso test cases
 */

public class RecyclerViewMatcher
{
    private final int recyclerViewId;

    public RecyclerViewMatcher(int recyclerViewId)
    {
        this.recyclerViewId = recyclerViewId;
    }

    public Matcher<View> atPosition(final int position)
    {
        return atPositionOnView(position, -1);
    }

    public Matcher<View> atPositionOnView(final int position, final int targetViewId)
    {

        return new TypeSafeMatcher<View>()
        {
            Resources resources = null;
            View childView;

            public void describeTo(Description description)
            {
                String idDescription = Integer.toString(recyclerViewId);
                if (this.resources != null)
                {
                    try
                    {
                        idDescription = this.resources.getResourceName(recyclerViewId);
                    } catch (Resources.NotFoundException var4)
                    {
                        idDescription = String.format("%s (resource name not found)", new Object[]{Integer.valueOf(recyclerViewId)});
                    }
                }

                description.appendText("with id: " + idDescription);
            }

            public boolean matchesSafely(View view)
            {

                this.resources = view.getResources();

                if (childView == null)
                {
                    RecyclerView recyclerView = view.getRootView().findViewById(recyclerViewId);
                    if (recyclerView != null && recyclerView.getId() == recyclerViewId)
                    {
                        if(recyclerView.findViewHolderForAdapterPosition(position) != null)
                            childView = recyclerView.findViewHolderForAdapterPosition(position).itemView;
                        else
                            return false;
                    } else
                    {
                        return false;
                    }
                }

                if (targetViewId == -1)
                {
                    return view == childView;
                } else
                {
                    View targetView = childView.findViewById(targetViewId);
                    return view == targetView;
                }

            }
        };
    }
}
