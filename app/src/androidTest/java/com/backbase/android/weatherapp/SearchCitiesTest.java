package com.backbase.android.weatherapp;

import android.view.KeyEvent;
import android.widget.EditText;

import com.backbase.android.weatherapp.CitiesListActivity.CitiesActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.pressKey;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by Sam on 28/04/2019.
 * SearchCitiesTest class for test cases as mentioned in the test instruction document
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class SearchCitiesTest
{
    /**
     * Use {@link CitiesActivity} to create and launch the activity under test.
     */

    @Rule
    public ActivityTestRule<CitiesActivity> citiesActivityTestRule = new ActivityTestRule<>(CitiesActivity.class, true, true);

    @Test
    public void searchCity_Activity()
    {
        delay(2000);

        // Click on search icon - we can find it by the r.Id.
        onView(withId(R.id.action_search)).perform(click());

        // Type city1 in search
        onView(isAssignableFrom(EditText.class)).perform(typeText("Amsterdam"), pressKey(KeyEvent.KEYCODE_ENTER));
        delay(1500);
        // Matching the search result
        onView(new RecyclerViewMatcher(R.id.recycler_view).atPositionOnView(0, R.id.name)).check(matches(withText("Amsterdam, US")));

        delay(1500);

        // Type city2 in search
        onView(isAssignableFrom(EditText.class)).perform(replaceText("Sydney"));
        delay(2000);
        // Matching the search result
        onView(new RecyclerViewMatcher(R.id.recycler_view).atPositionOnView(0, R.id.name)).check(matches(withText("Sydney, AU")));

        delay(1500);

        // Type wrong city name in search
        onView(isAssignableFrom(EditText.class)).perform(replaceText("Wrong city name"));

        delay(1500);

        // Type city4 in search
        onView(isAssignableFrom(EditText.class)).perform(replaceText("Doha"));
        delay(1500);
        // Matching the search result
        onView(new RecyclerViewMatcher(R.id.recycler_view).atPositionOnView(0, R.id.name)).check(matches(withText("Doha, QA")));
        delay(1500);

        // Removing all inputs from search box so it'll show all cities
        onView(isAssignableFrom(EditText.class)).perform(replaceText(""));
        delay(3000);
    }

    // RecyclerViewMatcher helper
    public static RecyclerViewMatcher withRecyclerView(final int recyclerViewId)
    {
        return new RecyclerViewMatcher(recyclerViewId);
    }

    private void delay(int delay)
    {
        try
        {
            Thread.sleep(delay);
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}
