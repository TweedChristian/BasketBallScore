package com.tweedchristian.android.basketballscore

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.content.res.AssetManager
import android.graphics.Bitmap
import android.graphics.Matrix
import android.media.ExifInterface
import android.media.ExifInterface.TAG_ORIENTATION
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_game_detail.*
import java.io.File
import java.io.IOException
import java.util.*

private const val TAG = "GameDetailFragment"

private const val ARGS_UUID = "uuid"
private const val ARGS_DATE = "date"

private const val REQUEST_PHOTO_TEAM_ONE = 1
private const val REQUEST_PHOTO_TEAM_TWO = 2

class GameDetailFragment : Fragment() {
    interface Callbacks {
        fun loadWinningList(winningTeam: Char)
    }

    private var callbacks: Callbacks? = null

    private lateinit var game: Game

    private lateinit var teamOne3ShotButton: Button
    private lateinit var teamTwo3ShotButton: Button
    private lateinit var teamOne2ShotButton: Button
    private lateinit var teamTwo2ShotButton: Button
    private lateinit var teamOneFreeThrowButton: Button
    private lateinit var teamTwoFreeThrowButton: Button
    private lateinit var resetButton: Button
    private lateinit var saveButton: Button
    private lateinit var displayButton: Button

    private lateinit var teamOneImageView: ImageView
    private lateinit var teamTwoImageView: ImageView
    private lateinit var teamOneImageButton: ImageButton
    private lateinit var teamTwoImageButton: ImageButton
    private lateinit var teamOnePhotoFile: File
    private lateinit var teamTwoPhotoFile: File
    private lateinit var teamOnePhotoUri: Uri
    private lateinit var teamTwoPhotoUri: Uri
    private lateinit var teamOneSoundButton: ImageButton
    private lateinit var teamTwoSoundButton: ImageButton

    private lateinit var teamOnePointsTextView: TextView
    private lateinit var teamTwoPointsTextView: TextView
    private lateinit var teamOneTitle: EditText
    private lateinit var teamTwoTitle: EditText

    private val basketballViewModel: BasketballViewModel by lazy {
        ViewModelProvider(this).get(BasketballViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val id = arguments?.getSerializable(ARGS_UUID) as UUID
        basketballViewModel.loadGameById(id)
        //If we want to load a bunch of fresh games
//        basketballViewModel.setupRepo(150)
    }
//    private val assets: AssetManager



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_game_detail, container, false)
        teamButtonInit(view)
        return view
    }

    /**Just in case*/
