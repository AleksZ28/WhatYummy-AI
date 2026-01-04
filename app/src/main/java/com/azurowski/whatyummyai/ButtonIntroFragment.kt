package com.azurowski.whatyummyai

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Button
import androidx.fragment.app.Fragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth

class ButtonIntroFragment : Fragment() {

    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth

    private val RC_SIGN_IN = 9001

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_button_intro, container, false)

        auth = Firebase.auth

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)

        view.findViewById<Button>(R.id.bLoginWithGoogle).setOnClickListener {
            signIn()
        }

        return view
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                Log.w("GoogleSignIn", "signInResult:failed code=" + e.statusCode)
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    Log.d("AUTH", "signInWithCredential:success")
                    val user = auth.currentUser

                    val rootLayout = requireActivity().findViewById<androidx.constraintlayout.widget.ConstraintLayout>(R.id.main)
                    val transition = androidx.transition.ChangeBounds().apply {
                        duration = 750
                        interpolator = AccelerateDecelerateInterpolator()
                    }
                    androidx.transition.TransitionManager.beginDelayedTransition(rootLayout, transition)

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
