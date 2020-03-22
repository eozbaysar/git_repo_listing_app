package com.ing.repoapp.core.navigation

import com.ing.repoapp.AndroidTest
import com.ing.repoapp.feature.presentation.views.ReposActivity
import com.ing.repoapp.shouldNavigateTo
import org.junit.Before
import org.junit.Test



class NavigatorTest : AndroidTest() {

    private lateinit var navigator: Navigator


    @Before fun setup() {
        navigator = Navigator()
    }

    @Test fun `should forward user to home screen (repos activity)`() {

        navigator.showMain(activityContext())

        RouteActivity::class shouldNavigateTo ReposActivity::class
    }


}
