package com.example.betareadingapp.feature_text.data.repository

import com.example.betareadingapp.domain.model.Text
import com.example.betareadingapp.domain.model.User
import android.net.Uri
import com.example.betareadingapp.domain.util.error.EmptyUserException
import com.example.betareadingapp.domain.util.error.NullSnapshotException
import com.example.betareadingapp.domain.util.error.UserDeserializationException
import com.example.betareadingapp.domain.util.error.UserNotLoggedInException

import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withTimeout
import javax.inject.Inject

class Repository @Inject constructor(
) {


    private var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val fireStoreDatabase = FirebaseFirestore.getInstance()


    suspend fun register(email: String, password: String, user: User): FirebaseUser {
        val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
        val currentUser = firebaseAuth.currentUser
        if (currentUser == null) {
            throw EmptyUserException()
        } else {
            fireStoreDatabase.collection("Users")
                .document(currentUser.uid)
                .set(user).await()
        }
        return result.user ?: throw EmptyUserException()
    }

    suspend fun login(email: String, password: String): FirebaseUser {


        val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()

        return result.user ?: throw EmptyUserException()
    }

//    fun logOut() {
//        firebaseAuth.signOut()
//
//    }


//    fun getLoggedUser(): Flow<Resource<FirebaseUser>> = flow {
//
//        emit(Resource.Loading())
//
//        if (firebaseAuth.currentUser != null) {
//
//            emit(Resource.Success(data = firebaseAuth.currentUser!!))
//        } else {
//            emit(Resource.Error(message = "Not Logged"))
//        }
//    }




    suspend fun getTexts(): List<Text> {

        val userId = firebaseAuth.currentUser?.uid ?: throw UserNotLoggedInException()

        val snapshot = fireStoreDatabase.collection("Text")
            .whereEqualTo("userId", userId)
            .get().await()
        val notes = snapshot.documents.mapNotNull { it.toObject(Text::class.java) }
        return notes
    }

    suspend fun getUserData(): User {

        val userId = firebaseAuth.currentUser?.uid ?: throw UserNotLoggedInException()

        val snapshot =  withTimeout(10000) {
            fireStoreDatabase.collection("Users")
                .document(userId).get().await()
        }
        return if (snapshot.exists()) {
            snapshot.toObject(User::class.java) ?: throw UserDeserializationException()
        } else {
            throw NullSnapshotException()
        }
    }

    suspend fun uploadPdfToFirebaseStorage1(
        uri: Uri,
        fileName: String,
        title: String,
        content: String
    ) {

        val userId = firebaseAuth.currentUser?.uid ?: throw UserNotLoggedInException()

        val user = getUserData()

        val uniqueId = fireStoreDatabase.collection("Text").document().id
        val pdfReference = Firebase.storage.reference.child("pdfs/${uniqueId}_${fileName}")

        val pdfSnapshot = pdfReference.putFile(uri).await()
        val downloadUrl = pdfSnapshot.storage.downloadUrl.await()


        val author = "${user.name} + ${user.surname}"

        val newText = hashMapOf(
            "userId" to userId,
            "author" to author,
            "title" to title,
            "content" to content,
            "file" to downloadUrl.toString(),
            "timestamp" to Timestamp.now()
        )
        saveTextToFirestore(newText, uniqueId)
    }
    suspend fun saveTextToFirestore(newText: Map<String, Any>, uniqueId: String) {
        fireStoreDatabase.collection("Text")
            .document(uniqueId).set(newText).await()
    }

}



