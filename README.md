# backspace_assign
Weather App
https://github.com/osamaashraf2005/backspace_assign

Approach to solve the search problem:
------------------------------------
* Created a TreeMap<String, CityInfo> at the app loading because as per instructions "Loading time of the app is not so important"
* TreeMap will provide subMap method for search which provides O(LogN) complexity
* Adding standard delay of 350ms before the search query executes (to prevent the useless query search if next key pressed) for optimized search
* It can be more optimized, but considering the development time was limited I'm making this solutions as best tradeoff between time and performance :)



Other important Assumptions/Decision:
------------------------------------
* Using AboutActivity for Showing "About Company" as well as "About City", so that there is no need third screen


UI Test Cases:
------------------------------------
Test Cases for Search provided under the androidTest with the name: SearchCitiesTest
Test Cases for Information Screen UI Test provided under the androidTest with the name: InformationScreenTest
