package com.app.dogedex.main

import android.content.Intent
import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageFormat
import android.graphics.Rect
import android.graphics.YuvImage
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.app.dogedex.R
import com.app.dogedex.api.ApiResponseStatus
import com.app.dogedex.api.ApiServiceInterceptor
import com.app.dogedex.auth.LoginActivity
import com.app.dogedex.databinding.ActivityMainBinding
import com.app.dogedex.dogdetail.DogDetailActivity
import com.app.dogedex.dogdetail.DogDetailActivity.Companion.IS_RECOGNITION_KEY
import com.app.dogedex.doglist.DogListActivity
import com.app.dogedex.machinelearning.Classifier
import com.app.dogedex.machinelearning.DogRecognition
import com.app.dogedex.model.Dog
import com.app.dogedex.model.User
import com.app.dogedex.settings.SettingsActivity
import com.app.dogedex.utils.LABEL_PATH
import com.app.dogedex.utils.MODEL_PATH
import org.tensorflow.lite.support.common.FileUtil
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var imageCapture: ImageCapture
    private lateinit var cameraExecutor: ExecutorService
    private var isCameraReady = false
    private lateinit var classifier: Classifier
    private val viewModel: MainViewModel by viewModels()

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                // Permission is granted. Continue the action or workflow in your
                // app. open camera
                setupCamera()
            } else {
                //Permission is not granted
                Toast.makeText(
                    this,
                    getString(R.string.you_need_to_accept_camera_permission_to_use_this_app),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val user = User.getLoggedInUser(this)
        if (user == null) {
            openLoginActivity()
            return
        } else {
            ApiServiceInterceptor.setSessionToken(user.authenticationToken)
        }

        binding.settingsFab.setOnClickListener {
            openSettingsActivity()

        }

        binding.dogListFab.setOnClickListener {
            openDogListActivity()
        }


        viewModel.status.observe(this){
            status ->

            when(status){
                is ApiResponseStatus.Error -> {
                    binding.loadingWheel.visibility = View.GONE
                    Toast.makeText(this, status.messageId, Toast.LENGTH_SHORT).show()
                }
                is ApiResponseStatus.Loading -> {binding.loadingWheel.visibility = View.VISIBLE}
                is ApiResponseStatus.Success -> {binding.loadingWheel.visibility = View.GONE}

            }
        }

        viewModel.dogList.observe(this){
            dog ->
            if (dog != null){
                openDogDetailActivity(dog)
            }
        }


        requestCameraPermission()

    }

    private fun openDogDetailActivity(dog: Dog) {
        val intent = Intent(this, DogDetailActivity::class.java)
        intent.putExtra(DogDetailActivity.DOG_KEY, dog)
        intent.putExtra(IS_RECOGNITION_KEY, true)
        startActivity(intent)
    }

    override fun onStart() {
        super.onStart()
        classifier = Classifier(
            FileUtil.loadMappedFile(
                this@MainActivity, MODEL_PATH),
            FileUtil.loadLabels(
                this@MainActivity, LABEL_PATH)
        )
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            //use to bind the lifecycle of cameras to gthe lifecycle owner
            val cameraProvider = cameraProviderFuture.get()
            // preview
            val preview = Preview.Builder().build()
            preview.setSurfaceProvider(binding.cameraPreview.surfaceProvider)

            //select camera back as default
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            val imageAnalysis = ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()
            imageAnalysis.setAnalyzer(cameraExecutor){ imageProxy ->

                val bitmap = converImageProxyToBitmap(imageProxy)
                if (bitmap != null){
                    val dogRecognition = classifier.recognizeImage(bitmap).first()
                    enableTakePhotoButton(dogRecognition)
                }

                imageProxy.close()
            }

            //bind uses cases to camera
            cameraProvider.bindToLifecycle(
                this,
                cameraSelector,
                preview,
                imageCapture,
                imageAnalysis
            )
        }, ContextCompat.getMainExecutor(this))

    }

    private fun enableTakePhotoButton(dogRecognition: DogRecognition) {
        if (dogRecognition.confidence > 70.0){
            binding.takePhotoFab.alpha = 1f
            binding.takePhotoFab.setOnClickListener {
                viewModel.getDogByMlId(dogRecognition.id)
            }
        }else{
            binding.takePhotoFab.alpha = 0.2f
            binding.takePhotoFab.setOnClickListener(null)
        }

    }


