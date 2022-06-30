package com.preloved.app


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
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
class RegisterUITest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun registerUITest() {
        val textInputEditText = onView(
            allOf(
                withId(R.id.et_nama),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.tf_nama),
                        0
                    ),
                    0
                )
            )
        )
        textInputEditText.perform(scrollTo(), replaceText("Aldi"), closeSoftKeyboard())

        val textInputEditText2 = onView(
            allOf(
                withId(R.id.et_nama), withText("Aldi"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.tf_nama),
                        0
                    ),
                    0
                )
            )
        )
        textInputEditText2.perform(pressImeActionButton())

        val textInputEditText3 = onView(
            allOf(
                withId(R.id.et_email),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.tf_email),
                        0
                    ),
                    0
                )
            )
        )
        textInputEditText3.perform(scrollTo(), replaceText("aldi1@gmail.com"), closeSoftKeyboard())

        val textInputEditText4 = onView(
            allOf(
                withId(R.id.et_email), withText("aldi1@gmail.com"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.tf_email),
                        0
                    ),
                    0
                )
            )
        )
        textInputEditText4.perform(pressImeActionButton())

        val textInputEditText5 = onView(
            allOf(
                withId(R.id.et_password),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.tf_password),
                        0
                    ),
                    0
                )
            )
        )
        textInputEditText5.perform(scrollTo(), replaceText("12345678"), closeSoftKeyboard())

        val textInputEditText6 = onView(
            allOf(
                withId(R.id.et_password), withText("12345678"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.tf_password),
                        0
                    ),
                    0
                )
            )
        )
        textInputEditText6.perform(pressImeActionButton())

        val textInputEditText7 = onView(
            allOf(
                withId(R.id.et_repassword),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.tf_repassword),
                        0
                    ),
                    0
                )
            )
        )
        textInputEditText7.perform(scrollTo(), replaceText("12345678"), closeSoftKeyboard())

        val textInputEditText8 = onView(
            allOf(
                withId(R.id.et_repassword), withText("12345678"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.tf_repassword),
                        0
                    ),
                    0
                )
            )
        )
        textInputEditText8.perform(pressImeActionButton())

        val textInputEditText9 = onView(
            allOf(
                withId(R.id.et_phone),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.tf_phone),
                        0
                    ),
                    0
                )
            )
        )
        textInputEditText9.perform(scrollTo(), replaceText("8385996633"), closeSoftKeyboard())

        val textInputEditText10 = onView(
            allOf(
                withId(R.id.et_phone), withText("8385996633"),
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
                withId(R.id.et_address),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.tf_address),
                        0
                    ),
                    0
                )
            )
        )
        textInputEditText11.perform(scrollTo(), replaceText("semarang"), closeSoftKeyboard())

        val textInputEditText12 = onView(
            allOf(
                withId(R.id.et_address), withText("semarang"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.tf_address),
                        0
                    ),
                    0
                )
            )
        )
        textInputEditText12.perform(pressImeActionButton())

        val materialButton = onView(
            allOf(
                withId(R.id.btn_register), withText("Register"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.ScrollView")),
                        0
                    ),
                    9
                )
            )
        )
        materialButton.perform(scrollTo(), click())
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
