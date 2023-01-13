package com.anilari.order.di

import com.anilari.order.ui.cart.CartViewModel
import com.anilari.order.ui.detailproduct.DetailProductViewModel
import com.anilari.order.ui.explore.ExploreViewModel
import com.anilari.order.ui.favorite.FavoriteViewModel
import com.anilari.order.ui.payment.PaymentViewModel
import com.anilari.order.ui.product.ProductViewModel
import com.anilari.order.ui.shop.ShopViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { ShopViewModel(get()) }
    viewModel { ProductViewModel(get()) }
    viewModel { DetailProductViewModel(get()) }
    viewModel { FavoriteViewModel(get()) }
    viewModel { ExploreViewModel(get()) }
    viewModel { CartViewModel(get()) }
    viewModel { PaymentViewModel(get()) }
}