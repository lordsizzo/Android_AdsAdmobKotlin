package lou.sizzo.adsadmobkotlin

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import lou.sizzo.adsadmobkotlin.ads.Ads
import lou.sizzo.adsadmobkotlin.utils.toast
import lou.sizzo.adsadmobkotlin.databinding.ActivityMainBinding
import lou.sizzo.adsadmobkotlin.dialogs.Dialogs
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var pDialog: Dialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        pDialog = Dialogs().progressDialog(this@MainActivity)


        //Inicializar el modo de visualización de Ads
        val iniciar =  selectMode("debug")

        //Listener para Interstial
        binding.btnInterstial.setOnClickListener {
            iniciar.let {

                if (it){
                    pDialog = Dialogs().progressDialog(this@MainActivity)
                    pDialog.show()
                    Ads().showInterstialAd(this@MainActivity, this, pDialog)
                }else{
                    toast("Selecciona el modo de Ads")
                }
            }
        }

        //Listener para Rewarded
        binding.btnRewarded.setOnClickListener{
            iniciar.let {

                if (it){
                    pDialog = Dialogs().progressDialog(this@MainActivity)
                    pDialog.show()
                    Ads().showRewardedAd(this@MainActivity, this, pDialog)
                }else{
                    toast("Selecciona el modo de Ads")
                }
            }
        }
    }

    fun selectMode(type: String): Boolean{

        var active: Boolean;
        when(type){
            "debug" ->{
                //El valor del test device será arrojado en el Logcat al momento de compilar e instalar la app, este deberá ser cambiado
                val testDeviceIds = listOf("AAE23BF1BA547EF5F01CA8C538031232")
                val configuration = RequestConfiguration.Builder().setTestDeviceIds(testDeviceIds).build()
                MobileAds.setRequestConfiguration(configuration)

                loadAds()
                active = true
            }
            "live" ->{
                MobileAds.initialize(
                    this
                ) { }

                loadAds()
                active = true
            }
            else -> {
                active = false
            }
        }
        return active
    }

    fun loadAds(){

        //Precargar Interstial
        Ads().loadInterstialAd(this@MainActivity, pDialog)

        //Precargar Rewarded
        Ads().loadRewarded(this@MainActivity)

        //Cargar Banners
        loadBanners()
    }

    fun loadBanners(){
        Ads().loadBanner(binding.adBannerTop)
        Ads().loadBanner(binding.adBannerBottom)
    }
}