package aleh.ahiyevich.criminal.view.fragments

import aleh.ahiyevich.criminal.Constants
import aleh.ahiyevich.criminal.MainActivity
import aleh.ahiyevich.criminal.R
import aleh.ahiyevich.criminal.databinding.FragmentDetailsCrimeBinding
import aleh.ahiyevich.criminal.model.OnItemClick
import aleh.ahiyevich.criminal.repository.DataBaseHelper
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
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.ui.PlayerView


class DetailsCrimeFragment : Fragment(), OnItemClick {

    private val listDetails: ArrayList<String> = ArrayList()
    private lateinit var playerView: PlayerView
    private var simpleExoPlayer: ExoPlayer? = null
    private val listVideoDetails = ArrayList<String>()
    private lateinit var sharedPref: SharedPreferences



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
        backWithFragment()
        initRwHelperListVideo()

        binding.btnAnswer.setOnClickListener {
            answerDialog()
            Toast.makeText(
                requireContext(),
                "Переход в поле для ответа\nВвсести ответ",
                Toast.LENGTH_SHORT
            ).show()
        }


    }

    private fun answerDialog() {
        val dialogBinding = layoutInflater.inflate(R.layout.dialog_answer, null)
        val whoCriminal = dialogBinding.findViewById<EditText>(R.id.edit_text_who)
        val howCriminal = dialogBinding.findViewById<EditText>(R.id.edit_text_how)
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
        dialogBinding.findViewById<Button>(R.id.confirm_answer).setOnClickListener {
//            myDialog.dismiss()
            if (whoCriminal.text.toString().contains("Вася") || whoCriminal.text.toString()
                    .contains("вася")
            ) {
                whoCriminal.setBackgroundColor(resources.getColor(R.color.teal_200))
                Toast.makeText(requireContext(), "Правильный ответ", Toast.LENGTH_SHORT).show()
            } else {
                whoCriminal.setBackgroundColor(resources.getColor(R.color.black_overlay))
                Toast.makeText(requireContext(), "Неверный ответ", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (howCriminal.text.toString().contains("Нож") || howCriminal.text.toString()
                    .contains("нож")
            ) {
                howCriminal.setBackgroundColor(resources.getColor(R.color.teal_200))
                Toast.makeText(requireContext(), "Правильный ответ", Toast.LENGTH_SHORT).show()
            } else {
                howCriminal.setBackgroundColor(resources.getColor(R.color.black_overlay))
                Toast.makeText(requireContext(), "Неверный ответ", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (whyCriminal.text.toString().contains("Обида") || whyCriminal.text.toString()
                    .contains("обида")
            ) {
                whyCriminal.setBackgroundColor(resources.getColor(R.color.teal_200))
                Toast.makeText(requireContext(), "Правильный ответ", Toast.LENGTH_SHORT).show()
            } else {
                whyCriminal.setBackgroundColor(resources.getColor(R.color.black_overlay))
                Toast.makeText(requireContext(), "Неверный ответ", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (whoCriminal.text.toString().contains("Вася") || whoCriminal.text.toString()
                    .contains("вася")
                && howCriminal.text.toString().contains("Ножом") || howCriminal.text.toString()
                    .contains("ножом")
                && whyCriminal.text.toString().contains("Обида") || whyCriminal.text.toString()
                    .contains("обида")
            ) {
                dialogBinding.findViewById<LinearLayout>(R.id.answer_check).visibility = View.GONE
                dialogBinding.findViewById<TextView>(R.id.congratulation).visibility = View.VISIBLE
            }
        }
    }


    private fun initRwHelperListVideo() {
        listVideoDetails.add("1")
        listVideoDetails.add("2")
        listVideoDetails.add("3")
        listVideoDetails.add("4")
        listVideoDetails.add("5")
        listVideoDetails.add("6")

        val recyclerViewVideoDetails = binding.recyclerViewVideoHelpList
        recyclerViewVideoDetails.adapter = VideoListDetailsAdapter(listVideoDetails)
        recyclerViewVideoDetails.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

    }


    private fun backWithFragment() {
        val numberSeason = arguments?.getString("KEY_SEASON").toString()
        binding.apply {
            detailsBack.setOnClickListener {
                replaceFragment(SeasonsFragment.newInstance(numberSeason))
                sharedPref = requireActivity().getPreferences(AppCompatActivity.MODE_PRIVATE)
                val token = sharedPref.getString(Constants.ACCESS_TOKEN,"")
                DataBaseHelper().getAuthUser(token!!,requireContext(),sharedPref,requireActivity() as AppCompatActivity)
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


        val videoSource = Uri.parse("https://www.rmp-streaming.com/media/big-buck-bunny-360p.mp4")
//        val videoSource = Uri.parse("https://cdn.bitmovin.com/content/assets/art-of-motion-dash-hls-progressive/mpds/f08e80da-bf1d-4e3d-8899-f0f6155f6efa.mpd")
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
        val arrayNameDetails = resources.getStringArray(R.array.details_crime_list)
        listDetails.removeAll(listDetails.toSet())

        for (i in arrayNameDetails) {
            listDetails.add(i)
        }
        val recyclerView: RecyclerView = binding.recyclerViewDetails
        recyclerView.setHasFixedSize(true)

        recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
        recyclerView.adapter = DetailsAdapter(listDetails, this)


    }

    private fun replaceFragment(fragment: Fragment) {
        requireActivity()
            .supportFragmentManager
            .beginTransaction()
            .replace(R.id.container_for_fragment, fragment)
            .addToBackStack("")
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

        fun newInstance(numberSeason: String, numberCrime: String): DetailsCrimeFragment {
            val bundle = Bundle()
            bundle.putString("KEY_SEASON", numberSeason)
            bundle.putString("KEY_CRIME", numberCrime)
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