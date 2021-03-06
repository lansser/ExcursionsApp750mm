package by.a750mm.excursionsapp750mm.presentation.screen.excursion

import android.content.Intent
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import by.a750mm.excursionsapp750mm.R
import by.a750mm.excursionsapp750mm.presentation.base.BaseRouter
import by.a750mm.excursionsapp750mm.presentation.screen.excursion.booking.ExcursionBookingFragment
import by.a750mm.excursionsapp750mm.presentation.screen.excursion.details.ExcursionDetailsFragment
import by.a750mm.excursionsapp750mm.presentation.screen.excursion.list.ExcursionListFragment
import by.a750mm.excursionsapp750mm.presentation.utils.visibility


class ExcursionRouter(activity: ExcursionActivity) : BaseRouter<ExcursionActivity>(activity) {


    fun goToExcursionList() {
        replaceFragment(activity.supportFragmentManager, ExcursionListFragment.getInstance(), R.id.container, false)
        val goBackImageButton: ImageButton = activity.findViewById<ImageButton>(R.id.goBackImageButton)
        val titleTextView: TextView = activity.findViewById<TextView>(R.id.titleTextView)
        goBackImageButton.visibility(false)
        titleTextView.text = activity.getString(R.string.title_750)
    }

    fun goToExcursionDetails(id: String) {
        val containerDetails = activity.findViewById<View>(R.id.containerDetails)
        if (containerDetails == null) {
            replaceFragment(activity.supportFragmentManager, ExcursionDetailsFragment.getInstance(id), R.id.container, true)
        } else {
            replaceFragment(activity.supportFragmentManager, ExcursionDetailsFragment.getInstance(id),
                    R.id.containerDetails, false)
        }
        val goBackImageButton: ImageButton = activity.findViewById<ImageButton>(R.id.goBackImageButton)
        val titleTextView: TextView = activity.findViewById<TextView>(R.id.titleTextView)
        titleTextView.text = activity.getString(R.string.title_750)
        goBackImageButton.visibility(true)
        goBackImageButton.setOnClickListener {
            goToExcursionList()
        }

    }

    fun goToExcursionBooking(id: String) {
        val containerDetails = activity.findViewById<View>(R.id.containerDetails)
        if (containerDetails == null) {
            replaceFragment(activity.supportFragmentManager, ExcursionBookingFragment.getInstance(id), R.id.container, true)
        } else {
            replaceFragment(activity.supportFragmentManager, ExcursionBookingFragment.getInstance(id),
                    R.id.containerDetails, false)
        }
        val goBackImageButton: ImageButton = activity.findViewById<ImageButton>(R.id.goBackImageButton)
        val titleTextView: TextView = activity.findViewById<TextView>(R.id.titleTextView)
        titleTextView.text = activity.getString(R.string.registration)
        goBackImageButton.visibility(true)
        goBackImageButton.setOnClickListener {
            goToExcursionDetails(id)
        }
    }

    fun showBookingComplete() {
        Toast.makeText(activity, activity.getString(R.string.book_add), Toast.LENGTH_SHORT).show()
    }

    fun showBookingError() {
        Toast.makeText(activity, activity.getString(R.string.fill_fotms), Toast.LENGTH_SHORT).show()
    }

    fun showSeatsError() {
        Toast.makeText(activity, activity.getString(R.string.muchseats), Toast.LENGTH_SHORT).show()
    }

    fun share(name: String) {
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT,
                    activity.getString(R.string.shareDescription1) + " \"$name\" " + activity.getString(R.string.shareDescription2))
            type = "text/plain"
        }
        activity.startActivity(Intent.createChooser(sendIntent, activity.getString(R.string.share)))
    }

}