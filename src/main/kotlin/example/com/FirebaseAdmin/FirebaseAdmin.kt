package example.com.FirebaseAdmin

import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.secretmanager.v1.AccessSecretVersionRequest
import com.google.cloud.secretmanager.v1.SecretManagerServiceClient
import com.google.cloud.secretmanager.v1.SecretManagerServiceSettings
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import kotlinx.io.IOException
import java.io.ByteArrayInputStream
import java.io.FileInputStream

object FirebaseAdmin {
    private const val STORAGE_BUCKET = "ktor-firebase-storage.appspot.com"

    private val options: FirebaseOptions by lazy {
        val credentialsPath = "C:/Users/chris/OneDrive/Desktop/odin-883a2-firebase-adminsdk-2bmbn-733feb3774.json"
        val credentials: GoogleCredentials
        try {
            credentials = GoogleCredentials.fromStream(FileInputStream(credentialsPath))
        } catch (e: IOException) {
            throw RuntimeException("Failed to load credentials from path: $credentialsPath", e)
        }
        val settings = SecretManagerServiceSettings.newBuilder()
            .setCredentialsProvider{ credentials }
            .build()
        val client = SecretManagerServiceClient.create(settings)
        try {
            val secretName = "projects/114210983918/secrets/ApiOdinGoogleCloud/versions/1"
            val request = AccessSecretVersionRequest.newBuilder().setName(secretName).build()
            val response = client.accessSecretVersion(request)
            val serviceAccountJson = response.payload.data.toStringUtf8()

            FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(ByteArrayInputStream(serviceAccountJson.toByteArray())))
                .setStorageBucket(STORAGE_BUCKET)
                .build()
        } finally {
            client.close()
        }
    }

    fun init(): FirebaseApp = FirebaseApp.initializeApp(options)
}