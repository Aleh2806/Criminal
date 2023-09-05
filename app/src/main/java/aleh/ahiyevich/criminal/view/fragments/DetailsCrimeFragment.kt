package aleh.ahiyevich.criminal.view.fragments

import aleh.ahiyevich.criminal.Constants
import aleh.ahiyevich.criminal.R
import aleh.ahiyevich.criminal.api.cases.DataCase
import aleh.ahiyevich.criminal.databinding.FragmentDetailsCrimeBinding
import aleh.ahiyevich.criminal.model.OnItemClick
import aleh.ahiyevich.criminal.repository.DataBaseHelper
import aleh.ahiyevich.criminal.view.adapters.CrimesAdapter
import aleh.ahiyevich.criminal.view.adapters.DetailsAdapter
import aleh.ahiyevich.criminal.view.adapters.VideoListDetailsAdapter
import android.app.Dialog
import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.ui.PlayerView


class DetailsCrimeFragment : Fragment(), OnItemClick {

    private val listDetails: ArrayList<String> = ArrayList()
    private val adapterNameMaterials = DetailsAdapter(listDetails, this)
    private lateinit var playerView: PlayerView
    private var simpleExoPlayer: ExoPlayer? = null
    private val listSuspects = ArrayList<String>()
    private lateinit var sharedPref: SharedPreferences
    private lateinit var progress: ProgressBar
    private var counterAnswers = 0


