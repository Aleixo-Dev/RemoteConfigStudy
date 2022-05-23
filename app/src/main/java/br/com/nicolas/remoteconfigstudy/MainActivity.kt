package br.com.nicolas.remoteconfigstudy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.get
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings

class MainActivity : AppCompatActivity() {

    private lateinit var remoteConfig: FirebaseRemoteConfig

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupRemoteConfig()

        findViewById<Button>(R.id.buttonGoFeature).setOnClickListener {
            goToNewActivity()
        }
    }

    private fun setupRemoteConfig() {
        remoteConfig = Firebase.remoteConfig
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 3600
        }
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)
        remoteConfig.fetchAndActivate()
    }

    private fun isNewFeature() = remoteConfig[FEATURE_KEY].asBoolean()

    private fun goToNewActivity() {
        if (isNewFeature()) {
            startActivity(Intent(this, ActivityFeatureTwo::class.java))
        }else{
            startActivity(Intent(this, ActivityFeatureOne::class.java))
        }
    }

    companion object {
        private const val FEATURE_KEY = "new_feature"
    }
}