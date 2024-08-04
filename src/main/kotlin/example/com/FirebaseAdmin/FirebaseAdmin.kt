package example.com.FirebaseAdmin

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import java.io.FileInputStream

object FirebaseAdmin {
    private const val STORAGE_BUCKET = "ktor-firebase-storage.appspot.com"
    val serviceAccount = FileInputStream("src/main/resources/odin-883a2-firebase-adminsdk-2bmbn-733feb3774.json")

    private val options: FirebaseOptions = FirebaseOptions.builder()
        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
        .setStorageBucket(STORAGE_BUCKET)
        .build()

    fun init(): FirebaseApp = FirebaseApp.initializeApp(options)
}