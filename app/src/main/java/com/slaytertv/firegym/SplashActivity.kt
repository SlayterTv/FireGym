package com.slaytertv.firegym

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.slaytertv.firegym.data.model.ExerciseEntity
import com.slaytertv.firegym.ui.viewmodel.exerciselist.ExerciseListViewModel
import com.slaytertv.firegym.ui.viewmodel.splash.ExerciseViewModel
import com.slaytertv.firegym.ui.viewmodel.splash.SplashViewModel
import com.slaytertv.firegym.ui.viewmodel.splash.SplashViewModelFactory
import com.slaytertv.firegym.util.SharedPrefConstants
import com.slaytertv.firegym.util.UiState
import com.slaytertv.firegym.util.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: SplashViewModelFactory
    private lateinit var viewModel: SplashViewModel
    private val SPLASH_DELAY: Long = 10000 // 2 segundos
    private val viewModelx: ExerciseViewModel by viewModels()
    val viewModelExerciseList: ExerciseListViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        viewModel = ViewModelProvider(this, viewModelFactory)[SplashViewModel::class.java]
    }
    fun cargardatos(){
        viewModelExerciseList.exercisealllist.observe(this) { state ->
            when(state){
                is UiState.Loading -> {
                    //binding.homeProgresbarTop.show()
                }
                is UiState.Failure -> {
                    //binding.homeProgresbarTop.hide()
                    toast(state.error)
                }
                is UiState.Sucess -> {
                    this.lifecycleScope.launch {
                        val cuac = state.data.map { exercise ->
                            /*val fotoBlob = withContext(Dispatchers.IO) {
                                sacarblob(exercise.foto)
                            }*/
                            ExerciseEntity(
                                id = exercise.id.toLong(),
                                category = exercise.category,
                                link = exercise.link,
                                foto = exercise.foto, //fotoBlob,
                                foto2 = exercise.foto2,
                                muscle_group = exercise.muscle_group,
                                spec_group = exercise.spec_group,
                                bar_data = exercise.bar_data,
                                name = exercise.name
                            )
                        }

                        for (cuacx in cuac) {
                            viewModelx.insertExercise(cuacx)
                        }
                    }

                    val sharedPreferences = getSharedPreferences("app_prefsx", Context.MODE_PRIVATE)
                    sharedPreferences.edit().putBoolean(SharedPrefConstants.DESCARGADODATPS, false).apply()


                    GlobalScope.launch {
                        delay(SPLASH_DELAY)
                        withContext(Dispatchers.Main) {
                            navigateToActivity(MainActivity())
                        }
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        checkInternetAndUser()
    }

    private fun checkInternetAndUser() {
        if (!isConnectedToInternet()) {
            showErrorAndExit("No tienes conexión a Internet")
            return
        }
        val sharedPreferences = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val isFirstRun = sharedPreferences.getBoolean(SharedPrefConstants.SLIDER_INICIAL, true)
        val sharedPreferences2 = getSharedPreferences("app_prefsx", Context.MODE_PRIVATE)
        val isDownloaded = sharedPreferences2.getBoolean(SharedPrefConstants.DESCARGADODATPS, true)
        if (!isFirstRun) {
            if (viewModel.isUserLoggedIn()) {
                if(isDownloaded){
                    viewModelExerciseList.getAllExerciseList()
                    cargardatos()
                }else{
                    navigateToActivity(MainActivity())
                }
            } else {
                navigateToActivity(AuthActivity())
            }
        }else{
            navigateToActivity(SliderActivity())
        }
    }

    private fun isConnectedToInternet(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
        return networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
    }
    private fun showErrorAndExit(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        finishAffinity()
    }
    private fun navigateToActivity(acti:Activity) {
        val intent = Intent(this, acti::class.java)
        startActivity(intent)
        finish()
    }
}