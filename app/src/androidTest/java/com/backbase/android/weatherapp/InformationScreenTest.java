package com.backbase.android.weatherapp;


import com.backbase.android.weatherapp.AboutCompanyActivity.AboutActivity;
import com.backbase.android.weatherapp.CitiesListActivity.CitiesActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by Sam on 28/04/2019.
 * InformationScreenTest class for UI test cases in AboutActivity as mentioned in the test instruction document
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class InformationScreenTest
{
    private String companyToBeDisplayed;
    private String companyAddress;
    private String companyCityName;
    private String companyPostal;
    private String aboutString;

    /**
     * Use {@link AboutActivity} to create and launch the activity under test.
     */

    @Rule
    public ActivityTestRule<AboutActivity> citiesActivityTestRule = new ActivityTestRule<>(AboutActivity.class, true, true);

    @Before
    public void initValidString()
    {
        // Specify a valid string.
        companyToBeDisplayed = "Backbase";
        companyAddress = "Jacob Bontiusplaats 9";
        companyCityName = "Amsterdam";
        companyPostal = "1018 LL";
        aboutString = "This is the Backbase assignment for Android engineering positions.";
    }


    /*
     * Method will search different cities and verifying their results
     * */
    @Test
    public void ui_Components_Text_Activity()
    {
        delay(1000);

        // Checking Company Name
        onView(withId(R.id.companyName)).check(matches(withText(companyToBeDisplayed)));

        // Checking Company Address
        onView(withId(R.id.companyAdress)).check(matches(withText(companyAddress)));

        // Checking Company City
        onView(withId(R.id.companyCity)).check(matches(withText(companyCityName)));

        // Checking Company Postal
        onView(withId(R.id.companypostal)).check(matches(withText(companyPostal)));

        // Checking About Info
        onView(withId(R.id.aboutInfo)).check(matches(withText(aboutString)));

        delay(1000);
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
