package com.ams303.cityconnect;

import android.content.ComponentName;
import android.content.Context;
import android.view.View;
import android.widget.EditText;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.InstrumentationRegistry.getTargetContext;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.hamcrest.core.AllOf.allOf;
import static org.junit.Assert.*;


import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.ams303.cityconnect.ui.login.LoginActivity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;


@RunWith(AndroidJUnit4.class)
@LargeTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Tests {

    @Rule
    public ActivityScenarioRule<LoginActivity> activityScenarioRule
            = new ActivityScenarioRule<>(LoginActivity.class);

    @Test
    public void A_loginOnlyOneField() {
        onView(withId(R.id.username))
                .perform(replaceText("r.rosmaninho@ua.pt"), closeSoftKeyboard());
        onView(withId(R.id.login)).perform(click());

        onView(withId(com.google.android.material.R.id.snackbar_text))
                .check(matches(withText("Ambos os campos são obrigatórios!")));
    }

    @Test
    public void B_loginBadCredentials() {
        onView(withId(R.id.username))
                .perform(replaceText("demo@ua.pt"), closeSoftKeyboard());
        onView(withId(R.id.password))
                .perform(replaceText("password"), closeSoftKeyboard());
        onView(withId(R.id.login)).perform(click());

        onView(withId(com.google.android.material.R.id.snackbar_text))
                .check(matches(withText("A combinação email/password não existe")));
    }

    @Test
    public void C_loginGoodCredentials() {
        Intents.init();

        onView(withId(R.id.username))
                .perform(replaceText("demo@ua.pt"), closeSoftKeyboard());
        onView(withId(R.id.password))
                .perform(replaceText("cityconnect"), closeSoftKeyboard());
        onView(withId(R.id.login)).perform(click());

        intended(hasComponent(new ComponentName(getTargetContext(), MainActivity.class)));
        Intents.release();
    }

    @Test
    public void D_openStoreCatalog() {
        Intents.init();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.store_list))
                .perform(RecyclerViewActions.actionOnItem(
                        hasDescendant(withText("Ramona")), click()));

        intended(hasComponent(new ComponentName(getTargetContext(), CatalogActivity.class)));
        Intents.release();
    }

    @Test
    public void E_addToCart() {
        Intents.init();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.store_list))
                .perform(RecyclerViewActions.actionOnItem(
                        hasDescendant(withText("Ramona")), click()));

        intended(hasComponent(new ComponentName(getTargetContext(), CatalogActivity.class)));
        Intents.release();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.item_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, new ViewAction() {
                    @Override
                    public Matcher<View> getConstraints() {
                        return null;
                    }

                    @Override
                    public String getDescription() {
                        return "Click on specific button";
                    }

                    @Override
                    public void perform(UiController uiController, View view) {
                        View button = view.findViewById(R.id.item_add);
                        // Maybe check for null
                        button.performClick();
                    }
                }));

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(android.R.id.button1))
                .inRoot(isDialog()) // <---
                .check(matches(isDisplayed()))
                .perform(click());

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        onView(withId(com.google.android.material.R.id.snackbar_text))
                .check(matches(withText("Adicionado ao carrinho com sucesso!")));

         /*
        pressBack();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(Matchers.allOf(ViewMatchers.withId(R.id.nav_view), ViewMatchers.hasSibling(ViewMatchers.withText("Carrinho")))).perform(ViewActions.click());

          */
    }
}