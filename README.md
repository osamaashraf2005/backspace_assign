# backspace_assign
Weather App

Approach to solve the search problem:
------------------------------------
* Created a TreeMap<String, CityInfo> at the app loading because as per instructions "Loading time of the app is not so important"
* TreeMap will provide subMap method for search which provides O(LogN) complexity
* Adding standard delay of 350ms before the search query executes (to prevent the useless query search if next key pressed) for optimized search



Other important Assumptions/Decision:
------------------------------------
* Using AboutActivity for Showing "About Company" as well as "About City", so that there is no need third screen


Test Cases for Search provided under the androidTest with the name: SearchCitiesTest
Test Cases for Information Screen UI Test provided under the androidTest with the name: InformationScreenTest