    private var _binding: FragmentDetailsCrimeBinding? = null
    private val binding: FragmentDetailsCrimeBinding
        get() {
            return _binding!!
        }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsCrimeBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preparePlayer()
        initRecyclerViewTittleMaterials()
        backFromFragment()
        initRwSuspects()
        answerDialogWho()
        answerDialogWhy()
    }


    private fun answerDialogWho() {
        binding.btnAnswer.setOnClickListener {
            val dialogBinding = layoutInflater.inflate(R.layout.dialog_answer, null)
            val whoCriminal = dialogBinding.findViewById<EditText>(R.id.edit_text_who)
            val cancel = dialogBinding.findViewById<ImageView>(R.id.btn_cancel)

            val myDialog = Dialog(requireContext())
            myDialog.setContentView(dialogBinding)
            myDialog.setCancelable(false)
            myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            myDialog.show()

            cancel.setOnClickListener {
                myDialog.dismiss()
            }

            // Обработка кнопки Подтвердить
            dialogBinding.findViewById<Button>(R.id.confirm_answer_who).setOnClickListener {
                progress = binding.progress
                if (whoCriminal.text.toString().contains("Q") || whoCriminal.text.toString()
                        .contains("q")
                ) {
                    if (counterAnswers == 0){
                        counterAnswers ++
                        progress.progress = 1
                        whoCriminal.setBackgroundColor(resources.getColor(R.color.progress_2))
                    } else {
                        progress.progress = 2
                        dialogBinding.findViewById<LinearLayout>(R.id.answer_check).visibility =
                            View.GONE
                        dialogBinding.findViewById<TextView>(R.id.congratulation).visibility =
                            View.VISIBLE
                    }
                } else {
                    whoCriminal.setBackgroundColor(resources.getColor(R.color.black_overlay))
                    Toast.makeText(requireContext(), "Неверный ответ", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
            }
        }
    }

    private fun answerDialogWhy() {
        binding.btnAnswer2.setOnClickListener {
            val dialogBinding = layoutInflater.inflate(R.layout.dialog_answer_2, null)
            val whyCriminal = dialogBinding.findViewById<EditText>(R.id.edit_text_why)
            val cancel = dialogBinding.findViewById<ImageView>(R.id.btn_cancel)

            val myDialog = Dialog(requireContext())
            myDialog.setContentView(dialogBinding)
            myDialog.setCancelable(false)
            myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            myDialog.show()

            cancel.setOnClickListener {
                myDialog.dismiss()
            }

            // Обработка кнопки Подтвердить
            dialogBinding.findViewById<Button>(R.id.confirm_answer_why).setOnClickListener {
                progress = binding.progress
                if (whyCriminal.text.toString().contains("Q") || whyCriminal.text.toString()
                        .contains("q")
                ) {
                    if (counterAnswers == 0){
                        counterAnswers ++
                        progress.progress = 1
                        whyCriminal.setBackgroundColor(resources.getColor(R.color.progress_2))
                    } else {
                        progress.progress = 2
                        dialogBinding.findViewById<LinearLayout>(R.id.answer_check).visibility =
                            View.GONE
                        dialogBinding.findViewById<TextView>(R.id.congratulation).visibility =
                            View.VISIBLE
                    }

                } else {
                    whyCriminal.setBackgroundColor(resources.getColor(R.color.black_overlay))
                    Toast.makeText(requireContext(), "Неверный ответ", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
            }
        }
    }



    private fun initRwSuspects() {
        listSuspects.removeAll(listSuspects.toSet())
        listSuspects.add("1")
        listSuspects.add("2")
        listSuspects.add("3")
        listSuspects.add("4")
        listSuspects.add("5")
        listSuspects.add("6")

        val recyclerViewVideoDetails = binding.recyclerViewVideoHelpList
        recyclerViewVideoDetails.setHasFixedSize(true)
        recyclerViewVideoDetails.adapter = VideoListDetailsAdapter(listSuspects)
        recyclerViewVideoDetails.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    }


    private fun backFromFragment() {
        val numberSeason = arguments?.getString("KEY_SEASON").toString()
        val numberCrime = arguments?.getString("KEY_CRIME").toString()
        binding.apply {
            detailsBack.setOnClickListener {
                sharedPref = requireActivity().getPreferences(AppCompatActivity.MODE_PRIVATE)
                val token = sharedPref.getString(Constants.ACCESS_TOKEN, "")
                DataBaseHelper().getAuthUser(
                    token!!,
                    requireContext(),
                    sharedPref,
                    requireActivity() as AppCompatActivity,
                    numberSeason
                )
            }
        }
    }

    // ExoPlayer - Подготовка к запуску и запуск по нажатию на кнопку Play
    private fun preparePlayer() {
        playerView = binding.playerView
        val btnForward = playerView.findViewById<ImageView>(R.id.exo_forward)
        val btnFullScreen = playerView.findViewById<ImageView>(R.id.btn_fullscreen)
        val progressBar = binding.progressBar

        simpleExoPlayer = ExoPlayer.Builder(requireContext()).build()

        btnForward.setOnClickListener {
            val currentPosition = simpleExoPlayer?.currentPosition
            simpleExoPlayer?.seekTo(currentPosition!! + 5000)
        }

        playerView.player = simpleExoPlayer
        playerView.keepScreenOn = true
        simpleExoPlayer?.addListener(object : Player.Listener {
            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                super.onPlayerStateChanged(playWhenReady, playbackState)
                if (playbackState == Player.STATE_BUFFERING) {
                    progressBar.visibility = View.VISIBLE
                } else if (playbackState == Player.STATE_READY) {
                    progressBar.visibility = View.GONE
                }
            }
        })


        val season = arguments?.getString("KEY_SEASON")
        val crime = arguments?.getString("KEY_CRIME")
        val video = arguments?.getString("VIDEO")

            val videoSource = Uri.parse("https://crime.api.unmodum.com//seasons/$season/cases/$crime/videos/$video")
            val mediaItem = MediaItem.fromUri(videoSource)
            simpleExoPlayer?.setMediaItem(mediaItem)
            simpleExoPlayer?.prepare()






        var isFullScreen = true

        btnFullScreen.setOnClickListener {
            if (isFullScreen) {
                playerView.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FILL
                requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                val params = playerView.layoutParams as ConstraintLayout.LayoutParams
                params.width = ViewGroup.LayoutParams.MATCH_PARENT
                params.height = ViewGroup.LayoutParams.MATCH_PARENT
                playerView.layoutParams = params
                binding.materialsTittle.visibility = View.GONE
                btnFullScreen.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.btn_fullscreen_exit
                    )
                )
                isFullScreen = false
            } else {
                requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                val params = playerView.layoutParams as ConstraintLayout.LayoutParams
                params.width = ViewGroup.LayoutParams.MATCH_PARENT
                params.height = 230 * (requireActivity().resources.displayMetrics.density).toInt()
                playerView.layoutParams = params
                binding.materialsTittle.visibility = View.VISIBLE
                btnFullScreen.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.btn_fullscreen
                    )
                )
                isFullScreen = true
            }
        }
    }

    // Освобождение и зануление exoplayer
    private fun releasePlayer() {
        simpleExoPlayer.let { player ->
            player?.release()
            simpleExoPlayer = null
        }
    }


    override fun onStop() {
        super.onStop()
        releasePlayer()
    }

    override fun onPause() {
        super.onPause()
        releasePlayer()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        releasePlayer()
    }


    private fun initRecyclerViewTittleMaterials() {
        listDetails.removeAll(listDetails.toSet())

        val crime = arguments?.getString("KEY_CRIME")?.toInt()
        val sharedPref: SharedPreferences =
            requireActivity().getPreferences(AppCompatActivity.MODE_PRIVATE)
        val token = sharedPref.getString(Constants.ACCESS_TOKEN, "")
        DataBaseHelper().getDirectoryCrimesName(token!!, crime!!, listDetails, adapterNameMaterials)

        val recyclerView: RecyclerView = binding.recyclerViewDetails
        recyclerView.setHasFixedSize(true)

        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        recyclerView.adapter = adapterNameMaterials


    }

    private fun replaceFragment(fragment: Fragment) {
        requireActivity()
            .supportFragmentManager
            .beginTransaction()
            .replace(R.id.container_for_fragment, fragment)
            .addToBackStack(null)
            .hide(this)
            .commit()
    }

    override fun onItemClick(position: Int) {
        val description = listDetails[position]
        val numberSeason = arguments?.getString("KEY_SEASON").toString()
        val numberCrime = arguments?.getString("KEY_CRIME").toString()

        when (position in 0..5) {
            (position == 0) -> replaceFragment(
                DescriptionsDetailsFragment.newInstance(
                    numberSeason,
                    numberCrime,
                    description,
                    "photo"
                )
            )
            (position == 1) -> replaceFragment(
                DescriptionsDetailsFragment.newInstance(
                    numberSeason,
                    numberCrime,
                    description,
                    "video"
                )
            )
            (position == 2) -> replaceFragment(
                DescriptionsDetailsFragment.newInstance(
                    numberSeason,
                    numberCrime,
                    description,
                    "questions"
                )
            )
            (position == 3) -> replaceFragment(
                DescriptionsDetailsFragment.newInstance(
                    numberSeason,
                    numberCrime,
                    description,
                    "suspects"
                )
            )
            (position == 4) -> replaceFragment(
                DescriptionsDetailsFragment.newInstance(
                    numberSeason,
                    numberCrime,
                    description,
                    "experts"
                )
            )
            (position == 5) -> replaceFragment(
                DescriptionsDetailsFragment.newInstance(
                    numberSeason,
                    numberCrime,
                    description,
                    "deponents"
                )
            )

            else -> {}
        }
    }

    companion object {

        fun newInstance(numberSeason: String, numberCrime: String, video: String): DetailsCrimeFragment {
            val bundle = Bundle()
            bundle.putString("KEY_SEASON", numberSeason)
            bundle.putString("KEY_CRIME", numberCrime)
            bundle.putString("VIDEO", video)
            val fragment = DetailsCrimeFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private fun callFullScreen() {
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // для ранних версий API
            requireActivity().window.decorView.systemUiVisibility = View.GONE
        } else if (Build.VERSION.SDK_INT >= 19) {
            // для более поздних версий API
            requireActivity().window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        }
    }

    override fun onStart() {
        super.onStart()
        callFullScreen()
    }


}