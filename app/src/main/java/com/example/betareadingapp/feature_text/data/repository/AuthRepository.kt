package com.example.betareadingapp.feature_text.data.repository

import com.example.betareadingapp.domain.model.Text
import com.example.betareadingapp.domain.model.User
import com.example.betareadingapp.domain.util.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepository @Inject constructor() {

    private var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val fireStoreDatabase = FirebaseFirestore.getInstance()


    suspend fun register(email: String, password: String, user: User): FirebaseUser {

        val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
        fireStoreDatabase.collection("Users")
            .document(firebaseAuth.currentUser!!.uid)
            .set(user).await()


        return result.user!!
    }

    suspend fun login(email: String, password: String): FirebaseUser {


        val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
        return result.user!!
    }

    fun logOut() {
        firebaseAuth.signOut()

    }

    fun getLoggedUser(): Flow<Resource<FirebaseUser>> = flow {

        emit(Resource.Loading())

        if (firebaseAuth.currentUser != null) {

            emit(Resource.Success(data = firebaseAuth.currentUser!!))
        } else {
            emit(Resource.Error(message = "Not Logged"))
        }
    }

    fun getUserData(): Flow<Resource<User>> = flow {
        emit(Resource.Loading())
        if (firebaseAuth.currentUser != null) {
            val snapshot = fireStoreDatabase.collection("User")
                .document(firebaseAuth.currentUser!!.uid).get().await()
            if (snapshot.exists()) {
                val user: User? = snapshot.toObject(User::class.java)
                emit(Resource.Success(data = user!!))
            }
        }
    }

    suspend fun getTexts(): List<Text> {
        val userId = firebaseAuth.currentUser!!.uid
        val snapshot = fireStoreDatabase.collection("Text")
            .whereEqualTo("userId", userId)
            .get().await()
        val notes = snapshot.documents.mapNotNull { it.toObject(Text::class.java) }
        return notes
    }

}