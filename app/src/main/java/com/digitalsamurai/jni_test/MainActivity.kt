package com.digitalsamurai.jni_test

import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.digitalsamurai.jni_test.databinding.ActivityMainBinding
import com.digitalsamurai.monochrome.JavaMonochromeConverter
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var currentImageUriFlow = MutableStateFlow<Uri?>(null)

    private var jobConverting: Job? = null

    private val mediaSelector =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {

                binding.imageSelected.setImageURI(uri)
                currentImageUriFlow.tryEmit(uri)


                binding.textSelectedPhotoFileInfo.text = buildInfo(uri)

            } else {
                Snackbar.make(binding.root, "INVALID URI", Snackbar.LENGTH_LONG).show()
            }
        }

    private val javaMonochromeConverter = JavaMonochromeConverter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Example of a call to a native method

        binding.buttonSelectContent.setOnClickListener {
            mediaSelector.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
        binding.buttonDecodeViaJava.setOnClickListener {
            val imageUri = currentImageUriFlow.value
            if (imageUri != null) {
                jobConverting?.cancel()
                jobConverting = null
                jobConverting = lifecycleScope.launch(Dispatchers.Default) {
                    var isFirstCollecting = false
                    javaMonochromeConverter.makeImageMonochrome(applicationContext, imageUri).collect { progress ->
                        // Да, я специально накодил жесткий костыль вместо человеческого стейта ровно для того, чтобы посмотреть как паттерн через errorReason входит в идею разработку на котлине
                        // Ответ: никак, это неудобно и непрактично, описание стейта в sealed interface гораздо проще в данной семантике разработки, так что не надо писать костыли!
                        // из плюсов такого подхода -- мы избавляемся от смарткастов и всего прочего, но я уверен, что sealed interface хорошо оптимизирован для смарткастов, поэтому этот плюс тоже бесполезен

                        if (progress.errorReason != null) {
                            binding.textJdkTimeConverting.text = "ERROR REASON: ${progress.errorReason}"
                            cancel()
                            return@collect
                        }


                        withContext(Dispatchers.Main) {

                        binding.imageSelected.setImageBitmap(progress.bitmap!!)
                        binding.imageSelected.invalidate()
                        }

                        if (progress.isFinished) {
                            binding.textJdkTimeConverting.text = "FINISHED"
                        }
                    }
                }
            }
        }

        lifecycleScope.launch {
            currentImageUriFlow.collect { uri ->
                binding.buttonDecodeViaJava.isEnabled = uri != null
            }
        }
    }

    private fun buildInfo(uri: Uri): String {
        val mimeType = contentResolver.getType(uri)
        val cursor = contentResolver.query(uri, null, null, null, null)!!

        cursor.moveToFirst()

        val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        val name = cursor.getString(nameIndex)

        val sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE)
        val size = cursor.getLong(sizeIndex)

        cursor.close()

        return "Name: ${name}\n" +
                "Size: ${size}\n" +
                "MimeType: ${mimeType}\n" +
                "uri: ${uri}"

        // конечно же это не работает, потому что uri файла ссылается на провайдер "content://"
        // вместо этого разработчики гугла предоставили нам ОЧЕНЬ удобную апиху чтения контента))))))))))
//        return "Name: ${file.name} \n" +
//                "Size: ${file.totalSpace}" +
//                "Absolute: ${file.absolutePath}" +
//                "Path: ${file.path}" +
//                "Free space: ${file.freeSpace}"
    }


    /**
     * A native method that is implemented by the 'jni_test' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String

    external fun obama(input: String): String

    companion object {
        // Used to load the 'jni_test' library on application startup.
        init {
            System.loadLibrary("jni_test")
        }
    }
}