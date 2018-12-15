package com.marko.cryptofunctional.runner

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import com.marko.cryptofunctional.app.TestApp

class TestRunner : AndroidJUnitRunner() {

	override fun newApplication(
		cl: ClassLoader?,
		className: String?,
		context: Context?
	): Application = TestApp()
}