package fd.firad.exercise

import android.app.Application
import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
val Context.dataStore by preferencesDataStore(name = "image_prefs")
class MyApplication : Application()