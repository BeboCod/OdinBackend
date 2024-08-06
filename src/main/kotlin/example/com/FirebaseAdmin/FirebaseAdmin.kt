package example.com.FirebaseAdmin

import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.secretmanager.v1.AccessSecretVersionRequest
import com.google.cloud.secretmanager.v1.SecretManagerServiceClient
import com.google.cloud.secretmanager.v1.SecretManagerServiceSettings
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import java.io.FileInputStream

object FirebaseAdmin {
    private const val STORAGE_BUCKET = "ktor-firebase-storage.appspot.com"
    private const val CREDENTIALS_PATH = "C:/Users/chris/OneDrive/Desktop/odin-883a2-2e9215e49c0f.json"

    private val options: FirebaseOptions by lazy {
        val credentials = GoogleCredentials.fromStream(FileInputStream(CREDENTIALS_PATH))
        val settings = SecretManagerServiceSettings.newBuilder()
            .setCredentialsProvider { credentials }
            .build()

        val client = SecretManagerServiceClient.create(settings)
        try {
            val secretName = "projects/114210983918/secrets/ApiOdinGoogleCloud/versions/1"
            val request = AccessSecretVersionRequest.newBuilder().setName(secretName).build()
            val response = client.accessSecretVersion(request)
            val serviceAccountJson = response.payload.data.toStringUtf8()

            FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccountJson.byteInputStream()))
                .setStorageBucket(STORAGE_BUCKET)
                .build()
        } finally {
            client.close()
        }
    }

    fun init(): FirebaseApp = FirebaseApp.initializeApp(options)
}