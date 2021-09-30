package com.example.basicjetpackcompose.data

import com.example.basicjetpackcompose.R

data class OnBoardingItem(
    val title: String,
    val text: String,
    val image: Int
) {
    companion object {
        fun get(): List<OnBoardingItem> {
            return listOf(
                OnBoardingItem(
                    "Lorem",
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                    R.drawable.onboarding1
                ),
                OnBoardingItem(
                    "Ipsum",
                    "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
                    R.drawable.onboarding2
                ),
                OnBoardingItem(
                    "Dolor",
                    "Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.",
                    R.drawable.onboarding3
                )
            )
        }
    }
}
