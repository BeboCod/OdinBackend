package example.com.FirebaseAdmin

import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.secretmanager.v1.AccessSecretVersionRequest
import com.google.cloud.secretmanager.v1.SecretManagerServiceClient
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import java.io.ByteArrayInputStream
import java.io.FileInputStream

object FirebaseAdmin {
    private const val STORAGE_BUCKET = "ktor-firebase-storage.appspot.com"

    private val options: FirebaseOptions by lazy {
        val client = SecretManagerServiceClient.create()
        try {
            val secretName = "projects/odin-883a2/secrets/ApiOdinGoogleCloud/versions/latest"
            val request = AccessSecretVersionRequest.newBuilder().setName(secretName).build()
            val response = client.accessSecretVersion(request)
            val serviceAccountJson = response.payload.data.toStringUtf8()

            FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(ByteArrayInputStream(serviceAccountJson.toByteArray())))
                .setStorageBucket(STORAGE_BUCKET)
                .build()
        } finally {
            client.shutdown()
        }
    }

    fun init(): FirebaseApp = FirebaseApp.initializeApp(options)
}