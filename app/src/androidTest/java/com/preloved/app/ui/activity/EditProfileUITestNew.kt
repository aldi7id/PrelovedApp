package com.preloved.app.ui.activity


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.preloved.app.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class EditProfileUITestNew {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun editProfileUITestNew() {
        Thread.sleep(5000)
        val bottomNavigationItemView = onView(
            allOf(
                withId(R.id.accountFragment), withContentDescription("Account"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.navigation_bar),
                        0
                    ),
                    4
                ),
                isDisplayed()
            )
        )
        bottomNavigationItemView.perform(click())

        val materialButton = onView(
            allOf(
                withId(android.R.id.button1), withText("Login"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.ScrollView")),
                        0
                    ),
                    3
                )
            )
        )
        materialButton.perform(scrollTo(), click())

        val textInputEditText = onView(
            allOf(
                withId(R.id.et_email),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.tf_email),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText.perform(replaceText("aldi10@gmail.com"), closeSoftKeyboard())

        val textInputEditText2 = onView(
            allOf(
                withId(R.id.et_email), withText("aldi10@gmail.com"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.tf_email),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText2.perform(pressImeActionButton())

        val textInputEditText3 = onView(
            allOf(
                withId(R.id.et_password),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.tf_password),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText3.perform(replaceText("12345678"), closeSoftKeyboard())

        val textInputEditText4 = onView(
            allOf(
                withId(R.id.et_password), withText("12345678"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.tf_password),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText4.perform(pressImeActionButton())

        val materialButton2 = onView(
            allOf(
                withId(R.id.btn_login), withText("Login"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                        0
                    ),
                    4
                ),
                isDisplayed()
            )
        )
        materialButton2.perform(click())
        Thread.sleep(5000)
        val bottomNavigationItemView2 = onView(
            allOf(
                withId(R.id.accountFragment), withContentDescription("Account"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.navigation_bar),
                        0
                    ),
                    4
                ),
                isDisplayed()
            )
        )
        bottomNavigationItemView2.perform(click())

        val materialTextView = onView(
            allOf(
                withId(R.id.tv_change_profile), withText("Change Profile"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                        1
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        materialTextView.perform(click())

        val textInputEditText5 = onView(
            allOf(
                withId(R.id.et_nama), withText("Aldi Dwi"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.tf_nama),
                        0
                    ),
                    0
                )
            )
        )
        textInputEditText5.perform(scrollTo(), replaceText("Aldi Dwi"))

        val textInputEditText6 = onView(
            allOf(
                withId(R.id.et_nama), withText("Aldi Dwi"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.tf_nama),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText6.perform(closeSoftKeyboard())

        val textInputEditText7 = onView(
            allOf(
                withId(R.id.et_nama), withText("Aldi Dwi"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.tf_nama),
                        0
                    ),
                    0
                )
            )
        )
        textInputEditText7.perform(pressImeActionButton())

        val textInputEditText8 = onView(
            allOf(
                withId(R.id.et_phone), withText("8387882441"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.tf_phone),
                        0
                    ),
                    0
                )
            )
        )
        textInputEditText8.perform(scrollTo(), replaceText("8387882441"))

        val textInputEditText9 = onView(
            allOf(
                withId(R.id.et_phone), withText("8387882441"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.tf_phone),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText9.perform(closeSoftKeyboard())

        val textInputEditText10 = onView(
            allOf(
                withId(R.id.et_phone), withText("8387882441"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.tf_phone),
                        0
                    ),
                    0
                )
            )
        )
        textInputEditText10.perform(pressImeActionButton())

        val textInputEditText11 = onView(
            allOf(
                withId(R.id.et_city), withText("Citayem"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.tf_city),
                        0
                    ),
                    0
                )
            )
        )
        textInputEditText11.perform(scrollTo(), replaceText("Citayem"))

        val textInputEditText12 = onView(
            allOf(
                withId(R.id.et_city), withText("Citayem"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.tf_city),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText12.perform(closeSoftKeyboard())

        val textInputEditText13 = onView(
            allOf(
                withId(R.id.et_city), withText("Citayem"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.tf_city),
                        0
                    ),
                    0
                )
            )
        )
        textInputEditText13.perform(pressImeActionButton())

        val textInputEditText14 = onView(
            allOf(
                withId(R.id.et_address), withText("Jl. Bogeng No.12"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.tf_address),
                        0
                    ),
                    0
                )
            )
        )
        textInputEditText14.perform(scrollTo(), replaceText("Jl. Bogeng No.12"))

        val textInputEditText15 = onView(
            allOf(
                withId(R.id.et_address), withText("Jl. Bogeng No.12"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.tf_address),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText15.perform(closeSoftKeyboard())

        val textInputEditText16 = onView(
            allOf(
                withId(R.id.et_address), withText("Jl. Bogeng No.12"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.tf_address),
                        0
                    ),
                    0
                )
            )
        )
        textInputEditText16.perform(pressImeActionButton())

        val materialButton3 = onView(
            allOf(
                withId(R.id.btn_change), withText("Save"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.ScrollView")),
                        0
                    ),
                    8
                )
            )
        )
        materialButton3.perform(scrollTo(), click())

        val appCompatImageButton = onView(
            allOf(
                withId(R.id.btn_back), withContentDescription("backbutton"),
                childAtPosition(
                    allOf(
                        withId(R.id.toolbar),
                        childAtPosition(
                            withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                            0
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        appCompatImageButton.perform(click())

        val materialTextView2 = onView(
            allOf(
                withId(R.id.tv_exit), withText("Logout"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                        1
                    ),
                    5
                ),
                isDisplayed()
            )
        )
        materialTextView2.perform(click())

        val materialButton4 = onView(
            allOf(
                withId(android.R.id.button1), withText("Sure"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.ScrollView")),
                        0
                    ),
                    3
                )
            )
        )
        materialButton4.perform(scrollTo(), click())
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
