package com.example.studytimemanager

import android.app.Application
import androidx.test.core.app.ApplicationProvider
import com.example.studytimemanager.viewmodel.ProgressViewModel
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class ProgressViewModelTest {

    private lateinit var viewModel: ProgressViewModel

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Application>()
        viewModel = ProgressViewModel(context)
    }

    @Test
    fun testAddPlan_increasesList() {
        val before = viewModel.plans.size
        viewModel.addPlan("Math", 30)
        assertEquals(before + 1, viewModel.plans.size)
    }

    @Test
    fun testUpdatePlan_changesSubject() {
        viewModel.addPlan("Science", 20)
        val plan = viewModel.plans.first()
        viewModel.updatePlan(plan.id, "Biology", 25)
        assertEquals("Biology", viewModel.plans.first().subject)
    }

    @Test
    fun testRemovePlan_deletesCorrectly() {
        viewModel.addPlan("Physics", 40)
        val plan = viewModel.plans.first()
        viewModel.removePlan(plan.id)
        assertFalse(viewModel.plans.any { it.id == plan.id })
    }
}