package de.hdmstuttgart.swagrecipes;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import androidx.test.espresso.matcher.BoundedMatcher;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.core.internal.deps.guava.base.Preconditions.checkNotNull;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import de.hdmstuttgart.swagrecipes.ui.CollectionActivity;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class InsertNewRecipeTest {

    @Rule
    public ActivityScenarioRule<CollectionActivity> activityScenarioRule
            = new ActivityScenarioRule<>(CollectionActivity.class);

    @Test
    public void insertNewRecipeTest() {

        onView(withId(R.id.floatingActionButtonAddRecipe))
                .perform(click());

        onView(withId(R.id.mealNameInput))
                .perform(replaceText("Schokososseeee"));

        onView(withId(R.id.isHealthyInput))
                .perform(click());

        onView(withId(R.id.isCheapInput))
                .perform(click());

        onView(withId(R.id.readyInMinutesInput))
                .perform(replaceText("10"));

        onView(withId(R.id.servingsInput))
                .perform(replaceText("5"));

        onView(withId(R.id.instructionsInput))
                .perform(replaceText("Take chocolate and make it like a smoothie"));

        onView(withId(R.id.vegetarianCheckbox))
                .perform(click());

        onView(withId(R.id.veganCheckbox))
                .perform(click());

        onView(withId(R.id.doneButton))
                .perform(click());

        onView(withId(R.id.collectionRecyclerView))
                .check(matches(atPosition(0, hasDescendant(withText("Schokososseeee")))));
    }

    public static Matcher<View> atPosition(final int position, @NonNull final Matcher<View> itemMatcher) {
        checkNotNull(itemMatcher);
        return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("has item at position " + position + ": ");
                itemMatcher.describeTo(description);
            }

            @Override
            protected boolean matchesSafely(final RecyclerView view) {
                RecyclerView.ViewHolder viewHolder = view.findViewHolderForAdapterPosition(position);
                if (viewHolder == null) {
                    // has no item on such position
                    return false;
                }
                return itemMatcher.matches(viewHolder.itemView);
            }
        };
    }
}