//    private fun takePhoto() {
//        val outputFileOptions = ImageCapture.OutputFileOptions.Builder(getOutputPhotoFile()).build()
//        imageCapture.takePicture(outputFileOptions, cameraExecutor,
//            object : ImageCapture.OnImageSavedCallback {
//                override fun onError(error: ImageCaptureException) {
//                    Toast.makeText(
//                        this@MainActivity,
//                        getString(R.string.the_photo_has_not_been_taken)+error,
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//
//                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
//                    // insert your code here.
//
//
//                }
//            })
//    }


//    private fun getOutputPhotoFile(): File{
//        val mediaDir = externalMediaDirs.firstOrNull()?.let {
//            File(it, resources.getString(R.string.app_name) + ".jpg").apply { mkdirs() }
//        }
//        return if (mediaDir != null && mediaDir.exists()){
//            mediaDir
//        } else{
//            filesDir
//        }
//    }

    override fun onDestroy() {
        super.onDestroy()
        if (::cameraExecutor.isInitialized) {
            cameraExecutor.shutdown()
        }

    }

    private fun setupCamera() {
        binding.cameraPreview.post{
            imageCapture = ImageCapture.Builder()
                .setTargetRotation(binding.cameraPreview.display.rotation)
                .build()
            cameraExecutor = Executors.newSingleThreadExecutor()
            startCamera()
            isCameraReady = true
        }
    }

    private fun requestCameraPermission() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                // You can use the API that requires the permission.
                setupCamera()
            }

            shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) -> {
                AlertDialog.Builder(this)
                    .setTitle(getString(R.string.accept_permission))
                    .setMessage(getString(R.string.it_need_permission_granted_to_use_this_app))
                    .setPositiveButton(R.string.ok) { _, _ ->
                        requestPermissionLauncher.launch(
                            Manifest.permission.CAMERA
                        )
                    }
                    .setNegativeButton(android.R.string.cancel) { _, _ ->
                    }
                    .show()
            }

            else -> {
                // You can directly ask for the permission.
                // The registered ActivityResultCallback gets the result of this request.
                requestPermissionLauncher.launch(
                    Manifest.permission.CAMERA
                )
            }
        }
    }

    private fun openDogListActivity() {
        startActivity(Intent(this, DogListActivity::class.java))
    }

    private fun openSettingsActivity() {
        startActivity(Intent(this, SettingsActivity::class.java))
    }

    private fun openLoginActivity() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    @SuppressLint("UnsafeOptInUsageError")
    private fun converImageProxyToBitmap(imageProxy: ImageProxy): Bitmap? {
        val image = imageProxy.image ?: return null

        val yBuffer = image.planes[0].buffer // Y plane
        val uBuffer = image.planes[1].buffer // U plane (chrominance)
        val vBuffer = image.planes[2].buffer // V plane (chrominance)

        val ySize = yBuffer.remaining()
        val uSize = uBuffer.remaining()
        val vSize = vBuffer.remaining()

        val nv21 = ByteArray(ySize + uSize + vSize)

        //U and V are swapped
        yBuffer.get(nv21, 0, ySize)
        vBuffer.get(nv21, ySize, vSize)
        uBuffer.get(nv21, ySize + vSize, uSize)

        val yuvImage = YuvImage(nv21, ImageFormat.NV21, image.width, image.height, null)
        val out = ByteArrayOutputStream()

        yuvImage.compressToJpeg(
            Rect(0,0,yuvImage.width, yuvImage.height),100,
            out
        )
        val imageBytes = out.toByteArray()

        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
    }
}