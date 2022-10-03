package com.example.cupcake

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.cupcake.model.OrderViewModel
import junit.framework.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class ViewModelTests {
    @get: Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun quantity_twelve_cupcakes() {
        //given
        val viewModel = OrderViewModel()
        viewModel.quantity.observeForever{}
        //when
        viewModel.setQuantity(12)
        //then
        assertEquals(12, viewModel.quantity.value)
    }

    @Test
    fun price_twelve_cupcakes() {
        //given
        val viewModel = OrderViewModel()
        viewModel.price.observeForever {}
        //when
        viewModel.setQuantity(12)
        //then
        assertEquals("£27.00", viewModel.price.value)
    }
}