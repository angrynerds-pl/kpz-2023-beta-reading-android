package com.example.betareadingapp.feature_text.data.repository

import android.net.Uri
import com.example.betareadingapp.feature_text.domain.model.Text
import com.example.betareadingapp.feature_text.domain.model.User
import com.example.betareadingapp.feature_text.domain.util.Resource
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.HttpException
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.io.IOException
import javax.inject.Inject

class AuthRepository
@Inject
constructor() {
    private var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val fireStoreDatabase = FirebaseFirestore.getInstance()

    fun register(email: String, password: String, user: User): Flow<Resource<FirebaseUser>> = flow {
        emit(Resource.Loading())

        try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            fireStoreDatabase.collection("Users")
                .document(firebaseAuth.currentUser!!.uid)
                .set(user).await()


            emit((result.user?.let {
                Resource.Success(data = it)
            }!!))

        } catch (e: HttpException) {
            emit(Resource.Error(message = e.localizedMessage ?: "Unknown Error"))
        } catch (e: IOException) {
            emit(Resource.Error(message = e.localizedMessage ?: "Check Your Internet Connection"))
        } catch (e: Exception) {
            emit(Resource.Error(message = e.localizedMessage ?: ""))
        }


    }

    fun login(email: String, password: String): Flow<Resource<FirebaseUser>> = flow {

        emit(Resource.Loading())

        try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            emit((result.user?.let {
                Resource.Success(data = it)
            }!!))


        } catch (e: HttpException) {
            emit(Resource.Error(message = e.localizedMessage ?: "Unknown Error"))
        } catch (e: IOException) {
            emit(Resource.Error(message = e.localizedMessage ?: "Check Your Internet Connection"))
        } catch (e: Exception) {
            emit(Resource.Error(message = e.localizedMessage ?: ""))
        }

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

        val userId = firebaseAuth.currentUser?.uid

        if (userId != null) {
            try {
                val snapshot = fireStoreDatabase.collection("Users")
                    .document(userId).get().await()

                if (snapshot.exists()) {
                    val user: User? = snapshot.toObject(User::class.java)
                    if (user != null) {
                        emit(Resource.Success(user))
                    } else {
                        emit(Resource.Error(message = "User data is null"))
                    }
                } else {
                    emit(Resource.Error(message = "User document does not exist"))
                }

            } catch (e: HttpException) {
                emit(Resource.Error(message = e.localizedMessage ?: "Unknown Error"))
            } catch (e: IOException) {
                emit(Resource.Error(message = e.localizedMessage ?: "Check Your Internet Connection"))
            } catch (e: Exception) {
                emit(Resource.Error(message = e.localizedMessage ?: ""))
            }
        } else {
            emit(Resource.Error(message = "User not logged in"))
        }
    }

    fun getTexts(): Flow<Resource<List<Text>>> = flow {
        emit(Resource.Loading())

        val userId = firebaseAuth.currentUser?.uid

        if (userId != null) {
            try {
                val snapshot = fireStoreDatabase.collection("Text")
                    .whereEqualTo("userId", userId)
                    .get().await()

                val notes = snapshot.documents.mapNotNull { it.toObject(Text::class.java) }
                emit(Resource.Success(notes))

            } catch (e: HttpException) {
                emit(Resource.Error(message = e.localizedMessage ?: "Unknown Error"))
            } catch (e: IOException) {
                emit(
                    Resource.Error(
                        message = e.localizedMessage ?: "Check Your Internet Connection"
                    )
                )
            } catch (e: Exception) {
                emit(Resource.Error(message = e.localizedMessage ?: ""))
            }
        } else {
            emit(Resource.Error(message = "User not logged in"))
        }

    }

    fun uploadPdfToFirebaseStorage(uri: Uri, fileName : String, author: String, title: String, content: String): Flow<Resource<String>> =
        flow {
            emit(Resource.Loading())

            val userId = firebaseAuth.currentUser?.uid

            if (userId != null) {
                    val uniqueId = fireStoreDatabase.collection("Text").document().id
                    val pdfReference = Firebase.storage.reference.child("pdfs/${uniqueId}_${fileName}")

                    try {

                        val pdfSnapshot = pdfReference.putFile(uri).await()

                        // jesli uda sie przeslac
                        val downloadUrl = pdfSnapshot.storage.downloadUrl.await()

                        val newText = hashMapOf(
                            "userId" to userId,
                            "author" to author,
                            "title" to title,
                            "content" to content,
                            "file" to downloadUrl.toString(),
                            "timestamp" to Timestamp.now()
                        )

                        if (!saveTextToFirestore(newText, uniqueId)) {
                            try {
                                pdfReference.delete()
                                emit(Resource.Error(message = "Error in create text"))
                                return@flow
                            } catch (e: Exception) {
                                emit(Resource.Error(message = "Error in create text and delete pdf from storage"))
                                return@flow
                            }

                        }
                        emit(Resource.Success("Success add text"))

                    } catch (e: Exception) {
                        emit(Resource.Error(message = "${e.message}"))
                    }

            } else {
                emit(Resource.Error(message = "User not logged in"))
            }
        }

    suspend fun saveTextToFirestore(newText: Map<String, Any>, uniqueId: String): Boolean {
        try {
            fireStoreDatabase.collection("Text")
                .document(uniqueId).set(newText).await()
            return true
        } catch (e: Exception) {
            return false
        }
    }


}