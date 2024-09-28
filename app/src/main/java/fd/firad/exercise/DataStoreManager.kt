package fd.firad.exercise

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

private val IMAGE_URL_KEY = stringPreferencesKey("cached_image_url")

// Save imageUrl
suspend fun saveImageUrl(context: Context, url: String) {
    context.dataStore.edit { preferences ->
        preferences[IMAGE_URL_KEY] = url
    }
}

// get imageUrl
suspend fun getLastSavedImageUrl(context: Context): String? {
    return context.dataStore.data.map { preferences ->
        preferences[IMAGE_URL_KEY]
    }.firstOrNull()
}