//    val teamOneInitialPoints = savedInstanceState?.getInt(TEAM_ONE_INDEX, 0) ?: 0
//    val teamTwoInitialPoints = savedInstanceState?.getInt(TEAM_TWO_INDEX, 0) ?: 0
//    val teamANameInitial = savedInstanceState?.getString(TEAM_A_NAME, "Team A") ?: "Team A"
//    val teamBNameInitial = savedInstanceState?.getString(TEAM_B_NAME, "Team B") ?: "Team B"
//    basketballViewModel.loadDataFromNewInstance(teamOneInitialPoints, teamTwoInitialPoints, teamANameInitial, teamBNameInitial)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        basketballViewModel.gameLiveData.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer { game ->
                game?.let{
                    this.game = game
                    teamOnePhotoFile = basketballViewModel.getTeamOnePhotoFile(game)
                    teamTwoPhotoFile = basketballViewModel.getTeamTwoPhotoFile(game)
                    teamOnePhotoUri = FileProvider.getUriForFile(requireActivity(),
                        "com.tweedchristian.android.basketballscore.fileprovider",
                    teamOnePhotoFile)
                    teamTwoPhotoUri = FileProvider.getUriForFile(requireActivity(),
                        "com.tweedchristian.android.basketballscore.fileprovider",
                        teamTwoPhotoFile)
                    updateTeams()
                }
            }
        )
    }

    override fun onStop() {
        super.onStop()
        basketballViewModel.saveGame(game)
    }

    companion object {
        fun newInstance(id: UUID): GameDetailFragment {
            var args = Bundle()
                .apply {
                    putSerializable(ARGS_UUID, id)
                }
            return GameDetailFragment().apply {
                arguments = args
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_PHOTO_TEAM_ONE){
            requireActivity().revokeUriPermission(teamOnePhotoUri,
                Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            updatePhotoView()
        }
        if(requestCode == REQUEST_PHOTO_TEAM_TWO) {
            requireActivity().revokeUriPermission(
                teamTwoPhotoUri,
                Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            updatePhotoView()
        }
    }

    override fun onStart() {
        super.onStart()
        val teamOneTitleWatcher = object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                game.teamAName = s.toString()
            }
            override fun afterTextChanged(s: Editable?) {
            }

        }
        val teamTwoTitleWatcher = object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                game.teamBName = s.toString()
            }
            override fun afterTextChanged(s: Editable?) {
            }

        }
        teamOneTitle.addTextChangedListener(teamOneTitleWatcher)
        teamTwoTitle.addTextChangedListener(teamTwoTitleWatcher)

        teamOneImageButton.apply {
            val packageManager: PackageManager = requireActivity().packageManager
            val captureImage = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            val resolvedActivity: ResolveInfo? =
                packageManager.resolveActivity(captureImage,
                    PackageManager.MATCH_DEFAULT_ONLY
                )
            if(resolvedActivity == null){
                isEnabled = false
            }
            setOnClickListener{
                captureImage.putExtra(MediaStore.EXTRA_OUTPUT, teamOnePhotoUri)
                val cameraActivities: List<ResolveInfo> =
                    packageManager.queryIntentActivities(captureImage,
                        PackageManager.MATCH_DEFAULT_ONLY
                    )
                for(cameraActivity in cameraActivities){
                    requireActivity().grantUriPermission(
                        cameraActivity.activityInfo.packageName,
                        teamOnePhotoUri,
                        Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                    )
                }
                startActivityForResult(captureImage, REQUEST_PHOTO_TEAM_ONE)
            }
        }

        teamTwoImageButton.apply {
            val packageManager: PackageManager = requireActivity().packageManager
            val captureImage = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            val resolvedActivity: ResolveInfo? =
                packageManager.resolveActivity(captureImage,
                    PackageManager.MATCH_DEFAULT_ONLY
                )
            if(resolvedActivity == null){
                isEnabled = false
            }
            setOnClickListener{
                captureImage.putExtra(MediaStore.EXTRA_OUTPUT, teamTwoPhotoUri)
                val cameraActivities: List<ResolveInfo> =
                    packageManager.queryIntentActivities(captureImage,
                        PackageManager.MATCH_DEFAULT_ONLY
                    )
                for(cameraActivity in cameraActivities){
                    requireActivity().grantUriPermission(
                        cameraActivity.activityInfo.packageName,
                        teamTwoPhotoUri,
                        Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                    )
                }
                startActivityForResult(captureImage, REQUEST_PHOTO_TEAM_TWO)
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
        requireActivity().revokeUriPermission(teamOnePhotoUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        requireActivity().revokeUriPermission(teamTwoPhotoUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
    }

    private fun updatePhotoView(){
        var exif: ExifInterface? = null
        if(teamOnePhotoFile.exists()){
            val bitmap = getScaledBitmap(teamOnePhotoFile.path, requireActivity())
            try {
                exif = ExifInterface(teamOnePhotoFile.path)
                val orientation: Int = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED)
                if(orientation == ExifInterface.ORIENTATION_ROTATE_90) {
                    try {
                        val matrix: Matrix = Matrix()
                        matrix.setRotate(90F)
                        val correctBitmap: Bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
                        teamOneImageView.setImageBitmap(correctBitmap)
                        bitmap.recycle()
                    }
                    catch(e: OutOfMemoryError) {
                        e.printStackTrace()
                    }
                }
                else {
                    teamOneImageView.setImageBitmap(bitmap)
                }
            }
            catch (e: IOException){
                Log.e(TAG, "IO EXCEPTION")
                e.printStackTrace()
            }
        }
        else {
            teamOneImageButton.setImageDrawable(null)
        }

        if(teamTwoPhotoFile.exists()){
            val bitmap = getScaledBitmap(teamTwoPhotoFile.path, requireActivity())
            try {
                exif = ExifInterface(teamTwoPhotoFile.path)
                val orientation: Int = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED)
                if(orientation == ExifInterface.ORIENTATION_ROTATE_90) {
                    try {
                        val matrix: Matrix = Matrix()
                        matrix.setRotate(90F)
                        val correctBitmap: Bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
                        teamTwoImageView.setImageBitmap(correctBitmap)
                        bitmap.recycle()
                    }
                    catch(e: OutOfMemoryError) {
                        e.printStackTrace()
                    }
                }
                else {
                    teamTwoImageView.setImageBitmap(bitmap)
                }
            }
            catch (e: IOException){
                Log.e(TAG, "IO EXCEPTION")
                e.printStackTrace()
            }
        }
        else {
            teamTwoImageView.setImageDrawable(null)
        }
    }

    /**
     * A function to update the displayed text to what is stored in the view model
     */
    private fun updateTeams() {
        teamOnePointsTextView.text = game.teamAScore.toString()
        teamTwoPointsTextView.text = game.teamBScore.toString()
        teamOneTitle.setText(game.teamAName)
        teamTwoTitle.setText(game.teamBName)
        updatePhotoView()
    }

    /**
     * A function that finds all the buttons and text views and set up callbacks
     */
    private fun teamButtonInit(view: View) {
        //Team One Init
        teamOne3ShotButton = view.findViewById(R.id.teamOne3Points)
        teamOne2ShotButton = view.findViewById(R.id.teamOne2Points)
        teamOneFreeThrowButton = view.findViewById(R.id.teamOneFreeThrow)
        teamOnePointsTextView = view.findViewById(R.id.teamOnePoints)
        teamOneTitle = view.findViewById(R.id.teamOneName)
        teamOneTitle.clearFocus()
        teamOneImageButton = view.findViewById(R.id.teamOneImageButton)
        teamOneImageView = view.findViewById(R.id.teamOneImage)
        teamOneSoundButton = view.findViewById(R.id.teamOneSoundButton)

        //Team Two Init
        teamTwo3ShotButton = view.findViewById(R.id.teamTwo3Points)
        teamTwo2ShotButton = view.findViewById(R.id.teamTwo2Points)
        teamTwoFreeThrowButton = view.findViewById(R.id.teamTwoFreeThrow)
        teamTwoPointsTextView = view.findViewById(R.id.teamTwoPoints)
        teamTwoTitle = view.findViewById(R.id.teamTwoName)
        teamTwoTitle.clearFocus()
        teamTwoImageButton = view.findViewById(R.id.teamTwoImageButton)
        teamTwoImageView = view.findViewById(R.id.teamTwoImage)
        teamTwoSoundButton = view.findViewById(R.id.teamTwoSoundButton)

//        Utility Buttons
        resetButton = view.findViewById(R.id.resetButton)
        saveButton = view.findViewById(R.id.saveButton)
        displayButton = view.findViewById(R.id.displayButton)

//        Setting Callbacks

        teamOne3ShotButton.setOnClickListener {
            game.teamAScore += 3
            updateTeams()
        }

        teamOne2ShotButton.setOnClickListener {
            game.teamAScore += 2
            updateTeams()
        }

        teamOneFreeThrowButton.setOnClickListener {
            game.teamAScore += 1
            updateTeams()
        }

        teamTwo3ShotButton.setOnClickListener {
            game.teamBScore += 3
            updateTeams()
        }

        teamTwo2ShotButton.setOnClickListener {
            game.teamBScore += 2
            updateTeams()
        }

        teamTwoFreeThrowButton.setOnClickListener {
            game.teamBScore += 1
            updateTeams()
        }

        saveButton.setOnClickListener {
            basketballViewModel.saveGame(game)
//            Toast.makeText(
//                this,
//                R.string.saved,
//                Toast.LENGTH_SHORT
//            ).show()
        }

        resetButton.setOnClickListener {
            game.teamAScore = 0
            game.teamBScore = 0
            updateTeams()
        }

        displayButton.setOnClickListener {
            callbacks?.loadWinningList(game.winningTeam)
        }

        teamOneSoundButton.setOnClickListener {
            basketballViewModel.playSound(true)
        }

        teamTwoSoundButton.setOnClickListener {
            basketballViewModel.playSound(false)
        }

//        Hide Keyboard and Clear Focus on Click-away
        view.setOnClickListener {
            val imm = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

            if(teamOneTitle.hasFocus()) {
                imm.hideSoftInputFromWindow(teamOneTitle.windowToken, 0)
            }

            if(teamTwoTitle.hasFocus()) {
                imm.hideSoftInputFromWindow(teamTwoTitle.windowToken, 0)
            }

            teamOneTitle.clearFocus()
            teamTwoTitle.clearFocus()
        }
    }
}