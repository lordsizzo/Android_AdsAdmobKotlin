package lou.sizzo.adsadmobkotlin.ads

import android.app.Activity
import android.app.Dialog
import android.content.Context
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import lou.sizzo.adsadmobkotlin.utils.toast
import lou.sizzo.adsadmobkotlin.R

class Ads {
    companion object{
        var mInterstitialAd: InterstitialAd? = null
        var mRewardedAd: RewardedAd? = null
    }

    fun loadBanner(mAdView: AdView){
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        mAdView.adListener = object: AdListener() {
            override fun onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            override fun onAdFailedToLoad(adError : LoadAdError) {

                println("Fallo al cargar banner: $adError")
                // Code to be executed when an ad request fails.
            }

            override fun onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            override fun onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            override fun onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }
        }
    }

    fun loadInterstialAd(context: Context, pDialog: Dialog){
        //Load Interstial
        var adsInterstialArray  = context.resources.getStringArray(R.array.arrayInterstial)
        var idInterstial = adsInterstialArray.random()
        var adRequest = AdRequest.Builder().build()
        InterstitialAd.load(context, idInterstial, adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                println("error interstial: ${adError.message}")
                pDialog.isShowing.let {
                    if (it)
                        pDialog.dismiss()
                }
                mInterstitialAd = null
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                println("Interstial was loaded.")
                mInterstitialAd = interstitialAd
            }
        })
    }

    fun showInterstialAd(context: Context, activity: Activity, pDialog: Dialog){
        //Callback Interstial
        mInterstitialAd?.fullScreenContentCallback = object: FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
                println("Interstial was dismissed.")
                pDialog.isShowing.let {
                    if (it)
                        pDialog.dismiss()
                }
            }

            override fun onAdFailedToShowFullScreenContent(adError: AdError?) {
                println("Interstial failed to show. $adError")
            }

            override fun onAdShowedFullScreenContent() {
                println("Interstial showed fullscreen content.")
                mInterstitialAd = null
            }
        }

        //Show Interstial
        if (mInterstitialAd != null) {
            mInterstitialAd?.show(activity)

            context.toast("Interstial finalizado con exito")
            loadInterstialAd(context, pDialog)
            pDialog.isShowing.let {
                if (it)
                    pDialog.dismiss()
            }
        } else {
            pDialog.isShowing.let {
                if (it)
                    pDialog.dismiss()
            }
            context.toast("El Interstial no estaba listo, prueba de nuevo")
            loadInterstialAd(context, pDialog)
        }
    }

    fun loadRewarded(context: Context){

        var adsRewardedArray  = context.resources.getStringArray(R.array.arrayBonificado)
        var idRewarded = adsRewardedArray.random()
        var adRequest = AdRequest.Builder().build()
        RewardedAd.load(context,idRewarded, adRequest, object : RewardedAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                println("error rewarded: ${adError.message}")
                mRewardedAd = null
            }

            override fun onAdLoaded(rewardedAd: RewardedAd) {
                println("Rewarded was loaded.")
                mRewardedAd = rewardedAd
            }
        })
    }
    fun showRewardedAd(context: Context, activity: Activity, pDialog: Dialog){
        //Callback Rewarded
        context.toast("Espera mientras termina el anuncio")
        mRewardedAd?.fullScreenContentCallback = object: FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
                println("Rewarded was dismissed.")
                context.toast("Es necesario terminal el anuncio")
                pDialog.isShowing.let {
                   if (it)
                       pDialog.dismiss()
                }
            }

            override fun onAdFailedToShowFullScreenContent(adError: AdError?) {
                println("Rewarded failed to show.")
                pDialog.isShowing.let {
                    if (it)
                        pDialog.dismiss()
                }
            }

            override fun onAdShowedFullScreenContent() {
                println("Rewarded showed fullscreen content.")
                mRewardedAd = null
            }
        }

        //Show Rewarded
        if (mRewardedAd != null) {
            mRewardedAd?.show(activity) {
                loadRewarded(context)
                context.toast("Bonificación dada con exito")
            }

        } else {
            if (pDialog.isShowing){
                pDialog.dismiss()
            }
            context.toast("La bonificación no estaba lista, prueba de nuevo")
            loadRewarded(context)
        }
    }
}
