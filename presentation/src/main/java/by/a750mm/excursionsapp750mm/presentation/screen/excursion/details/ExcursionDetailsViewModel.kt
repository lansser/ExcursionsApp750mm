package by.a750mm.excursionsapp750mm.presentation.screen.excursion.details

import android.annotation.SuppressLint
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.util.Log
import by.a750mm.excursionsapp750mm.R
import by.a750mm.excursionsapp750mm.domain.usecases.GetByIdUseCase
import by.a750mm.excursionsapp750mm.presentation.app.App
import by.a750mm.excursionsapp750mm.presentation.base.BaseViewModel
import by.a750mm.excursionsapp750mm.presentation.screen.excursion.ExcursionRouter
import io.reactivex.rxkotlin.subscribeBy
import java.text.SimpleDateFormat
import com.google.android.gms.maps.model.LatLng
import javax.inject.Inject


class ExcursionDetailsViewModel : BaseViewModel<ExcursionRouter>() {
    val name = ObservableField<String>(" ")
    val description = ObservableField<String>(" ")
    val plan = ObservableField<String>(" ")
    val imgUrl = ObservableField<String>("")
    val date = ObservableField<String>(" ")
    val seats = ObservableField<Int>(0)
    private var excursionId: String? = null
    val isProgressEnabled = ObservableBoolean(false)
    val isExcursionEnabled = ObservableBoolean(false)
    var mMapLatLng = ObservableField<LatLng>(LatLng(53.9027, 27.5616))
    private val millis = System.currentTimeMillis() % 1000
    @SuppressLint("SimpleDateFormat")
    val df2 = SimpleDateFormat("dd/MM/yyyy")
    @Inject
    lateinit var excursionUseCase: GetByIdUseCase


    fun setExcursionId(id: String) {
        if (excursionId != null) return
        excursionId = id
        App.appComponent.inject(this)
        isProgressEnabled.set(true)
        val disposable = excursionUseCase.getById(id).subscribeBy(
                onNext = {
                    name.set(it.name)
                    val dateText = df2.format(it.nextDate)
                    if (it.nextDate < millis) {
                        date.set("Скоро поедем")
                    } else {
                        date.set(dateText)
                    }
                    imgUrl.set(it.imgUrl)
                    description.set(it.description)
                    plan.set(it.plan)
                    seats.set(it.seatsRest)
                    val latLng = (it.location).split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                    val latitude = java.lang.Double.parseDouble(latLng[0])
                    val longitude = java.lang.Double.parseDouble(latLng[1])
                    mMapLatLng.set(LatLng(latitude, longitude))
                    isProgressEnabled.set(false)
                    if (seats.get()!! > 0)
                        isExcursionEnabled.set(true)
                },
                onError = {
                    isProgressEnabled.set(false)
                    router?.showError(it)
                }
        )
        addToDisposable(disposable)

    }

    fun onClickGo() {
        if (excursionId != null) {
            router!!.goToExcursionBooking(excursionId!!)
        } else {
            return
        }
    }

    fun onClickShare() {
        router!!.share(name.get()!!)
    }

    override fun onCleared() {
    }

}
