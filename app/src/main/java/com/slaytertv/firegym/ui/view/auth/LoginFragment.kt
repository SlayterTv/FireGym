package com.slaytertv.firegym.ui.view.auth

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.slaytertv.firegym.AuthActivity
import com.slaytertv.firegym.MainActivity
import com.slaytertv.firegym.R
import com.slaytertv.firegym.SplashActivity
import com.slaytertv.firegym.databinding.FragmentLoginBinding
import com.slaytertv.firegym.ui.viewmodel.auth.LoginViewModel
import com.slaytertv.firegym.util.UiState
import com.slaytertv.firegym.util.anallogin
import com.slaytertv.firegym.util.clickdisable
import com.slaytertv.firegym.util.clickenable
import com.slaytertv.firegym.util.dialogx
import com.slaytertv.firegym.util.hide
import com.slaytertv.firegym.util.isValidEmail
import com.slaytertv.firegym.util.show
import com.slaytertv.firegym.util.toast
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginFragment : Fragment() {
    //creamos val tag
    val TAG: String = "LoginFragment"
    //binding
    lateinit var binding: FragmentLoginBinding
    //viewmodel de auth para cver los cambios
    val viewModel: LoginViewModel by viewModels()
    //
    private val mainLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val mainIntent = Intent(requireContext(), AuthActivity::class.java)
            startActivity(mainIntent)
            requireActivity().finish()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    //botones e inicializacion de funciones
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //llamar observer
        observer()
        initListeners()
    }

    //inicio observador
    fun observer(){
        //ciclo de vida del login con el live data
        viewModel.login.observe(viewLifecycleOwner) { state ->
            when(state){
                is UiState.Loading -> {
                    handleLoading(isLoading = true)
                }
                is UiState.Failure -> {
                    handleLoading(isLoading = false)
                    //toast(state.error)
                    dialogx(state.error)
                }
                is UiState.Sucess -> {
                    handleLoading(isLoading = false)
                    //analitycs para informar wel login
                    anallogin(binding.passEt.text.toString())
                    val authIntent = Intent(requireContext(), SplashActivity::class.java)
                    mainLauncher.launch(authIntent)
                    //toast(state.data)
                    //findNavController().navigate(R.id.action_loginFragment_to_homeActivity)
                    //findNavController().navigate(R.id.action_loginFragment_to_home_navigation)
                }
            }
        }
    }
    //cambia el esto del boton y el progressbar
    private fun handleLoading(isLoading: Boolean) {
        with(binding) {
            if (isLoading) {
                binding.loginBtn.setText("")
                binding.loginBtn.clickdisable()
                binding.loginProgress.show()
            } else {
                binding.loginBtn.setText("Login")
                binding.loginBtn.clickenable()
                binding.loginProgress.hide()
            }
        }
    }

    //fin observer



    //initlistener
    //botones
    private fun initListeners() {
        with(binding){
            //botn login
            loginBtn.setOnClickListener {
                handleLogin()
            }
            //btn si se olvida el passwr
            forgotPassLabel.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_forgotFragment)
            }
            //btn si se necesita registrar
            registerLabel.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
            }
        }
    }
    //cuando se apreta boton login
    private fun handleLogin() {
        //pregubnta si los campos estan vacios
        if (validation()) {
            //se manda al viewmodel con los datos para loguear
            viewModel.login(
                email = binding.emailEt.text.toString(),
                password = binding.passEt.text.toString()
            )
        }
    }
    //validar camopse vacios
    fun validation(): Boolean {
        var isValid = true

        if (binding.emailEt.text.isNullOrEmpty()){
            isValid = false
            toast(getString(R.string.enter_email))
        }else{
            if (!binding.emailEt.text.toString().isValidEmail()){
                isValid = false
                toast(getString(R.string.invalid_email))
            }
        }
        if (binding.passEt.text.isNullOrEmpty()){
            isValid = false
            toast(getString(R.string.enter_password))
        }else{
            if (binding.passEt.text.toString().length < 8){
                isValid = false
                toast(getString(R.string.invalid_password))
            }
        }
        return isValid
    }
    //fin intlistener

}