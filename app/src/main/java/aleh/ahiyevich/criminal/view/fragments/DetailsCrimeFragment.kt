package aleh.ahiyevich.criminal.view.fragments

import aleh.ahiyevich.criminal.R
import aleh.ahiyevich.criminal.databinding.FragmentDetailsCrimeBinding
import aleh.ahiyevich.criminal.model.OnItemClick
import aleh.ahiyevich.criminal.view.adapters.DetailsAdapter
import android.content.Context
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.ui.PlayerView
import kotlinx.android.synthetic.main.custom_controller.*


class DetailsCrimeFragment : Fragment(), OnItemClick {

    private val listDetails: ArrayList<String> = ArrayList()
    private lateinit var playerView: PlayerView
    private var simpleExoPlayer: ExoPlayer? = null



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
        initRecyclerView()
        backWithFragment()

    }


    private fun backWithFragment() {
        val numberSeason = arguments?.getString("KEY_SEASON").toString()
        binding.apply {
            detailsBack.setOnClickListener {
                replaceFragment(SeasonsFragment.newInstance(numberSeason))
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

    private fun releasePlayer(){
        simpleExoPlayer.let { player->
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


    private fun initRecyclerView() {
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