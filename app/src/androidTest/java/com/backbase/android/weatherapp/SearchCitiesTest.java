package com.backbase.android.weatherapp;

import android.view.KeyEvent;
import android.widget.EditText;

import com.backbase.android.weatherapp.CitiesListActivity.CitiesActivity;

import org.junit.Before;
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
 * SearchCitiesTest class for search test cases as mentioned in the test instruction document
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

    private String city1;
    private String expected_result_city1;

    private String city2;
    private String expected_result_city2;

    private String city3;
    private String expected_result_city3;

    private String wrong_city_name;

    private String empty_city;

    @Before
    public void initValidString()
    {
        // Specify a valid string.
        city1 = "Amsterdam";
        expected_result_city1 = "Amsterdam, US";

        city2 = "Sydney";
        expected_result_city2 = "Sydney, AU";

        city3 = "Doha";
        expected_result_city3 = "Doha, QA";

        wrong_city_name = "Wrong city name";

        empty_city = "";
    }

    /*
    * Method will search different cities and verifying their results
    * */
    @Test
    public void searchCity_Activity()
    {
        delay(3000);

        // Click on search icon - we can find it by the r.Id.
        onView(withId(R.id.action_search)).perform(click());
        delay(1500);

        // Type city1 in search
        onView(isAssignableFrom(EditText.class)).perform(typeText(city1), pressKey(KeyEvent.KEYCODE_ENTER));
        delay(1500);
        // Matching the search result
        onView(new RecyclerViewMatcher(R.id.recycler_view).atPositionOnView(0, R.id.name)).check(matches(withText(expected_result_city1)));

        delay(1500);

        // Type city2 in search
        onView(isAssignableFrom(EditText.class)).perform(replaceText(city2));
        delay(2000);
        // Matching the search result
        onView(new RecyclerViewMatcher(R.id.recycler_view).atPositionOnView(0, R.id.name)).check(matches(withText(expected_result_city2)));

        delay(1500);

        // Type city3 in search
        onView(isAssignableFrom(EditText.class)).perform(replaceText(city3));
        delay(1500);
        // Matching the search result
        onView(new RecyclerViewMatcher(R.id.recycler_view).atPositionOnView(0, R.id.name)).check(matches(withText(expected_result_city3)));
        delay(1500);

        // Type wrong city name in search
        onView(isAssignableFrom(EditText.class)).perform(replaceText(wrong_city_name));

        delay(2500);
        // Removing all inputs from search box so it'll show all cities
        onView(isAssignableFrom(EditText.class)).perform(replaceText(empty_city));
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
