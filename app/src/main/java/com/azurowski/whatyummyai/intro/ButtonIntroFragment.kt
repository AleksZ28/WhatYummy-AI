package com.azurowski.whatyummyai.intro

import android.credentials.GetCredentialException
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.transition.ChangeBounds
import androidx.transition.TransitionManager
import com.azurowski.whatyummyai.R
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.launch

class ButtonIntroFragment : Fragment() {

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_button_intro, container, false)

        auth = FirebaseAuth.getInstance()

        view.findViewById<Button>(R.id.bLoginWithGoogle).setOnClickListener {
            lifecycleScope.launch {
                signInWithCredentialManager()
            }
        }

        return view
    }

    private suspend fun signInWithCredentialManager() {
        val credentialManager = CredentialManager.create(requireContext())

        val googleIdOption = GetGoogleIdOption.Builder()
            .setServerClientId(getString(R.string.default_web_client_id))
            .build()

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        try {
            val result = credentialManager.getCredential(requireContext(), request)
            val googleIdToken = GoogleIdTokenCredential.createFrom(result.credential.data)
            firebaseAuthWithGoogle(googleIdToken.idToken)
        } catch (e: GetCredentialException) {
            Log.e("AUTH", "Błąd logowania: ${e.message}")
        } catch (e: Exception) {
            Log.e("AUTH", "${e.message}")
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    Log.d("AUTH", "signInWithCredential:success")
                    val user = auth.currentUser

                    val rootLayout = requireActivity().findViewById<ConstraintLayout>(R.id.main)
                    val transition = ChangeBounds().apply {
                        duration = 750
                        interpolator = AccelerateDecelerateInterpolator()
                    }
                    TransitionManager.beginDelayedTransition(rootLayout, transition)

                    parentFragmentManager.beginTransaction()
                        .replace(R.id.fcvIntro, afterLoginFragment())
                        .replace(R.id.fcvButtonIntro, afterLoginButton())
                        .replace(R.id.fcvIsland, IslandLoginFragment())
                        .commit()

                } else {
                    Log.w("AUTH", "signInWithCredential:failure", task.exception)
                }
            }
    }
}
